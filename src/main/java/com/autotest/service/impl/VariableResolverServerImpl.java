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
    VarExpressServiceImpl varExpressService;
    @Autowired
    VariableResultServiceImpl variableResultService;

    public Map<String,Object> resolver(Integer suitid,Integer buildid,String source){
        Map<String,Object> result = new HashMap<String,Object>();
        boolean caseVerify = true;  //存储变量是否定义
        List<String> varList = getVariableList(source);//case中输入的变量名列表
        List<Variable> variableList = new ArrayList<Variable>();//数据库中对应的变量名列表
        String msg = "";
        for (String s :varList){
            Variable variable = new Variable();
            s = s.substring(2,s.length()-1);
            variable.setName(s);
            variable.setSuitid(suitid);
            Variable variable1= variableService.selectByVariable(variable);//返回数据库中对应变量的所有配置信息；
            if (null == variable1 ){
                //输入的变量名，在数据库中不存在，把所有的不存在的都取出
                caseVerify = false;
                msg =msg + "${"+s+"}、";
            }else
                variableList.add(variable1);
        }
        if (caseVerify == false) {
            result.put("message", "解析失败，Case中使用的自定义变量未定义:" + msg);
            result.put("success", 0);
            return result;
        }
        List<VariableResult> variableResultList = new ArrayList<VariableResult>();//数据库中对应的变量名计算结果列表

        boolean valueVerify = true; //存储变量能否正常解析；
        for(Variable variable:variableList){
            //Variable variable = variableService.selectByID(id);
            varExpressService.resolveExpress(variable,buildid);//计算出变量表达式的值
            VariableResult variableValue = variableResultService.selectResutBy(variable.getVariableid(),buildid);//变量在result中对应的结果值
            /**
             * 如果值不存在，为什么没有退出？此处会报空指针。
             */
            if(null != variableValue.getException() && !variableValue.getException().equals("")){
                //如果计算结果时存在异常，就说明变量无法获取对应的值。
                valueVerify = false;
                msg =msg + "${"+variable.getName()+"}、";
            }
            variableResultList.add(variableValue);
        }
        if (valueVerify == false) {
            result.put("message", "变量值获取失败，Case中使用的自定义变量无法计算出对应的值:" + msg);
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

    //返回变量列表。
    public List<String> getVariableList(String source){
        List<String> variableList = new ArrayList<String>();
        // \$\{[a-zA-Z0-9_.\[\]]*}
        String regExp = "\\$\\{[a-zA-Z0-9_.\\[\\]]*}";
        Pattern pattern = Pattern.compile(regExp);

        Matcher matcher = pattern.matcher(source);

        while(matcher.find()){
            String variable = matcher.group();
            //System.out.println(matcher.group());
            variableList.add(variable);
        }
        return variableList;
    }


}
