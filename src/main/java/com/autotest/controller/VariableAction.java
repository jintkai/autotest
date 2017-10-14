package com.autotest.controller;

import com.autotest.service.impl.VariableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/variable")
public class VariableAction {

    @Autowired
    VariableServiceImpl variableService;
    @RequestMapping(value = "/" ,method = RequestMethod.POST)
    public Map<String,Object> getByid(@RequestParam String by,@RequestParam Integer id){
        Map<String,Object> resultMap = new HashMap<String,Object>();

        if (by.equals("id"))
            resultMap.put("result",variableService.selectByID(id));
        if (by.equals("suitid")) {
            resultMap.put("result", variableService.selectBySuitID(id));
        }

        return resultMap;
    }

    @RequestMapping(value = "/suitID" ,method = RequestMethod.POST)
    public Map<String,Object> getBySuitID(@RequestParam Integer id){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        return resultMap;
    }


}
