package com.autotest.dao;

import com.autotest.model.VariableResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VariableResultMapper {

    int insert(VariableResult result);
    int updateByPrimaryKey(VariableResult result);
    VariableResult selectByVariableIDAndBuildID(@Param("variableid") Integer variableid,@Param("buildid") Integer buildid);
}
