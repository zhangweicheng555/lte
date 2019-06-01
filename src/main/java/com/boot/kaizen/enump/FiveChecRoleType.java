package com.boot.kaizen.enump;

import java.util.HashMap;
import java.util.Map;

/**
 * 5g单验角色色设置
 * 
 * @author weichengz
 * @date 2019年5月29日 上午10:38:45
 */
public enum FiveChecRoleType {

	/**5G单验负责人*/
	FIVE_CHECK_LEADER("FIVE_CHECK_LEADER", "5G单验负责人")

	/**5G单验测试人*/
	, FIVE_CHECK_TEST("FIVE_CHECK_TEST", "5G单验测试人")

	;

	private String k;
	private String val;

	private static Map<String, String> map = new HashMap<String, String>();
	static {
		for (FiveChecRoleType s : FiveChecRoleType.values()) {
			map.put(s.getk(), s.getval());
		}
	}

	private FiveChecRoleType(String k, String val) {
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
	public static FiveChecRoleType getByLikeV(String v) {
		for (FiveChecRoleType s : FiveChecRoleType.values()) {
			if (v.contains(s.getval())) {
				return s;
			}
		}
		return null;
	}

	public static String getV(String k) {
		for (FiveChecRoleType s : FiveChecRoleType.values()) {
			if (s.getk().equals(k)) {
				return s.getval();
			}
		}
		return null;
	}

	public static Map<String, String> map() {
		return map;
	}

	public static FiveChecRoleType enumOf(String k) {
		for (FiveChecRoleType s : FiveChecRoleType.values()) {
			if (s.getk().equals(k)) {
				return s;
			}
		}
		return null;
	}

}