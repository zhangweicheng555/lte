package com.boot.kaizen.dao.lte;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.boot.kaizen.model.lte.LteConfig;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:15:52
 */
@Mapper
public interface LteConfigDao {
	/**
	 * 
	 * @Description: 查询
	 * @author weichengz
	 * @date 2018年10月28日 下午4:42:04
	 */
	List<LteConfig> find(@Param("map") Map<String, Object> map);

	/**
	 * 
	 * @Description: 保存
	 * @author weichengz
	 * @date 2018年10月28日 下午4:42:11
	 */
	void save(LteConfig lteConfig);

	/**
	 * 
	 * @Description: 查询
	 * @author weichengz
	 * @date 2018年10月28日 下午5:03:03
	 */
	@Select("select * from lte_config where id=#{id}")
	LteConfig findById(@Param("id") Long id);

	/**
	 * 
	 * @Description: 修改
	 * @author weichengz
	 * @date 2018年10月28日 下午5:43:30
	 */
	void update(LteConfig lteConfig);

	Integer delete(@Param("idsArray") Long[] array);

	/**
	 * 查询信息 app
	 * 
	 * @param projId
	 */
	LteConfig findInfoById(@Param("projId") Long projId);

	/**
	 * 
	 * @Description: 改变流程状态
	 * @author weichengz
	 * @date 2018年11月4日 上午9:51:44
	 */
	void changeStatus(@Param("id") Long id, @Param("status") Long status);

	List<LteConfig> queryListByEnodeBid(@Param("eNodeBID") String eNodeBID);

	/**
	 * 
	 * @Description: 根据项目id查询配置信息
	 * @author weichengz
	 * @date 2018年11月11日 上午10:54:41
	 */
	List<LteConfig> queryListByProjId(@Param("projId") Long projId);

}
