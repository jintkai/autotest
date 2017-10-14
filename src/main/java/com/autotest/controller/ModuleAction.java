package com.autotest.controller;

import com.autotest.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/modules")
public class ModuleAction {

    @Autowired
    ModuleService moduleService;

    @RequestMapping("/")
    public Map<String,Object> getModules(){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        return resultMap;
    }
    @RequestMapping("/{moduleName}")
    public Map<String,Object> getModules(@PathVariable String moduleName){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result",moduleService.findModuleByName(moduleName));
        return resultMap;
    }
}
