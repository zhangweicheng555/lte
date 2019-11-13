package com.boot.kaizen.business.es.service;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.boot.kaizen.business.es.model.QueryParamData;

/**
 * es 业务逻辑层
 * 
 * @author weichengz
 * @date 2019年11月12日 上午10:29:13
 */
@Service
public class EsBussService implements IEsBussService {

	@Cacheable(value="queryPeopleNumByTimeRange",key="#pid")
	@Override
	public List<Map<String, Object>> queryMainLog(String pid, QueryParamData queryParamData) {
		System.out.println("-----------------------------加载数据开始-----------------------------------");
		return Esutil.scrollQuery(queryParamData);
	}

}
