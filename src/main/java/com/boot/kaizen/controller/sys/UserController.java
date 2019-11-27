package com.boot.kaizen.controller.sys;

import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.boot.kaizen.annotation.LogAnnotation;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.entity.RequestParamEntity;
import com.boot.kaizen.enump.Constant;
import com.boot.kaizen.model.SysUser;
import com.boot.kaizen.model.UserDto;
import com.boot.kaizen.service.UserService;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.TableResultUtil;
import com.boot.kaizen.util.UserUtil;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;

/**
 * 用户控制层
 * 
 * @author weichengz
 * @date 2018年9月2日 上午10:16:10
 */
@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger("dayLogger");

	@Autowired
	private UserService userService;

	/**
	 * 
	 * @Description: 查询列表
	 * @author weichengz
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @date 2018年10月7日 上午11:24:20
	 */
	@LogAnnotation(flag = "user")
	@ResponseBody
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public TableResultUtil find(RequestParamEntity param) throws InterruptedException, ExecutionException {
		LoginUser user = UserUtil.getLoginUser();
		param.getMap().put("projId", "");
		if (Constant.SYSTEM_ID_PROJECT != user.getProjId()) {
			param.getMap().put("projId", user.getProjId());
		}

		PageInfo<SysUser> pageInfo = PageHelper.startPage(param.getPage(), param.getLimit())
				.doSelectPageInfo(new ISelect() {
					@Override
					public void doSelect() {
						userService.find(param.getMap());
					}
				});
		return new TableResultUtil(0L, "操作成功", pageInfo.getTotal(), pageInfo.getList());
	}

	/**
	 * 
	 * @Description: 添加
	 * @author weichengz
	 * @date 2018年10月7日 上午11:04:45
	 */
	@RequestMapping(value = "/saveUser")
	public JsonMsgUtil saveUser(SysUser sysUser) {
		SysUser u = userService.getUser(sysUser.getUsername());
		if (u != null) {
			throw new IllegalArgumentException(sysUser.getUsername() + "已存在");
		}
		LoginUser user = UserUtil.getLoginUser();
		return userService.saveUserRole(sysUser,user);
	}

	/**
	 * 
	 * @Description: 修改用户
	 * @author weichengz
	 * @date 2018年10月14日 下午7:19:02
	 */
	@RequestMapping(value = "/updateUser")
	public JsonMsgUtil updateUser(SysUser sysUser) {
		LoginUser user = UserUtil.getLoginUser();
		return userService.updateUser(sysUser, user.getProjId());
	}

	@PutMapping(params = "headImgUrl")
	@ApiOperation(value = "修改头像")
	public void updateHeadImgUrl(String headImgUrl) {
		SysUser user = UserUtil.getLoginUser();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);
		userDto.setHeadImgUrl(headImgUrl);
		// userService.updateUser(userDto);
		log.debug("{}修改了头像", user.getUsername());
	}

	@RequestMapping(value = "/editPassword")
	public JsonMsgUtil editPassword(@RequestParam(value = "id") Long id,
			@RequestParam(value = "oldPassword") String oldPassword,
			@RequestParam(value = "newPassword") String newPassword) {
		userService.changePassword(id, oldPassword, newPassword);
		return new JsonMsgUtil(true, "修改成功", "");
	}

	@ApiOperation(value = "当前登录用户")
	@GetMapping("/current")
	public SysUser currentUser() {
		return UserUtil.getLoginUser();
	}

	/**
	 * 
	 * @Description: 根据id查询
	 * @author weichengz
	 * @date 2018年10月14日 下午6:55:45
	 */
	@RequestMapping(value = "/findById")
	public JsonMsgUtil findById(@RequestParam("id") Long id) {
		return userService.findById(id);
	}

	@RequestMapping(value = "/delete")
	//@PreAuthorize("hasAuthority('sys:user:edit')")
	public JsonMsgUtil delete(@RequestParam("ids") String ids) {
		LoginUser user = UserUtil.getLoginUser();
		Long projId = null;
		if (Constant.SYSTEM_ID_PROJECT != user.getProjId()) {
			projId = user.getProjId();
		}
		return userService.delete(ids, projId, user.getId());
	}

}
