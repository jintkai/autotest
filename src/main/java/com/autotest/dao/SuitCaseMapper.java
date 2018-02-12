package com.autotest.dao;

import com.autotest.model.SuitCase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SuitCaseMapper {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(SuitCase record);

    SuitCase selectByPrimaryKey(Integer id);

    List<SuitCase> selectBySuitIdCaseId(Integer suitid,Integer caseid);

    int updateByPrimaryKeySelective(SuitCase record);

    List<SuitCase> selectBySuitID(Integer suitid);
    List<SuitCase> selectMainCaseBySuitID(Integer suitid);


    List<SuitCase> selectSubCase(Integer caseid);


}