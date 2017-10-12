package com.autotest.dao;

import com.autotest.model.SuitCaseResultHis;

public interface SuitCaseResultHisMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SuitCaseResultHis record);

    int insertSelective(SuitCaseResultHis record);

    SuitCaseResultHis selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SuitCaseResultHis record);

    int updateByPrimaryKey(SuitCaseResultHis record);
}