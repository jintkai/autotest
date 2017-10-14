package com.autotest.dao;

import com.autotest.model.Variable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VariableMapper {

    Variable selectByPrimaryKey(Integer variableid);

    List<Variable> selectBySuitid(Integer suitid);
}

