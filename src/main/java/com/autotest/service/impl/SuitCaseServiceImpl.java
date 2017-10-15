package com.autotest.service.impl;

import com.autotest.dao.SuitCaseMapper;
import com.autotest.model.SuitCase;
import com.autotest.model.Variable;
import com.autotest.model.VariableResult;
import com.autotest.service.SuitCaseService;
import com.autotest.service.impl.common.HttpClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SuitCaseServiceImpl implements SuitCaseService {

    @Autowired
    SuitCaseMapper suitCaseMapper;
    @Autowired
    HttpClientServiceImpl httpClientService;

    @Autowired
    VariableServiceImpl variableService;
    @Autowired
    VariableReportServiceImpl variableReportService;
    @Override
    public SuitCase selectById(Integer id) {
        return suitCaseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SuitCase> selectBySuitID(Integer suitID) {
        return suitCaseMapper.selectBySuitID(suitID);
    }

    @Override
    public Map<String, String> suitCaseRun(SuitCase suitCase) {
        String caseVerify ="";
        String url = suitCase.getRequesturl();
        List<String> varList = getVariableList(url);//case中输入的变量名列表
        List<Variable> variableList = new ArrayList<Variable>();//数据库中对应的变量名列表
        for (String s :varList){
            Variable variable = new Variable();
            s = s.substring(2,s.length()-1);
            variable.setName(s);
            variable.setSuitid(suitCase.getSuitid());
            Variable variable1= variableService.selectByVariable(variable);
            if (null == variable1){
                caseVerify = "变量验证失败，case中使用的变量不在变量列表中";
            }
            variableList.add(variable1);
        }
        List<VariableResult> variableResultList = new ArrayList<VariableResult>();//数据库中对应的变量名列表

        for(Variable variable:variableList){
            VariableResult variableValue = variableReportService.selectResutBy(variable.getVariableid(),10);
            System.out.println(variableValue);
        }

        return httpClientService.sentRequest(Integer.valueOf(suitCase.getRequesttype()),suitCase.getRequesturl(),suitCase.getRequestheader(),suitCase.getRequestbody());
    }


    public List<String> getVariableList(String source){
        List<String> variableList = new ArrayList<String>();
        String regExp = "\\$\\{[a-zA-Z0-9_.\\[\\]]*}";
        Pattern pattern = Pattern.compile(regExp);

        Matcher matcher = pattern.matcher(source);

        while(matcher.find()){
            String variable = matcher.group();
            System.out.println(matcher.group());
            variableList.add(variable);
        }
        return variableList;
    }
}
