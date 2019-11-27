package com.boot.kaizen.service;

import java.util.List;
import com.boot.kaizen.entity.DistributeTreeTable;
import com.boot.kaizen.util.JsonMsgUtil;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:26:11
 */
public interface DistributeService {

	/**
	 * 
	 * @Description: 权限分配查询列表
	 * @author weichengz
	 * @date 2018年10月21日 上午10:26:29
	 */
	public List<DistributeTreeTable> list(Long projId,String projName);

	/**
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2018年10月21日 上午10:26:55
	 */
	public List<Long> findUserIds(Long roleId);

	/**
	 * 
	* @Description: 角色用户分配
	* @author weichengz
	 * @param userIds 
	* @date 2018年10月21日 上午11:08:29
	 */
	public JsonMsgUtil distribute(Long roleId, String userIds);

}
