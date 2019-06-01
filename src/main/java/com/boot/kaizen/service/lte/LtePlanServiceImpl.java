package com.boot.kaizen.service.lte;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.boot.kaizen.controller.lte.model.BaseStationBean;
import com.boot.kaizen.dao.lte.LtePlanDao;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.model.SysUser;
import com.boot.kaizen.model.lte.LtePlan;
import com.boot.kaizen.model.lte.LtePlanInfo;
import com.boot.kaizen.service.UserService;
import com.boot.kaizen.util.ExcelUtil;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.MyDateUtil;

@Service
class LtePlanServiceImpl implements ILtePlanService {

	@Autowired
	private LtePlanDao planDao;
	@Autowired
	private ILteConfigService lteConfigService;
	@Autowired
	private ILteCellStructuralValidationService lteCellStructuralValidationService;
	@Autowired
	private ILteCellParamtersService lteCellParamtersService;
	@Autowired
	private ILteStationCheckService lteStationCheckService;
	@Autowired
	private ILteGcParameterService lteGcParameterService;

	@Autowired
	private ILteLoadTestService lteLoadTestService;
	@Autowired
	private ILteCellCheckService lteCellCheckService;

	@Autowired
	private UserService userService;
	@Value("${files.path}")
	private String lteImage;
	@Value("${files.importExcel}")
	private String importExcel;

	@Override
	public List<LtePlan> find(Map<String, Object> map) {
		return planDao.find(map);
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public JsonMsgUtil edit(LtePlan ltePlan, LoginUser loginUser) {
		if (loginUser == null) {
			throw new DisabledException("用户已过期，重新登陆");
		}
		if (ltePlan.getId() != null) {// edit
			ltePlan.setUpdateTime(new Date());
			planDao.updateByPrimaryKeySelective(ltePlan);
		} else {// add
			if (checkInfo(loginUser.getProjId(), ltePlan)) {
				ltePlan.setProjId(loginUser.getProjId());
				ltePlan.setCreateAt(loginUser.getId());
				ltePlan.setCreateTime(new Date());
				planDao.insertSelective(ltePlan);
			} else {
				throw new IllegalArgumentException("该项目下已经存在与【站号、测试时间】相同的的信息");
			}
		}
		return new JsonMsgUtil(true, "添加成功", ltePlan);
	}

	private Boolean checkInfo(Long projId, LtePlan ltePlan) {
		Boolean flag = true;
		String testTime = ltePlan.getTestTime();
		String mENodeBID = ltePlan.getmENodeBID();
		if (projId == null || StringUtils.isBlank(testTime) || StringUtils.isBlank(mENodeBID)) {
			throw new IllegalArgumentException("[基站号，项目ID，测试时间]不能为空");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("mENodeBID", mENodeBID);
		map.put("projId", projId);
		map.put("testTime", testTime);
		List<LtePlan> ltePlans = find(map);
		if (ltePlans != null && ltePlans.size() > 0) {
			flag = false;
		}
		return flag;
	}

	@Override
	public JsonMsgUtil findById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("未传入数据id");
		}
		LtePlan ltePlan = planDao.findById(id);
		if (ltePlan == null) {
			throw new IllegalArgumentException("查询的数据不存在");
		}
		return new JsonMsgUtil(true, "查询成功", ltePlan);
	}

	@Override
	public JsonMsgUtil delete(String ids) {
		JsonMsgUtil j = new JsonMsgUtil(false);
		try {
			if (StringUtils.isNoneBlank(ids)) {
				String[] idsArray = ids.trim().split(",");
				Long[] array = new Long[idsArray.length];
				for (int i = 0; i < idsArray.length; i++) {
					String id = idsArray[i];
					array[i] = Long.valueOf(id.trim());
				}
				// 删除项目
				Integer deleteNum = planDao.delete(array);
				j = new JsonMsgUtil(true, "删除操作成功", deleteNum);
			}
		} catch (Exception e) {
			throw e;
		}
		return j;
	}

	@Override
	public List<Map<String, Object>> queryPlanList(Long userId, Long projId, String testDate) {
		if (StringUtils.isBlank(testDate)) {
			testDate = MyDateUtil.getNowDate("yyyy-MM-dd");
		}
		return planDao.queryPlanList(userId, projId, testDate);
	}

	@Override
	public List<BaseStationBean> queryStationList(Long userId, Long projId, String testDate) {
		return planDao.queryStationList(userId, projId, testDate);
	}

	@Override
	public JsonMsgUtil queryLtePlanInfo(Long id, LoginUser user) {
		JsonMsgUtil j = new JsonMsgUtil(false);
		LtePlanInfo planInfo = planDao.queryLtePlanInfo(id);
		if (planInfo != null) {
			/** 查询测试配置 */
			if (user == null) {
				throw new IllegalArgumentException("登陆超时");
			}
			planInfo.setConfigs(lteConfigService.queryListByProjId(user.getProjId()));

			Map<String, Object> map = new HashMap<>();
			map.put("mENodeBID", planInfo.getmENodeBID());
			map.put("eNodeBID", planInfo.getmENodeBID());
			map.put("projId", planInfo.getProjId());
			map.put("testDate", planInfo.getTestTime());

			planInfo.setGcParameters(lteGcParameterService.find(map));
			planInfo.setStationChecks(lteStationCheckService.find(map));
			planInfo.setLoadTests(lteLoadTestService.findListByMenodeBID(map));
			planInfo.setCellChecks(lteCellCheckService.find(map));

			j = new JsonMsgUtil(true, "查询规划信息成功", planInfo);
		} else {
			j.setMessage("查询的规划信息不存在");
		}
		return j;
	}

	@Override
	public LtePlanInfo queryLtePlanInfoByEnobId(Long id) {
		LtePlanInfo ltePlanInfo = planDao.queryLtePlanInfo(id);
		Map<String, Object> map = new HashMap<>();
		map.put("testDate", ltePlanInfo.getTestTime());
		map.put("projId", ltePlanInfo.getProjId());
		map.put("eNodeBID", ltePlanInfo.getmENodeBID());
		if (ltePlanInfo != null) {
			ltePlanInfo.setLteCellStructuralValidations(lteCellStructuralValidationService.query(map));
			ltePlanInfo.setLteCellParamtersList(lteCellParamtersService.query(map));
		}
		return ltePlanInfo;
	}

	@Override
	public JsonMsgUtil check(Long id, Long statusM) {
		LtePlan ltePlan = planDao.findById(id);
		if (ltePlan == null) {
			throw new IllegalArgumentException("测试计划已被删除");
		}
		planDao.check(id, statusM);
		return new JsonMsgUtil(true, "审核完成", "");
	}

	@Override
	public void exportPlanDoc(String absolutePathModelExcel, LtePlanInfo ltePlanInfo, Map<String, String> map,
			HttpServletResponse response, HttpSession session) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(absolutePathModelExcel)));

			if (ltePlanInfo != null) {

				HSSFSheet sheetPic = workbook.getSheetAt(2);
				HSSFPatriarch patriarch = sheetPic.createDrawingPatriarch();

				String rsrpFtpUpImage = map.get("rsrpFtpUpImage");
				if (StringUtils.isNoneBlank(rsrpFtpUpImage)) {
					// 这个是小区打点集合
					String[] images = rsrpFtpUpImage.split(",");
					for (int i = 0; i < images.length; i++) {
						String image = images[i];
						if (StringUtils.isNoneBlank(image)) {
							createExcelPic(workbook, patriarch, lteImage + image, (short) (i * 6), 4,
									(short) ((i + 1) * 6), 5);
						}
					}
				}
				String sinrThresholdImage = map.get("sinrThresholdImage");
				if (StringUtils.isNoneBlank(sinrThresholdImage)) {
					sinrThresholdImage = lteImage + sinrThresholdImage;
				}
				String rsrpThresholdImage = map.get("rsrpThresholdImage");
				if (StringUtils.isNoneBlank(rsrpThresholdImage)) {
					rsrpThresholdImage = lteImage + rsrpThresholdImage;
				}
				String upFtpRateThresholdImage = map.get("upFtpRateThresholdImage");
				if (StringUtils.isNoneBlank(upFtpRateThresholdImage)) {
					upFtpRateThresholdImage = lteImage + upFtpRateThresholdImage;
				}
				String downFtpRateThresholdImage = map.get("downFtpRateThresholdImage");
				if (StringUtils.isNoneBlank(downFtpRateThresholdImage)) {
					downFtpRateThresholdImage = lteImage + downFtpRateThresholdImage;
				}

				createExcelPic(workbook, patriarch, sinrThresholdImage, (short) 0, 2, (short) 6, 3);
				createExcelPic(workbook, patriarch, rsrpThresholdImage, (short) 6, 2, (short) 13, 3);
				createExcelPic(workbook, patriarch, upFtpRateThresholdImage, (short) 13, 2, (short) 20, 3);
				createExcelPic(workbook, patriarch, downFtpRateThresholdImage, (short) 20, 2, (short) 27, 3);

				HSSFCell cell;
				HSSFRow row;

				row = sheetPic.getRow(1);
				cell = row.getCell(1);
				cell.setCellValue(map.get("communityName1"));
				cell = row.getCell(14);
				cell.setCellValue(map.get("communityName2"));
				cell = row.getCell(27);
				cell.setCellValue(map.get("communityName3"));

				HSSFSheet sheetOne = workbook.getSheetAt(0);
				String mENodeBID = map.get("mENodeBID");
				String mBaseStationName = map.get("mBaseStationName");
				String mBaseStationType = map.get("mBaseStationType");

				row = sheetOne.getRow(0);
				cell = row.getCell(0);
				cell.setCellValue(mBaseStationName + "_" + mBaseStationType + "_" + mENodeBID);

				row = sheetOne.getRow(3);
				cell = row.getCell(1);
				cell.setCellValue(map.get("mBaseStationName"));
				cell = row.getCell(7);
				cell.setCellValue(map.get("mBaseStationType"));

				row = sheetOne.getRow(7);
				cell = row.getCell(2);
				cell.setCellValue(map.get("mLongitude"));
				cell = row.getCell(6);
				cell.setCellValue(map.get("mLongitudes"));
				row = sheetOne.getRow(8);
				cell = row.getCell(2);
				cell.setCellValue(map.get("mLatitude"));
				cell = row.getCell(6);
				cell.setCellValue(map.get("mLatitudes"));
				row = sheetOne.getRow(9);
				cell = row.getCell(2);
				cell.setCellValue(map.get("mTac"));
				cell = row.getCell(6);
				cell.setCellValue(map.get("mTacs"));
				row = sheetOne.getRow(10);
				cell = row.getCell(2);
				cell.setCellValue(map.get("mENodeBID"));
				cell = row.getCell(6);
				cell.setCellValue(map.get("mENodeBIDs"));

				row = sheetOne.getRow(13);
				cell = row.getCell(2);
				cell.setCellValue(map.get("mAntennaHangUp1"));
				cell = row.getCell(3);
				cell.setCellValue(map.get("mAntennaHangUpcs1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("mAntennaHangUp2"));
				cell = row.getCell(6);
				cell.setCellValue(map.get("mAntennaHangUpcs2"));
				cell = row.getCell(8);
				cell.setCellValue(map.get("mAntennaHangUp3"));
				cell = row.getCell(9);
				cell.setCellValue(map.get("mAntennaHangUpcs3"));

				row = sheetOne.getRow(14);
				cell = row.getCell(2);
				cell.setCellValue(map.get("mAzimuth1"));
				cell = row.getCell(3);
				cell.setCellValue(map.get("mAzimuthcs1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("mAzimuth2"));
				cell = row.getCell(6);
				cell.setCellValue(map.get("mAzimuthcs2"));
				cell = row.getCell(8);
				cell.setCellValue(map.get("mAzimuth3"));
				cell = row.getCell(9);
				cell.setCellValue(map.get("mAzimuthcs3"));

				row = sheetOne.getRow(15);
				cell = row.getCell(2);
				cell.setCellValue(map.get("mMechanicalLowerInclination1"));
				cell = row.getCell(3);
				cell.setCellValue(map.get("mMechanicalLowerInclinationcs1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("mMechanicalLowerInclination2"));
				cell = row.getCell(6);
				cell.setCellValue(map.get("mMechanicalLowerInclinationcs2"));
				cell = row.getCell(8);
				cell.setCellValue(map.get("mMechanicalLowerInclination3"));
				cell = row.getCell(9);
				cell.setCellValue(map.get("mMechanicalLowerInclinationcs3"));

				row = sheetOne.getRow(16);
				cell = row.getCell(2);
				cell.setCellValue(map.get("mPresetElectricDip1"));
				cell = row.getCell(3);
				cell.setCellValue(map.get("mPresetElectricDipcs1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("mPresetElectricDip2"));
				cell = row.getCell(6);
				cell.setCellValue(map.get("mPresetElectricDipcs2"));
				cell = row.getCell(8);
				cell.setCellValue(map.get("mPresetElectricDip3"));
				cell = row.getCell(9);
				cell.setCellValue(map.get("mPresetElectricDipcs3"));

				row = sheetOne.getRow(17);
				cell = row.getCell(2);
				cell.setCellValue(map.get("mtotalLowerInclination1"));
				cell = row.getCell(3);
				cell.setCellValue(map.get("mtotalLowerInclinationcs1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("mtotalLowerInclination2"));
				cell = row.getCell(6);
				cell.setCellValue(map.get("mtotalLowerInclinationcs2"));
				cell = row.getCell(8);
				cell.setCellValue(map.get("mtotalLowerInclination3"));
				cell = row.getCell(9);
				cell.setCellValue(map.get("mtotalLowerInclinationcs3"));

				row = sheetOne.getRow(18);
				cell = row.getCell(2);
				cell.setCellValue(map.get("mFrequency1"));
				cell = row.getCell(3);
				cell.setCellValue(map.get("mFrequencycs1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("mFrequency2"));
				cell = row.getCell(6);
				cell.setCellValue(map.get("mFrequencycs2"));
				cell = row.getCell(8);
				cell.setCellValue(map.get("mFrequency3"));
				cell = row.getCell(9);
				cell.setCellValue(map.get("mFrequencycs3"));

				row = sheetOne.getRow(20);
				cell = row.getCell(2);
				cell.setCellValue(map.get("mPCI1"));
				cell = row.getCell(3);
				cell.setCellValue(map.get("mPCIcs1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("mPCI2"));
				cell = row.getCell(6);
				cell.setCellValue(map.get("mPCIcs2"));
				cell = row.getCell(8);
				cell.setCellValue(map.get("mPCI3"));
				cell = row.getCell(9);
				cell.setCellValue(map.get("mPCIcs3"));

				row = sheetOne.getRow(19);
				cell = row.getCell(2);
				cell.setCellValue(map.get("mCellID1"));
				cell = row.getCell(3);
				cell.setCellValue(map.get("mCellIDcs1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("mCellID2"));
				cell = row.getCell(6);
				cell.setCellValue(map.get("mCellIDcs2"));
				cell = row.getCell(8);
				cell.setCellValue(map.get("mCellID3"));
				cell = row.getCell(9);
				cell.setCellValue(map.get("mCellIDcs3"));

				row = sheetOne.getRow(28);
				cell = row.getCell(2);
				cell.setCellValue(map.get("veryClose"));
				row = sheetOne.getRow(29);
				cell = row.getCell(2);
				cell.setCellValue(map.get("vastDistances"));
				row = sheetOne.getRow(30);
				cell = row.getCell(2);
				cell.setCellValue(map.get("ultraHigh"));
				row = sheetOne.getRow(31);
				cell = row.getCell(2);
				cell.setCellValue(map.get("azimuthSuperoverlap"));
				row = sheetOne.getRow(32);
				cell = row.getCell(2);
				cell.setCellValue(map.get("skyBlockCondition"));
				row = sheetOne.getRow(33);
				cell = row.getCell(2);
				cell.setCellValue(map.get("declinationOverlap"));
				row = sheetOne.getRow(34);
				cell = row.getCell(2);
				cell.setCellValue(map.get("canBeAdjusted"));

				row = sheetOne.getRow(41);
				cell = row.getCell(1);
				cell.setCellValue(map.get("testPerson1"));
				cell = row.getCell(3);
				cell.setCellValue(map.get("testPersonPhone1"));
				row = sheetOne.getRow(42);
				cell = row.getCell(1);
				cell.setCellValue(map.get("testPerson2"));
				cell = row.getCell(3);
				cell.setCellValue(map.get("testPersonPhone2"));

				HSSFSheet sheetTwo = workbook.getSheetAt(1);

				row = sheetTwo.getRow(3);
				cell = row.getCell(3);
				cell.setCellValue(map.get("csfbTestCount1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("csfbFallSuccessCount1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("csfbFallFailCount1"));
				cell = row.getCell(6);
				cell.setCellValue(map.get("csfbFallRate1"));
				row = sheetTwo.getRow(5);
				cell = row.getCell(3);
				cell.setCellValue(map.get("goodFtpDownAverageRsrp1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("generalFtpDownAverageRsrp1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("poorFtpDownAverageRsrp1"));
				row = sheetTwo.getRow(6);
				cell = row.getCell(3);
				cell.setCellValue(map.get("goodFtpDownAverageSinr1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("generalFtpDownAverageSinr1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("poorFtpDownAverageSinr1"));
				row = sheetTwo.getRow(7);
				cell = row.getCell(3);
				cell.setCellValue(map.get("goodFtpDownRate1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("generalFtpDownRate1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("poorFtpDownRate1"));
				row = sheetTwo.getRow(8);
				cell = row.getCell(3);
				cell.setCellValue(map.get("goodFtpUpAverageRsrp1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("generalFtpUpAverageRsrp1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("poorFtpUpAverageRsrp1"));
				row = sheetTwo.getRow(9);
				cell = row.getCell(3);
				cell.setCellValue(map.get("goodFtpUpAverageSinr1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("generalFtpUpAverageSinr1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("poorFtpUpAverageSinr1"));
				row = sheetTwo.getRow(10);
				cell = row.getCell(3);
				cell.setCellValue(map.get("goodFtpUpRate1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("generalFtpUpRate1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("poorFtpUpRate1"));

				row = sheetTwo.getRow(13);
				cell = row.getCell(3);
				cell.setCellValue(map.get("csfbTestCount1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("csfbFallSuccessCount1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("csfbFallFailCount1"));
				cell = row.getCell(6);
				cell.setCellValue(map.get("csfbFallRate1"));
				row = sheetTwo.getRow(15);
				cell = row.getCell(3);
				cell.setCellValue(map.get("goodFtpDownAverageRsrp1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("generalFtpDownAverageRsrp1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("poorFtpDownAverageRsrp1"));
				row = sheetTwo.getRow(16);
				cell = row.getCell(3);
				cell.setCellValue(map.get("goodFtpDownAverageSinr1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("generalFtpDownAverageSinr1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("poorFtpDownAverageSinr1"));
				row = sheetTwo.getRow(17);
				cell = row.getCell(3);
				cell.setCellValue(map.get("goodFtpDownRate1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("generalFtpDownRate1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("poorFtpDownRate1"));
				row = sheetTwo.getRow(18);
				cell = row.getCell(3);
				cell.setCellValue(map.get("goodFtpUpAverageRsrp1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("generalFtpUpAverageRsrp1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("poorFtpUpAverageRsrp1"));
				row = sheetTwo.getRow(19);
				cell = row.getCell(3);
				cell.setCellValue(map.get("goodFtpUpAverageSinr1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("generalFtpUpAverageSinr1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("poorFtpUpAverageSinr1"));
				row = sheetTwo.getRow(20);
				cell = row.getCell(3);
				cell.setCellValue(map.get("goodFtpUpRate1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("generalFtpUpRate1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("poorFtpUpRate1"));

				row = sheetTwo.getRow(23);
				cell = row.getCell(3);
				cell.setCellValue(map.get("csfbTestCount1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("csfbFallSuccessCount1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("csfbFallFailCount1"));
				cell = row.getCell(6);
				cell.setCellValue(map.get("csfbFallRate1"));
				row = sheetTwo.getRow(25);
				cell = row.getCell(3);
				cell.setCellValue(map.get("goodFtpDownAverageRsrp1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("generalFtpDownAverageRsrp1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("poorFtpDownAverageRsrp1"));
				row = sheetTwo.getRow(26);
				cell = row.getCell(3);
				cell.setCellValue(map.get("goodFtpDownAverageSinr1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("generalFtpDownAverageSinr1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("poorFtpDownAverageSinr1"));
				row = sheetTwo.getRow(27);
				cell = row.getCell(3);
				cell.setCellValue(map.get("goodFtpDownRate1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("generalFtpDownRate1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("poorFtpDownRate1"));
				row = sheetTwo.getRow(28);
				cell = row.getCell(3);
				cell.setCellValue(map.get("goodFtpUpAverageRsrp1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("generalFtpUpAverageRsrp1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("poorFtpUpAverageRsrp1"));
				row = sheetTwo.getRow(29);
				cell = row.getCell(3);
				cell.setCellValue(map.get("goodFtpUpAverageSinr1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("generalFtpUpAverageSinr1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("poorFtpUpAverageSinr1"));
				row = sheetTwo.getRow(30);
				cell = row.getCell(3);
				cell.setCellValue(map.get("goodFtpUpRate1"));
				cell = row.getCell(4);
				cell.setCellValue(map.get("generalFtpUpRate1"));
				cell = row.getCell(5);
				cell.setCellValue(map.get("poorFtpUpRate1"));

				map.put("title", ltePlanInfo.getmENodeBID() + "_" + ltePlanInfo.getmBaseStationType() + "_"
						+ ltePlanInfo.getmBaseStationName() + "_" + System.currentTimeMillis());
			}

			OutputStream out = response.getOutputStream();
			String fileNameStr = map.get("title");
			response.setContentType("application/x-msdownload");
			response.addHeader("Content-Disposition",
					"attachment;filename=\"" + new String((fileNameStr + ".xls").getBytes("GBK"), "ISO8859-1") + "\"");
			workbook.write(out);

		} catch (IOException io) {

		}
	}

	/**
	 * 导出图片到excel
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2018年11月17日 下午8:10:22
	 */
	private void createExcelPic(HSSFWorkbook workbook, HSSFPatriarch patriarch, String sinrFtpUpImage1, Short leftCol,
			Integer leftRow, Short rughtCol, Integer rightRow) throws IOException {
		if (StringUtils.isNoneBlank(sinrFtpUpImage1)) {
			BufferedImage bufferImg3 = null;
			ByteArrayOutputStream byteArrayOut3 = new ByteArrayOutputStream();
			File file = new File(sinrFtpUpImage1);
			if (file.exists()) {
				bufferImg3 = ImageIO.read(file);
				ImageIO.write(bufferImg3, getPictureType(sinrFtpUpImage1), byteArrayOut3);
				HSSFClientAnchor anchor3 = new HSSFClientAnchor(0, 0, 0, 0, leftCol, leftRow, rughtCol, rightRow);
				if (("png").equals(getPictureType(sinrFtpUpImage1))) {
					patriarch.createPicture(anchor3,
							workbook.addPicture(byteArrayOut3.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
				} else {
					patriarch.createPicture(anchor3,
							workbook.addPicture(byteArrayOut3.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
				}
			}
		}
	}

	/**
	 * 得到图片的类型
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2018年11月17日 下午8:10:31
	 */
	public String getPictureType(String fileName) {
		String proType = fileName.substring(fileName.lastIndexOf(".") + 1);
		return proType;
	}

	@Override
	public JsonMsgUtil queryUserByProjId(Long projId) {
		JsonMsgUtil j = new JsonMsgUtil(false);
		List<Map<String, Object>> listMaps = userService.queryUserByProjId(projId);
		if (listMaps != null && listMaps.size() > 0) {
			j = new JsonMsgUtil(true, "查询成功", listMaps);
		}
		return j;
	}

	@Transactional
	@Override
	public JsonMsgUtil upload(MultipartFile file, LoginUser loginUser) {
		JsonMsgUtil msg = new JsonMsgUtil(false);
		try {
			// 导入
			HSSFWorkbook wbs = new HSSFWorkbook(file.getInputStream());
			HSSFSheet sheet1 = wbs.getSheetAt(0);
			HSSFRow row = null;
			if (sheet1.getRow(0).getLastCellNum() != 15) {
				msg = new JsonMsgUtil(false, "导入excel的列数要求为15列", null);
				return msg;
			}
			List<LtePlan> list = new ArrayList<LtePlan>();
			LtePlan ltePlan = null;
			for (int j = 1; j <= sheet1.getLastRowNum(); j++) {
				row = sheet1.getRow(j);
				if (row == null) {
					continue;
				}
				// 导入excel总行数记录
				int cellIndex = 0;
				ltePlan = new LtePlan();
				// 基站号
				String mENodeBID = ExcelUtil.cell_string(row.getCell(cellIndex++));
				if (StringUtils.isNoneBlank(mENodeBID)) {
					ltePlan.setmENodeBID(mENodeBID);
				} else {
					msg = new JsonMsgUtil(false, "站号不能为空", null);
					return msg;
				}
				// 基站名称
				String mBaseStationName = ExcelUtil.cell_string(row.getCell(cellIndex++));
				if (StringUtils.isNoneBlank(mBaseStationName)) {
					ltePlan.setmBaseStationName(mBaseStationName);
				} else {
					msg = new JsonMsgUtil(false, "站号不能为空", null);
					return msg;
				}
				// 基站类型
				String mBaseStationType = ExcelUtil.cell_string(row.getCell(cellIndex++));
				ltePlan.setmBaseStationType(mBaseStationType);
				// 海拔
				String mAltitude = ExcelUtil.cell_string(row.getCell(cellIndex++));
				ltePlan.setmAltitude(mAltitude);
				// 经度
				String mLongitude = ExcelUtil.cell_string(row.getCell(cellIndex++));
				ltePlan.setmLongitude(mLongitude);
				// 纬度
				String mLatitude = ExcelUtil.cell_string(row.getCell(cellIndex++));
				ltePlan.setmLatitude(mLatitude);
				// TAC
				String mTac = ExcelUtil.cell_string(row.getCell(cellIndex++));
				ltePlan.setmTac(mTac);
				// 测试工程师
				String testPerson = ExcelUtil.cell_string(row.getCell(cellIndex++));
				ltePlan.setTestPerson(testPerson);
				// 测试工程师电话
				String testPersonPhone = ExcelUtil.cell_string(row.getCell(cellIndex++));
				ltePlan.setTestPersonPhone(testPersonPhone);
				// 后台工程师
				String backPerson = ExcelUtil.cell_string(row.getCell(cellIndex++));
				ltePlan.setBackPerson(backPerson);
				// 后台工程师电话
				String backPersonPhone = ExcelUtil.cell_string(row.getCell(cellIndex++));
				ltePlan.setBackPersonPhone(backPersonPhone);

				// 测试时间
				// 日期
				try {
					// 43352 42006
					String testDate = ExcelUtil.cell_string(row.getCell(cellIndex++));
					if (StringUtils.isNoneBlank(testDate)) {
						try {
							Integer.valueOf(testDate);// 验证日期是不是正确
							ltePlan.setTestTime(ExcelUtil.dealDateToString(testDate));
						} catch (Exception e) {
							msg = new JsonMsgUtil(false, "测试日期格式不正确", null);
							return msg;
						}
					} else {
						msg = new JsonMsgUtil(false, "测试日期不能为空", null);
						return msg;
					}
				} catch (Exception e) {
					msg = new JsonMsgUtil(false, "测试日期格式不正确", null);
					return msg;
				}
				// 处理人
				String loginName = ExcelUtil.cell_string(row.getCell(cellIndex++));
				SysUser user = userService.queryUser(loginUser.getProjId(), loginName);
				if (user != null) {
					ltePlan.setDealPersonId(String.valueOf(user.getId()));
				} else {
					msg = new JsonMsgUtil(false, "该项目下不存在此处理人【" + loginName + "】", null);
					return msg;
				}

				// 设备厂家
				String deviceCompany = ExcelUtil.cell_string(row.getCell(cellIndex++));
				ltePlan.setDeviceCompany(deviceCompany);
				// 分公司
				String secondCompany = ExcelUtil.cell_string(row.getCell(cellIndex++));
				ltePlan.setSecondCompany(secondCompany);

				// 添加进list里面
				list.add(ltePlan);
			}

			for (LtePlan nobPlan2 : list) {
				edit(nobPlan2, loginUser);
			}
			msg = new JsonMsgUtil(true, "上传成功", null);
		} catch (Exception e) {
			msg = new JsonMsgUtil(false, e.getMessage(), null);
		}
		return msg;
	}

	@Override
	public int insertSelective(LtePlan record) {
		return planDao.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(LtePlan record) {
		return planDao.updateByPrimaryKeySelective(record);
	}

}
