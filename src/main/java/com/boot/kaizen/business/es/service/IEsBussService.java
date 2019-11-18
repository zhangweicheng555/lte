package com.boot.kaizen.business.es.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.boot.kaizen.business.es.model.OneButtonTest;
import com.boot.kaizen.business.es.model.OutHomeLogModel;
import com.boot.kaizen.business.es.model.QueryParamData;

/**
 * es业务逻辑接口
 * 
 * @author weichengz
 * @date 2019年11月12日 上午10:29:31
 */

public interface IEsBussService {

	/**
	 * 地图页面加载全部的信息 这个要进行缓存
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月12日 上午10:33:09
	 */
	List<Map<String, Object>> queryMainLog(String pid, QueryParamData queryParamData);

	/**
	 * 
	 * @Description: 导出一键测试
	 * @author weichengz
	 * @throws Exception 
	 * @date 2019年11月14日 上午10:49:51
	 */
	void exportOneButtonTest(String[] headers, Collection<OneButtonTest> collection, String string,
			HttpServletResponse response) throws Exception;

	/**
	 * 室外测试导出
	* @Description: TODO
	* @author weichengz
	* @date 2019年11月14日 上午11:49:07
	 */
	void exportLogoutHomeTest(String[] headers, Collection<OutHomeLogModel> collection, String string,
			HttpServletResponse response) throws Exception;

}
