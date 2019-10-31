package com.boot.kaizen.service.lte;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import com.boot.kaizen.model.lte.LteGcParameter;
import com.boot.kaizen.util.JsonMsgUtil;

/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:31:45
 */
public interface ILteGcParameterService {

	/**
	 * 
	 * @Description: 列表信息查询
	 * @author weichengz
	 * @date 2018年10月28日 下午12:29:52
	 */
	public List<LteGcParameter> find(Map<String, Object> map);


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
	 * 查询工参列表 app
	 * 
	 * @param getmENodeBID
	 */
	public List<LteGcParameter> queryGcParameterList(String mENodeBID, String testDate);

	
	int insertSelective(LteGcParameter record);

	int updateByPrimaryKeySelective(LteGcParameter record);

}
