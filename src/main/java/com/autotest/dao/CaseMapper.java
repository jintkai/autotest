package com.autotest.dao;

import com.autotest.model.Case;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CaseMapper {

    int deleteByPrimaryKey(Integer caseid);

    int insertSelective(Case record);

    Case selectByPrimaryKey(Integer caseid);

    int updateByPrimaryKeySelective(Case record);

    List<Case> selectByCaseName(String casename);
}