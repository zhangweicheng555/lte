package com.boot.kaizen.business.buss.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.boot.kaizen.business.buss.model.SimProject;
import com.boot.kaizen.business.buss.service.ISimProjectService;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.entity.RequestParamEntity;
import com.boot.kaizen.model.SysProject;
import com.boot.kaizen.service.SysProjectService;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.MyUtil;
import com.boot.kaizen.util.TableResultUtil;
import com.boot.kaizen.util.UserUtil;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 测试配置项
 * 
 * @author weichengz
 * @date 2020年1月14日 下午4:37:15
 */
@Controller
@RequestMapping("/buss/sim/project")
public class SimProjectController {

	@Autowired
	private ISimProjectService simProjectService;
	@Autowired
	private SysProjectService sysProjectService;

	/**
	 * 分页查询
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月21日 上午10:36:39
	 */
	@ResponseBody
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public TableResultUtil find(RequestParamEntity param) {
		// 默认降序排列
		List<String> orders = param.getOrders();
		if (orders == null || orders.size() == 0) {
			orders = Arrays.asList("createTime");
		} else {
			orders.add("createTime");
		}
		param.setOrders(orders);

		PageInfo<SimProject> pageInfo = PageHelper.startPage(param.getPage(), param.getLimit())
				.doSelectPageInfo(new ISelect() {
					@Override
					public void doSelect() {
						// 条件构造器
						EntityWrapper<SimProject> qryWrapper = MyUtil.createQueryPlus(param);
						// 查询列表
						simProjectService.selectMaps(qryWrapper);
					}
				});
		return new TableResultUtil(0L, "查询成功", pageInfo.getTotal(), pageInfo.getList());
	}

	/**
	 * 修改
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月21日 上午10:36:32
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public JsonMsgUtil edit(SimProject simProject) {

		if (StringUtils.isBlank(simProject.getId())) {
			throw new IllegalArgumentException("要修改的主键ID不存在");
		}
		// 校验
		simProject.verificationAll();
		// 判断是不是存在了
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("project_name", simProject.getProject_name());
		columnMap.put("net_id", simProject.getNet_id());
		columnMap.put("province_name", simProject.getProvince_name());
		columnMap.put("city_name", simProject.getCity_name());
		columnMap.put("operators", simProject.getOperators());
		List<SimProject> selectByMap = simProjectService.selectByMap(columnMap);
	
		if (selectByMap == null || selectByMap.size() == 0) {
		} else {
			for (SimProject simProject2 : selectByMap) {
				if (!simProject.getId().equals(simProject2.getId())) {
					throw new IllegalArgumentException("添加失败：该项目已经存在!");
				}
			}
		}
		// 更新
		simProjectService.insertOrUpdate(simProject);

		//将LOG生成了项目，根据条件进行修改
		String project_name = simProject.getProject_name();
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("projName", project_name);
		List<SysProject> sysProjects = sysProjectService.findByCondition(paramMap);
		if (sysProjects !=null && sysProjects.size()>0) {//修改现在已经存在的项目
			
			SysProject sysProject=sysProjects.get(0);
			sysProject.setProjIntro("SIM项目自动生成");
			sysProject.setProjName(project_name);
			sysProject.setProjSimName(project_name);
			sysProject.setProjCode(simProject.getCity_name());
			sysProject.setProProvice(simProject.getProvince_name());
			sysProject.setHostAp(simProject.getIhandle_url());
			sysProject.setProjOperator(simProject.getOperators());
			sysProject.setSort(UUID.randomUUID().toString().replace("-", ""));
			sysProject.setUpdateTime(new Date());
			
			sysProjectService.update(sysProject);
			
		}
		
		return new JsonMsgUtil(true, "操作成功", "");
	}

	/**
	 * 添加
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月21日 上午10:36:32
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonMsgUtil add(SimProject simProject) {
		// 校验
		simProject.verificationAll();
		// 判断是不是存在了
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("project_name", simProject.getProject_name());
		columnMap.put("net_id", simProject.getNet_id());
		columnMap.put("province_name", simProject.getProvince_name());
		columnMap.put("city_name", simProject.getCity_name());
		columnMap.put("operators", simProject.getOperators());
		List<SimProject> selectByMap = simProjectService.selectByMap(columnMap);
		if (selectByMap == null || selectByMap.size() == 0) {
			// 添加
			simProjectService.insertOrUpdate(simProject);
			//判断lOG项目是否已经生成了项目  根据sim的项目名字直接判断即可
			String project_name = simProject.getProject_name();
			Map<String, Object> paramMap=new HashMap<>();
			paramMap.put("projName", project_name);
			List<SysProject> sysProjects = sysProjectService.findByCondition(paramMap);
			if (sysProjects==null || sysProjects.size()==0) {//添加项目
				
				SysProject sysProject=new SysProject();
				sysProject.setProjIntro("SIM项目自动生成");
				sysProject.setProjName(project_name);
				sysProject.setProjSimName(project_name);
				sysProject.setProjCode(simProject.getCity_name());
				sysProject.setProProvice(simProject.getProvince_name());
				sysProject.setHostAp(simProject.getIhandle_url());
				sysProject.setProjOperator(simProject.getOperators());
				sysProject.setSort(UUID.randomUUID().toString().replace("-", ""));
				
				sysProject.setCreateTime(new Date());
				sysProject.setUpdateTime(new Date());
				
				sysProjectService.insertSelf(sysProject);
				
			}
		} else {
			throw new IllegalArgumentException("添加失败：该项目已经存在!");
		}
		return new JsonMsgUtil(true, "操作成功", "");
	}

	/**
	 * 通过任务ID查询
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月21日 上午10:36:46
	 */
	@ResponseBody
	@RequestMapping(value = "/findById", method = RequestMethod.POST)
	public JsonMsgUtil findById(@RequestParam("id") String id) {
		SimProject simProject = simProjectService.selectById(id);
		return new JsonMsgUtil(true, "操作成功", simProject);
	}

	/**
	 * 任务IDs批量删出
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月21日 上午10:36:57
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JsonMsgUtil delete(@RequestParam("ids") String ids) {
		if (StringUtils.isBlank(ids)) {
			throw new IllegalArgumentException("要删除的ID不存在");
		}
		simProjectService.deleteBatchIds(Arrays.asList(ids.split(",")));
		return new JsonMsgUtil(true, "操作成功", "");
	}
	
	/**
	 * 文件的批量上传
	 */
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public JsonMsgUtil uploadRoadTest(@RequestParam(value = "files") MultipartFile file) {
		LoginUser loginUser = UserUtil.getLoginUser();
		return simProjectService.upload(file, loginUser);
	}

}
