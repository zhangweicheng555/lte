package com.boot.kaizen.service.lte;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.boot.kaizen.model.lte.LteCellParamters;
import com.boot.kaizen.util.MyUtil;
import com.boot.kaizen.dao.lte.LteCellParamtersMapper;

@Service
class LteCellParametersServiceImpl implements ILteCellParamtersService {

	@Autowired
	private LteCellParamtersMapper lteCellParamtersMapper;
	
	@Override
	public int insertSelective(LteCellParamters record) {
		return lteCellParamtersMapper.insertSelective(record);
	}

	@Override
	public LteCellParamters selectByPrimaryKey(String id) {
		return lteCellParamtersMapper.selectByPrimaryKey(id);
	}

	@Override
	public void upSert(LteCellParamters lteCellParamters) {
		Long  projId=lteCellParamters.getProjId();
		String cellId=lteCellParamters.getCellId();
		String eNodeBID=lteCellParamters.geteNodeBID();
		String testDate=lteCellParamters.getTestDate();
		
		if (StringUtils.isNoneBlank(cellId) && StringUtils.isNoneBlank(eNodeBID) && projId != null && StringUtils.isNoneBlank(testDate)) {
			Map<String, Object> map=new HashMap<>();
			map.put("projId", projId);
			map.put("cellId", cellId);
			map.put("eNodeBID", eNodeBID);
			map.put("testDate", testDate);
			List<LteCellParamters> lteCellParamtersList = query(map);
			if (lteCellParamtersList != null && lteCellParamtersList.size()>0) {
				LteCellParamters model=lteCellParamtersList.get(0);
				lteCellParamters.setId(model.getId());
				lteCellParamtersMapper.updateByPrimaryKeySelective(lteCellParamters);
			}else {
				lteCellParamters.setId(MyUtil.getUuid());
				lteCellParamtersMapper.insertSelective(lteCellParamters);
			}
		}else {
			throw new IllegalArgumentException("projId/cellId/eNodeBID/testDate不能为空");
		}
	}

	@Override
	public List<LteCellParamters> query(Map<String, Object> map) {
		return lteCellParamtersMapper.query(map);
	}

	
}
