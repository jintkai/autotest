package com.autotest.service;

import com.autotest.model.Module;

import java.util.List;

public interface ModuleService {

    int deleteModuleById(Integer moduleid);

    int insertModule(Module record);

    Module selectModuleById(Integer moduleid);

    int updateModule(Module record);

    List<Module> selectModuleByName(String name);

    List<Module> selectModules();
}
