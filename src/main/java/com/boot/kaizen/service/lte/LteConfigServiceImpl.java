package com.boot.kaizen.service.lte;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.boot.kaizen.dao.lte.LteConfigDao;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.model.lte.LteConfig;
import com.boot.kaizen.util.JsonMsgUtil;

@Service
class LteConfigServiceImpl implements ILteConfigService {

	@Autowired
	private LteConfigDao configDao;

	@Override
	public List<LteConfig> find(Map<String, Object> map) {
		return configDao.find(map);
	}

	@Override
	public JsonMsgUtil edit(LteConfig lteConfig, LoginUser loginUser) {
		if (loginUser == null) {
			throw new DisabledException("用户已过期，重新登陆");
		}
		if (lteConfig.getId() != null) {// edit
			lteConfig.setUpdateTime(new Date());
			configDao.update(lteConfig);
		} else {// add
			if (checkInfo(loginUser.getProjId())) {
				lteConfig.setProjId(loginUser.getProjId());
				lteConfig.setCreateAt(loginUser.getId());
				lteConfig.setCreateTime(new Date());
				configDao.save(lteConfig);
			} else {
				throw new IllegalArgumentException("该项目下已经存在测试配置项");
			}
		}
		return new JsonMsgUtil(true, "添加成功", lteConfig);
	}

	/**
	 * 
	 * @Description: 核对该项目下只有一个配置项
	 * @author weichengz
	 * @date 2019年2月2日 下午10:20:00
	 */
	private Boolean checkInfo(Long projId) {
		Boolean flag = true;
		Map<String, Object> map = new HashMap<>();
		map.put("projId", projId);
		List<LteConfig> lteConfigs = find(map);
		if (lteConfigs != null && lteConfigs.size() > 0) {
			flag = false;
		}
		return flag;
	}

	@Override
	public JsonMsgUtil findById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("未传入数据id");
		}
		LteConfig lteConfig = configDao.findById(id);
		if (lteConfig == null) {
			throw new IllegalArgumentException("查询的数据不存在");
		}
		return new JsonMsgUtil(true, "查询成功", lteConfig);
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
				Integer deleteNum = configDao.delete(array);
				j = new JsonMsgUtil(true, "删除操作成功", deleteNum);
			}
		} catch (Exception e) {
			throw e;
		}
		return j;
	}

	@Override
	public LteConfig findInfoById(Long projId) {
		return configDao.findInfoById(projId);
	}

	@Override
	public void changeStatus(Long id, Long status) {
		configDao.changeStatus(id, status);
	}

	@Override
	public List<LteConfig> queryListByProjId(Long projId) {
		List<LteConfig> list = new ArrayList<>();
		List<LteConfig> lteConfigs = configDao.queryListByProjId(projId);
		if (lteConfigs != null) {
			list = lteConfigs;
		}
		return list;
	}

}
