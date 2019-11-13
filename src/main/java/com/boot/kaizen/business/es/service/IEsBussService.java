package com.boot.kaizen.business.es.service;

import java.util.List;
import java.util.Map;

import com.boot.kaizen.business.es.model.QueryParamData;

/**
 * es业务逻辑接口
 * 
 * @author weichengz
 * @date 2019年11月12日 上午10:29:31
 */

public interface IEsBussService {
	
	/**
	 * 地图页面加载全部的信息  这个要进行缓存
	* @Description: TODO
	* @author weichengz
	* @date 2019年11月12日 上午10:33:09
	 */
	List<Map<String, Object>> queryMainLog(String pid, QueryParamData queryParamData);

}
