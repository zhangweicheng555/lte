package com.boot.kaizen.business.buss.service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.service.IService;
import com.boot.kaizen.business.buss.model.SimProject;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.util.JsonMsgUtil;

/**
 * 测试配置项业务接口
 * @author weichengz
 * @date 2020年1月14日 下午4:38:33
 */
public interface ISimProjectService extends IService<SimProject> {


	JsonMsgUtil upload(MultipartFile file, LoginUser loginUser);
}
