package com.boot.kaizen.controller.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.boot.kaizen.controller.lte.model.BaseStationBean;
import com.boot.kaizen.controller.lte.model.CommunityBean;
import com.boot.kaizen.controller.lte.model.MCommunityNetworkOptimizationBean;
import com.boot.kaizen.controller.lte.model.MCommunityProjectBean;
import com.boot.kaizen.model.SysUser;
import com.boot.kaizen.model.lte.LteCellCheck;
import com.boot.kaizen.model.lte.LteCellParamters;
import com.boot.kaizen.model.lte.LteCellStructuralValidation;
import com.boot.kaizen.model.lte.LteConfig;
import com.boot.kaizen.model.lte.LteGcParameter;
import com.boot.kaizen.model.lte.LteLoadTest;
import com.boot.kaizen.model.lte.LteStationCheck;
import com.boot.kaizen.service.SysProjectService;
import com.boot.kaizen.service.UserService;
import com.boot.kaizen.service.lte.ILteCellCheckService;
import com.boot.kaizen.service.lte.ILteCellParamtersService;
import com.boot.kaizen.service.lte.ILteCellStructuralValidationService;
import com.boot.kaizen.service.lte.ILteConfigService;
import com.boot.kaizen.service.lte.ILteGcParameterService;
import com.boot.kaizen.service.lte.ILteLoadTestService;
import com.boot.kaizen.service.lte.ILtePlanService;
import com.boot.kaizen.service.lte.ILteStationCheckService;
import com.boot.kaizen.util.AppUtil;
import com.boot.kaizen.util.FileUtil;

/**
 * LTE app对接模块
 * 
 * @author a-zhangweicheng
 *
 */
@RestController
@RequestMapping("/lte/app")
public class LteAppController {

	@Value("${files.path}")
	private String filesPath;

	@Autowired
	private SysProjectService projectService;
	@Autowired
	private UserService userService;
	@Autowired
	private ILtePlanService ltePlanService;
	@Autowired
	private ILteGcParameterService lteGcParameterService;
	@Autowired
	private ILteStationCheckService lteStationCheckService;
	@Autowired
	private ILteCellCheckService lteCellCheckService;
	@Autowired
	private ILteConfigService lteConfigService;
	@Autowired
	private ILteLoadTestService lteLoadTestService;
	@Autowired
	private ILteCellParamtersService lteCellParamtersService;
	@Autowired
	private ILteCellStructuralValidationService lteCellStructuralValidationService;
	// 0:失败;1:成功; 2:服务器异常

	/**
	 * 根据用户名密码获取 token username password login
	 */

	/**
	 * 根据登录的用户查询拥有的项目
	 * 
	 * @param username
	 */
	@RequestMapping(value = "/queryProjectList", method = RequestMethod.POST)
	public AppUtil queryProjectList(@RequestParam(value = "name", required = true) String name) {
		AppUtil appUtil = new AppUtil(1, "查询成功", "");
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			List<Map<String, Object>> queryProjects = projectService.queryProjects(name);
			if (queryProjects == null || queryProjects.size() == 0) {
				appUtil = new AppUtil(0, "查询项目列表为空", "");
				return appUtil;
			}

			for (Map<String, Object> map : queryProjects) {
				Map<String, Object> model = new HashMap<>(3);
				Long projId = Long.valueOf(map.get("id").toString());
				model.put("proName", map.get("projName").toString());
				model.put("projId", projId);
				SysUser user = userService.queryUser(projId, name);
				model.put("userId", user.getId());
				list.add(model);
			}
			appUtil.setDataSource(list);
			return appUtil;
		} catch (Exception e) {
			appUtil = new AppUtil(2, "系统异常：" + e.getMessage(), "");
			return appUtil;
		}
	}

	/**
	 * 查询测试计划任务列表 默认是查询今天的 传日期就查询相应日期的
	 * 
	 * @param username
	 * @param projId
	 */
	@RequestMapping(value = "/queryPlanList", method = RequestMethod.POST)
	public AppUtil queryPlanList(@RequestParam(value = "userId", required = true) Long userId,
			@RequestParam(value = "projId", required = true) Long projId,
			@RequestParam(value = "testDate", required = false) String testDate) {
		AppUtil appUtil = new AppUtil(1, "查询成功", "");
		try {
			List<Map<String, Object>> planList = ltePlanService.queryPlanList(userId, projId, testDate);

			if (planList == null || planList.size() == 0) {
				appUtil = new AppUtil(0, "查询任务列表为空", "");
				return appUtil;
			}
			appUtil.setDataSource(planList);
			return appUtil;
		} catch (Exception e) {
			appUtil = new AppUtil(2, "系统异常：" + e.getMessage(), "");
			return appUtil;
		}
	}

	/**
	 * 拉取基站列表
	 * 
	 * @param userId
	 * @param projId
	 * @param testDate
	 */
	@RequestMapping(value = "/queryStationList", method = RequestMethod.POST)
	public AppUtil queryStationList(@RequestParam(value = "userId", required = true) Long userId,
			@RequestParam(value = "projId", required = true) Long projId,
			@RequestParam(value = "testDate", required = true) String testDate) {
		AppUtil appUtil = new AppUtil(1, "查询成功", "");
		try {
			List<BaseStationBean> stationList = ltePlanService.queryStationList(userId, projId, testDate);
			if (stationList != null && stationList.size() > 0) {
				for (BaseStationBean baseStationBean : stationList) {
					List<LteGcParameter> lteGcParameters = lteGcParameterService
							.queryGcParameterList(baseStationBean.getmENodeBID(), testDate);
					if (lteGcParameters != null && lteGcParameters.size() > 0) {
						for (LteGcParameter lteGcParameter : lteGcParameters) {
							CommunityBean communityBean = new CommunityBean();
							communityBean.setmCommunityBeanName(lteGcParameter.getmCellName());

							MCommunityProjectBean mCommunityProjectBean = new MCommunityProjectBean(
									lteGcParameter.getmCellID(), lteGcParameter.getmFrequency(),
									lteGcParameter.getmPCI());
							communityBean.setmCommunityProject(mCommunityProjectBean);

							MCommunityNetworkOptimizationBean mCommunityNetworkOptimizationBean = new MCommunityNetworkOptimizationBean(
									lteGcParameter.getmRsPower(), lteGcParameter.getmAntennaHangUp(),
									lteGcParameter.getmAzimuth(), lteGcParameter.getmMechanicalLowerInclination(),
									lteGcParameter.getmPresetElectricDip(), lteGcParameter.getMtotalLowerInclination());
							communityBean.setmCommunityNetworkOptimization(mCommunityNetworkOptimizationBean);

							baseStationBean.getmCommunityBeanList().add(communityBean);
						}
					}
				}
			} else {
				appUtil = new AppUtil(0, "查询信息列表为空", "");
				return appUtil;
			}
			appUtil.setDataSource(stationList);
			return appUtil;
		} catch (Exception e) {
			appUtil = new AppUtil(2, "系统异常：" + e.getMessage(), "");
			return appUtil;
		}
	}

	/**
	 * 上传基站核查结果
	 */
	@RequestMapping(value = "/uploadStationCheck", method = RequestMethod.POST)
	public AppUtil uploadStationCheck(LteStationCheck stationCheck) {
		AppUtil appUtil = new AppUtil(1, "操作成功", null);

		if (stationCheck == null) {
			appUtil = new AppUtil(0, "接收基站核查列表为空", "");
			return appUtil;
		}

		if (stationCheck.getProjId() == null || StringUtils.isBlank(stationCheck.getTestDate())
				|| StringUtils.isBlank(stationCheck.geteNodeBID())) {
			appUtil = new AppUtil(0, "项目id/测试时间/基站号不能为空", "");
			return appUtil;
		}

		List<LteStationCheck> stationChecks = new ArrayList<LteStationCheck>();
		stationChecks.add(stationCheck);
		try {
			lteStationCheckService.batchInsert(stationChecks);
			appUtil.setDataSource(null);
			return appUtil;
		} catch (Exception e) {
			appUtil = new AppUtil(2, "系统异常：" + e.getMessage(), "");
			return appUtil;
		}
	}

	/**
	 * 上传小区测试结果
	 */
	@RequestMapping(value = "/uploadCellCheck", method = RequestMethod.POST)
	public AppUtil uploadCellCheck(LteCellCheck cellCheck) {
		AppUtil appUtil = new AppUtil(1, "查询成功", null);
		if (cellCheck == null) {
			appUtil = new AppUtil(0, "接收小区测试列表为空", "");
			return appUtil;
		}
		if (StringUtils.isBlank(cellCheck.geteNodeBID()) || cellCheck.getProjId() == null
				|| StringUtils.isBlank(cellCheck.getCellId()) || StringUtils.isBlank(cellCheck.getTestDate())) {
			appUtil = new AppUtil(0, "基站号/项目ID/小区号/测试时间不能为空", "");
			return appUtil;
		}
		List<LteCellCheck> cellChecks = new ArrayList<>();
		cellChecks.add(cellCheck);
		try {
			// 批量添加
			lteCellCheckService.batchInsert(cellChecks);
			appUtil.setDataSource(null);
			return appUtil;
		} catch (Exception e) {
			appUtil = new AppUtil(2, "系统异常：" + e.getMessage(), "");
			return appUtil;
		}
	}

	/**
	 * 上传小区参数验收接口
	 */
	@RequestMapping(value = "/uploadCellParameters", method = RequestMethod.POST)
	public AppUtil uploadCellParameters(LteCellParamters lteCellParamters) {
		AppUtil appUtil = new AppUtil(1, "上传成功", null);
		try {
			// 批量添加
			lteCellParamtersService.upSert(lteCellParamters);
			appUtil.setDataSource(null);
			return appUtil;
		} catch (Exception e) {
			appUtil = new AppUtil(2, "系统异常：" + e.getMessage(), "");
			return appUtil;
		}
	}

	/**
	 * 上传结构验收接口
	 */
	@RequestMapping(value = "/structuralValidation", method = RequestMethod.POST)
	public AppUtil structuralValidation(LteCellStructuralValidation lteCellStructuralValidation) {
		AppUtil appUtil = new AppUtil(1, "上传成功", null);

		try {
			// 批量添加
			lteCellStructuralValidationService.upSert(lteCellStructuralValidation);
			appUtil.setDataSource(null);
			return appUtil;
		} catch (Exception e) {
			appUtil = new AppUtil(2, "系统异常：" + e.getMessage(), "");
			return appUtil;
		}
	}

	/**
	 * 获取测试项默认配置
	 */
	@RequestMapping(value = "/findTestConfig", method = RequestMethod.POST)
	public AppUtil findTestConfig(@RequestParam("projId") Long projId,
			@RequestParam(value = "userId", required = false) Long userId) {
		AppUtil appUtil = new AppUtil(1, "查询成功", null);
		try {
			if (projId == null) {
				appUtil = new AppUtil(0, "项目projId不能为空", "");
				return appUtil;
			}
			LteConfig lteConfig = lteConfigService.findInfoById(projId);
			if (lteConfig == null) {
				appUtil = new AppUtil(0, "该项目下测试配置项不存在", "");
				return appUtil;
			}
			if (lteConfig.getStatus() != 2) {
				appUtil = new AppUtil(0, "该项目下测试配置项未通过审核", "");
				return appUtil;
			}

			Map<String, Object> mVoiceConfig = new HashMap<>();
			Map<String, Object> mFtpConfig = new HashMap<>();

			mVoiceConfig.put("mVoiceCount", lteConfig.getmVoiceCount());
			mVoiceConfig.put("mVoiceTarget", lteConfig.getmVoiceTarget());

			mFtpConfig.put("mFtpService", lteConfig.getmFtpService());
			mFtpConfig.put("mFtpPort", lteConfig.getmFtpPort());
			mFtpConfig.put("mFtpUserName", lteConfig.getmFtpUserName());
			mFtpConfig.put("mFtpPaw", lteConfig.getmFtpPaw());
			mFtpConfig.put("mFtpFileDownPath", lteConfig.getmFtpFileDownPath());
			mFtpConfig.put("mFtpFileUpPath", lteConfig.getmFtpFileUpPath());
			mFtpConfig.put("mFtpUpRateTarget", lteConfig.getmFtpUpRateTarget());
			mFtpConfig.put("mFtpDownRateTarget", lteConfig.getmFtpDownRateTarget());

			Map<String, Object> finalMap = new HashMap<>(2);
			finalMap.put("mVoiceConfig", mVoiceConfig);
			finalMap.put("mFtpConfig", mFtpConfig);
			appUtil.setDataSource(finalMap);
			return appUtil;
		} catch (Exception e) {
			appUtil = new AppUtil(2, "系统异常：" + e.getMessage(), "");
			return appUtil;
		}
	}

	/**
	 * 上传路测信息
	 */
	@RequestMapping(value = "/uploadRoadTest", method = RequestMethod.POST)
	public AppUtil uploadRoadTest(@RequestParam("projId") Long projId, @RequestParam("userId") Long userId,
			@RequestParam("eNodeBID") String eNodeBID, @RequestParam("testDate") String testDate,

			@RequestParam(value = "cellFiles", required = false) MultipartFile[] cellFiles,

			@RequestParam(value = "sinrThresholdImage", required = false) MultipartFile sinrThresholdImage,
			@RequestParam(value = "rsrpThresholdImage", required = false) MultipartFile rsrpThresholdImage,
			@RequestParam(value = "upFtpRateThresholdImage", required = false) MultipartFile upFtpRateThresholdImage,
			@RequestParam(value = "downFtpRateThresholdImage", required = false) MultipartFile downFtpRateThresholdImage,
			@RequestParam(value = "roadLogFile", required = false) MultipartFile roadLogFile) {
		AppUtil appUtil = new AppUtil(1, "上传成功", null);
		try {
			if (projId == null || StringUtils.isBlank(eNodeBID) || userId == null) {
				appUtil = new AppUtil(0, "项目projId、用户userId、基站号eNodeBID/测试时间testDate不能为空", "");
				return appUtil;
			}

			String sinrThresholdImageName = upFile(sinrThresholdImage, "lte");
			String rsrpThresholdImageName = upFile(rsrpThresholdImage, "lte");
			String upFtpRateThresholdImageName = upFile(upFtpRateThresholdImage, "lte");
			String downFtpRateThresholdImageName = upFile(downFtpRateThresholdImage, "lte");
			String roadLogFileName = upFile(roadLogFile, "lte");

			String rsrpFtpUpImage = "";
			if (cellFiles != null && cellFiles.length > 0) {
				for (int i = 0; i < cellFiles.length; i++) {
					String name = upFile(cellFiles[i], "lte");
					if (StringUtils.isNoneBlank(name)) {
						if (StringUtils.isNoneBlank(rsrpFtpUpImage)) {
							rsrpFtpUpImage = rsrpFtpUpImage + "," + name;
						} else {
							rsrpFtpUpImage = name;
						}
					}
				}
			}

			LteLoadTest loadTest = new LteLoadTest(userId, eNodeBID, testDate, rsrpFtpUpImage, sinrThresholdImageName,
					rsrpThresholdImageName, upFtpRateThresholdImageName, downFtpRateThresholdImageName,
					roadLogFileName);
			loadTest.setProjId(projId);
			lteLoadTestService.save(loadTest);
			appUtil.setDataSource("上传成功");
			return appUtil;
		} catch (Exception e) {
			appUtil = new AppUtil(2, "系统异常：" + e.getMessage(), "");
			return appUtil;
		}
	}

	private String upFile(MultipartFile file, String modelName) {
		return FileUtil.upFile(file, filesPath, modelName);
	}

}