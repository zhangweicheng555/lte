package com.boot.kaizen.business.buss.service;
import java.util.List;
import com.baomidou.mybatisplus.service.IService;
import com.boot.kaizen.business.buss.model.RequestParamConfig;
import com.boot.kaizen.business.buss.model.TestConfig;

/**
 * 测试配置项业务接口
 * @author weichengz
 * @date 2020年1月14日 下午4:38:33
 */
public interface ITestConfigService extends IService<TestConfig> {

	List<RequestParamConfig> queryItemAll(String item, Integer projId,String type);

}
