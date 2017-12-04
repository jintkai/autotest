package com.autotest.service.impl;

import com.autotest.dao.VariableMapper;
import com.autotest.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VariableServiceImpl {

    @Autowired
    VariableMapper variableMapper;

    @Autowired
    VariableResultServiceImpl variableResultService;

    public Variable selectByID(Integer id){
        return variableMapper.selectByPrimaryKey(id);
    }

    public List<Variable> selectBySuitID(Integer suitid){
        return variableMapper.selectBySuitid(suitid);
    }

    public Variable selectByVariable(Variable variable){
        return variableMapper.select(variable);
    }

    public List<Variable> selectBySuitCaseId(Integer suitCaseId){
        return variableMapper.selectBySuitCaseId(suitCaseId);
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


    public String resolveExpress(Variable variable,Integer buildid,Object info){

        List<String> list = getVariableList(variable.getVarExpress());
        for (int i = 0 ;i <= list.size() ; i++){
            variable = resolveVariable(variable,buildid,info);
        }
        VariableResult variableResult = variableResultService.selectResutBy(variable.getVariableid(),buildid);
        return variableResult.getValue();

    }
    public Variable resolveVariable(Variable variable,Integer buildid,Object info){
        String result ="";
        List<String> list = getVariableList(variable.getVarExpress());
        if (list.size() == 0 ){
            //变量中没有使用其他变量
            VariableResult variableResult = variableResultService.selectResutBy(variable.getVariableid(),buildid);
            //value没有值，或者value = null时，进行赋值计算；
            if (variableResult.getValue() == null || variableResult.getValue().equals("") ) {
                if (variable.getType().equals("random")) {
                    String s = variable.getVarExpress();
                    int i = ((int) (Math.random() * 10000)) % Integer.valueOf(s);
                    variableResult.setValue(String.valueOf(i));
                    variableResult.setException("");
                    variableResultService.update(variableResult);
                    result = String.valueOf(i);
                }
                if (variable.getType().equals("jdbc")) {
                    String str = "jdbc";
                    result = str;
                }
                if (variable.getType().equals("string")) {
                    result = variable.getVarExpress();
                    variableResult.setValue(result);
                    variableResult.setException("");
                    variableResultService.update(variableResult);
                }
                if (variable.getType().equals("suitbody")){
                    HttpInfo httpInfo = (HttpInfo) info;
                    String body = httpInfo.getResponseBody();
                    if (body.length()>1024) {
                        System.out.println("长度过长......");
                        variableResult.setValue(body.substring(0, 1024));
                    }else
                        variableResult.setValue(body);

                    variableResultService.update(variableResult);
                    /*String s = varExpress.getParam();
                    SuitCaseResult suitCaseResult = new SuitCaseResult();
                    suitCaseResult.setSuitcaseid(Integer.valueOf(s));
                    suitCaseResult.setSuitid(variable.getSuitid());
                    suitCaseResult.setBuildid(buildid);
                    suitCaseResult = suitCaseResultService.selectList(suitCaseResult).get(0);
                    variableResult.setValue(suitCaseResult.getResponsebody());
                    variableResult.setException("");
                    variableResultService.update(variableResult);
                    result = String.valueOf(suitCaseResult.getResponsebody());*/
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
            variable2 = selectByVariable(variable2);
            String tmp = resolveExpress(variable2,buildid,info);
            variable.setVarExpress(variable.getVarExpress().replace(list.get(0),tmp));
            return variable;
        }
    }




}
