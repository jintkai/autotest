package com.autotest.service.impl;

import com.autotest.dao.SuitCaseReportMapper;
import com.autotest.model.SuitCaseReport;
import com.autotest.service.SuitCaseReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuitCaseReportServiceImpl implements SuitCaseReportService {

    @Autowired
    public SuitCaseReportMapper suitCaseReportMapper;

    @Override
    public int deleteSuitCaseReportById(Integer id){
        return suitCaseReportMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSuitCaseReport(SuitCaseReport record){
        return suitCaseReportMapper.insertSelective(record);
    }

    @Override
    public SuitCaseReport selectSuitCaseReportById(Integer id){
        return suitCaseReportMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateBSuitCaseReport(SuitCaseReport record){
        return suitCaseReportMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<SuitCaseReport> selectBySuitIdbuildId(Integer suitid,Integer buildid){
        return suitCaseReportMapper.selectBySuitIdbuildId(suitid,buildid);
    }

    @Override
    public List<SuitCaseReport> selectSuitCaseReportAll(){
        return suitCaseReportMapper.select();
    }

}
