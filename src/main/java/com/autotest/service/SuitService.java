package com.autotest.service;

import com.autotest.model.Suit;

import java.util.List;

/**
 * Created by ying.zhang on 17/10/15.
 */
public interface SuitService {

    int deleteSuitById(Integer suitid);

    int insertSuit(Suit record);

    Suit selectSuitById(Integer suitid);

    int updateSuit(Suit record);

    List<Suit> selectSuitByName(String suitname);
}
