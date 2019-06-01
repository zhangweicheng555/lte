package com.boot.kaizen.service.lte;

import java.util.List;
import java.util.Map;
import com.boot.kaizen.model.lte.LteCellCheck;
import com.boot.kaizen.util.JsonMsgUtil;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:31:24
 */
public interface ILteCellCheckService {

	/**
	 * 
	 * @Description: 列表信息查询
	 * @author weichengz
	 * @date 2018年10月28日 下午12:29:52
	 */
	public List<LteCellCheck> find(Map<String, Object> map);

	/**
	 * 
	 * @Description: 删除
	 * @author weichengz
	 * @date 2018年10月28日 下午11:05:44
	 */
	public JsonMsgUtil delete(String ids);

	/**
	 * 批量添加数据 app
	 * 
	 * @param stationChecks
	 */
	void batchInsert(List<LteCellCheck> cellChecks);

	/**
	 * 删除
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2018年11月11日 下午10:47:49
	 */
	void deleteByeNodeBID(String mENodeBID);

	/**
	 * 
	* @Description: 数据更新
	* @author weichengz
	* @date 2019年2月3日 下午12:21:36
	 */
	void updateByPrimaryKeySelective(LteCellCheck lteCellCheck);
}
