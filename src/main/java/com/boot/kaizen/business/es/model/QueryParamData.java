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

import com.boot.kaizen.util.MyDateUtil;

public class QueryParamData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String index = "test";// 索引
	private String type = "test";// 类型

	private Map<String, Object> matchMap;// 模糊匹配的map 全文检索的那种
	private Map<String, Object> termMap; // 精确查询的map
	private Map<String, Object> phraseMap; // 短语匹配

	private Map<String, Object> filterMap; // 过滤条件
	private boolean revelPk = true; // 是否显示记录得主键key 这个主键是es自带得

	private Map<String, Object> sortMap; // 排序字段

	private List<String> revelFields;// 指定要显示的字段

	private Integer page = 1;// 默认页码
	private Integer limit = 1000;// 默认数量 这个是分页显示的数量 或者是滚动查询的数量

	private List<Map<String, Object>> rows = new ArrayList<>();// 条件查询返回的数据
	private Long totalNums = 0L;// 满足查询条件的总数量

	private Map<String, Map<String, Long>> rangeMap=new HashMap<>(); // 范围查询 目前支持 lte le gt gte组合

	private Long targetTime;// 时间戳字段 自己使用得

	private String beginTime;// 开始时间 一般用于范围搜索的时候 使用
	private String endTime;// 结束时间
	private String pid;// 室外测试的列表id

	
	
	public QueryParamData(String index, String type, boolean revelPk, Integer limit) {
		super();
		this.index = index;
		this.type = type;
		this.revelPk = revelPk;
		this.limit = limit;
	}
	/**
	 * 自定义数据结构
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
	public QueryParamData(String index, String type, Map<String, Object> termMap, List<String> revelFields,Integer limit) {
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

}
