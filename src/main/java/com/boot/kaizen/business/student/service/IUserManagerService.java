package com.boot.kaizen.business.student.service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.service.IService;
import com.boot.kaizen.business.student.model.UserManager;

/**
 * 模板实例步骤4
 * 
 * @author weichengz
 * @date 2019年9月26日 下午12:12:22
 */
public interface IUserManagerService extends IService<UserManager> {

	public List<UserManager> query(Map<String, Object> map);
}
