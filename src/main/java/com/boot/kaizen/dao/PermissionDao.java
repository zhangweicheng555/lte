package com.boot.kaizen.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.boot.kaizen.model.Permission;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:15:52
 */
@Mapper
public interface PermissionDao {

	@Select("select * from sys_permission t order by t.sort")
	List<Permission> listAll();
	
	@Select("select * from sys_permission t where t.type = 1 order by t.sort")
	List<Permission> listParents();
	
	@Select("select * from sys_permission t where t.type = 1 and t.parentId=#{parentId} order by t.sort")
	List<Permission> listParents(Long parentId);

	@Select("select distinct p.* from sys_permission p inner join sys_role_permission rp on p.id = rp.permissionId inner join sys_role_user ru on ru.roleId = rp.roleId where ru.userId = #{userId} order by p.sort")
	List<Permission> listByUserId(Long userId);

	@Select("select p.* from sys_permission p inner join sys_role_permission rp on p.id = rp.permissionId where rp.roleId = #{roleId} order by p.sort")
	List<Permission> listByRoleId(Long roleId);

	@Select("select * from sys_permission t where t.id = #{id}")
	Permission getById(Long id);

	@Insert("insert into sys_permission(parentId, name, css, href, type, permission, sort) values(#{parentId}, #{name}, #{css}, #{href}, #{type}, #{permission}, #{sort})")
	int save(Permission permission);
	
	int update(@Param("permission") Permission permission);

	@Delete("delete from sys_permission where id = #{id} or parentId = #{id}")
	int delete(Long id);


	@Select("select ru.userId from sys_role_permission rp inner join sys_role_user ru on ru.roleId = rp.roleId where rp.permissionId = #{permissionId}")
	Set<Long> listUserIds(Long permissionId);

	List<Permission> queryByUserIdAndProjId(@Param("username") String username,@Param("projId")  Long projId);
	
	List<Permission> selectByCondition(@Param("map") Map<String, Object> map);
	
	List<Long> queryChildIdsByParentIds(@Param("id") Long id);
}
