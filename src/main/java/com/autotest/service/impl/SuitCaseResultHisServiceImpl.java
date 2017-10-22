package com.autotest.service.impl;

import com.autotest.dao.SuitCaseResultHisMapper;
import com.autotest.model.SuitCaseResultHis;
import com.autotest.service.SuitCaseResultHisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ying.zhang on 17/10/15.
 */
@Service
public class SuitCaseResultHisServiceImpl implements SuitCaseResultHisService {

    @Autowired
    public SuitCaseResultHisMapper suitCaseResultHisMapper;

    @Override
    public int deleteSuitHisById(Integer id){
        return suitCaseResultHisMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSuitHis(SuitCaseResultHis record){
        return suitCaseResultHisMapper.insertSelective(record);
    }

    @Override
    public SuitCaseResultHis selectSuitHisById(Integer id){
        return suitCaseResultHisMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateSuitHis(SuitCaseResultHis record){
        return suitCaseResultHisMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<SuitCaseResultHis> selectBySuitIdbuildIdCaseId(Integer suitcaseid, Integer suitid, Integer buildid){
        return suitCaseResultHisMapper.selectBySuitIdbuildIdSuitCaseId(suitcaseid,suitid,buildid);
    }
}
