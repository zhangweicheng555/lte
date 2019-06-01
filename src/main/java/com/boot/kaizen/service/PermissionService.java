package com.boot.kaizen.service;

import java.util.List;
import com.boot.kaizen.entity.ZtreeModel;
import com.boot.kaizen.model.Permission;
import com.boot.kaizen.util.JsonMsgUtil;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:26:11
 */
public interface PermissionService {

	void save(Permission permission);

	void update(Permission permission);

	JsonMsgUtil delete(Long id);

	JsonMsgUtil edit(Permission permission);

	List<ZtreeModel> find(List<Long> ids);

	/**
	 * 
	 * @Description: 根据项目名称跟用户id加载对应的资源
	 * @author weichengz
	 * @date 2018年10月28日 上午12:42:01
	 */
	List<Permission> queryByUserIdAndProjId(String username, Long projId);
	
}
