package com.boot.kaizen.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.boot.kaizen.dao.SysProjectRoleRelationDao;
import com.boot.kaizen.model.SysProjectRoleRelation;
import com.boot.kaizen.service.ProjectRoleRelationService;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:28:22
 */
@Service
public class ProjectRoleRelationServiceImpl implements ProjectRoleRelationService {

	@Autowired
	private SysProjectRoleRelationDao projectRoleRelationDao;

	@Override
	public void batchInsert(List<SysProjectRoleRelation> relations) {
		projectRoleRelationDao.batchInsert(relations);
	}

	@Override
	public void deleteByRoleAndProId(Long roleId) {
		projectRoleRelationDao.deleteByRoleAndProId(roleId);
	}

	@Override
	public void deleteByProIds(Long[] array) {
		projectRoleRelationDao.deleteByProIds(array);
	}

	

}
