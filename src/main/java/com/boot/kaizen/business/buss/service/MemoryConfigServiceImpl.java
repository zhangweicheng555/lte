package com.boot.kaizen.business.buss.service;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.boot.kaizen.business.buss.dao.MemoryConfigMapper;
import com.boot.kaizen.business.buss.model.MemoryConfig;

/**
 * 
 * @author weichengz
 * @date 2019年10月21日 上午10:32:39
 */
@Service
public class MemoryConfigServiceImpl extends ServiceImpl<MemoryConfigMapper, MemoryConfig>
		implements IMemoryConfigService {

}
