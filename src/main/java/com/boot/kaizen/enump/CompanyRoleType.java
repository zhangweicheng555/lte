package com.boot.kaizen.enump;

import java.util.HashMap;
import java.util.Map;

/**
 * 上海联通流程角色记录 添加的角色必须包含这几个字
 * 
 * @author weichengz
 * @date 2018年10月6日 下午6:35:44
 */
public enum CompanyRoleType {

	/** 发布任务 【这个角色就可以】 */
	WANG_YOU_GUANLI_CHU("WANG_YOU_GUANLI_CHU", "网优管理处")

	/** 厂家任务确认 厂家审核 */
	,CHANG_JIA_DANYAN_LEADER("CHANG_JIA_DANYAN_LEADER", "厂家单验负责人")

	/** 区域任务分配 厂家区域审核 */
	,CHANG_JIA_DANYAN_REGION_LEADER("CHANG_JIA_DANYAN_REGION_LEADER", "厂家单验区域负责人"),

	CHECK_ENGINEER("CHECK_ENGINEER", "塔工")

	/** 勘站里面的 现场勘站 以上三个 【角色+厂家+分公司】 */

	/** 这个是处理性能测试的人员角色 */
	,TEST_ENGINEER("TEST_ENGINEER", "性能测试工程师")

	/** 报告审核 【角色+分区】 */
	,WANG_YOU_FEN_QU("WANG_YOU_FEN_QU", "网优分区")

	/** XI_TONG("XI_TONG", "系统")// 这个废弃 自动的 */
	,BACK_ENGINEER("BACK_ENGINEER", "后台工程师")

	;

	private String k;
	private String val;

	private static Map<String, String> map = new HashMap<String, String>();
	static {
		for (CompanyRoleType s : CompanyRoleType.values()) {
			map.put(s.getk(), s.getval());
		}
	}

	private CompanyRoleType(String k, String val) {
		this.k = k;
		this.val = val;
	}

	@Override
	public String toString() {
		return this.k;
	}

	public String getk() {
		return this.k;
	}

	public String getval() {
		return this.val;
	}

	/**
	 * @Description: 通过v模糊匹配
	 * @author weichengz
	 * @date 2019年2月17日 上午11:34:43
	 */
	public static CompanyRoleType getByLikeV(String v) {
		for (CompanyRoleType s : CompanyRoleType.values()) {
			if (v.contains(s.getval())) {
				return s;
			}
		}
		return null;
	}

	public static String getV(String k) {
		for (CompanyRoleType s : CompanyRoleType.values()) {
			if (s.getk().equals(k)) {
				return s.getval();
			}
		}
		return null;
	}

	public static Map<String, String> map() {
		return map;
	}

	public static CompanyRoleType enumOf(String k) {
		for (CompanyRoleType s : CompanyRoleType.values()) {
			if (s.getk().equals(k)) {
				return s;
			}
		}
		return null;
	}

}