package com.boot.kaizen.service.lte;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.boot.kaizen.model.lte.LteCellStructuralValidation;
import com.boot.kaizen.util.MyUtil;
import com.boot.kaizen.dao.lte.LteCellStructuralValidationMapper;

@Service
class LteCellStructuralValidationServiceImpl implements ILteCellStructuralValidationService {

	@Autowired
	private LteCellStructuralValidationMapper lteCellStructuralValidationMapper;
	
	@Override
	public int insertSelective(LteCellStructuralValidation record) {
		return lteCellStructuralValidationMapper.insertSelective(record);
	}

	@Override
	public LteCellStructuralValidation selectByPrimaryKey(String id) {
		return lteCellStructuralValidationMapper.selectByPrimaryKey(id);
	}

	@Override
	public void upSert(LteCellStructuralValidation lteCellStructuralValidation) {
		Long  projId=lteCellStructuralValidation.getProjId();
		String eNodeBID=lteCellStructuralValidation.geteNodeBID();
		String testDate=lteCellStructuralValidation.getTestDate();
		
		if (StringUtils.isNoneBlank(eNodeBID) && projId != null && StringUtils.isNoneBlank(testDate)) {
			Map<String, Object> map=new HashMap<>();
			map.put("projId", projId);
			map.put("eNodeBID", eNodeBID);
			map.put("testDate", eNodeBID);
			List<LteCellStructuralValidation> lteCellStructuralValidations = query(map);
			if (lteCellStructuralValidations != null &&lteCellStructuralValidations.size()>0) {
				LteCellStructuralValidation model=lteCellStructuralValidations.get(0);
				lteCellStructuralValidation.setId(model.getId());
				lteCellStructuralValidationMapper.updateByPrimaryKeySelective(lteCellStructuralValidation);
			}else {
				lteCellStructuralValidation.setId(MyUtil.getUuid());
				lteCellStructuralValidationMapper.insertSelective(lteCellStructuralValidation);
			}
		}else {
			throw new IllegalArgumentException("projId/eNodeBID/testDate不能为空");
		}
	}

	@Override
	public List<LteCellStructuralValidation> query(Map<String, Object> map) {
		return lteCellStructuralValidationMapper.query(map);
	}

	
}
