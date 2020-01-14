package com.boot.kaizen.business.buss.service;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.boot.kaizen.business.buss.dao.TestConfigMapper;
import com.boot.kaizen.business.buss.model.TestConfig;

/**
 * 
 * @author weichengz
 * @date 2019年10月21日 上午10:32:39
 */
@Service
public class TestConfigServiceImpl extends ServiceImpl<TestConfigMapper, TestConfig> implements ITestConfigService {

}
