package com.autotest.dao;

import com.autotest.model.SuitCaseResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SuitCaseResultMapper {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(SuitCaseResult record);

    SuitCaseResult selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SuitCaseResult record);

    List<SuitCaseResult> selectBySuitIdbuildIdSuitCaseId(Integer suitcaseid, Integer suitid, Integer buildid);

}