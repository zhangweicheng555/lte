package com.boot.kaizen.controller.lte.model;

/**
 * 小区数数据集
 * @author weichengz
 * @date 2019年5月31日 上午11:13:09
 */
public class CommunityBean {

	/* 小区名字 */private String mCommunityBeanName = "";
	// 对应小区号
	/* 小区工程 */private MCommunityProjectBean mCommunityProject;
	/* 小区网优 */private MCommunityNetworkOptimizationBean mCommunityNetworkOptimization;

	public String getmCommunityBeanName() {
		return mCommunityBeanName;
	}

	public void setmCommunityBeanName(String mCommunityBeanName) {
		this.mCommunityBeanName = mCommunityBeanName;
	}

	public MCommunityProjectBean getmCommunityProject() {
		return mCommunityProject;
	}

	public void setmCommunityProject(MCommunityProjectBean mCommunityProject) {
		this.mCommunityProject = mCommunityProject;
	}

	public MCommunityNetworkOptimizationBean getmCommunityNetworkOptimization() {
		return mCommunityNetworkOptimization;
	}

	public void setmCommunityNetworkOptimization(MCommunityNetworkOptimizationBean mCommunityNetworkOptimization) {
		this.mCommunityNetworkOptimization = mCommunityNetworkOptimization;
	}

	public CommunityBean() {
		super();
	}

}
