package com.autotest.dao;

import com.autotest.model.SuitCase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SuitCaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SuitCase record);

    int insertSelective(SuitCase record);

    SuitCase selectByPrimaryKey(Integer id);
    List<SuitCase> selectBySuitID(Integer suitid);
    int updateByPrimaryKeySelective(SuitCase record);

    int updateByPrimaryKey(SuitCase record);
}