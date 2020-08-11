package com.boot.kaizen.business.es.model.logModel;

/** 专业指标 */
public class NeighborCellInfo {
	//一下显示 start
	private String neighbor_Cell_EARFCN;
	private String neighbor_Cell_PCI;
	private String neighbor_Cell_RSRP;
	private String neighbor_Cell_RSRQ;
	private String neighbor_Cell_RSSI;
	private String neighbor_Cell_beam_id;
	private String neighbor_Cell_SINR;
	private String beams_Count;
	public String getNeighbor_Cell_EARFCN() {
		return neighbor_Cell_EARFCN;
	}
	public void setNeighbor_Cell_EARFCN(String neighbor_Cell_EARFCN) {
		this.neighbor_Cell_EARFCN = neighbor_Cell_EARFCN;
	}
	public String getNeighbor_Cell_PCI() {
		return neighbor_Cell_PCI;
	}
	public void setNeighbor_Cell_PCI(String neighbor_Cell_PCI) {
		this.neighbor_Cell_PCI = neighbor_Cell_PCI;
	}
	public String getNeighbor_Cell_RSRP() {
		return neighbor_Cell_RSRP;
	}
	public void setNeighbor_Cell_RSRP(String neighbor_Cell_RSRP) {
		this.neighbor_Cell_RSRP = neighbor_Cell_RSRP;
	}
	public String getNeighbor_Cell_RSRQ() {
		return neighbor_Cell_RSRQ;
	}
	public void setNeighbor_Cell_RSRQ(String neighbor_Cell_RSRQ) {
		this.neighbor_Cell_RSRQ = neighbor_Cell_RSRQ;
	}
	public String getNeighbor_Cell_RSSI() {
		return neighbor_Cell_RSSI;
	}
	public void setNeighbor_Cell_RSSI(String neighbor_Cell_RSSI) {
		this.neighbor_Cell_RSSI = neighbor_Cell_RSSI;
	}
	public String getNeighbor_Cell_beam_id() {
		return neighbor_Cell_beam_id;
	}
	public void setNeighbor_Cell_beam_id(String neighbor_Cell_beam_id) {
		this.neighbor_Cell_beam_id = neighbor_Cell_beam_id;
	}
	public String getNeighbor_Cell_SINR() {
		return neighbor_Cell_SINR;
	}
	public void setNeighbor_Cell_SINR(String neighbor_Cell_SINR) {
		this.neighbor_Cell_SINR = neighbor_Cell_SINR;
	}
	public String getBeams_Count() {
		return beams_Count;
	}
	public void setBeams_Count(String beams_Count) {
		this.beams_Count = beams_Count;
	}
	public NeighborCellInfo(String neighbor_Cell_EARFCN, String neighbor_Cell_PCI, String neighbor_Cell_RSRP,
			String neighbor_Cell_RSRQ, String neighbor_Cell_RSSI, String neighbor_Cell_beam_id,
			String neighbor_Cell_SINR, String beams_Count) {
		super();
		this.neighbor_Cell_EARFCN = neighbor_Cell_EARFCN;
		this.neighbor_Cell_PCI = neighbor_Cell_PCI;
		this.neighbor_Cell_RSRP = neighbor_Cell_RSRP;
		this.neighbor_Cell_RSRQ = neighbor_Cell_RSRQ;
		this.neighbor_Cell_RSSI = neighbor_Cell_RSSI;
		this.neighbor_Cell_beam_id = neighbor_Cell_beam_id;
		this.neighbor_Cell_SINR = neighbor_Cell_SINR;
		this.beams_Count = beams_Count;
	}
	public NeighborCellInfo() {
		super();
	}

	
	
	
}
