package com.autotest.service.impl;

import com.autotest.dao.SuitCaseReportMapper;
import com.autotest.model.SuitCaseReport;
import com.autotest.service.SuitCaseReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SuitCaseReportServiceImpl implements SuitCaseReportService {

    @Autowired
    SuitCaseReportMapper suitCaseReportMapper;
    @Override
    public List<SuitCaseReport> selectBySuit(Integer suitId) {
        return suitCaseReportMapper.selectBySuit(suitId);
    }


}
