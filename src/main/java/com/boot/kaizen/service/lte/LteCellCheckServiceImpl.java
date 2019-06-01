package com.boot.kaizen.service.lte;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.boot.kaizen.dao.lte.LteCellCheckDao;
import com.boot.kaizen.model.lte.LteCellCheck;
import com.boot.kaizen.util.JsonMsgUtil;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:33:27
 */
@Service
class LteCellCheckServiceImpl implements ILteCellCheckService {

	@Autowired
	private LteCellCheckDao cellCheckDao;

	@Override
	public List<LteCellCheck> find(Map<String, Object> map) {
		return cellCheckDao.find(map);
	}

	@Override
	public JsonMsgUtil delete(String ids) {
		JsonMsgUtil j = new JsonMsgUtil(false);
		try {
			if (StringUtils.isNoneBlank(ids)) {
				String[] idsArray = ids.trim().split(",");
				Long[] array = new Long[idsArray.length];
				for (int i = 0; i < idsArray.length; i++) {
					String id = idsArray[i];
					array[i] = Long.valueOf(id.trim());
				}
				// 删除项目
				Integer deleteNum = cellCheckDao.delete(array);
				j = new JsonMsgUtil(true, "删除操作成功", deleteNum);
			}
		} catch (Exception e) {
			throw e;
		}
		return j;
	}

	@Transactional
	@Override
	public void batchInsert(List<LteCellCheck> cellChecks) {
		LteCellCheck cellCheck = cellChecks.get(0);
		LteCellCheck model = checkInfo(cellCheck);
		if (model !=null) {
			cellCheck.setId(model.getId());
			cellCheck.setUpdateTime(new Date());
			updateByPrimaryKeySelective(cellCheck);
		}else {
			cellCheckDao.batchInsert(cellChecks);
		}
	}

	private LteCellCheck checkInfo(LteCellCheck cellCheck) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("eNodeBID", cellCheck.geteNodeBID());
		map.put("testDate", cellCheck.getTestDate());
		map.put("cellId", cellCheck.getCellId());
		map.put("projId", cellCheck.getProjId());
		List<LteCellCheck> lteCellChecks = find(map);
		if (lteCellChecks != null && lteCellChecks.size()>0) {
			return lteCellChecks.get(0);
		}
		return null;
	}

	@Override
	public void deleteByeNodeBID(String mENodeBID) {
		cellCheckDao.deleteByeNodeBID(mENodeBID);
	}

	@Override
	public void updateByPrimaryKeySelective(LteCellCheck lteCellCheck) {
		cellCheckDao.updateByPrimaryKeySelective(lteCellCheck);
	}

}
