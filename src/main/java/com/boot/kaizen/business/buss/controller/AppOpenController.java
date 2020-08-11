package com.boot.kaizen.business.buss.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.boot.kaizen.business.buss.model.LogAnaLyze;
import com.boot.kaizen.business.buss.model.OneButtonTestParam;
import com.boot.kaizen.business.buss.model.SimProject;
import com.boot.kaizen.business.buss.model.fiveg.FiveLogMain;
import com.boot.kaizen.business.buss.model.fiveg.FootFtpzbBean;
import com.boot.kaizen.business.buss.model.fiveg.FootHttpzbBean;
import com.boot.kaizen.business.buss.model.fiveg.FootLtewxzbBean;
import com.boot.kaizen.business.buss.model.fiveg.FootNrwxzbBean;
import com.boot.kaizen.business.buss.model.fiveg.FootPingzbBean;
import com.boot.kaizen.business.buss.model.fiveg.FootYyzbBean;
import com.boot.kaizen.business.buss.model.fiveg.LogFoot;
import com.boot.kaizen.business.buss.model.fiveg.model.LteDataInfoBean;
import com.boot.kaizen.business.buss.model.fiveg.model.NrLogBodyBean;
import com.boot.kaizen.business.buss.model.fiveg.model.NrLogHeadBean;
import com.boot.kaizen.business.buss.model.fiveg.model.ProLteDataInfoBean;
import com.boot.kaizen.business.buss.model.fiveg.model.SignalBean;
import com.boot.kaizen.business.buss.service.ILogAnaLyzeService;
import com.boot.kaizen.business.buss.service.IOneButtonTestService;
import com.boot.kaizen.business.buss.service.IOutHomeService;
import com.boot.kaizen.business.buss.service.ISimProjectService;
import com.boot.kaizen.business.es.model.OneButtonTest;
import com.boot.kaizen.business.es.model.OutHomeLogModel;
import com.boot.kaizen.business.es.model.logModel.SignalDataBean;
import com.boot.kaizen.model.Permission;
import com.boot.kaizen.model.SysProject;
import com.boot.kaizen.model.SysProjectRoleRelation;
import com.boot.kaizen.model.SysRole;
import com.boot.kaizen.model.SysRolePermission;
import com.boot.kaizen.model.SysRoleUser;
import com.boot.kaizen.model.SysUser;
import com.boot.kaizen.service.PermissionService;
import com.boot.kaizen.service.ProjectRoleRelationService;
import com.boot.kaizen.service.RoleService;
import com.boot.kaizen.service.SysProjectService;
import com.boot.kaizen.service.SysRolePermissionService;
import com.boot.kaizen.service.SysRoleUserService;
import com.boot.kaizen.service.UserService;
import com.boot.kaizen.util.FileUtil;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.MyDateUtil;
import com.boot.kaizen.util.MyUtil;

/**
 * app上传的开放接口
 * 
 * @author weichengz
 * @date 2020年6月16日 下午2:08:46
 */
@Controller
@RequestMapping("/buss/app")
public class AppOpenController {

	@Autowired
	private IOneButtonTestService oneButtonTestService;
	@Autowired
	private SysProjectService sysProjectService;
	@Autowired
	private IOutHomeService outHomeService;
	@Autowired
	private ILogAnaLyzeService logAnaLyzeService;
	@Autowired
	private SysRolePermissionService sysRolePermissionService;

	private static List<String> CITYS = Arrays.asList("联通", "电信", "移动", "中国联通", "中国电信", "中国移动");

	private static final double DEFAAULT_NUM = -99999;// 统计分析的默认值
	
	@Autowired
	private TransportClient transportClient;

	@Value("${files.path}")
	private String filesCommonPath;

	@Value("${sim.openUpAppFlag}")
	private String openUpAppFlag;

	@Autowired
	private ISimProjectService simProjectService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private SysRoleUserService sysRoleUserService;
	@Autowired
	private ProjectRoleRelationService projectRoleRelationService;
	@Autowired
	private PermissionService permissionService;

	@Value("${commonval.ihandleUser}")
	private String ihandleUser;
	@Value("${commonval.ihandleUserPermission}")
	private String ihandleUserPermission;

	/**
	 * 
	 * @Description: 上传室外测试
	 * @author weichengz
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @date 2020年6月17日 下午2:39:37
	 */
	@ResponseBody
	@PostMapping(value = "/uploadOutHomeLog")
	public JsonMsgUtil uploadOutHomeLog(@RequestParam(value = "file", required = false) MultipartFile[] files)
			throws UnsupportedEncodingException, IOException, InterruptedException, ExecutionException {

		if (files == null || files.length == 0) {
			throw new IllegalArgumentException("未上传室外测试的LOG");
		}

		for (MultipartFile file : files) {

			String user = null;
			String city = null;
			String operators = null;
			SysProject sysProject = null;

			if (file != null && StringUtils.isNoneBlank(file.getOriginalFilename()) && !file.isEmpty()) {

				String outHomeTestId = MyUtil.getUuid();// 室外测试列表的id 后续所有的操作 都会以这个为索引主键
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(file.getInputStream(), "UTF-8"));
				BulkRequest request = new BulkRequest();
				String str = null;// 文件里面一行记录

				int num = 0;// 读取文件的时候 记录游标使用
				NrLogHeadBean nrLogHeadBean = null;
				NrLogBodyBean nrLogBodyBean = null;
				String beginTime = null;
				String endTime = null;
				LogFoot logFoot = null;
				List<LogAnaLyze> logAnaLyzes = new ArrayList<>();// 存储Log分析信息

				String logType = "0";// log的格式 0代表4g的 1代表5g的 20200720
				SignalDataBean signalDataBean = null;// 为了处理最后的几位部分准备的

				while ((str = bufferedReader.readLine()) != null) {

					if (num == 0) {// 20200720
						if (str.contains("mSignaBean") || str.contains("mSignaEventBean") || str.contains("longitude")
								|| str.contains("latitude") || str.contains("doorDataInfoBeans")
								|| str.contains("doorDataInfoViceBeans")) {
							logType = "0";
						} else {
							logType = "1";// 5g
						}
					}

					if (("1").equals(logType)) {
						String id = MyUtil.getUuid();// 每个mainLog的主键
						if (num == 0) {// 第一行的处理方式
							nrLogHeadBean = JSONObject.parseObject(str, NrLogHeadBean.class);
							// 校验头部的信息 不正确直接抛异常
							checkHeaderInfo(nrLogHeadBean);
							num++;

							user = nrLogHeadBean.getUser();
							city = nrLogHeadBean.getCity();
							operators = nrLogHeadBean.getOperator();
							if (StringUtils.isBlank(user)) {
								throw new IllegalArgumentException("Ihandle用户账号为空");
							}
							if (StringUtils.isBlank(city)) {
								throw new IllegalArgumentException("未传入地市名称");
							}
							city = city.trim().replace("市", "");// 后端做地市的市去掉处理

							if (StringUtils.isBlank(operators)) {
								throw new IllegalArgumentException("未传入运营商名称");
							}

							if (operators.contains("电信")) {
								operators = "电信";
							} else if (operators.contains("联通")) {
								operators = "联通";
							} else if (operators.contains("移动")) {
								operators = "移动";
							} else {
								throw new IllegalArgumentException("运营商错误：【联通、电信、移动、中国联通、中国电信、中国移动 其一】");
							}

							// 处理权限开始
							// 根据地市运营商查询SIM项目
							SimProject simProject = null;
							Map<String, Object> paramMap = new HashMap<>();
							paramMap.put("city_name", city);
							paramMap.put("operators", operators);
							List<SimProject> selectByMap = simProjectService.selectByMap(paramMap);
							if (selectByMap != null && selectByMap.size() > 0) {
								simProject = selectByMap.get(0);
							} else {// zzzzzzz
								city = openUpAppFlag;
								paramMap.put("city_name", openUpAppFlag);// 写死临时的目录
								List<SimProject> simProjects = simProjectService.selectByMap(paramMap);
								if (simProjects != null && simProjects.size() > 0) {
									simProject = simProjects.get(0);
								}
							}
							if (simProject != null) {
								// 查询项目的编号
								String project_name = simProject.getProject_name();
								paramMap.clear();
								paramMap.put("projName", project_name);
								List<SysProject> sysProjects = sysProjectService.findByCondition(paramMap);
								if (sysProjects == null || sysProjects.size() == 0) {
									throw new IllegalArgumentException("【" + city + "、" + operators + "】对应的云后台项目不存在");
								}
								sysProject = sysProjects.get(0);
							} else {
								throw new IllegalArgumentException("归档项目匹配失败");
							}
							// 处理权限结束

						} else {
							// 如果最后一行
							if (str.contains("httpzbBean") || str.contains("pingzbBean") || str.contains("ftpzbBean")
									|| str.contains("nrwxzbBean") || str.contains("ltewxzbBean")
									|| str.contains("yyzbBean")) {
								// 存储文件
								String fileStorePath = FileUtil.upFileNew(file, filesCommonPath, "outhomelog");
								logFoot = JSONObject.parseObject(str, LogFoot.class);
								// 处理最后一条记录的 就是 室外测试 列表
								handleOutHomeTest(logFoot, request, beginTime,
										FileUtil.getFilenameByOriginal(file.getOriginalFilename()), fileStorePath,
										sysProject.getId() + "", nrLogHeadBean, endTime, outHomeTestId);
								// 处理统计分析记录
								finalAnalyzeLogData(logAnaLyzes, logFoot, outHomeTestId);
							} else {// 其他行
								nrLogBodyBean = JSONObject.parseObject(str, NrLogBodyBean.class);
								if (nrLogBodyBean != null) {
									// 记录时间
									if (num == 1) {
										beginTime = nrLogBodyBean.dealLongTimeToStr(nrLogBodyBean.getTestTime());
										num++;
									}
									endTime = nrLogBodyBean.dealLongTimeToStr(nrLogBodyBean.getTestTime());
									nrLogBodyBean.setTestTime(endTime);//重新设置时间为 yyyy-MM-dd HH:mm:ss的格式
									
									
									// 先将百度经纬度转为wgs84
									//nrLogBodyBean.dealLngLatBdToWgs84();
									nrLogBodyBean.setPid(outHomeTestId);// 室外测试的主键id
									nrLogBodyBean.setId(id);/** 赋值 注意这个很重要 一定要先赋值 */
									// 信令 大字段信息表得 存储
									handleMsgInfoField(nrLogBodyBean, request);
									// 主表信息转移
									IndexRequest indexRequestMain = new IndexRequest("logmain5g", "logmain5g", id);
									FiveLogMain mainLogModel = new FiveLogMain(nrLogBodyBean, nrLogHeadBean);
									indexRequestMain.source(JSONObject.toJSONString(mainLogModel), XContentType.JSON);
									request.add(indexRequestMain);
									// 添加日志分析信息
									addLogAnalyzeInfo(logAnaLyzes, nrLogBodyBean, nrLogHeadBean,mainLogModel);
								}
							}
						}
					} else {// 4g得处理方式
						String id = MyUtil.getUuid();// 每个mainLog的主键
						signalDataBean = JSONObject.parseObject(str, SignalDataBean.class);
						if (num == 0) {// 第一行的处理方式
							nrLogHeadBean = signalDataBean.toNrLogHeadBean();
							;
							// 校验头部的信息 不正确直接抛异常
							checkHeaderInfo(nrLogHeadBean);
							num++;

							user = nrLogHeadBean.getUser();
							city = nrLogHeadBean.getCity();
							operators = nrLogHeadBean.getOperator();
							if (StringUtils.isBlank(user)) {
								throw new IllegalArgumentException("Ihandle用户账号为空");
							}
							if (StringUtils.isBlank(city)) {
								throw new IllegalArgumentException("未传入地市名称");
							}
							city = city.trim().replace("市", "");// 后端做地市的市去掉处理

							if (StringUtils.isBlank(operators)) {
								throw new IllegalArgumentException("未传入运营商名称");
							}

							if (operators.contains("电信")) {
								operators = "电信";
							} else if (operators.contains("联通")) {
								operators = "联通";
							} else if (operators.contains("移动")) {
								operators = "移动";
							} else {
								throw new IllegalArgumentException("运营商错误：【联通、电信、移动、中国联通、中国电信、中国移动 其一】");
							}

							// 处理权限开始
							// 根据地市运营商查询SIM项目
							SimProject simProject = null;
							Map<String, Object> paramMap = new HashMap<>();
							paramMap.put("city_name", city);
							paramMap.put("operators", operators);
							List<SimProject> selectByMap = simProjectService.selectByMap(paramMap);
							if (selectByMap != null && selectByMap.size() > 0) {
								simProject = selectByMap.get(0);
							} else {// zzzzzzz
								city = openUpAppFlag;
								paramMap.put("city_name", openUpAppFlag);// 写死临时的目录
								List<SimProject> simProjects = simProjectService.selectByMap(paramMap);
								if (simProjects != null && simProjects.size() > 0) {
									simProject = simProjects.get(0);
								}
							}
							if (simProject != null) {
								// 查询项目的编号
								String project_name = simProject.getProject_name();
								paramMap.clear();
								paramMap.put("projName", project_name);
								List<SysProject> sysProjects = sysProjectService.findByCondition(paramMap);
								if (sysProjects == null || sysProjects.size() == 0) {
									throw new IllegalArgumentException("【" + city + "、" + operators + "】对应的云后台项目不存在");
								}
								sysProject = sysProjects.get(0);
							} else {
								throw new IllegalArgumentException("归档项目匹配失败");
							}
							// 处理权限结束

						}
						nrLogBodyBean = signalDataBean.toNrLogBodyBean();
						if (nrLogBodyBean != null) {
							// 记录时间
							if (num == 1) {
								beginTime = nrLogBodyBean.dealLongTimeToStr(nrLogBodyBean.getTestTime());
								num++;
							}
							endTime = nrLogBodyBean.dealLongTimeToStr(nrLogBodyBean.getTestTime());
							// 先将百度经纬度转为wgs84
							//nrLogBodyBean.dealLngLatBdToWgs84();
							nrLogBodyBean.setPid(outHomeTestId);// 室外测试的主键id
							nrLogBodyBean.setTestTime(endTime);
							nrLogBodyBean.setId(id);/** 赋值 注意这个很重要 一定要先赋值 */
							// 信令 大字段信息表得 存储
							handleMsgInfoField(nrLogBodyBean, request);
							// 主表信息转移
							IndexRequest indexRequestMain = new IndexRequest("logmain5g", "logmain5g", id);
							FiveLogMain mainLogModel = new FiveLogMain(nrLogBodyBean, nrLogHeadBean);
							indexRequestMain.source(JSONObject.toJSONString(mainLogModel), XContentType.JSON);
							request.add(indexRequestMain);
							// 添加日志分析信息
							addLogAnalyzeInfo(logAnaLyzes, nrLogBodyBean, nrLogHeadBean,mainLogModel);
						}
					}
				}

				if (("0").equals(logType)) {// 针对4g得log最后处理统计分析模块
					// 存储文件
					String fileStorePath = FileUtil.upFileNew(file, filesCommonPath, "outhomelog");
					if (signalDataBean != null) {
						logFoot = signalDataBean.ToLogFoot();
						// 处理最后一条记录的 就是 室外测试 列表
						handleOutHomeTest(logFoot, request, beginTime,
								FileUtil.getFilenameByOriginal(file.getOriginalFilename()), fileStorePath,
								sysProject.getId() + "", nrLogHeadBean, endTime, outHomeTestId);
						// 处理统计分析记录
						finalAnalyzeLogData(logAnaLyzes, logFoot, outHomeTestId);
					}
				}

				/** 分批的添加进去 */
				transportClient.bulk(request).get();

				// 批量保存logAnaLyzes
				logAnaLyzeService.insertBatch(logAnaLyzes, 500);
				
				if (bufferedReader != null) {
					bufferedReader.close();
				}

				// 上传log结束

				// 在上传没有问题的情况下 查询用户 如果用户不存在就创建默认账号和权限 存在不做处理
				// 查询用户是不是存在
				SysUser sysUser = userService.getUser(user.trim());
				if (sysUser == null) {
					// 创建用户
					sysUser = new SysUser(user, "123456", user);
					userService.saveUser(sysUser);
					// 查看该项目下的默认角色是不是存在
					SysRole sysRole = existsRoleProIn(sysProject);
					// 建立角色和用户的关系
					sysRoleUserService.batchInsert(Arrays.asList(new SysRoleUser(sysUser.getId(), sysRole.getId())));
				} else {// 用户存在的情况下
						// 查询该项目下的用户是不是存在
					SysUser userModel = userService.queryUser(sysProject.getId(), user);
					if (userModel == null) {// 项目下用户不存在
						// 开始
						// 查看该项目下的默认角色是不是存在
						SysRole sysRole = existsRoleProject(sysProject);
						// 建立角色和用户的关系
						sysRoleUserService
								.batchInsert(Arrays.asList(new SysRoleUser(sysUser.getId(), sysRole.getId())));
						// 结束
					} else {// 项目下用户存在
							// 开始
							// 查看该项目下的默认角色是不是存在
						SysRole sysRole = existsRoleInProject(sysProject);
						// 查看用户是不是有默认角色的权限 没有就勾上 有忽略
						List<SysRoleUser> sysRoleUsers = sysRoleUserService.queryRoleUserByRoleUserId(userModel.getId(),
								sysRole.getId());
						if (sysRoleUsers == null || sysRoleUsers.size() == 0) {
							// 建立角色和用户的关系
							sysRoleUserService
									.batchInsert(Arrays.asList(new SysRoleUser(sysUser.getId(), sysRole.getId())));
						}
						// 结束
					}
				}
			}

		}
		return new JsonMsgUtil(true, "添加成功", "");
	}

	private Double formatStringToDouble(String sinr) {
		if (StringUtils.isNotBlank(sinr)) {
			try {
				if (Double.valueOf(sinr) != null) {
					return Double.valueOf(sinr);
				}
			} catch (Exception e) {
				return 0D;
			}
		} else {
			return 0D;
		}
		return 0D;
	}

	/**
	 * 室外测试统计分析部分的统计
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2020年5月21日 下午4:10:55
	 */
	private void addLogAnalyzeInfo(List<LogAnaLyze> logAnaLyzes, NrLogBodyBean nrLogBodyBean,
			NrLogHeadBean nrLogHeadBean, FiveLogMain mainLogModel) {
		LogAnaLyze model = new LogAnaLyze();
		model.setDownLoadSpeed(formatStringToDouble(nrLogBodyBean.getDownLoadSpeed()));
		model.setUpLoadSpeed(formatStringToDouble(nrLogBodyBean.getUpLoadSpeed()));
		model.setPid(nrLogBodyBean.getPid());
		int rootSupport = nrLogHeadBean.getRootSupport();
		model.setCreateTime(new Date());
		model.setSinr(DEFAAULT_NUM);// 先设置为默认的值
		model.setRsrp(DEFAAULT_NUM);// 先设置为默认的值

		model.setSsrsrp(formatStringToDouble(mainLogModel.getSsrsrp()));// 设置ssrsrp sssinr的值
		model.setSssinr(formatStringToDouble(mainLogModel.getSssinr()));

		
		if (0 == rootSupport) {// 非root
			LteDataInfoBean lteDataInfoBean = nrLogBodyBean.getLteDataInfoBean();
			if (lteDataInfoBean != null) {
				model.setSinr(formatStringToDouble(lteDataInfoBean.getLteSINR()));
				model.setRsrp(formatStringToDouble(lteDataInfoBean.getLteRSRP()));
			}
		} else {// root的
			ProLteDataInfoBean proLteDataInfoBeans = nrLogBodyBean.getProLteDataInfoBean();
			if (proLteDataInfoBeans != null) {
				model.setSinr(formatStringToDouble(proLteDataInfoBeans.getServingCellPccSinr()));
				model.setRsrp(formatStringToDouble(proLteDataInfoBeans.getServingCellPccRsrp()));
			}
		}
		logAnaLyzes.add(model);

	}

	/**
	 * 处理信令里面得大字段信息存储
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月7日 下午4:23:50
	 */
	private void handleMsgInfoField(NrLogBodyBean signalDataBean, BulkRequest request) {
		List<SignalBean> signalBeans = signalDataBean.getSignalBeans();
		if (signalBeans != null && signalBeans.size() > 0) {
			for (SignalBean mSignaBean : signalBeans) {
				String id = UUID.randomUUID().toString().replace("-", "");
				String msg = mSignaBean.getmMeaasge();
				IndexRequest indexRequestEvent = new IndexRequest("logmessage", "logmessage");
				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("id", id); // 单条信令得id
				paramMap.put("ppid", signalDataBean.getPid()); // 室外测试列表的id
				paramMap.put("pid", signalDataBean.getId()); // 主log得id
				paramMap.put("message", msg);// 信令得信息 大字段信息
				paramMap.put("latitude", signalDataBean.getLatitude()); // 主 纬度
				paramMap.put("longitude", signalDataBean.getLongitude()); // 主经度
				mSignaBean.setmMeaasge(id);
				indexRequestEvent.source(JSONObject.toJSONString(paramMap), XContentType.JSON);
				request.add(indexRequestEvent);
			}
		}
	}

	/**
	 * 
	 * @Description: 处理统计分析最后模块
	 * @author weichengz
	 * @date 2020年5月22日 下午2:27:31
	 */
	private void finalAnalyzeLogData(List<LogAnaLyze> logAnaLyzes, LogFoot logFoot, String outHomeTestId) {
		if (logAnaLyzes != null && logAnaLyzes.size() > 0) {
			LogAnaLyze logAnaLyze = logAnaLyzes.get(0);
			if (logFoot != null) {

				logAnaLyze.setUniqueRecord(MyUtil.getUuid());// UUID
				logAnaLyze.setFinalLogData(JSONObject.toJSONString(logFoot));

				FootYyzbBean yyzbBean = logFoot.getYyzbBean();
				if (yyzbBean != null) {
					logAnaLyze.setZjcs(formatStringToDouble(yyzbBean.getZjcs()));
					logAnaLyze.setDhcs(formatStringToDouble(yyzbBean.getDhcs()));
					logAnaLyze.setWjtcs(
							logAnaLyze.getZjcs() - logAnaLyze.getDhcs() - formatStringToDouble(yyzbBean.getJtcs()));
					logAnaLyze.setZhcs(logAnaLyze.getZjcs() - logAnaLyze.getWjtcs() - logAnaLyze.getDhcs());
				}

				FootPingzbBean pingzbBean = logFoot.getPingzbBean();
				if (pingzbBean != null) {
					logAnaLyze.setPingQqcs(formatStringToDouble(pingzbBean.getQqcs()));
					logAnaLyze.setPingCgch(formatStringToDouble(pingzbBean.getCgcs()));
					logAnaLyze.setPingSbch(logAnaLyze.getPingQqcs() - logAnaLyze.getPingCgch());
				}

				FootHttpzbBean httpzbBean = logFoot.getHttpzbBean();
				if (httpzbBean != null) {
					logAnaLyze.setHttpQqcs(formatStringToDouble(httpzbBean.getQqcs()));
					logAnaLyze.setHttpCgch(formatStringToDouble(httpzbBean.getCgcs()));
					logAnaLyze.setHttpSbch(logAnaLyze.getHttpQqcs() - logAnaLyze.getHttpCgch());
				}
			}
		}
	}

	/**
	 *
	 * @Description: 室外测试的列表添加记录 处理最后一条记录的
	 * @author weichengz
	 * @date 2019年11月11日 上午10:25:31
	 */
	private void handleOutHomeTest(LogFoot logFoot, BulkRequest request, String beginTime, String fileName,
			String filePath, String cityId, NrLogHeadBean nrLogHeadBean, String endTime, String outHomeTestId) {
		OutHomeLogModel outHomeLogModel = new OutHomeLogModel(logFoot, beginTime, endTime);
		outHomeLogModel.setCityId(cityId);
		outHomeLogModel.setId(outHomeTestId);

		outHomeLogModel.setCity(nrLogHeadBean.getCity());
		outHomeLogModel.setTestPerson(nrLogHeadBean.getUser());
		outHomeLogModel.setPhoneType(nrLogHeadBean.getPhone());
		
		int logversion = nrLogHeadBean.getLogversion();
		if (1 == logversion) {// NR5G NSA
			outHomeLogModel.setNetWorkType("NR5G NSA");
			if (logFoot != null) {
				FootNrwxzbBean nrwxzbBean = logFoot.getNrwxzbBean();
				if (nrwxzbBean != null) {
					outHomeLogModel.setAvgRsrp(nrwxzbBean.getSsrsrp());
					outHomeLogModel.setAvgSinr(nrwxzbBean.getSssinr());
				}

				FootFtpzbBean ftpzbBean = logFoot.getFtpzbBean();
				if (ftpzbBean != null) {
					outHomeLogModel.setAvgDownRate(ftpzbBean.getDlAvg());
					outHomeLogModel.setAvgUpRate(ftpzbBean.getUlAvg());
				}
			}

		} else {// 0是 LTE
			outHomeLogModel.setNetWorkType("LTE");
			if (logFoot != null) {
				FootLtewxzbBean ltewxzbBean = logFoot.getLtewxzbBean();
				if (ltewxzbBean != null) {
					outHomeLogModel.setAvgRsrp(ltewxzbBean.getRsrpp());
					outHomeLogModel.setAvgSinr(ltewxzbBean.getSinrp());
				}

				FootFtpzbBean ftpzbBean = logFoot.getFtpzbBean();
				if (ftpzbBean != null) {
					outHomeLogModel.setAvgDownRate(ftpzbBean.getDlAvg());
					outHomeLogModel.setAvgUpRate(ftpzbBean.getUlAvg());
				}

			}
		}

		outHomeLogModel.setFileName(fileName);
		outHomeLogModel.setFileUpTime(new Date().getTime());// 文件上传日期
		outHomeLogModel.setFilePath(filePath);
		outHomeLogModel.setOperatorService(nrLogHeadBean.getOperator());
		outHomeLogModel.setLogType("1");
		// 存储室外测试列表
		outHomeService.insert(outHomeLogModel);
	}

	/**
	 * 
	 * @Description: 头部信息的校验
	 * @author weichengz
	 * @date 2020年5月6日 下午3:55:18
	 */
	private void checkHeaderInfo(NrLogHeadBean nrLogHeadBean) {
		int logversion = nrLogHeadBean.getLogversion();
		if (0 != logversion && 1 != logversion) {
			throw new IllegalArgumentException("Logversion值不符合要求：" + logversion);
		}
		int rootSupport = nrLogHeadBean.getRootSupport();
		if (0 != rootSupport && 1 != rootSupport) {
			throw new IllegalArgumentException("rootSupport值不符合要求：" + rootSupport);
		}
	}

	/**
	 * 
	 * @Description: 提交一键测试
	 * @author weichengz
	 * @date 2020年6月17日 下午2:39:37
	 */
	@ResponseBody
	@PostMapping(value = "/uploadOneButtonTest")
	public JsonMsgUtil uploadOneButtonTest(@RequestBody OneButtonTestParam oneButtonTestParam) {

		if (oneButtonTestParam == null) {
			throw new IllegalArgumentException("接收的数据为空");
		}

		String userName = oneButtonTestParam.getUser();
		if (StringUtils.isBlank(userName)) {
			throw new IllegalArgumentException("Ihandle用户账号为空");
		}
		String city = oneButtonTestParam.getCity();
		if (StringUtils.isBlank(city)) {
			throw new IllegalArgumentException("未传入地市名称");
		}
		city = city.trim().replace("市", "");// 后端做地市的市去掉处理

		String operators = oneButtonTestParam.getOperators();
		if (StringUtils.isBlank(operators)) {
			throw new IllegalArgumentException("未传入运营商名称");
		}
		if (!CITYS.contains(operators)) {
			throw new IllegalArgumentException("运营商错误：【联通、电信、移动其一】");
		}

		// 根据地市运营商查询SIM项目
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("city_name", city);
		paramMap.put("operators", operators);
		List<SimProject> selectByMap = simProjectService.selectByMap(paramMap);
		if (selectByMap != null && selectByMap.size() > 0) {
			SimProject simProject = selectByMap.get(0);
			// 查询项目的编号
			String project_name = simProject.getProject_name();
			paramMap.clear();
			paramMap.put("projName", project_name);
			List<SysProject> sysProjects = sysProjectService.findByCondition(paramMap);
			if (sysProjects == null || sysProjects.size() == 0) {
				throw new IllegalArgumentException("【" + city + "、" + operators + "】对应的云后台项目不存在");
			}
			SysProject sysProject = sysProjects.get(0);

			List<OneButtonTest> datas = oneButtonTestParam.getDatas();
			if (datas == null || datas.size() == 0) {
				throw new IllegalArgumentException("上传的数据为空");
			}

			for (OneButtonTest oneButtonTest : datas) {
				oneButtonTest.setId(MyUtil.getUuid());
				oneButtonTest.setCityId(sysProject.getId() + "");
				Date date = MyDateUtil.stringToDate(oneButtonTest.getTestTime(), "yyyy-MM-dd HH:mm:ss");
				if (date != null) {
					oneButtonTest.setTestTimeQuery(date.getTime());
				}
				//oneButtonTest.dealLngLatBdToWgs84();
				oneButtonTestService.insert(oneButtonTest);
			}
			// 在上传没有问题的情况下 查询用户 如果用户不存在就创建默认账号和权限 存在不做处理
			// 查询用户是不是存在
			SysUser sysUser = userService.getUser(userName);
			if (sysUser == null) {
				// 创建用户
				sysUser = new SysUser(userName, "123456", userName);
				userService.saveUser(sysUser);
				// 查看该项目下的默认角色是不是存在
				SysRole sysRole = existsRoleProIn(sysProject);
				// 建立角色和用户的关系
				sysRoleUserService.batchInsert(Arrays.asList(new SysRoleUser(sysUser.getId(), sysRole.getId())));
			} else {// 用户存在的情况下
					// 查询该项目下的用户是不是存在
				SysUser user = userService.queryUser(sysProject.getId(), userName);
				if (user == null) {// 项目下用户不存在
					// 开始
					// 查看该项目下的默认角色是不是存在
					SysRole sysRole = existsRoleProject(sysProject);
					// 建立角色和用户的关系
					sysRoleUserService.batchInsert(Arrays.asList(new SysRoleUser(sysUser.getId(), sysRole.getId())));
					// 结束
				} else {// 项目下用户存在
						// 开始
						// 查看该项目下的默认角色是不是存在
					SysRole sysRole = existsRoleInProject(sysProject);
					// 查看用户是不是有默认角色的权限 没有就勾上 有忽略
					List<SysRoleUser> sysRoleUsers = sysRoleUserService.queryRoleUserByRoleUserId(user.getId(),
							sysRole.getId());
					if (sysRoleUsers == null || sysRoleUsers.size() == 0) {
						// 建立角色和用户的关系
						sysRoleUserService
								.batchInsert(Arrays.asList(new SysRoleUser(sysUser.getId(), sysRole.getId())));
					}
					// 结束
				}
			}

		} else {
			throw new IllegalArgumentException("【" + city + "、" + operators + "】对应的项目不存在");
		}

		return new JsonMsgUtil(true, "添加成功", "");
	}

	private SysRole existsRoleProIn(SysProject sysProject) {
		List<SysRole> sysRoles = roleService.queryByProId(sysProject.getId());
		SysRole sysRole = null;
		if (sysRoles != null && sysRoles.size() > 0) {
			for (SysRole model : sysRoles) {
				if (ihandleUser.equals(model.getName())) {
					sysRole = model;
					break;
				}
			}
		}

		if (sysRole == null) {// 说明角色不存在
			// 创建角色
			sysRole = new SysRole(ihandleUser, "Ihandle默认角色", "1");
			roleService.save(sysRole);
			// 建立角色和项目的关系
			SysProjectRoleRelation sysProjectRoleRelation = new SysProjectRoleRelation(sysRole.getId(),
					sysProject.getId());
			projectRoleRelationService.batchInsert(Arrays.asList(sysProjectRoleRelation));
			// 建立角色和资源的关系ihandleUserPermission
			String[] permissions = ihandleUserPermission.split(",");
			for (String permiss : permissions) {
				List<Permission> permissions2 = permissionService
						.selectByCondition(MyUtil.createHashMap("permission~" + permiss));
				if (permissions2 != null && permissions2.size() > 0) {
					Permission perModel = permissions2.get(0);
					sysRolePermissionService
							.batchInsert(Arrays.asList(new SysRolePermission(sysRole.getId(), perModel.getId())));
				}
			}
		}
		return sysRole;
	}

	private SysRole existsRoleProject(SysProject sysProject) {
		List<SysRole> sysRoles = roleService.queryByProId(sysProject.getId());
		SysRole sysRole = null;
		if (sysRoles != null && sysRoles.size() > 0) {
			for (SysRole model : sysRoles) {
				if (ihandleUser.equals(model.getName())) {
					sysRole = model;
					break;
				}
			}
		}

		if (sysRole == null) {// 说明角色不存在
			// 创建角色
			sysRole = new SysRole(ihandleUser, "Ihandle默认角色", "1");
			roleService.save(sysRole);
			// 建立角色和项目的关系
			SysProjectRoleRelation sysProjectRoleRelation = new SysProjectRoleRelation(sysRole.getId(),
					sysProject.getId());
			projectRoleRelationService.batchInsert(Arrays.asList(sysProjectRoleRelation));
			// 建立角色和资源的关系ihandleUserPermission
			String[] permissions = ihandleUserPermission.split(",");
			for (String permiss : permissions) {
				List<Permission> permissions2 = permissionService
						.selectByCondition(MyUtil.createHashMap("permission~" + permiss));
				if (permissions2 != null && permissions2.size() > 0) {
					Permission perModel = permissions2.get(0);
					sysRolePermissionService
							.batchInsert(Arrays.asList(new SysRolePermission(sysRole.getId(), perModel.getId())));
				}
			}
		}
		return sysRole;
	}

	private SysRole existsRoleInProject(SysProject sysProject) {
		List<SysRole> sysRoles = roleService.queryByProId(sysProject.getId());
		SysRole sysRole = null;
		if (sysRoles != null && sysRoles.size() > 0) {
			for (SysRole model : sysRoles) {
				if (ihandleUser.equals(model.getName())) {
					sysRole = model;
					break;
				}
			}
		}

		if (sysRole == null) {// 说明角色不存在
			// 创建角色
			sysRole = new SysRole(ihandleUser, "Ihandle默认角色", "1");
			roleService.save(sysRole);
			// 建立角色和项目的关系
			SysProjectRoleRelation sysProjectRoleRelation = new SysProjectRoleRelation(sysRole.getId(),
					sysProject.getId());
			projectRoleRelationService.batchInsert(Arrays.asList(sysProjectRoleRelation));
			// 建立角色和资源的关系ihandleUserPermission
			String[] permissions = ihandleUserPermission.split(",");
			for (String permiss : permissions) {
				List<Permission> permissions2 = permissionService
						.selectByCondition(MyUtil.createHashMap("permission~" + permiss));
				if (permissions2 != null && permissions2.size() > 0) {
					Permission perModel = permissions2.get(0);
					sysRolePermissionService
							.batchInsert(Arrays.asList(new SysRolePermission(sysRole.getId(), perModel.getId())));
				}
			}
		}
		return sysRole;
	}
}
