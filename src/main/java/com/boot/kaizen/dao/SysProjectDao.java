package com.boot.kaizen.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.boot.kaizen.business.buss.model.SimProject;
import com.boot.kaizen.model.SysProject;

/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:15:52
 */

@Mapper
public interface SysProjectDao {

	List<SysProject> query();

	List<SysProject> findWithRoleRealtion(@Param("projId") Long projId, @Param("projName") String projName);

	List<SysProject> findList(@Param("projId") Long projId);
	
	List<SysProject> findByCondition(@Param("map") Map<String, Object> map);

	List<SysProject> find(@Param("map") Map<String, Object> map);

	Integer delete(@Param("idsArray") Long[] array);

	@Insert("insert into sys_project(proj_name,proj_code,proj_intro,sort,createTime,proj_province,hostAp,projSimName,projOperator) values(#{projName},#{projCode},#{projIntro},#{sort},#{createTime},#{proProvice},#{hostAp},#{projSimName},#{projOperator})")
	void insertSelf(SysProject sysProject);

	@Update("update sys_project set proj_name=#{projName},proj_code=#{projCode},proj_intro=#{projIntro},sort=#{sort},updateTime=#{updateTime},proj_province=#{proProvice},hostAp=#{hostAp},projSimName=#{projSimName},projOperator=#{projOperator} where id=#{id}")
	void update(SysProject sysProject);

	@Select("select * from sys_project where id=#{id}")
	SysProject findById(@Param("id") Long id);

	SysProject selectById(@Param("id") Long id);
	
	Long findRandomProj(@Param("username") String username);

	/**
	 * 
	 * @Description: 查询这个用户拥有的所有的项目 id跟name
	 * @author weichengz
	 * @date 2018年10月27日 下午11:40:12
	 */
	List<Map<String, Object>> queryProjects(@Param("username") String username);
}
