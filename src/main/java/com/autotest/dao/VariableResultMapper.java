package com.autotest.dao;

import com.autotest.model.VariableResult;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VariableResultMapper {

    int insert(VariableResult result);
}
