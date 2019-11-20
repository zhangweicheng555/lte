package com.boot.kaizen.business.es.model.sim;

import java.io.Serializable;
import java.util.List;

public class GcModelList implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private List<GcModel> RECORDS;

	public List<GcModel> getRECORDS() {
		return RECORDS;
	}

	public void setRECORDS(List<GcModel> rECORDS) {
		RECORDS = rECORDS;
	}

	public GcModelList(List<GcModel> rECORDS) {
		super();
		RECORDS = rECORDS;
	}

	public GcModelList() {
		super();
	}

}
