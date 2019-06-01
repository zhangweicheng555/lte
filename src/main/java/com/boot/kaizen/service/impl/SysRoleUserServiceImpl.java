package com.boot.kaizen.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.boot.kaizen.dao.SysRoleUserDao;
import com.boot.kaizen.model.SysRoleUser;
import com.boot.kaizen.service.SysRoleUserService;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:29:08
 */
@Service
public class SysRoleUserServiceImpl implements SysRoleUserService {

	@Autowired
	private SysRoleUserDao roleUserDao;

	@Override
	public void delete(Long roleId) {
		roleUserDao.delete(roleId);
	}

	@Override
	public void batchInsert(List<SysRoleUser> roleUsers) {
		roleUserDao.batchInsert(roleUsers);
	}

	@Override
	public void deleteBatch(Long[] userIds) {
		roleUserDao.deleteBatch(userIds);
	}

	@Override
	public void deleteByProjIds(Long[] projIds) {
		roleUserDao.deleteByProjIds(projIds);
	}

}
