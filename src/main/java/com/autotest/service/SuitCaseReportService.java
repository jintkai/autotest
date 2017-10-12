package com.autotest.service;

import com.autotest.model.SuitCaseReport;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SuitCaseReportService {

    public List<SuitCaseReport> selectBySuit(Integer suitid);
}
