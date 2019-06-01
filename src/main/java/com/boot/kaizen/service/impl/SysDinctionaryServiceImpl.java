package com.boot.kaizen.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.boot.kaizen.dao.SysDinctionaryDao;
import com.boot.kaizen.model.SysDic;
import com.boot.kaizen.service.SysDictionaryService;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:28:42
 */
@Service
public class SysDinctionaryServiceImpl implements SysDictionaryService {

	@Autowired
	private SysDinctionaryDao dinctionaryDao;

	@Override
	public List<SysDic> findByType(String type) {
		return dinctionaryDao.findByType(type);
	}

	

}
