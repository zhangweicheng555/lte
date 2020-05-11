package com.boot.kaizen.business.es.model;
/**
 * Elasticsearch查询参数实体类设计
 * @author weichengz
 * @date 2019年4月3日 上午10:39:36
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.geo.GeoPoint;

import com.boot.kaizen.util.MyDateUtil;

public class QueryParamData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String index;// 索引
	private String type;// 类型

	private Map<String, Object> matchMap;// 模糊匹配的map 全文检索的那种
	private Map<String, Object> termMap; // 精确查询的map
	private Map<String, Object> phraseMap; // 短语匹配

	private Map<String, List<GeoPoint>> geoMap; // 仅仅支持2个 多点图形查询(这个没有顺序之分)、对角查询（注意对角查询的时候 第一个是左上角 第二个是右下角） key是字段

	private Map<String, Map<GeoPoint, Double>> diatinceGeoMap; // k：经纬度字段的名字 GeoPoint 中心点 Double 是距离多少范围内 注意 这里的单位是 1万公里

	private Map<String, GeoPoint> sortGeoMap; // 地理查询的时候的排序 仅仅支持ASC key匹配的地理字段 v:中心点
	private Map<String, Object> sortMap; // 排序字段 两个排序 一般最好选择 之一

	// 这个ormap可以用于根据多个id查询数据
	private Map<String, List<Object>> orMap; // or条件模式查询时用(or的时候 仅仅支持termMap联合使用) 注意 如果or中有过滤的条件 放在 termMap 里面 (a=b and
												// c=d) or (a=b and f=e) 这个a就可以放在termMap里面
												// list 是key对应的值列表

	private Map<String, Object> filterMap; // 过滤条件 这个目前的理解是【查询结束之后 执行的操作 不建议使用】
	private boolean revelPk = true; // 是否显示记录得主键key 这个主键是es自带得

	private List<String> revelFields;// 指定要显示的字段
	private List<String> excludeFields;// 指定派出的字段 这俩个 使用其中一个

	private Integer page = 1;// 默认页码
	private Integer limit = 1000;// 默认数量 这个是分页显示的数量 或者是滚动查询的数量

	private List<Map<String, Object>> rows = new ArrayList<>();// 条件查询返回的数据
	private Long totalNums = 0L;// 满足查询条件的总数量

	private Map<String, Map<String, Long>> rangeMap = new HashMap<>(); // 范围查询 目前支持 lte le gt gte组合

	private Long targetTime;// 时间戳字段 自己使用得

	private String beginTime;// 开始时间 一般用于范围搜索的时候 使用
	private String endTime;// 结束时间
	private String pid;// 室外测试的列表id
	private String id;// 日志主体的每个点的主键id
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public QueryParamData(String index, String type) {
		super();
		this.index = index;
		this.type = type;
	}

	public List<String> getExcludeFields() {
		return excludeFields;
	}

	public void setExcludeFields(List<String> excludeFields) {
		this.excludeFields = excludeFields;
	}

	public Map<String, List<Object>> getOrMap() {
		return orMap;
	}

	public void setOrMap(Map<String, List<Object>> orMap) {
		this.orMap = orMap;
	}

	public Map<String, GeoPoint> getSortGeoMap() {
		return sortGeoMap;
	}

	public void setSortGeoMap(Map<String, GeoPoint> sortGeoMap) {
		this.sortGeoMap = sortGeoMap;
	}

	public Map<String, Map<GeoPoint, Double>> getDiatinceGeoMap() {
		return diatinceGeoMap;
	}

	public void setDiatinceGeoMap(Map<String, Map<GeoPoint, Double>> diatinceGeoMap) {
		this.diatinceGeoMap = diatinceGeoMap;
	}

	public Map<String, List<GeoPoint>> getGeoMap() {
		return geoMap;
	}

	public void setGeoMap(Map<String, List<GeoPoint>> geoMap) {
		this.geoMap = geoMap;
	}

	public QueryParamData(String index, String type, boolean revelPk, Integer limit) {
		super();
		this.index = index;
		this.type = type;
		this.revelPk = revelPk;
		this.limit = limit;
	}

	/**
	 * 自定义数据结构
	 * 
	 * @author weichengz
	 * @date 2019年11月13日 上午11:41:44
	 */
	public QueryParamData(String index, String type, Map<String, Object> termMap, List<String> revelFields,
			Integer page, Integer limit) {
		super();
		this.index = index;
		this.type = type;
		this.termMap = termMap;
		this.revelFields = revelFields;
		this.page = page;
		this.limit = limit;
	}

	public QueryParamData(String index, String type, Map<String, Object> termMap, List<String> revelFields,
			Integer limit) {
		super();
		this.index = index;
		this.type = type;
		this.termMap = termMap;
		this.revelFields = revelFields;
		this.limit = limit;
	}

	/**
	 * 
	 * @Description: 处理某个字段关于范围的问题
	 * @author weichengz
	 * @date 2019年11月11日 下午4:32:23
	 */
	public void handleFieldRange(String field, String beginTime, String endTime) {
		Map<String, Long> paramMap = new HashMap<String, Long>();
		if (StringUtils.isNotBlank(beginTime)) {
			Date date = MyDateUtil.stringToDate(beginTime, "yyyy-MM-dd HH:mm:ss");
			if (date != null) {
				paramMap.put("GTE", date.getTime());
			}
		}
		if (StringUtils.isNotBlank(endTime)) {
			Date date = MyDateUtil.stringToDate(endTime, "yyyy-MM-dd HH:mm:ss");
			if (date != null) {
				paramMap.put("LTE", date.getTime());
			}
		}
		if (!paramMap.isEmpty()) {
			this.rangeMap.put(field, paramMap);
		}
	}

	public String getBeginTime() {
		return beginTime;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public QueryParamData(String index, String type, Map<String, Object> termMap, boolean revelPk,
			List<String> revelFields, Integer limit) {
		super();
		this.index = index;
		this.type = type;
		this.termMap = termMap;
		this.revelPk = revelPk;
		this.revelFields = revelFields;
		this.limit = limit;
	}

	public Long getTargetTime() {
		return targetTime;
	}

	public void setTargetTime(Long targetTime) {
		this.targetTime = targetTime;
	}

	public boolean isRevelPk() {
		return revelPk;
	}

	public void setRevelPk(boolean revelPk) {
		this.revelPk = revelPk;
	}

	public List<String> getRevelFields() {
		return revelFields;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, Map<String, Long>> getRangeMap() {
		return rangeMap;
	}

	public void setRangeMap(Map<String, Map<String, Long>> rangeMap) {
		this.rangeMap = rangeMap;
	}

	public void setRevelFields(List<String> revelFields) {
		this.revelFields = revelFields;
	}

	public List<Map<String, Object>> getRows() {
		return rows;
	}

	public Map<String, Object> getSortMap() {
		return sortMap;
	}

	public void setSortMap(Map<String, Object> sortMap) {
		this.sortMap = sortMap;
	}

	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
	}

	public Long getTotalNums() {
		return totalNums;
	}

	public void setTotalNums(Long totalNums) {
		this.totalNums = totalNums;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Map<String, Object> getFilterMap() {
		return filterMap;
	}

	public void setFilterMap(Map<String, Object> filterMap) {
		this.filterMap = filterMap;
	}

	public Map<String, Object> getMatchMap() {
		return matchMap;
	}

	public Map<String, Object> getPhraseMap() {
		return phraseMap;
	}

	public void setPhraseMap(Map<String, Object> phraseMap) {
		this.phraseMap = phraseMap;
	}

	public void setMatchMap(Map<String, Object> matchMap) {
		this.matchMap = matchMap;
	}

	public Map<String, Object> getTermMap() {
		return termMap;
	}

	public void setTermMap(Map<String, Object> termMap) {
		this.termMap = termMap;
	}

	public QueryParamData(Map<String, Object> matchMap, Map<String, Object> termMap, Map<String, Object> phraseMap) {
		super();
		this.matchMap = matchMap;
		this.termMap = termMap;
		this.phraseMap = phraseMap;
	}

	public QueryParamData() {
	}

	/**
	 * 校验索引跟类型必须存在
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月17日 下午3:45:18
	 */
	public void verificationIndexType() {
		if (StringUtils.isBlank(this.index) || StringUtils.isBlank(this.type)) {
			throw new IllegalArgumentException("索引跟类型不能为空");
		}
	}

	/**
	 * 处理 距离这个点 1万公里的点的距离 参数设置
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月22日 下午2:41:53
	 */
	public void dealGeoDiatanceBuss(GeoPoint ceterPoint, double d, String geoField) {
		// 处理点位置
		Map<String, Map<GeoPoint, Double>> diatinceGeoMapModel = new HashMap<>();
		Map<GeoPoint, Double> ceterPointMap = new HashMap<>();
		ceterPointMap.put(ceterPoint, d);
		diatinceGeoMapModel.put(geoField, ceterPointMap);
		this.diatinceGeoMap = diatinceGeoMapModel;

		// 处理排序
		Map<String, GeoPoint> sortGeoMapModel = new HashMap<>();
		sortGeoMapModel.put(geoField, ceterPoint);
		this.sortGeoMap = sortGeoMapModel;
	}

}
