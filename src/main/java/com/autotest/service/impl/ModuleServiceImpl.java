package com.autotest.service.impl;

import com.autotest.dao.ModuleMapper;
import com.autotest.model.Module;
import com.autotest.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl  implements ModuleService{
    @Autowired
    ModuleMapper moduleMapper;
    @Override
    public Module findModuleByName(String name) {
        return moduleMapper.selectByName(name);
    }

    @Override
    public Module findModuleByID(Integer id) {
        return moduleMapper.selectByPrimaryKey(id);
    }
}
