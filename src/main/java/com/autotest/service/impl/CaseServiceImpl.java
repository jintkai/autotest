package com.autotest.service.impl;

import com.autotest.dao.CaseMapper;
import com.autotest.model.Case;
import com.autotest.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ying.zhang on 17/10/15.
 */
@Service
public class CaseServiceImpl implements CaseService{

    @Autowired
    CaseMapper caseMapper;

    @Override
    public int deleteCaseById(Integer caseid){
        return caseMapper.deleteByPrimaryKey(caseid);
    }

    @Override
    public int insertCase(Case record){
        return caseMapper.insertSelective(record);
    }

    @Override
    public Case selectCaseById(Integer caseid){
        return caseMapper.selectByPrimaryKey(caseid);
    }

    @Override
    public int updateCase(Case record){
        return caseMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<Case> selectCasesByCaseName(String casename){
        return caseMapper.selectByCaseName(casename);
    }

    @Override
    public int caseCount(Integer time) {
        return   caseMapper.countCase(time);
    }
}
