package com.boot.kaizen.business.analyze.service;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.boot.kaizen.business.analyze.common.HighchartCommon;
import com.boot.kaizen.business.analyze.dao.AnalyzeDao;
import com.boot.kaizen.business.analyze.model.HighchartModel;
import com.boot.kaizen.business.analyze.model.Highcharty;
import com.boot.kaizen.business.analyze.model.HighchartyData;
import com.boot.kaizen.enump.ChartConstant;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.MyDateUtil;

/**
 * 数据分析业务逻辑
 * 
 * @author weichengz
 * @date 2019年2月1日 上午10:15:02
 */
@Service
public class AnalyzeService implements IAnalyzeService {

	@Autowired
	private AnalyzeDao analyzeDao;

	@Override
	public JsonMsgUtil analyzeLteAllAndComplete(String beginTime, String endTime, String type, Long projId) {
		HighchartModel highchartModel = new HighchartModel();
		Highcharty hyAll = new Highcharty("总数");
		Highcharty hyFinish = new Highcharty("完成数量");

		if (StringUtils.isBlank(type)) {
			throw new IllegalArgumentException("类型不能为空");
		}

		if (StringUtils.isBlank(beginTime) && StringUtils.isBlank(endTime)) {
			endTime = MyDateUtil.getNowDate("yyyy-MM-dd");
			beginTime = MyDateUtil.getSomeDateByDay(endTime, -14);
		}
		List<HighchartCommon> highchartCommons = null;
		if (ChartConstant.CHART_DAY.equals(type)) {
			highchartCommons = analyzeDao.analyzeLteAllAndCompleteByDay(beginTime, endTime, projId);
		}
		if (ChartConstant.CHART_MONTH.equals(type)) {
			highchartCommons = analyzeDao.analyzeLteAllAndCompleteByMonth(beginTime, endTime, projId);
		}

		if (highchartCommons != null && highchartCommons.size() > 0) {
			for (HighchartCommon highchartCommon : highchartCommons) {

				highchartModel.getCategories().add(highchartCommon.getName());

				hyAll.setType("line");
				hyAll.getData().add(new HighchartyData(highchartCommon.getAllNum()));

				hyFinish.getData().add(new HighchartyData(highchartCommon.getFinishNum()));

			}
			highchartModel.getSeries().add(hyAll);
			highchartModel.getSeries().add(hyFinish);
		}
		highchartModel.setBeginTime(beginTime);
		highchartModel.setEndTime(endTime);

		return new JsonMsgUtil(true, "查询成功", highchartModel);
	}

	@Override
	public JsonMsgUtil analyzeLteAllAndCompleteByPerson(String beginTime, String endTime, Long projId) {
		HighchartModel highchartModel = new HighchartModel();
		Highcharty hyAll = new Highcharty("总数");
		Highcharty hyFinish = new Highcharty("完成数量");

		if (StringUtils.isBlank(beginTime) && StringUtils.isBlank(endTime)) {
			endTime = MyDateUtil.getNowDate("yyyy-MM-dd");
			beginTime = MyDateUtil.getSomeDateByDay(endTime, -14);
		}
		List<HighchartCommon> highchartCommons = analyzeDao.analyzeLteAllAndCompleteByPerson(beginTime, endTime,
				projId);

		if (highchartCommons != null && highchartCommons.size() > 0) {
			for (HighchartCommon highchartCommon : highchartCommons) {

				highchartModel.getCategories().add(highchartCommon.getName());

				hyAll.setType("line");
				hyAll.getData().add(new HighchartyData(highchartCommon.getAllNum()));

				hyFinish.getData().add(new HighchartyData(highchartCommon.getFinishNum()));

			}
			highchartModel.getSeries().add(hyAll);
			highchartModel.getSeries().add(hyFinish);
		}
		highchartModel.setBeginTime(beginTime);
		highchartModel.setEndTime(endTime);

		return new JsonMsgUtil(true, "查询成功", highchartModel);
	}

	
}
