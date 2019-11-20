package com.boot.kaizen.business.es.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.boot.kaizen.business.es.dao.GcModelMapper;
import com.boot.kaizen.business.es.model.sim.GcModel;

/**
 * 自动单验流程业务接口
 * 
 * @author weichengz
 * @date 2019年2月19日 上午12:28:53
 */
@Service
public class GcModelServiceImpl implements GcModelService {

	@Autowired
	private GcModelMapper gcModelMapper;

	@Override
	public List<GcModel> find() {

		return gcModelMapper.find();
	}

}
