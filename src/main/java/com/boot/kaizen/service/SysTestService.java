package com.boot.kaizen.service;

import java.util.List;

import com.boot.kaizen.model.ParamTest;
import com.boot.kaizen.model.SysTest;

/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:26:11
 */
public interface SysTestService {

	public List<SysTest> findTest();

	public List<SysTest> findTest(int pageNum,int pageSize);

	public List<SysTest> findTest(ParamTest paramTest);
}
