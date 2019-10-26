package com.boot.kaizen.business.es.model;
/**
 * Elasticsearch查询参数实体类设计
 * @author weichengz
 * @date 2019年4月3日 上午10:39:36
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class QueryParamData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String index="test";// 索引
	private String type="test";// 类型

	private Map<String, Object> matchMap;// 模糊匹配的map 全文检索的那种
	private Map<String, Object> termMap; // 精确查询的map
	private Map<String, Object> phraseMap; // 短语匹配

	private Map<String, Object> filterMap; // 过滤条件
	private Map<String, Object> sortMap; // 排序字段

	private List<String> revelFields;// 指定要显示的字段

	private Integer page = 1;// 默认页码
	private Integer size = 1000;// 默认数量 这个是分页显示的数量 或者是滚动查询的数量

	private List<Map<String, Object>> rows = new ArrayList<>();// 条件查询返回的数据
	private Long totalNums = 0L;// 满足查询条件的总数量

	private Map<String, Map<String, Long>> rangeMap; // 范围查询 目前支持 lte le gt gte组合

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

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
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
	* @Description: TODO
	* @author weichengz
	* @date 2019年10月17日 下午3:45:18
	 */
	public void verificationIndexType() {
		if (StringUtils.isBlank(this.index) || StringUtils.isBlank(this.type) ) {
			throw new IllegalArgumentException("索引跟类型不能为空");
		}
	}

}
