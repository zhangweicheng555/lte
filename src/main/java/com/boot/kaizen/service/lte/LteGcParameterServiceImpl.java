package com.boot.kaizen.service.lte;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.boot.kaizen.dao.lte.LteGcParameterDao;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.model.lte.LteGcParameter;
import com.boot.kaizen.util.ExcelUtil;
import com.boot.kaizen.util.JsonMsgUtil;

@Service
class LteGcParameterServiceImpl implements ILteGcParameterService {

	@Autowired
	private LteGcParameterDao gcParameterDao;

	@Override
	public List<LteGcParameter> find(Map<String, Object> map) {
		return gcParameterDao.find(map);
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public JsonMsgUtil edit(LteGcParameter lteGcParameter, LoginUser loginUser) {
		if (loginUser == null) {
			throw new DisabledException("用户已过期，重新登陆");
		}
		if (lteGcParameter.getId() != null) {// edit
			lteGcParameter.setUpdateTime(new Date());
			gcParameterDao.update(lteGcParameter);
		} else {// add
			if (checkInfo(loginUser.getProjId(), lteGcParameter)) {
				lteGcParameter.setProjId(loginUser.getProjId());
				lteGcParameter.setCreateAt(loginUser.getId());
				lteGcParameter.setCreateTime(new Date());
				// 保存数据
				gcParameterDao.save(lteGcParameter);
			} else {
				throw new IllegalArgumentException("该项目下已经存在该[站号,小区]的信息");
			}
		}
		return new JsonMsgUtil(true, "添加成功", lteGcParameter);
	}

	/**
	 * 
	 * @Description: 核对信息
	 * @author weichengz
	 * @date 2019年2月2日 下午10:42:30
	 */
	private Boolean checkInfo(Long projId, LteGcParameter lteGcParameter) {
		Boolean flag = true;
		String mENodeBID = lteGcParameter.getmENodeBID();
		String mCellID = lteGcParameter.getmCellID();
		String testDate = lteGcParameter.getConfigName();
		if (StringUtils.isBlank(mENodeBID) || StringUtils.isBlank(mCellID) || projId == null
				|| StringUtils.isBlank(testDate)) {
			throw new IllegalArgumentException("[站号，小区ID，项目ID，测试时间]不能为空");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("mCellID", mCellID);
		map.put("mENodeBID", mENodeBID);
		map.put("projId", projId);
		map.put("testDate", testDate);
		List<LteGcParameter> lteGcParameters = find(map);
		if (lteGcParameters != null && lteGcParameters.size() > 0) {
			flag = false;
		}
		return flag;
	}

	@Override
	public JsonMsgUtil findById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("未传入数据id");
		}
		LteGcParameter lteGcParameter = gcParameterDao.findById(id);
		if (lteGcParameter == null) {
			throw new IllegalArgumentException("查询的数据不存在");
		}
		return new JsonMsgUtil(true, "查询成功", lteGcParameter);
	}

	@Transactional
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
				Integer deleteNum = gcParameterDao.delete(array);
				j = new JsonMsgUtil(true, "删除操作成功", deleteNum);
			}
		} catch (Exception e) {
			throw e;
		}
		return j;
	}

	@Override
	public List<LteGcParameter> queryGcParameterList(String mENodeBID, String testDate) {
		return gcParameterDao.queryGcParameterList(mENodeBID, testDate);
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
			if (sheet1.getRow(0).getLastCellNum() != 13) {
				msg = new JsonMsgUtil(false, "导入excel的列数要求为13列", null);
				return msg;
			}
			List<LteGcParameter> list = new ArrayList<LteGcParameter>();
			LteGcParameter lteGcParameter = null;
			for (int j = 1; j <= sheet1.getLastRowNum(); j++) {
				row = sheet1.getRow(j);
				if (row == null) {
					continue;
				}
				// 导入excel总行数记录
				int cellIndex = 0;
				lteGcParameter = new LteGcParameter();
				// 基站号
				String mENodeBID = cell_string(row.getCell(cellIndex++));
				if (StringUtils.isNoneBlank(mENodeBID)) {
					lteGcParameter.setmENodeBID(mENodeBID);
				} else {
					msg = new JsonMsgUtil(false, "站号不能为空", null);
					return msg;
				}
				// 基站名称
				String mBaseStationName = cell_string(row.getCell(cellIndex++));
				if (StringUtils.isNoneBlank(mBaseStationName)) {
					lteGcParameter.setmBaseStationName(mBaseStationName);
				} else {
					msg = new JsonMsgUtil(false, "站名不能为空", null);
					return msg;
				}
				// 小区号
				String mCellID = cell_string(row.getCell(cellIndex++));
				if (StringUtils.isNoneBlank(mCellID)) {
					lteGcParameter.setmCellID(mCellID);
				} else {
					msg = new JsonMsgUtil(false, "小区号不能为空", null);
					return msg;
				}

				String mCellName = cell_string(row.getCell(cellIndex++));
				lteGcParameter.setmCellName(mCellName);
				String mFrequency = cell_string(row.getCell(cellIndex++));
				lteGcParameter.setmFrequency(mFrequency);
				String mPCI = cell_string(row.getCell(cellIndex++));
				lteGcParameter.setmPCI(mPCI);
				String mRsPower = cell_string(row.getCell(cellIndex++));
				lteGcParameter.setmRsPower(mRsPower);
				String mAntennaHangUp = cell_string(row.getCell(cellIndex++));
				lteGcParameter.setmAntennaHangUp(mAntennaHangUp);
				String mAzimuth = cell_string(row.getCell(cellIndex++));
				lteGcParameter.setmAzimuth(mAzimuth);
				String mMechanicalLowerInclination = cell_string(row.getCell(cellIndex++));
				lteGcParameter.setmMechanicalLowerInclination(mMechanicalLowerInclination);
				String mPresetElectricDip = cell_string(row.getCell(cellIndex++));
				lteGcParameter.setmPresetElectricDip(mPresetElectricDip);
				String mtotalLowerInclination = cell_string(row.getCell(cellIndex++));
				lteGcParameter.setMtotalLowerInclination(mtotalLowerInclination);
				// 小区号
				String testDate = cell_string(row.getCell(cellIndex++));
				if (StringUtils.isNoneBlank(testDate)) {
					try {
						Integer.valueOf(testDate);// 验证日期是不是正确
						lteGcParameter.setConfigName(ExcelUtil.dealDateToString(testDate));
					} catch (Exception e) {
						msg = new JsonMsgUtil(false, "测试日期格式不正确", null);
						return msg;
					}
				} else {
					msg = new JsonMsgUtil(false, "测试时间不能为空", null);
					return msg;
				}
				// 添加进list里面
				list.add(lteGcParameter);
			}

			for (LteGcParameter nobPlan2 : list) {
				edit(nobPlan2, loginUser);
			}
			msg = new JsonMsgUtil(true, "上传成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new JsonMsgUtil(false, e.getMessage(), null);
		}
		return msg;
	}

	public String cell_string(HSSFCell cell) {
		if (cell == null) {
			return "";
		}
		cell.setCellType(Cell.CELL_TYPE_STRING);
		return cell.getStringCellValue();
	}

	public String dealDateToString(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.YEAR, 1900);
		cal1.set(Calendar.DAY_OF_MONTH, 1);
		cal1.set(Calendar.DAY_OF_YEAR, 1);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);

		cal1.add(Calendar.DAY_OF_YEAR, Integer.parseInt(strDate) - 2);
		Date date = cal1.getTime();
		return sdf.format(date);
	}

	@Override
	public int insertSelective(LteGcParameter record) {
		return gcParameterDao.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(LteGcParameter record) {
		return gcParameterDao.updateByPrimaryKeySelective(record);
	}

}
