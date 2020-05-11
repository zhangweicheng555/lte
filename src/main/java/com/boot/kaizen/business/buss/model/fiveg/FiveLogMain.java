package com.boot.kaizen.business.buss.model.fiveg;

import java.io.Serializable;
import java.util.List;
import com.boot.kaizen.business.buss.model.fiveg.model.LteDataInfoBean;
import com.boot.kaizen.business.buss.model.fiveg.model.NrDataInfoBean;
import com.boot.kaizen.business.buss.model.fiveg.model.NrLogBodyBean;
import com.boot.kaizen.business.buss.model.fiveg.model.NrLogHeadBean;
import com.boot.kaizen.business.buss.model.fiveg.model.ProLteDataInfoBean;
import com.boot.kaizen.business.buss.model.fiveg.model.ProNrDataInfoBean;
import com.boot.kaizen.business.buss.model.fiveg.model.SignalBean;
import com.boot.kaizen.business.buss.model.fiveg.model.SignalEventBean;

/**
 * 写入es的5G LOG处理的实体类
 * 
 * @author weichengz
 * @date 2020年5月6日 上午10:07:41
 */
public class FiveLogMain implements Serializable {

	private static final long serialVersionUID = 1L;

	private String pid;// 室外测试的id
	private String id;// 每个log实体类的字段

	// 头部
	private String logversion;// 0: LTE 1: NR5G NSA
	private String dualSimSupport;// 0: Single SIM 1: Dual SIM 针对双卡测试的判断 如果勾选双卡测试就是1 如果没勾选双卡测试就0
	private String operatorCompareSupport;// 0: 非运营商竟对测试 1: 运营商竟对测试 针对竞对测试的 勾选了就是1 没勾选就是0
	private String rootSupport;// 0: None Root1: Root
	private String phone;// 手机型号
	private String operator;// 运营商（主卡）
	private String operator_y;// 运营商（副卡）

	// 主体
	private String latitude;// 纬度
	private String longitude;// 经度
	private String speed;// 时速
	private String height;// 海拔高度（m）
	private String testTime;// 当前时间(yyyy-MM-dd HH:mm:ss)
	private String downLoadSpeed;// APP下载速率
	private String upLoadSpeed;// APP上传速率
	private String normalEventType;
	private String abNormalEventType;

	/**
	 * root 非root的图例模块的数据信息 ，注意root/非root只能选择其一 一下根据条件赋值
	 */
	private String rsrp;
	private String sinr;
	private String ssrsrp;
	private String sssinr;

	private NrDataInfoBean nrDataInfoBean;
	private LteDataInfoBean lteDataInfoBean;
	private ProNrDataInfoBean proNrDataInfoBean;
	private ProLteDataInfoBean proLteDataInfoBeans;

	private List<SignalEventBean> signalEventBeans;
	private List<SignalBean> signalBeans;

	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogversion() {
		return logversion;
	}

	public void setLogversion(String logversion) {
		this.logversion = logversion;
	}

	public String getDualSimSupport() {
		return dualSimSupport;
	}

	public void setDualSimSupport(String dualSimSupport) {
		this.dualSimSupport = dualSimSupport;
	}

	public String getOperatorCompareSupport() {
		return operatorCompareSupport;
	}

	public void setOperatorCompareSupport(String operatorCompareSupport) {
		this.operatorCompareSupport = operatorCompareSupport;
	}

	public String getRootSupport() {
		return rootSupport;
	}

	public void setRootSupport(String rootSupport) {
		this.rootSupport = rootSupport;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperator_y() {
		return operator_y;
	}

	public void setOperator_y(String operator_y) {
		this.operator_y = operator_y;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public String getDownLoadSpeed() {
		return downLoadSpeed;
	}

	public void setDownLoadSpeed(String downLoadSpeed) {
		this.downLoadSpeed = downLoadSpeed;
	}

	public String getUpLoadSpeed() {
		return upLoadSpeed;
	}

	public void setUpLoadSpeed(String upLoadSpeed) {
		this.upLoadSpeed = upLoadSpeed;
	}

	public String getNormalEventType() {
		return normalEventType;
	}

	public void setNormalEventType(String normalEventType) {
		this.normalEventType = normalEventType;
	}

	public String getAbNormalEventType() {
		return abNormalEventType;
	}

	public void setAbNormalEventType(String abNormalEventType) {
		this.abNormalEventType = abNormalEventType;
	}

	public String getRsrp() {
		return rsrp;
	}

	public void setRsrp(String rsrp) {
		this.rsrp = rsrp;
	}

	public String getSinr() {
		return sinr;
	}

	public void setSinr(String sinr) {
		this.sinr = sinr;
	}

	public String getSsrsrp() {
		return ssrsrp;
	}

	public void setSsrsrp(String ssrsrp) {
		this.ssrsrp = ssrsrp;
	}

	public String getSssinr() {
		return sssinr;
	}

	public void setSssinr(String sssinr) {
		this.sssinr = sssinr;
	}

	public NrDataInfoBean getNrDataInfoBean() {
		return nrDataInfoBean;
	}

	public void setNrDataInfoBean(NrDataInfoBean nrDataInfoBean) {
		this.nrDataInfoBean = nrDataInfoBean;
	}

	public LteDataInfoBean getLteDataInfoBean() {
		return lteDataInfoBean;
	}

	public void setLteDataInfoBean(LteDataInfoBean lteDataInfoBean) {
		this.lteDataInfoBean = lteDataInfoBean;
	}

	public ProNrDataInfoBean getProNrDataInfoBean() {
		return proNrDataInfoBean;
	}

	public void setProNrDataInfoBean(ProNrDataInfoBean proNrDataInfoBean) {
		this.proNrDataInfoBean = proNrDataInfoBean;
	}

	public ProLteDataInfoBean getProLteDataInfoBeans() {
		return proLteDataInfoBeans;
	}

	public void setProLteDataInfoBeans(ProLteDataInfoBean proLteDataInfoBeans) {
		this.proLteDataInfoBeans = proLteDataInfoBeans;
	}

	public List<SignalEventBean> getSignalEventBeans() {
		return signalEventBeans;
	}

	public void setSignalEventBeans(List<SignalEventBean> signalEventBeans) {
		this.signalEventBeans = signalEventBeans;
	}

	public List<SignalBean> getSignalBeans() {
		return signalBeans;
	}

	public void setSignalBeans(List<SignalBean> signalBeans) {
		this.signalBeans = signalBeans;
	}

	public FiveLogMain(String pid, String id, String logversion, String dualSimSupport, String operatorCompareSupport,
			String rootSupport, String phone, String operator, String operator_y, String latitude, String longitude,
			String speed, String height, String testTime, String downLoadSpeed, String upLoadSpeed,
			String normalEventType, String abNormalEventType, String rsrp, String sinr, String ssrsrp, String sssinr,
			NrDataInfoBean nrDataInfoBean, LteDataInfoBean lteDataInfoBean, ProNrDataInfoBean proNrDataInfoBean,
			ProLteDataInfoBean proLteDataInfoBeans, List<SignalEventBean> signalEventBeans,
			List<SignalBean> signalBeans) {
		super();
		this.pid = pid;
		this.id = id;
		this.logversion = logversion;
		this.dualSimSupport = dualSimSupport;
		this.operatorCompareSupport = operatorCompareSupport;
		this.rootSupport = rootSupport;
		this.phone = phone;
		this.operator = operator;
		this.operator_y = operator_y;
		this.latitude = latitude;
		this.longitude = longitude;
		this.speed = speed;
		this.height = height;
		this.testTime = testTime;
		this.downLoadSpeed = downLoadSpeed;
		this.upLoadSpeed = upLoadSpeed;
		this.normalEventType = normalEventType;
		this.abNormalEventType = abNormalEventType;
		this.rsrp = rsrp;
		this.sinr = sinr;
		this.ssrsrp = ssrsrp;
		this.sssinr = sssinr;
		this.nrDataInfoBean = nrDataInfoBean;
		this.lteDataInfoBean = lteDataInfoBean;
		this.proNrDataInfoBean = proNrDataInfoBean;
		this.proLteDataInfoBeans = proLteDataInfoBeans;
		this.signalEventBeans = signalEventBeans;
		this.signalBeans = signalBeans;
	}

	public FiveLogMain() {
		super();
	}

	public FiveLogMain(NrLogBodyBean nrLogBodyBean, NrLogHeadBean nrLogHeadBean) {
		this.pid = nrLogBodyBean.getPid();
		this.id = nrLogBodyBean.getId();
		
		
		int rootSupport2 = nrLogHeadBean.getRootSupport();
		this.logversion = nrLogHeadBean.getLogversion()+"";
		this.dualSimSupport = nrLogHeadBean.getDualSimSupport()+"";
		this.operatorCompareSupport = nrLogHeadBean.getOperatorCompareSupport()+"";
		this.rootSupport = rootSupport2+"";
		this.phone = nrLogHeadBean.getPhone();
		this.operator = nrLogHeadBean.getOperator();
		this.operator_y = nrLogHeadBean.getOperator_y();
		
		
		this.latitude = nrLogBodyBean.getLatitude();
		this.longitude = nrLogBodyBean.getLongitude();
		this.speed = nrLogBodyBean.getSpeed();
		this.height = nrLogBodyBean.getHeight();
		this.testTime = nrLogBodyBean.getTestTime();
		this.downLoadSpeed = nrLogBodyBean.getDownLoadSpeed();
		this.upLoadSpeed = nrLogBodyBean.getUpLoadSpeed();
		this.normalEventType = nrLogBodyBean.getNormalEventType()+"";
		this.abNormalEventType = nrLogBodyBean.getAbNormalEventType()+"";
		
		if (0==rootSupport2) {//非root
			LteDataInfoBean lteDataInfoBean2 = nrLogBodyBean.getLteDataInfoBean();
			if (lteDataInfoBean2 !=null) {
				this.rsrp = lteDataInfoBean2.getLteRSRP();
				this.sinr = lteDataInfoBean2.getLteSINR();
			}
			NrDataInfoBean nrDataInfoBean2 = nrLogBodyBean.getNrDataInfoBean();
			if (nrDataInfoBean2 !=null) {
				this.ssrsrp = nrDataInfoBean2.getSsRSRP();
				this.sssinr = nrDataInfoBean2.getSsSINR();
			}
		}else {
			ProLteDataInfoBean proLteDataInfoBeans2 = nrLogBodyBean.getProLteDataInfoBeans();
			if (proLteDataInfoBeans2 !=null) {
				this.rsrp = proLteDataInfoBeans2.getServingCellPccRsrp();
				this.sinr = proLteDataInfoBeans2.getServingCellPccSinr();
			}
			
			 ProNrDataInfoBean proNrDataInfoBean2 = nrLogBodyBean.getProNrDataInfoBean();
			if (proNrDataInfoBean2 !=null) {
				this.ssrsrp = proNrDataInfoBean2.getSsRSRP();
				this.sssinr = proNrDataInfoBean2.getSsSINR();
			}
		}
		
		
		this.nrDataInfoBean = nrLogBodyBean.getNrDataInfoBean();
		this.lteDataInfoBean = nrLogBodyBean.getLteDataInfoBean();
		this.proNrDataInfoBean = nrLogBodyBean.getProNrDataInfoBean();
		this.proLteDataInfoBeans = nrLogBodyBean.getProLteDataInfoBeans();
		this.signalEventBeans = nrLogBodyBean.getSignalEventBeans();
		this.signalBeans = nrLogBodyBean.getSignalBeans();
	}




	
}
