package com.autotest.dao;

import com.autotest.model.SuitCaseResult;

import java.util.List;

public interface SuitCaseResultMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SuitCaseResult record);

    int insertSelective(SuitCaseResult record);

    SuitCaseResult selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SuitCaseResult record);

    int updateByPrimaryKey(SuitCaseResult record);
    List<SuitCaseResult> selectList(SuitCaseResult suitCaseResult);
    List<SuitCaseResult> selectBySuitIdbuildIdSuitCaseId(Integer suitcaseid, Integer suitid, Integer buildid);

}