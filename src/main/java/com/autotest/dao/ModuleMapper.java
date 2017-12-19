package com.autotest.dao;

import com.autotest.model.Module;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ModuleMapper {

    int deleteByPrimaryKey(Integer moduleid);

    int insertSelective(Module record);

    Module selectByPrimaryKey(Integer moduleid);

    int updateByPrimaryKeySelective(Module record);

    List<Module> selectByName(String name);

    List<Module> selectModules();
    List<Module> selectByNameAndPid(Module record);
}