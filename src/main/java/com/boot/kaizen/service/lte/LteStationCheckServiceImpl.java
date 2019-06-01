package com.boot.kaizen.service.lte;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boot.kaizen.dao.lte.LteStationCheckDao;
import com.boot.kaizen.model.lte.LteStationCheck;
import com.boot.kaizen.util.JsonMsgUtil;

@Service
class LteStationCheckServiceImpl implements ILteStationCheckService {

	@Autowired
	private LteStationCheckDao stationCheckDao;

	@Override
	public List<LteStationCheck> find(Map<String, Object> map) {
		return stationCheckDao.find(map);
	}

	@Transactional(rollbackFor=Exception.class)
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
				Integer deleteNum = stationCheckDao.delete(array);
				j = new JsonMsgUtil(true, "删除操作成功", deleteNum);
			}
		} catch (Exception e) {
			throw e;
		}
		return j;
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public void batchInsert(List<LteStationCheck> stationChecks) {
		LteStationCheck cellCheck = stationChecks.get(0);
		LteStationCheck model = checkAppIndo(cellCheck);
		if (model !=null) {
			cellCheck.setId(model.getId());
			cellCheck.setUpdateTime(new Date());
			updateInfo(cellCheck);
		}else {
			stationCheckDao.batchInsert(stationChecks);
		}
	}

	/**
	 * 
	* @Description: 校验信息该项目下是否存在改站号的信息
	* @author weichengz
	* @date 2019年2月3日 上午11:55:26
	 */
	private LteStationCheck checkAppIndo(LteStationCheck cellCheck) {
		Map<String, Object> map=new HashMap<>();
		map.put("eNodeBID", cellCheck.geteNodeBID());
		map.put("testDate", cellCheck.getTestDate());
		map.put("projId", cellCheck.getProjId());
		List<LteStationCheck> lteStationChecks = find(map);
		if (lteStationChecks != null && lteStationChecks.size()>0) {
			return lteStationChecks.get(0);
		}
		return null;
	}

	@Override
	public LteStationCheck findById(Long id) {
		return stationCheckDao.findById(id);
	}

	@Override
	public void deleteByeNodeBID(String mENodeBID) {
		stationCheckDao.deleteByeNodeBID(mENodeBID);
	}

	@Override
	public void updateInfo(LteStationCheck stationCheck) {
		stationCheckDao.updateInfo(stationCheck);
	}

}
