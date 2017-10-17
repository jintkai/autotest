package com.autotest.service.impl;

import com.autotest.dao.VariableMapper;
import com.autotest.dao.VariableResultMapper;
import com.autotest.model.Variable;
import com.autotest.model.VariableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariableResultServiceImpl {

    @Autowired
    VariableResultMapper variableResultMapper;
    @Autowired
    VariableMapper variableMapper;

    public int insert(VariableResult variableResult){
        return variableResultMapper.insert(variableResult);
    }

    public int copyVariable(Integer suitid,Integer buildID){
        List<Variable> variables = variableMapper.selectBySuitid(suitid);

        for (Variable v :variables){
            VariableResult variableResult = new VariableResult();
            variableResult.setVariableid(v.getVariableid());
            variableResult.setBuildid(buildID);
            insert(variableResult);
        }
        return variables.size();
    }

    public VariableResult selectResutBy(Integer variableid,Integer buildID){
        return variableResultMapper.selectByVariableIDAndBuildID(variableid,buildID);
    }
}
