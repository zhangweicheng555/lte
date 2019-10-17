package com.boot.kaizen.config;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;

/**
 * 注意这个主键如果你传递，那么就采用你的 ，不传递会生成序列号 实现主键的自动复制,所有类都继承，注意类的ID
 * @author weichengz
 * @date 2019年10月17日 下午10:15:23
 */
@SuppressWarnings("rawtypes")
public class SerializeKeyConfig<T extends Model> extends Model<T> {
	private static final long serialVersionUID = 1L;

	protected String id;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
