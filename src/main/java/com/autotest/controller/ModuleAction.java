package com.autotest.controller;

import com.autotest.model.BaseResp;
import com.autotest.model.Module;
import com.autotest.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
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
        resultMap.put("result",moduleService.selectModules());
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

    @RequestMapping(value = "/delete")
    BaseResp deleteModuleById(Integer moduleid) {
        BaseResp baseResp = new BaseResp();

        int result = moduleService.deleteModuleById(moduleid);
        if (result ==1){
            baseResp.setCode(200);
        }else {
            baseResp.setCode(500);
        }
        return baseResp;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public BaseResp insertModule(Module record) {
        BaseResp resp = new BaseResp();
        if (record != null) {
            if (moduleService.insertModule(record) == 1){
                resp.setCode(200);
            }else{
                resp.setCode(500);
                resp.setMsg("增加失败，检查moduleName，modulePid是否重复");
            }
        }
        return resp;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public BaseResp updateModule(Module record) {
        BaseResp resp = new BaseResp();
        if (record != null){
            int result = moduleService.updateModule(record);
            if(result == 1){
                resp.setCode(200);
            }else {
                resp.setCode(500);
                resp.setMsg("数据重复，无法提交!");
            }
        }
        return resp;
    }

}
