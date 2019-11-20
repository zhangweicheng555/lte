package com.boot.kaizen.business.es.service;

import java.util.List;

import com.boot.kaizen.business.es.model.sim.GcModel;

/**
 * 自动单验流程业务接口
 * 
 * @author weichengz
 * @date 2019年2月19日 上午12:28:05
 */
public interface GcModelService {

	List<GcModel> find();

}
