package com.boot.kaizen.service.lte;

import java.util.List;
import java.util.Map;
import com.boot.kaizen.model.lte.LteStationCheck;
import com.boot.kaizen.util.JsonMsgUtil;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:31:45
 */
public interface ILteStationCheckService {

	/**
	 * 
	 * @Description: 列表信息查询
	 * @author weichengz
	 * @date 2018年10月28日 下午12:29:52
	 */
	public List<LteStationCheck> find(Map<String, Object> map);

	/**
	 * 
	 * @Description: 删除
	 * @author weichengz
	 * @date 2018年10月28日 下午11:05:44
	 */
	public JsonMsgUtil delete(String ids);

	/**
	 * 批量查询 app
	 * 
	 * @param stationChecks
	 */
	void batchInsert(List<LteStationCheck> stationChecks);

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	LteStationCheck findById(Long id);

	void deleteByeNodeBID(String mENodeBID);

	/**
	 * 
	 * @Description: 修改信息
	 * @author weichengz
	 * @date 2019年2月3日 下午12:08:08
	 */
	void updateInfo(LteStationCheck stationCheck);
}
