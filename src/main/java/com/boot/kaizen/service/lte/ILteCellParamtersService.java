package com.boot.kaizen.service.lte;

import java.util.List;
import java.util.Map;
import com.boot.kaizen.model.lte.LteCellParamters;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:31:33
 */
public interface ILteCellParamtersService {

	/**
	 * 
	 * @Description: 添加
	 * @author weichengz
	 * @date 2018年12月31日 上午10:10:10
	 */
	int insertSelective(LteCellParamters record);

	/**
	 * 
	 * @Description: 通过主键查询
	 * @author weichengz
	 * @date 2018年12月31日 上午10:10:48
	 */
	LteCellParamters selectByPrimaryKey(String id);
	
	/**
	 * 添加更新  app对接
	* @Description: TODO
	* @author weichengz
	* @date 2019年1月1日 上午11:33:10
	 */
	void upSert(LteCellParamters lteCellParamters);
	
	
	/**
	 * 根据条件查询
	* @Description: TODO
	* @author weichengz
	* @date 2019年1月6日 下午7:41:36
	 */
	List<LteCellParamters> query(Map<String, Object> map);
}
