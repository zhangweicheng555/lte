package com.boot.kaizen.controller.sys;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.entity.RequestParamEntity;
import com.boot.kaizen.enump.Constant;
import com.boot.kaizen.model.SysProject;
import com.boot.kaizen.model.SysProjectMapper;
import com.boot.kaizen.service.IProjectMapperService;
import com.boot.kaizen.service.SysProjectService;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.MyUtil;
import com.boot.kaizen.util.TableResultUtil;
import com.boot.kaizen.util.UserUtil;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 项目 与 SIM平台得映射关系
 * @author weichengz
 * @date 2019年11月28日 下午5:03:57
 */
@Controller
@RequestMapping("/projectmapper")
public class ProjectMapperController {

	@Autowired
	private IProjectMapperService projectMapperService;
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
		// 处理map
		Map<String, Object> resultMap = MyUtil.clearMapEmptyVal(param.getMap());
		PageInfo<SysProjectMapper> pageInfo = PageHelper.startPage(param.getPage(), param.getLimit())
				.doSelectPageInfo(new ISelect() {
					@Override
					public void doSelect() {
						// 条件构造器
						EntityWrapper<SysProjectMapper> qryWrapper = MyUtil.createEntityWrapper(resultMap, "createTime",
								"AND");
						// 查询列表
						projectMapperService.selectMaps(qryWrapper);
					}
				});
		return new TableResultUtil(0L, "查询成功", pageInfo.getTotal(), pageInfo.getList());
	}

	/**
	 * 编辑
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月21日 上午10:36:32
	*/
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public JsonMsgUtil edit(SysProjectMapper sysProjectMapper) {
		if (StringUtils.isBlank(sysProjectMapper.getId())) {
			sysProjectMapper.setCreateTime(new Date());
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projSimName", sysProjectMapper.getProjSimName());
		List<SysProjectMapper> datas = projectMapperService.selectByMap(paramMap);
		if (datas !=null && datas.size() >0) {
			String id = sysProjectMapper.getId();//af1f0a2266524085b89913c6235f0f8c
			if (StringUtils.isBlank(id)) {
				throw new IllegalArgumentException("已存在SIM项目名称");
			}else {
				for (SysProjectMapper testConfig2 : datas) {
					if (!id.equals(testConfig2.getId())) {
						throw new IllegalArgumentException("已存在SIM项目名称");
					}
				}
			}
		}
		projectMapperService.insertOrUpdate(sysProjectMapper);
		return new JsonMsgUtil(true, "操作成功", "");
	} 

	@ResponseBody
	@RequestMapping(value = "/findList", method = RequestMethod.POST)
	public JsonMsgUtil findList() {
		List<SysProjectMapper> datas = projectMapperService.selectByMap(new HashMap<>());
		return new JsonMsgUtil(true, "操作成功", datas);
	}
	
	/**
	 * 查询项目得列表  注意 如果是系统项目 显示所有得项目列表 否则只显示当前登陆得项目名字
	* @Description: TODO
	* @author weichengz
	* @date 2019年11月29日 上午10:32:29
	 */
	@ResponseBody
	@RequestMapping(value = "/findNowList", method = RequestMethod.POST)
	public JsonMsgUtil findNowList() {
		LoginUser loginUser = UserUtil.getLoginUser();
		Long projId = loginUser.getProjId();
		if (projId==null) {
			throw new IllegalArgumentException("用户不属于任何的项目");
		}
		SysProject sysProject = sysProjectService.selectById(projId);
		if (sysProject==null) {
			throw new IllegalArgumentException("用户不属于任何的项目");
		}
		Map<String,Object> paramMap = new HashMap<>();
		if (!Constant.SYSTEM_ID_PROJECT.equals(projId)) {
			paramMap.put("id", sysProject.getProjIntro());
		}
		List<SysProjectMapper> datas = projectMapperService.selectByMap(paramMap);
		return new JsonMsgUtil(true, "操作成功", datas);
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
		SysProjectMapper sysProjectMapper = projectMapperService.selectById(id);
		return new JsonMsgUtil(true, "操作成功", sysProjectMapper);
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
		projectMapperService.deleteBatchIds(Arrays.asList(ids.split(",")));
		return new JsonMsgUtil(true, "操作成功", "");
	}

}
