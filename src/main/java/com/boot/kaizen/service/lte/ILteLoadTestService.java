package com.boot.kaizen.service.lte;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.boot.kaizen.model.lte.LteLoadTest;

/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:31:45
 */
public interface ILteLoadTestService {

	/**
	 * 上传路测信息 app
	 * 
	 * @param lteLoadTest
	 */
	void save(LteLoadTest lteLoadTest);

	void deleteByeNodeBID(String mENodeBID);

	List<LteLoadTest> findListByMenodeBID(@Param("map") Map<String, Object> map);
}
