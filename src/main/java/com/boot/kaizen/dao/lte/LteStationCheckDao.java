package com.boot.kaizen.dao.lte;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.boot.kaizen.model.lte.LteStationCheck;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:15:52
 */
@Mapper
public interface LteStationCheckDao {
	/**
	 * 
	 * @Description: 查询
	 * @author weichengz
	 * @date 2018年10月28日 下午4:42:04
	 */
	List<LteStationCheck> find(@Param("map") Map<String, Object> map);

	/**
	 *
	 * @Description: 删除
	 * @author weichengz
	 * @date 2018年10月28日 下午11:06:34
	 */
	Integer delete(@Param("idsArray") Long[] array);

	/**
	 * 批量添加数据 app
	 * 
	 * @param stationChecks
	 */
	void batchInsert(@Param("stationChecks") List<LteStationCheck> stationChecks);

	/**
	 * 
	 * @Description: 修改信息
	 * @author weichengz
	 * @date 2019年2月3日 下午12:07:04
	 */
	void updateInfo(LteStationCheck stationCheck);

	/**
	 * 根据id查询
	 * 
	 * @param stationChecks
	 * @return
	 */
	LteStationCheck findById(@Param("id") Long id);

	/**
	 * 
	 * @Description: 查询
	 * @author weichengz
	 * @date 2018年11月11日 上午8:28:32
	 */
	List<LteStationCheck> findListByMenodeBID(@Param("mENodeBID") String mENodeBID);

	/**
	 * 根据站号删除
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2018年11月11日 下午10:47:21
	 */
	void deleteByeNodeBID(@Param("mENodeBID") String mENodeBID);

}
