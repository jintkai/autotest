package com.autotest.controller;

import com.autotest.service.impl.VariableResultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/variableresult")
public class VariableResultAction {

    @Autowired
    VariableResultServiceImpl variableResultService;

    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public Map<String,Object> getBySuitID(@RequestParam Integer id){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("results",variableResultService.copyVariable(id,10));
        return resultMap;
    }
}
