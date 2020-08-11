package com.boot.kaizen.business.es.model.logModel;

import java.util.List;

/** 专业指标 */
public class ProIndicators5G {
	// 一下显示 start
	private String band_Index;
	private String bandwidth_DL_And_UL_Str;
	private String frequencyPointA_Str;
	private String frequency_DL_Str;
	private String gscn_Str;
	private String subcarrier_spacing;
	private String ssb_Index_Str;
	private String server_Cell_EARFCN_DL_And_PCI_Str;
	private String rsrpStr;
	private String sinrStr;
	private String rsrqStr;
	private String ue_TxPower_PUSCHStr;
	private String ue_TxPower_PUCCHStr;
	private String u_TxPower_SRS;
	private String cQI_Average_Str;
	private String mcs_value_UL_str;
	private String mcs_value_DL_str;
	private String modulation_UL;
	// stop

	// start DL
	private String modulation_DL;
	private String bler_UL_str;
	private String bler_DL_str;
	private String grant_Num_UL_Str;

	// start UL
	private String grant_Num_DL_Str;
	private String rank_index;
	private String pdcp_ul_throughputStr;
	private String pdcp_dl_throughputStr;
	private String rlc_ul_throughputStr;

	private String rlc_dl_throughputStr;
	private String mac_ul_throughputStr;
	private String mac_dl_throughputStr;
	private String rb_numslot_PUSCH_str;
	private String rb_numslot_PDSCH_str;
	
	private List<NeighborCellInfo> neighborCellInfo;
	
	
	
	
	public String getBand_Index() {
		return band_Index;
	}
	public void setBand_Index(String band_Index) {
		this.band_Index = band_Index;
	}
	public String getBandwidth_DL_And_UL_Str() {
		return bandwidth_DL_And_UL_Str;
	}
	public void setBandwidth_DL_And_UL_Str(String bandwidth_DL_And_UL_Str) {
		this.bandwidth_DL_And_UL_Str = bandwidth_DL_And_UL_Str;
	}
	public String getFrequencyPointA_Str() {
		return frequencyPointA_Str;
	}
	public void setFrequencyPointA_Str(String frequencyPointA_Str) {
		this.frequencyPointA_Str = frequencyPointA_Str;
	}
	public String getFrequency_DL_Str() {
		return frequency_DL_Str;
	}
	public void setFrequency_DL_Str(String frequency_DL_Str) {
		this.frequency_DL_Str = frequency_DL_Str;
	}
	public String getGscn_Str() {
		return gscn_Str;
	}
	public void setGscn_Str(String gscn_Str) {
		this.gscn_Str = gscn_Str;
	}
	public String getSubcarrier_spacing() {
		return subcarrier_spacing;
	}
	public void setSubcarrier_spacing(String subcarrier_spacing) {
		this.subcarrier_spacing = subcarrier_spacing;
	}
	public String getSsb_Index_Str() {
		return ssb_Index_Str;
	}
	public void setSsb_Index_Str(String ssb_Index_Str) {
		this.ssb_Index_Str = ssb_Index_Str;
	}
	public String getServer_Cell_EARFCN_DL_And_PCI_Str() {
		return server_Cell_EARFCN_DL_And_PCI_Str;
	}
	public void setServer_Cell_EARFCN_DL_And_PCI_Str(String server_Cell_EARFCN_DL_And_PCI_Str) {
		this.server_Cell_EARFCN_DL_And_PCI_Str = server_Cell_EARFCN_DL_And_PCI_Str;
	}
	public String getRsrpStr() {
		return rsrpStr;
	}
	public void setRsrpStr(String rsrpStr) {
		this.rsrpStr = rsrpStr;
	}
	public String getSinrStr() {
		return sinrStr;
	}
	public void setSinrStr(String sinrStr) {
		this.sinrStr = sinrStr;
	}
	public String getRsrqStr() {
		return rsrqStr;
	}
	public void setRsrqStr(String rsrqStr) {
		this.rsrqStr = rsrqStr;
	}
	public String getUe_TxPower_PUSCHStr() {
		return ue_TxPower_PUSCHStr;
	}
	public void setUe_TxPower_PUSCHStr(String ue_TxPower_PUSCHStr) {
		this.ue_TxPower_PUSCHStr = ue_TxPower_PUSCHStr;
	}
	public String getUe_TxPower_PUCCHStr() {
		return ue_TxPower_PUCCHStr;
	}
	public void setUe_TxPower_PUCCHStr(String ue_TxPower_PUCCHStr) {
		this.ue_TxPower_PUCCHStr = ue_TxPower_PUCCHStr;
	}
	public String getU_TxPower_SRS() {
		return u_TxPower_SRS;
	}
	public void setU_TxPower_SRS(String u_TxPower_SRS) {
		this.u_TxPower_SRS = u_TxPower_SRS;
	}
	public String getcQI_Average_Str() {
		return cQI_Average_Str;
	}
	public void setcQI_Average_Str(String cQI_Average_Str) {
		this.cQI_Average_Str = cQI_Average_Str;
	}
	public String getMcs_value_UL_str() {
		return mcs_value_UL_str;
	}
	public void setMcs_value_UL_str(String mcs_value_UL_str) {
		this.mcs_value_UL_str = mcs_value_UL_str;
	}
	public String getMcs_value_DL_str() {
		return mcs_value_DL_str;
	}
	public void setMcs_value_DL_str(String mcs_value_DL_str) {
		this.mcs_value_DL_str = mcs_value_DL_str;
	}
	public String getModulation_UL() {
		return modulation_UL;
	}
	public void setModulation_UL(String modulation_UL) {
		this.modulation_UL = modulation_UL;
	}
	public String getModulation_DL() {
		return modulation_DL;
	}
	public void setModulation_DL(String modulation_DL) {
		this.modulation_DL = modulation_DL;
	}
	public String getBler_UL_str() {
		return bler_UL_str;
	}
	public void setBler_UL_str(String bler_UL_str) {
		this.bler_UL_str = bler_UL_str;
	}
	public String getBler_DL_str() {
		return bler_DL_str;
	}
	public void setBler_DL_str(String bler_DL_str) {
		this.bler_DL_str = bler_DL_str;
	}
	public String getGrant_Num_UL_Str() {
		return grant_Num_UL_Str;
	}
	public void setGrant_Num_UL_Str(String grant_Num_UL_Str) {
		this.grant_Num_UL_Str = grant_Num_UL_Str;
	}
	public String getGrant_Num_DL_Str() {
		return grant_Num_DL_Str;
	}
	public void setGrant_Num_DL_Str(String grant_Num_DL_Str) {
		this.grant_Num_DL_Str = grant_Num_DL_Str;
	}
	public String getRank_index() {
		return rank_index;
	}
	public void setRank_index(String rank_index) {
		this.rank_index = rank_index;
	}
	public String getPdcp_ul_throughputStr() {
		return pdcp_ul_throughputStr;
	}
	public void setPdcp_ul_throughputStr(String pdcp_ul_throughputStr) {
		this.pdcp_ul_throughputStr = pdcp_ul_throughputStr;
	}
	public String getPdcp_dl_throughputStr() {
		return pdcp_dl_throughputStr;
	}
	public void setPdcp_dl_throughputStr(String pdcp_dl_throughputStr) {
		this.pdcp_dl_throughputStr = pdcp_dl_throughputStr;
	}
	public String getRlc_ul_throughputStr() {
		return rlc_ul_throughputStr;
	}
	public void setRlc_ul_throughputStr(String rlc_ul_throughputStr) {
		this.rlc_ul_throughputStr = rlc_ul_throughputStr;
	}
	public String getRlc_dl_throughputStr() {
		return rlc_dl_throughputStr;
	}
	public void setRlc_dl_throughputStr(String rlc_dl_throughputStr) {
		this.rlc_dl_throughputStr = rlc_dl_throughputStr;
	}
	public String getMac_ul_throughputStr() {
		return mac_ul_throughputStr;
	}
	public void setMac_ul_throughputStr(String mac_ul_throughputStr) {
		this.mac_ul_throughputStr = mac_ul_throughputStr;
	}
	public String getMac_dl_throughputStr() {
		return mac_dl_throughputStr;
	}
	public void setMac_dl_throughputStr(String mac_dl_throughputStr) {
		this.mac_dl_throughputStr = mac_dl_throughputStr;
	}
	public String getRb_numslot_PUSCH_str() {
		return rb_numslot_PUSCH_str;
	}
	public void setRb_numslot_PUSCH_str(String rb_numslot_PUSCH_str) {
		this.rb_numslot_PUSCH_str = rb_numslot_PUSCH_str;
	}
	public String getRb_numslot_PDSCH_str() {
		return rb_numslot_PDSCH_str;
	}
	public void setRb_numslot_PDSCH_str(String rb_numslot_PDSCH_str) {
		this.rb_numslot_PDSCH_str = rb_numslot_PDSCH_str;
	}
	
	public ProIndicators5G() {
		super();
	}
	public List<NeighborCellInfo> getNeighborCellInfo() {
		return neighborCellInfo;
	}
	public void setNeighborCellInfo(List<NeighborCellInfo> neighborCellInfo) {
		this.neighborCellInfo = neighborCellInfo;
	}
	public ProIndicators5G(String band_Index, String bandwidth_DL_And_UL_Str, String frequencyPointA_Str,
			String frequency_DL_Str, String gscn_Str, String subcarrier_spacing, String ssb_Index_Str,
			String server_Cell_EARFCN_DL_And_PCI_Str, String rsrpStr, String sinrStr, String rsrqStr,
			String ue_TxPower_PUSCHStr, String ue_TxPower_PUCCHStr, String u_TxPower_SRS, String cQI_Average_Str,
			String mcs_value_UL_str, String mcs_value_DL_str, String modulation_UL, String modulation_DL,
			String bler_UL_str, String bler_DL_str, String grant_Num_UL_Str, String grant_Num_DL_Str, String rank_index,
			String pdcp_ul_throughputStr, String pdcp_dl_throughputStr, String rlc_ul_throughputStr,
			String rlc_dl_throughputStr, String mac_ul_throughputStr, String mac_dl_throughputStr,
			String rb_numslot_PUSCH_str, String rb_numslot_PDSCH_str, List<NeighborCellInfo> neighborCellInfo) {
		super();
		this.band_Index = band_Index;
		this.bandwidth_DL_And_UL_Str = bandwidth_DL_And_UL_Str;
		this.frequencyPointA_Str = frequencyPointA_Str;
		this.frequency_DL_Str = frequency_DL_Str;
		this.gscn_Str = gscn_Str;
		this.subcarrier_spacing = subcarrier_spacing;
		this.ssb_Index_Str = ssb_Index_Str;
		this.server_Cell_EARFCN_DL_And_PCI_Str = server_Cell_EARFCN_DL_And_PCI_Str;
		this.rsrpStr = rsrpStr;
		this.sinrStr = sinrStr;
		this.rsrqStr = rsrqStr;
		this.ue_TxPower_PUSCHStr = ue_TxPower_PUSCHStr;
		this.ue_TxPower_PUCCHStr = ue_TxPower_PUCCHStr;
		this.u_TxPower_SRS = u_TxPower_SRS;
		this.cQI_Average_Str = cQI_Average_Str;
		this.mcs_value_UL_str = mcs_value_UL_str;
		this.mcs_value_DL_str = mcs_value_DL_str;
		this.modulation_UL = modulation_UL;
		this.modulation_DL = modulation_DL;
		this.bler_UL_str = bler_UL_str;
		this.bler_DL_str = bler_DL_str;
		this.grant_Num_UL_Str = grant_Num_UL_Str;
		this.grant_Num_DL_Str = grant_Num_DL_Str;
		this.rank_index = rank_index;
		this.pdcp_ul_throughputStr = pdcp_ul_throughputStr;
		this.pdcp_dl_throughputStr = pdcp_dl_throughputStr;
		this.rlc_ul_throughputStr = rlc_ul_throughputStr;
		this.rlc_dl_throughputStr = rlc_dl_throughputStr;
		this.mac_ul_throughputStr = mac_ul_throughputStr;
		this.mac_dl_throughputStr = mac_dl_throughputStr;
		this.rb_numslot_PUSCH_str = rb_numslot_PUSCH_str;
		this.rb_numslot_PDSCH_str = rb_numslot_PDSCH_str;
		this.neighborCellInfo = neighborCellInfo;
	}

	
	
	
}
