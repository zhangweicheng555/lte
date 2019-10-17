package com.boot.kaizen.business.student.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.boot.kaizen.business.student.model.UserManager;
import com.boot.kaizen.business.student.service.IUserManagerService;
import com.boot.kaizen.entity.RequestParamEntity;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.TableResultUtil;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * mybatisplus 控制层测试 模板实例步骤
 * 
 * @author weichengz
 * @date 2019年8月30日 下午5:43:21
 */
@Controller
@RequestMapping("/zwc/manager")
public class UserManagerController {

	@Autowired
	private IUserManagerService userManagerService;

	/**
	 * 分页查询做法
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年8月30日 下午5:43:14
	 */
	@ResponseBody
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public TableResultUtil find(RequestParamEntity param) {

		// param.getMap().put("projId", MyUtil.getDealProjId(UserUtil.getLoginUser()));

		PageInfo<UserManager> pageInfo = PageHelper.startPage(param.getPage(), param.getLimit())
				.doSelectPageInfo(new ISelect() {
					@Override
					public void doSelect() {
						userManagerService.selectByMap(param.getMap());
					}
				});
		return new TableResultUtil(0L, "查询成功", pageInfo.getTotal(), pageInfo.getList());
	}

	/**
	 * 添加修改都用这个方法
	 * @Description:  1.编辑 有ID就修改 没有id就添加 添加时 如果存在ID就使用 2.insertOrUpdate 选择性修改
	 * @author weichengz
	 * @date 2019年10月17日 下午11:56:57
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public JsonMsgUtil edit(UserManager userManager) {
		userManagerService.insertOrUpdate(userManager);
		return new JsonMsgUtil(true, "操作成功", "");
	}

	/**
	 * 
	 * @Description: 通过任务id查询
	 * @author weichengz
	 * @date 2019年2月17日 下午11:13:03
	 */
	@ResponseBody
	@RequestMapping(value = "/findById", method = RequestMethod.POST)
	public JsonMsgUtil findById(@RequestParam("id") String id) {
		UserManager userManager = userManagerService.selectById(id);
		return new JsonMsgUtil(true, "操作成功", userManager);
	}

	/**
	 * 
	 * @Description: 删除
	 * @author weichengz
	 * @date 2019年2月17日 下午11:13:10
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JsonMsgUtil delete(@RequestParam("ids") String ids) {
		if (StringUtils.isBlank(ids)) {
			throw new IllegalArgumentException("要删除的ID不存在");
		}
		userManagerService.deleteBatchIds(Arrays.asList(ids.split(",")));
		return new JsonMsgUtil(true, "操作成功", "");
	}


}
