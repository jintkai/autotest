package com.autotest.service;

import com.autotest.model.SuitCaseReport;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SuitCaseReportService {

    int deleteSuitCaseReportById(Integer id);

    int insertSuitCaseReport(SuitCaseReport record);

    SuitCaseReport selectSuitCaseReportById(Integer id);

    int updateBSuitCaseReport(SuitCaseReport record);

    List<SuitCaseReport> selectBySuitIdbuildId(Integer suitid,Integer buildid);

    List<SuitCaseReport> selectSuitCaseReportAll();

}
