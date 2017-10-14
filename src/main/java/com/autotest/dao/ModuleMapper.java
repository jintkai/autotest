package com.autotest.dao;

import com.autotest.model.Module;
import org.apache.ibatis.annotations.Mapper;

@Mapper

public interface ModuleMapper {
    int deleteByPrimaryKey(Integer moduleid);

    int insert(Module record);

    int insertSelective(Module record);

    Module selectByPrimaryKey(Integer moduleid);

    int updateByPrimaryKeySelective(Module record);

    int updateByPrimaryKey(Module record);
    Module selectByName(String name);
}