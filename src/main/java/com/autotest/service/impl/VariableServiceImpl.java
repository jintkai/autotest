package com.autotest.service.impl;

import com.autotest.dao.VariableMapper;
import com.autotest.model.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariableServiceImpl {

    @Autowired
    VariableMapper variableMapper;

    public Variable selectByID(Integer id){
        return variableMapper.selectByPrimaryKey(id);
    }

    public List<Variable> selectBySuitID(Integer suitid){
        return variableMapper.selectBySuitid(suitid);
    }
}
