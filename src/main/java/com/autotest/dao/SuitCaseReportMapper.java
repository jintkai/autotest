package com.autotest.dao;

import com.autotest.model.SuitCaseReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SuitCaseReportMapper {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(SuitCaseReport record);

    SuitCaseReport selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SuitCaseReport record);

    List<SuitCaseReport> selectBySuitIdbuildId(Integer suitid,Integer buildid);

    @Select("select * from qa_suitcase_build_report")
    List<SuitCaseReport> select();

}