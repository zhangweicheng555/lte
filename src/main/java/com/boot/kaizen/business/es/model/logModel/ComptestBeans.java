package com.boot.kaizen.business.es.model.logModel;

import java.util.ArrayList;

public class ComptestBeans {

	private ArrayList<MLteNeighborhoodInfo> mLteNeighborhoodInfos;
	private double lng;
	private double lat;
	private String time;

	public ArrayList<MLteNeighborhoodInfo> getmLteNeighborhoodInfos() {
		return mLteNeighborhoodInfos;
	}

	public void setmLteNeighborhoodInfos(ArrayList<MLteNeighborhoodInfo> mLteNeighborhoodInfos) {
		this.mLteNeighborhoodInfos = mLteNeighborhoodInfos;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public ComptestBeans(ArrayList<MLteNeighborhoodInfo> mLteNeighborhoodInfos, double lng, double lat, String time) {
		super();
		this.mLteNeighborhoodInfos = mLteNeighborhoodInfos;
		this.lng = lng;
		this.lat = lat;
		this.time = time;
	}

	public ComptestBeans() {
		super();
	}

}
