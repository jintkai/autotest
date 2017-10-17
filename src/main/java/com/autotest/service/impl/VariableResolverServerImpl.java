package com.autotest.service.impl;

import com.autotest.model.Variable;
import com.autotest.model.VariableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VariableResolverServerImpl {

    @Autowired
    VariableServiceImpl variableService;

    @Autowired
    VariableResultServiceImpl variableResultService;

    public Map<String,Object> resolver(Integer suitid,Integer buildid,String source){
        Map<String,Object> result = new HashMap<String,Object>();
        boolean caseVerify = true;
        List<String> varList = getVariableList(source);//case中输入的变量名列表
        List<Variable> variableList = new ArrayList<Variable>();//数据库中对应的变量名列表
        String msg = "";
        for (String s :varList){
            Variable variable = new Variable();
            s = s.substring(2,s.length()-1);
            variable.setName(s);
            variable.setSuitid(suitid);
            Variable variable1= variableService.selectByVariable(variable);
            if (null == variable1 ){
                caseVerify = false;
                msg =msg + "${"+s+"}、";
            }
            variableList.add(variable1);
        }
        if (caseVerify == false) {
            result.put("message", "变量验证失败，case中使用的变量不在变量列表中:" + msg);
            result.put("success", 0);
            return result;
        }
        List<VariableResult> variableResultList = new ArrayList<VariableResult>();//数据库中对应的变量名列表

        boolean valueVerify = true;
        for(Variable variable:variableList){
            VariableResult variableValue = variableResultService.selectResutBy(variable.getVariableid(),buildid);
            if(null != variableValue.getException() && !variableValue.getException().equals("")){
                valueVerify = false;
                msg =msg + "${"+variable.getName()+"}、";
            }
            variableResultList.add(variableValue);
        }
        if (valueVerify == false) {
            result.put("message", "变量值错误，case中使用的变量无法计算出对应的值:" + msg);
            result.put("success", 0);
            return result;
        }

        String target = source;
        for(int i = 0 ;i < variableList.size(); i++){
            target = target.replace("${"+variableList.get(i).getName()+"}",variableResultList.get(i).getValue());
        }

        result.put("success", 1);
        result.put("result",target);
        return result;

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
