package com.boot.kaizen.controller.sys;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boot.kaizen.entity.DistributeTreeTable;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.enump.Constant;
import com.boot.kaizen.service.DistributeService;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.UserUtil;

/**
 * 权限分配
 * 
 * @author weichengz
 * @date 2018年10月21日 上午9:15:06
 */
@RestController
@RequestMapping("/distribute")
public class DistributeController {

	@Autowired
	private DistributeService distributeService;
	
	
	@PreAuthorize("hasAuthority('sys:distribute:list')")
	@RequestMapping(value = "/list")
	public List<DistributeTreeTable> list(@RequestParam(value="projName",required=false) String projName) {
		LoginUser user = UserUtil.getLoginUser();
		Long projId=null;
		if (Constant.SYSTEM_ID_PROJECT !=user.getProjId()) {
			projId=user.getProjId();
		}
		return distributeService.list(projId,projName);
	}

	@RequestMapping(value = "/findUserIds")
	public List<Long> findUserIds(@RequestParam("roleId") Long roleId) {
		return distributeService.findUserIds(roleId);
	}
	@RequestMapping(value = "/distribute")
	public JsonMsgUtil distribute(@RequestParam("userIds") String userIds,@RequestParam("roleId") Long roleId) {
		return distributeService.distribute(roleId,userIds);
	}

}
