package com.boot.kaizen.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.boot.kaizen.model.SysUser;

/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:15:52
 */
@Mapper
public interface SysUserDao {

	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into sys_user(username, password, nickname, headImgUrl, phone, telephone, email, birthday, sex, status, createTime) values(#{username}, #{password}, #{nickname}, #{headImgUrl}, #{phone}, #{telephone}, #{email}, #{birthday}, #{sex}, #{status}, #{createTime})")
	int save(SysUser user);

	@Select("select * from sys_user t where t.id = #{id}")
	SysUser getById(Long id);

	@Select("select * from sys_user t where t.username = #{username}")
	SysUser getUser(String username);

	@Update("update sys_user t set t.password = #{password} where t.id = #{id}")
	int changePassword(@Param("id") Long id, @Param("password") String password);

	Integer count(@Param("params") Map<String, Object> params);

	List<SysUser> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset,
			@Param("limit") Integer limit);

	@Delete("delete from sys_role_user where userId = #{userId}")
	int deleteUserRole(Long userId);

	int saveUserRoles(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

	List<SysUser> find(@Param("map") Map<String, Object> map);

	void deleteBatch(@Param("idsArray") Long[] array, @Param("projId") Long projId);

	String findUserNames(@Param("roleId") Long roleId);

	List<Long> findUserIds(@Param("roleId") Long roleId);

	SysUser queryUser(@Param("projId") Long projId, @Param("username") String username);

	List<Map<String, Object>> queryUserByProjId(@Param("projId") Long projId);

	List<Map<String, Object>> queryUserByRoleId(@Param("roleId") Long roleId);

	void insertSelective(SysUser sysUser);

	int update(@Param("user") SysUser user);

	@Select("SELECT u.id,u.username,u.nickname FROM sys_user u LEFT JOIN sys_role_user ru ON u.id = ru.userId LEFT JOIN proj_role_relation pr ON ru.roleId = pr.role_id WHERE ru.roleId IN (SELECT r.id FROM sys_role r WHERE r.`name` = '系统管理员' OR r.`name` = '审批人员') AND pr.proj_id = #{projId}")
	List<SysUser> queryAcceptUserByProj(@Param("projId") Long projectId);
}
