package com.boot.kaizen.dao.lte;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.boot.kaizen.controller.lte.model.BaseStationBean;
import com.boot.kaizen.model.lte.LtePlan;
import com.boot.kaizen.model.lte.LtePlanInfo;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:15:52
 */
@Mapper
public interface LtePlanDao {
	/**
	 * 
	 * @Description: 查询
	 * @author weichengz
	 * @date 2018年10月28日 下午4:42:04
	 */
	List<LtePlan> find(@Param("map") Map<String, Object> map);

	/**
	 * 
	 * @Description: 查询规划表相关的所有表的信息
	 * @author weichengz
	 * @date 2018年11月11日 上午8:41:06
	 */
	LtePlanInfo queryLtePlanInfo(@Param("id") Long id);

	/**
	 * 
	 * @Description: 保存
	 * @author weichengz
	 * @date 2018年10月28日 下午4:42:11
	 */
	void save(LtePlan ltePlan);

	/**
	 * 
	 * @Description: 查询
	 * @author weichengz
	 * @date 2018年10月28日 下午5:03:03
	 */
	@Select("select * from lte_plane where id=#{id}")
	LtePlan findById(@Param("id") Long id);

	/**
	 * 
	 * @Description: 修改
	 * @author weichengz
	 * @date 2018年10月28日 下午5:43:30
	 */
	void update(LtePlan ltePlan);

	Integer delete(@Param("idsArray") Long[] array);

	/**
	 * 根据用户的id跟项目的id查询任务列表 app
	 * 
	 * @param userId
	 * @param projId
	 * @return
	 */
	List<Map<String, Object>> queryPlanList(@Param("userId") Long userId, @Param("projId") Long projId,
			@Param("nowDate") String nowDate);

	/**
	 * 拉取基站列表 app
	 * 
	 * @param userId
	 * @param projId
	 * @param testDate
	 */
	List<BaseStationBean> queryStationList(@Param("userId") Long userId, @Param("projId") Long projId,
			@Param("testDate") String testDate);

	/**
	 * 审核
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2018年11月11日 下午7:02:24
	 */
	void check(@Param("id") Long id, @Param("statusM") Long statusM);

	 int insertSelective(LtePlan record);
	 int updateByPrimaryKeySelective(LtePlan record);
}
