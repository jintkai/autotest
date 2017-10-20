package com.autotest.service.impl;

import com.autotest.model.SuitCaseResult;
import com.autotest.model.VarExpress;
import com.autotest.model.Variable;
import com.autotest.model.VariableResult;
import com.autotest.service.SuitCaseReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VarExpressServiceImpl {

    @Autowired
    VariableServiceImpl variableService;
    @Autowired
    VariableResultServiceImpl variableResultService;
    @Autowired
    SuitCaseResultServiceImpl suitCaseResultService;
    public String resolveExpress(Variable variable,Integer buildid){
        String result ="";
        VarExpress varExpress = new VarExpress();
        List<VariableResult> results = new ArrayList<VariableResult>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            varExpress = mapper.readValue(variable.getVarExpress(),VarExpress.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> list = getVariableList(varExpress.getParam());

        for (int i = 0 ;i <= list.size() ; i++){
            variable = resolveVariable(variable,buildid);
        }
        VariableResult variableResult = variableResultService.selectResutBy(variable.getVariableid(),buildid);
        return variableResult.getValue();

    }
    public Variable resolveVariable(Variable variable,Integer buildid){
        String result ="";
        VarExpress varExpress = new VarExpress();
        ObjectMapper mapper = new ObjectMapper();
        try {
            varExpress = mapper.readValue(variable.getVarExpress(),VarExpress.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> list = getVariableList(varExpress.getParam());
        if (list.size() == 0 ){
            //变量中没有使用其他变量
            VariableResult variableResult = variableResultService.selectResutBy(variable.getVariableid(),buildid);
            //value没有值，或者value = null时，进行赋值计算；
            if (variableResult.getValue().equals("") || variableResult.getValue() == null) {
                if (varExpress.getMethod().equals("random")) {
                    String s = varExpress.getParam();
                    int i = ((int) (Math.random() * 10000)) % Integer.valueOf(s);
                    variableResult.setValue(String.valueOf(i));
                    variableResult.setException("");
                    variableResultService.update(variableResult);
                    result = String.valueOf(i);
                }
                if (varExpress.getMethod().equals("jdbc")) {
                    String str = "jdbc";
                    result = str;
                }
                if (varExpress.getMethod().equals("string")) {
                    result = varExpress.getParam();
                    variableResult.setValue(result);
                    variableResult.setException("");
                    variableResultService.update(variableResult);
                }
                if (varExpress.getMethod().equals("suitbody")){
                    String s = varExpress.getParam();
                    SuitCaseResult suitCaseResult = new SuitCaseResult();
                    suitCaseResult.setSuitcaseid(Integer.valueOf(s));
                    suitCaseResult.setSuitid(variable.getSuitid());
                    suitCaseResult.setBuildid(buildid);
                    suitCaseResult = suitCaseResultService.selectList(suitCaseResult).get(0);
                    variableResult.setValue(suitCaseResult.getResponsebody());
                    variableResult.setException("");
                    variableResultService.update(variableResult);
                    result = String.valueOf(suitCaseResult.getResponsebody());
                }
            }
            return variable;

        }else
        {
            //变量中使用了其他变量，读取第一个变量获取值
            String s = list.get(0).substring(2,list.get(0).length()-1);
            Variable variable2 = new Variable();
            variable2.setName(s);
            variable2.setSuitid(variable.getSuitid());
            variable2 = variableService.selectByVariable(variable2);
            String tmp = resolveExpress(variable2,buildid);
            variable.setVarExpress(variable.getVarExpress().replace(list.get(0),tmp));
            return variable;
            }
    }


    public List<String> getVariableList(String source){
        List<String> variableList = new ArrayList<String>();
        // \$\{[a-zA-Z0-9_.\[\]]*}
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
