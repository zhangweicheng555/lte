package com.boot.kaizen.service;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.boot.kaizen.model.SysProject;
import com.boot.kaizen.util.JsonMsgUtil;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:26:11
 */
public interface SysProjectService {

	/**
	 * 查询出对应的角色
	 * 
	 * @author weichengz
	 * @date 2018年9月8日 下午2:52:28
	 */
	public List<SysProject> query();

	/**
	 * 
	 * @Description: 只查询项目信息
	 * @author weichengz
	 * @date 2018年10月4日 上午2:41:05
	 */
	public List<SysProject> findList(Long projId);

	/**
	 * 返回角色的id/名字串
	 * 
	 * @author weichengz
	 * @date 2018年9月8日 下午2:53:15
	 */
	public List<SysProject> find(Map<String, Object> map);

	/**
	 * 删除
	 * 
	 * @author weichengz
	 * @date 2018年9月9日 下午12:59:30
	 */
	public JsonMsgUtil delete(String ids);

	/**
	 * 编辑
	 * 
	 * @author weichengz
	 * @date 2018年9月16日 下午6:27:00
	 */
	public JsonMsgUtil edit(SysProject sysProject);

	public JsonMsgUtil  findById(Long id);
	public SysProject  selectById(Long id);

	/**
	 * 
	 * @Description: 查询带有角色的项目列表
	 * @author weichengz
	 * @date 2018年10月4日 上午1:54:49
	 */
	public List<SysProject> findWithRoleRealtion(@Param("projId") Long projId,@Param("projName") String projName);

	/**
	 * @Description: 通过用户名字随机查询所在项目中的一个项目
	 * @author weichengz
	 * @date 2018年10月27日 下午7:31:53
	 */
	public Long findRandomProj(String username);

	/**
	 * 
	* @Description: 处理登陆后选择项目模块
	* @author weichengz
	* @date 2018年10月27日 下午11:38:08
	 */
	public JsonMsgUtil queryProjectsForUsername(String username);
	
	
	/**
	 * 通过用户名字查询所属项目列表  app使用
	 * @param username
	 * @return
	 */
	List<Map<String, Object>> queryProjects(String username);
	
	/**
	 * 添加
	* @Description: TODO
	* @author weichengz
	* @date 2020年6月15日 上午11:25:30
	 */
	void insertSelf(SysProject sysProject);
	
	/**
	 * 列表的条件查询
	* @Description: TODO
	* @author weichengz
	* @date 2020年6月15日 上午11:33:46
	 */
	List<SysProject> findByCondition(Map<String, Object> map);
	/**
	 * 修改
	* @Description: TODO
	* @author weichengz
	* @date 2020年6月15日 上午11:54:21
	 */
	void update(SysProject sysProject);
	
}
