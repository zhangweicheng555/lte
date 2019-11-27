package com.boot.kaizen.service;

import java.util.List;

import com.boot.kaizen.model.SysProjectRoleRelation;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:26:11
 */
public interface ProjectRoleRelationService {

	void batchInsert(List<SysProjectRoleRelation> relations);

	void deleteByRoleAndProId(Long roleId);

	void deleteByProIds(Long[] array);
	
	
	
}
