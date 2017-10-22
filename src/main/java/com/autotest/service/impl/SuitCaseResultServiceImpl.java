package com.autotest.service.impl;

import com.autotest.dao.SuitCaseResultMapper;
import com.autotest.model.SuitCaseResult;
import com.autotest.service.SuitCaseResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ying.zhang on 17/10/15.
 */
@Service
public class SuitCaseResultServiceImpl implements SuitCaseResultService {

    @Autowired
    SuitCaseResultMapper suitCaseResultMapper;

    public List<SuitCaseResult> selectList(SuitCaseResult suitCaseResult) {
        return suitCaseResultMapper.selectList(suitCaseResult);
    }
    @Override
    public int deleteSuitResultById(Integer id){
        return suitCaseResultMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSuitResult(SuitCaseResult record){
        return suitCaseResultMapper.insertSelective(record);
    }

    @Override
    public SuitCaseResult selectSuitResultById(Integer id){
        return suitCaseResultMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateSuitResult(SuitCaseResult record){
        return suitCaseResultMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<SuitCaseResult> selectBySuitIdbuildIdSuitCaseId(Integer suitcaseid, Integer suitid, Integer buildid){
        return suitCaseResultMapper.selectBySuitIdbuildIdSuitCaseId(suitcaseid,suitid,buildid);
    }
}
