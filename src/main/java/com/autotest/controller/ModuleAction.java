package com.autotest.controller;

import com.autotest.model.Module;
import com.autotest.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/module")
public class ModuleAction {

    @Autowired
    ModuleService moduleService;

    @RequestMapping("/")
    public Map<String, Object> getModules() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        return resultMap;
    }

    @RequestMapping("/{moduleName}")
    public Map<String, Object> getModules(@PathVariable String moduleName) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", moduleService.selectModuleByName(moduleName));

        return resultMap;
    }

    @RequestMapping("/selectById")
    Module selectModuleById(Integer moduleid) {
        return moduleService.selectModuleById(moduleid);
    }

    @RequestMapping("/delete")
    int deleteModuleById(Integer moduleid) {
        return moduleService.deleteModuleById(moduleid);
    }

    @RequestMapping("/insert")
    int insertModule(Module record) {
        if (record != null) {
            return moduleService.insertModule(record);
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("message","已经存在!");
        return 0;
    }

    @RequestMapping("/update")
    int updateModule(Module record) {
        if (record != null){
            return moduleService.updateModule(record);
        }
        return 0;
    }

}
