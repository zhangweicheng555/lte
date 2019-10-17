package com.boot.kaizen.business.student.dao;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.boot.kaizen.business.student.model.UserManager;
/**
 * 模板实例步骤2
 * @author weichengz
 * @date 2019年9月26日 下午12:12:08
 */
@Mapper
public interface UserManagerMapper extends BaseMapper<UserManager>{
	
	List<UserManager> findByTest(@Param("map") Map<String, Object> map);
}