package com.boot.kaizen.service.lte;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.boot.kaizen.controller.lte.model.BaseStationBean;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.model.lte.LtePlan;
import com.boot.kaizen.model.lte.LtePlanInfo;
import com.boot.kaizen.util.JsonMsgUtil;

/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:31:45
 */
public interface ILtePlanService {

	/**
	 * 
	 * @Description: 列表信息查询
	 * @author weichengz
	 * @date 2018年10月28日 下午12:29:52
	 */
	public List<LtePlan> find(Map<String, Object> map);

	/**
	 * 
	 * @Description: 编辑
	 * @author weichengz
	 * @date 2018年10月28日 下午4:37:23
	 */
	public JsonMsgUtil edit(LtePlan ltePlan, LoginUser loginUser);

	/**
	 * 
	 * @Description: 查询
	 * @author weichengz
	 * @date 2018年10月28日 下午5:00:17
	 */
	public JsonMsgUtil findById(Long id);

	/**
	 * 
	 * @Description: 删除
	 * @author weichengz
	 * @date 2018年10月28日 下午5:53:29
	 */
	public JsonMsgUtil delete(String ids);

	/**
	 * 查询用户的任务 app
	 * 
	 * @param userId
	 * @param projId
	 */
	List<Map<String, Object>> queryPlanList(Long userId, Long projId, String testDate);

	/**
	 * 拉取基站列表 app
	 * 
	 * @param userId
	 * @param projId
	 * @param testDate
	 */
	public List<BaseStationBean> queryStationList(Long userId, Long projId, String testDate);

	/**
	 * 
	 * @Description: 查询规划表相关的所有表的信息
	 * @author weichengz
	 * @date 2018年11月11日 上午8:41:06
	 */
	JsonMsgUtil queryLtePlanInfo(Long id, LoginUser user);

	/**
	 * 审核
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2018年11月11日 下午6:59:49
	 */
	public JsonMsgUtil check(Long id, Long statusM);

	/**
	 * 根据id查询 规划表的详细信息
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2018年11月17日 上午11:03:02
	 */
	public LtePlanInfo queryLtePlanInfoByEnobId(Long id);

	/**
	 * 导出报告
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2018年11月17日 下午6:41:51
	 */
	public void exportPlanDoc(String absolutePathModelExcel, LtePlanInfo ltePlanInfo, Map<String, String> map,
			HttpServletResponse response, HttpSession session);

	/**
	 * 根据项目id获取所有的用户
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2018年11月18日 上午8:18:24
	 */
	public JsonMsgUtil queryUserByProjId(Long projId);

	/**
	 * 上传
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年1月9日 上午12:57:32
	 */
	public JsonMsgUtil upload(MultipartFile file, LoginUser loginUser);

	int insertSelective(LtePlan record);

	int updateByPrimaryKeySelective(LtePlan record);
}
