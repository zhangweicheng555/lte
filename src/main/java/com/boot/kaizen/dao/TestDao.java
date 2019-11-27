package com.boot.kaizen.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.boot.kaizen.model.ParamTest;
import com.boot.kaizen.model.SysTest;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:15:52
 */
@Mapper
public interface TestDao {

	public List<SysTest> findTest();

	public List<SysTest> findTest(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

	public List<SysTest> findTest(ParamTest paramTest);
}
