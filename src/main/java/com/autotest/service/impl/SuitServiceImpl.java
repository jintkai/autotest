package com.autotest.service.impl;

import com.autotest.dao.SuitMapper;
import com.autotest.model.Suit;
import com.autotest.service.SuitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ying.zhang on 17/10/15.
 */
@Service
public class SuitServiceImpl implements SuitService{

    @Autowired
    SuitMapper suitMapper;

    @Override
    public int deleteSuitById(Integer suitid){
        return suitMapper.deleteByPrimaryKey(suitid);
    }

    @Override
    public int insertSuit(Suit record){
        return suitMapper.insertSelective(record);
    }

    @Override
    public Suit selectSuitById(Integer suitid){
        return suitMapper.selectByPrimaryKey(suitid);
    }

    @Override
    public int updateSuit(Suit record){
        return suitMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<Suit> selectSuitByName(String suitname){
        return suitMapper.selectByName(suitname);
    }



}
