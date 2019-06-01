package com.boot.kaizen.business.analyze.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.boot.kaizen.business.analyze.common.HighchartCommon;

@Mapper
public interface AnalyzeDao {
	/**
	 * @Description: lte单验 统计 总数 完成的数量 按天
	 * @author weichengz
	 * @date 2019年2月1日 下午12:49:35
	 */
	public List<HighchartCommon> analyzeLteAllAndCompleteByDay(@Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("projId") Long projId);

	/**
	 * 
	 * @Description: lte单验 统计 总数 完成的数量 按月
	 * @author weichengz
	 * @date 2019年2月2日 上午7:29:47
	 */
	public List<HighchartCommon> analyzeLteAllAndCompleteByMonth(@Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("projId") Long projId);

	/**
	 * 
	 * @Description: lte单验 统计 总数 完成的数量 按人
	 * @author weichengz
	 * @date 2019年2月2日 上午8:38:35
	 */
	public List<HighchartCommon> analyzeLteAllAndCompleteByPerson(@Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("projId") Long projId);

	/**
	 * 
	 * @Description: fdd tdd宏站 统计 总数 完成的数量 按天
	 * @author weichengz
	 * @date 2019年2月2日 下午3:00:25
	 */
	public List<HighchartCommon> analyzeFddThAllAndCompleteByDay(@Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("projId") Long projId, @Param("jzType") String jzType);

	/**
	 * 
	 * @Description: fdd tdd宏站 统计 总数 完成的数量 按月
	 * @author weichengz
	 * @date 2019年2月2日 下午3:01:06
	 */
	public List<HighchartCommon> analyzeFddThAllAndCompleteMonth(@Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("projId") Long projId, @Param("jzType") String jzType);

	/**
	 * 
	 * @Description: fdd tdd宏站 统计 总数 完成的数量 按人
	 * @author weichengz
	 * @date 2019年2月2日 下午3:06:16
	 */
	public List<HighchartCommon> analyzeFddThAllAndCompleteByPerson(@Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("projId") Long projId, @Param("jzType") String jzType);

	/**
	 * @Description: tddsf单验 统计 总数 完成的数量 按天
	 * @author weichengz
	 * @date 2019年2月1日 下午12:49:35
	 */
	public List<HighchartCommon> analyzeTddsfAllAndCompleteByDay(@Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("projId") Long projId);

	/**
	 * 
	 * @Description: tddsf单验 统计 总数 完成的数量 按月
	 * @author weichengz
	 * @date 2019年2月2日 上午7:29:47
	 */
	public List<HighchartCommon> analyzeTddsfAllAndCompleteByMonth(@Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("projId") Long projId);

	/**
	 * 
	 * @Description: tddsf单验 统计 总数 完成的数量 按人
	 * @author weichengz
	 * @date 2019年2月2日 上午8:38:35
	 */
	public List<HighchartCommon> analyzeTddsfAllAndCompleteByPerson(@Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("projId") Long projId);

	/**
	 * 自动单验
	 * 
	 * @Description: 根据厂家分公司 统计完成 未完成 出错误的数量
	 * @author weichengz
	 * @date 2019年4月15日 上午10:59:33
	 */
	public List<Map<String, Object>> analyzeAutoCheckForCompany(@Param("projId") Integer projId);

}
