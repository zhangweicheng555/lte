package com.boot.kaizen.service.lte;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.boot.kaizen.dao.lte.LteLoadTestDao;
import com.boot.kaizen.model.lte.LteLoadTest;
import com.boot.kaizen.util.FileUtil;

@Service
class LteLoadTestServiceImpl implements ILteLoadTestService {

	@Value("${files.path}")
	private String filesPath;
	@Autowired
	private LteLoadTestDao loadTestDao;

	@Transactional
	@Override
	public void save(LteLoadTest lteLoadTest) {
		// 保存数据
		loadTestDao.save(lteLoadTest);
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteByeNodeBID(String mENodeBID) {
		// 查询数据
		Map<String, Object> map = new HashMap<>();
		map.put("eNodeBID", mENodeBID);
		List<LteLoadTest> lteLoadTests = findListByMenodeBID(map);

		// 删除数据
		loadTestDao.deleteByeNodeBID(mENodeBID);

		// 删除图片
		if (lteLoadTests != null && lteLoadTests.size() > 0) {
			for (LteLoadTest lteLoadTest : lteLoadTests) {
				deletePic(lteLoadTest, filesPath);
			}
		}
	}

	@Override
	public List<LteLoadTest> findListByMenodeBID(Map<String, Object> map) {
		return loadTestDao.findListByMenodeBID(map);
	}

	/**
	 * 删除图片
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年2月13日 上午11:59:56
	 */
	private void deletePic(LteLoadTest lteLoadTest, String basePath) {
		if (StringUtils.isNoneBlank(lteLoadTest.getSinrThresholdImage())) {
			FileUtil.deleteFileByName(basePath, lteLoadTest.getSinrThresholdImage());
		}
		if (StringUtils.isNoneBlank(lteLoadTest.getRsrpThresholdImage())) {
			FileUtil.deleteFileByName(basePath, lteLoadTest.getRsrpThresholdImage());
		}
		if (StringUtils.isNoneBlank(lteLoadTest.getUpFtpRateThresholdImage())) {
			FileUtil.deleteFileByName(basePath, lteLoadTest.getUpFtpRateThresholdImage());
		}
		if (StringUtils.isNoneBlank(lteLoadTest.getDownFtpRateThresholdImage())) {
			FileUtil.deleteFileByName(basePath, lteLoadTest.getDownFtpRateThresholdImage());
		}
		if (StringUtils.isNoneBlank(lteLoadTest.getRoadLogFile())) {
			FileUtil.deleteFileByName(basePath, lteLoadTest.getRoadLogFile());
		}
		String rsrpFtpUpImage = lteLoadTest.getRsrpFtpUpImage();
		if (StringUtils.isNoneBlank(rsrpFtpUpImage)) {
			String[] images = rsrpFtpUpImage.split(",");
			for (String image : images) {
				FileUtil.deleteFileByName(basePath, image);
			}
		}
	}

}
