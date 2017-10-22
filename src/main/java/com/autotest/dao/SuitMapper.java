package com.autotest.dao;

import com.autotest.model.Suit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SuitMapper {

    int deleteByPrimaryKey(Integer suitid);

    int insertSelective(Suit record);

    Suit selectByPrimaryKey(Integer suitid);

    int updateByPrimaryKeySelective(Suit record);

    List<Suit> selectByName(String suitname);
}