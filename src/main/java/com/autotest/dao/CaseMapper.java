package com.autotest.dao;

import com.autotest.model.Case;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CaseMapper {
    int deleteByPrimaryKey(Integer caseid);

    int insert(Case record);

    int insertSelective(Case record);

    Case selectByPrimaryKey(Integer caseid);

    int updateByPrimaryKeySelective(Case record);

    int updateByPrimaryKey(Case record);
}