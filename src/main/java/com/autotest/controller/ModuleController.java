package com.autotest.controller;

import com.autotest.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/autotest")
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @RequestMapping("/modules")
    public Map<String,Object> getModules(){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        return resultMap;
    }
    @RequestMapping("/modules/{moduleName}")
    public Map<String,Object> getModules(@PathVariable String moduleName){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result",moduleService.findModuleByName(moduleName));
        return resultMap;
    }
}
