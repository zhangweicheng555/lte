package com.boot.kaizen.dao.lte;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.boot.kaizen.model.lte.LteGcParameter;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:15:52
 */
@Mapper
public interface LteGcParameterDao {
	/**
	 * 
	 * @Description: 查询
	 * @author weichengz
	 * @date 2018年10月28日 下午4:42:04
	 */
	List<LteGcParameter> find(@Param("map") Map<String, Object> map);

	/**
	 * 
	 * @Description: 保存
	 * @author weichengz
	 * @date 2018年10月28日 下午4:42:11
	 */
	Long save(LteGcParameter lteGcParameter);

	/**
	 * 
	 * @Description: 查询
	 * @author weichengz
	 * @date 2018年10月28日 下午5:03:03
	 */
	@Select("select * from lte_gc where id=#{id}")
	LteGcParameter findById(@Param("id") Long id);

	/**
	 * 
	 * @Description: 修改
	 * @author weichengz
	 * @date 2018年10月28日 下午5:43:30
	 */
	void update(LteGcParameter lteGcParameter);

	Integer delete(@Param("idsArray") Long[] array);

	/**
	 * 查询工参列表 app
	 * 
	 * @param mENodeBID
	 */
	List<LteGcParameter> queryGcParameterList(@Param("mENodeBID") String mENodeBID,@Param("testDate") String testDate);
	
	int insertSelective(LteGcParameter record);
    int updateByPrimaryKeySelective(LteGcParameter record);

}
