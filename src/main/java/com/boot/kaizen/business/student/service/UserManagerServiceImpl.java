package com.boot.kaizen.business.student.service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.boot.kaizen.business.student.dao.UserManagerMapper;
import com.boot.kaizen.business.student.model.UserManager;
/**
 * 模板实例步骤5 
 * @author weichengz
 * @date 2019年9月26日 下午12:12:27
 */
@Service
public class UserManagerServiceImpl extends ServiceImpl<UserManagerMapper, UserManager> implements IUserManagerService {

	@Override
	public List<UserManager> query(Map<String, Object> map) {
		return baseMapper.findByTest(map);
	}
}
