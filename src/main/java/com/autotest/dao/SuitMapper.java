package com.autotest.dao;

import com.autotest.model.Suit;

public interface SuitMapper {
    int deleteByPrimaryKey(Integer suitid);

    int insert(Suit record);

    int insertSelective(Suit record);

    Suit selectByPrimaryKey(Integer suitid);

    int updateByPrimaryKeySelective(Suit record);

    int updateByPrimaryKey(Suit record);
}