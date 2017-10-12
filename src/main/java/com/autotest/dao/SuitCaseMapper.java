package com.autotest.dao;

import com.autotest.model.SuitCase;

public interface SuitCaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SuitCase record);

    int insertSelective(SuitCase record);

    SuitCase selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SuitCase record);

    int updateByPrimaryKey(SuitCase record);
}