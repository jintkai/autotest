package com.autotest.service;

import com.autotest.model.Module;

public interface ModuleService {
    Module findModuleByName(String name);
    Module findModuleByID(Integer id);
}
