package com.boot.kaizen.dao.lte;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.boot.kaizen.model.lte.LteLoadTest;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:15:52
 */
@Mapper
public interface LteLoadTestDao {

	/**
	 * 上传路测信息 app
	 * 
	 * @param lteLoadTest
	 */
	void save(LteLoadTest lteLoadTest);

	/**
	 * 
	 * @Description: 查询
	 * @author weichengz
	 * @date 2018年11月11日 上午8:28:32
	 */
	List<LteLoadTest> findListByMenodeBID(@Param("map") Map<String, Object> map);

	/**
	 * 根据站号删除
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2018年11月11日 下午10:47:21
	 */
	void deleteByeNodeBID(@Param("mENodeBID") String mENodeBID);
}
