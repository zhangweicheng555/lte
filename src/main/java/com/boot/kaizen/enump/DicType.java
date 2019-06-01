package com.boot.kaizen.enump;

import java.util.HashMap;
import java.util.Map;

/**
 * 与字典对应 字典枚举
 * 
 * @author weichengz
 * @date 2018年10月6日 下午6:35:44
 */
public enum DicType {

	/**按钮*/
	BTN("menu", "0", "按钮"), 
	
	/**菜单*/
	MENU("menu", "1", "菜单")
	
	/**男*/
	, MEAL("sex", "0", "男"), 
	
	/**女*/
	FEMEAL("sex", "1", "女")

	;

	private String type;
	private String k;
	private String val;

	private static Map<String, String> map = new HashMap<String, String>();
	static {
		for (DicType s : DicType.values()) {
			map.put(s.getk(), s.getType());
		}
	}

	private DicType(String type, String k, String val) {
		this.type = type;
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

	public String getType() {
		return this.type;
	}

	/**
	 * 
	 * @Description: 根据 k/type 得Val
	 * @author weichengz
	 * @date 2018年10月6日 下午9:57:45
	 */
	public static String getV(String k, String type) {
		for (DicType s : DicType.values()) {
			if (s.getk().equals(k) && s.getType().equals(type)) {
				return s.getval();
			}
		}
		return null;
	}

	public static String valOf(String k) {
		for (DicType s : DicType.values()) {
			if (s.getk().equals(k)) {
				return s.getval();
			}
		}
		return null;
	}

	public static Map<String, String> map() {
		return map;
	}

	public static DicType enumOf(String k) {
		for (DicType s : DicType.values()) {
			if (s.getk().equals(k)) {
				return s;
			}
		}
		return null;
	}

}