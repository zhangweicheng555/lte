package com.boot.kaizen.controller.sys;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.kaizen.annotation.LogAnnotation;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.entity.RequestParamEntity;
import com.boot.kaizen.enump.Constant;
import com.boot.kaizen.model.SysProject;
import com.boot.kaizen.model.SysUser;
import com.boot.kaizen.service.SysProjectService;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.TableResultUtil;
import com.boot.kaizen.util.UserUtil;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 项目管理控制层
 * 
 * @author weichengz
 * @date 2018年9月8日 上午11:08:56
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	private SysProjectService projectService;

	@ResponseBody
	@RequestMapping(value = "/query")
	public List<SysProject> query() {
		return projectService.query();
	}

	@ResponseBody
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public TableResultUtil find(RequestParamEntity param) {
		LoginUser user = UserUtil.getLoginUser();
		param.getMap().put("projIntroModel", "");
		if (Constant.SYSTEM_ID_PROJECT != user.getProjId()) {
			Long projId = user.getProjId();
			if (projId == null) {
				throw new IllegalArgumentException("用户不属于任何的项目");
			}
			SysProject sysProject = projectService.selectById(projId);
			if (sysProject == null) {
				throw new IllegalArgumentException("用户不属于任何的项目");
			}
			param.getMap().put("projIntroModel", sysProject.getProjIntro());
		}
		PageInfo<SysProject> pageInfo = PageHelper.startPage(param.getPage(), param.getLimit())
				.doSelectPageInfo(new ISelect() {
					@Override
					public void doSelect() {
						projectService.find(param.getMap());
					}
				});
		return new TableResultUtil(0L, "操作成功", pageInfo.getTotal(), pageInfo.getList());
	}

	@LogAnnotation(flag = "delete")
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JsonMsgUtil delete(@RequestParam("ids") String ids) {
		return projectService.delete(ids);
	}

	/**
	 * 修改信息
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2018年10月2日 上午10:52:30
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public JsonMsgUtil edit(SysProject sysProject) {

		return projectService.edit(sysProject);
	}

	@ResponseBody
	@RequestMapping(value = "/findById", method = RequestMethod.POST)
	public JsonMsgUtil findById(@RequestParam("id") Long id) {
		return projectService.findById(id);
	}

	/**
	 * 
	 * @Description: index页面显示项目模块
	 * @author weichengz
	 * @date 2018年10月27日 下午11:26:50
	 */
	@ResponseBody
	@RequestMapping(value = "/dealIndexProject", method = RequestMethod.POST)
	public JsonMsgUtil dealIndexProject() {
		SysUser sysUser = UserUtil.getLoginUser();
		if (sysUser == null) {
			throw new IllegalArgumentException("用户不存在");
		}
		String username = sysUser.getUsername();
		return projectService.queryProjectsForUsername(username);
	}

	/**
	 * 
	 * @Description: 更换项目
	 * @author weichengz
	 * @date 2018年10月28日 上午12:53:06
	 */
	@ResponseBody
	@RequestMapping(value = "/changeProject", method = RequestMethod.POST)
	public JsonMsgUtil changeProject() {
		return new JsonMsgUtil(true);
	}

}
