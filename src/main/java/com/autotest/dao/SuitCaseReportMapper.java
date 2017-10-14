package com.autotest.dao;

import com.autotest.model.SuitCaseReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
public interface SuitCaseReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SuitCaseReport record);

    int insertSelective(SuitCaseReport record);

    SuitCaseReport selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SuitCaseReport record);

    int updateByPrimaryKey(SuitCaseReport record);


    List<SuitCaseReport> selectBySuit(Integer SuitId);

    @Select("select * from qa_suitcase_build_report")
    List<SuitCaseReport> select();

}