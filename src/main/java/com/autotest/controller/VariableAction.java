package com.autotest.controller;

import com.autotest.model.BaseResp;
import com.autotest.model.Variable;
import com.autotest.service.impl.VarExpressServiceImpl;
import com.autotest.service.impl.VariableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/variable")
public class VariableAction {

    @Autowired
    VariableServiceImpl variableService;
    @Autowired
    VarExpressServiceImpl varExpressService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Map<String, Object> getByid(@RequestParam String by, @RequestParam Integer id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        if (by.equals("id"))
            resultMap.put("result", variableService.selectByID(id));
        if (by.equals("suitid")) {
            resultMap.put("result", variableService.selectBySuitID(id));
        }

        return resultMap;
    }

    @RequestMapping(value = "/suitID", method = RequestMethod.GET)
    public BaseResp getBySuitID(@RequestParam Integer id) {
        BaseResp baseResp = new BaseResp();
        List<Variable> variableList = variableService.selectBySuitID(id);
        if (variableList == null) {
            baseResp.setMsg("查询结果空！");
            baseResp.setCode(500);
        } else {
            baseResp.setCode(200);
            baseResp.setData(variableList);
        }
        return baseResp;
    }


    @RequestMapping(value = "/resolve", method = RequestMethod.POST)
    public Map<String, Object> resolveVariable(@RequestParam Integer id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Variable variable = variableService.selectByID(id);
        resultMap.put("resolve", varExpressService.resolveExpress(variable, 1));
        return resultMap;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public BaseResp insertVariable(Variable variable) {
        BaseResp baseResp = new BaseResp();
        if (variable.getSuitid() == null && variable.getSuitcaseId() == null) {
            baseResp.setCode(500);
            return baseResp;
        }
        return baseResp;
    }

}
