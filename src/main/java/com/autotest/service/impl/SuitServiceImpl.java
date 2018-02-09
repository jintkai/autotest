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
        List<Suit> suitList = selectSuitByName(record.getSuitname());
        if (suitList.size() == 0){
            return suitMapper.insertSelective(record);
        }else{
            return 0;
        }
    }

    @Override
    public Suit selectSuitById(Integer suitid){
        return suitMapper.selectByPrimaryKey(suitid);
    }

    @Override
    public List<Suit> selectSuitList() {
        return suitMapper.selectSuitList();
    }

    @Override
    public int updateSuit(Suit record){
        List<Suit> suitList = selectSuitByName(record.getSuitname());
        if (suitList.size() == 1 && suitList.get(0).getSuitid() == record.getSuitid() || suitList.size() == 0){
            return suitMapper.updateByPrimaryKeySelective(record);
        }
        return 0;
    }

    @Override
    public List<Suit> selectSuitByName(String suitname){
        return suitMapper.selectByName(suitname);
    }

}
