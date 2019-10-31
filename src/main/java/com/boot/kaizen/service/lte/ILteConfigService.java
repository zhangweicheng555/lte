package com.boot.kaizen.service.lte;

import java.util.List;
import java.util.Map;
import com.boot.kaizen.model.lte.LteConfig;
import com.boot.kaizen.util.JsonMsgUtil;

/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:31:45
 */
public interface ILteConfigService {

	/**
	 * 
	 * @Description: 列表信息查询
	 * @author weichengz
	 * @date 2018年10月28日 下午12:29:52
	 */
	public List<LteConfig> find(Map<String, Object> map);

	
	/**
	 * 
	 * @Description: 查询
	 * @author weichengz
	 * @date 2018年10月28日 下午5:00:17
	 */
	public JsonMsgUtil findById(Long id);

	/**
	 * 
	 * @Description: 删除
	 * @author weichengz
	 * @date 2018年10月28日 下午5:53:29
	 */
	public JsonMsgUtil delete(String ids);

	/**
	 * 查询信息 app
	 * 
	 * @param projId
	 */
	LteConfig findInfoById(Long projId);

	/**
	 * 
	 * @Description: 改变流程状态状态
	 * @author weichengz
	 * @date 2018年11月4日 上午9:50:59
	 */
	void changeStatus(Long id, Long status);

	/**
	 * 
	 * @Description: 查询测试配置信息
	 * @author weichengz
	 * @date 2018年11月11日 上午10:48:22
	 */
	public List<LteConfig> queryListByProjId(Long projId);
}
