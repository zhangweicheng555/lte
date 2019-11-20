package com.boot.kaizen.business.es.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.boot.kaizen.business.es.model.sim.GcModel;

@Mapper
public interface GcModelMapper {
	
	List<GcModel> find();
	
    int deleteByPrimaryKey(String lte_ci);

    int insert(GcModel record);

    int insertSelective(GcModel record);

    GcModel selectByPrimaryKey(String lte_ci);

    int updateByPrimaryKeySelective(GcModel record);

    int updateByPrimaryKeyWithBLOBs(GcModel record);
}