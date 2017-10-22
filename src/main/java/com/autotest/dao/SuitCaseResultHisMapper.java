package com.autotest.dao;

import com.autotest.model.SuitCaseResultHis;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SuitCaseResultHisMapper {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(SuitCaseResultHis record);

    SuitCaseResultHis selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SuitCaseResultHis record);

    List<SuitCaseResultHis> selectBySuitIdbuildIdSuitCaseId(Integer suitcaseid, Integer suitid, Integer buildid);
}