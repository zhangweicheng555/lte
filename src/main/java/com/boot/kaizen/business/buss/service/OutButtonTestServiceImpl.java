package com.boot.kaizen.business.buss.service;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.boot.kaizen.business.buss.dao.OneButtonTestMapper;
import com.boot.kaizen.business.es.model.OneButtonTest;

/**
 * 
 * @author weichengz
 * @date 2019年10月21日 上午10:32:39
 */
@Service
public class OutButtonTestServiceImpl extends ServiceImpl<OneButtonTestMapper, OneButtonTest>
		implements IOneButtonTestService {

}
