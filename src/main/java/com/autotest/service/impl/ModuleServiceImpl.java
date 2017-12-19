package com.autotest.service.impl;

import com.autotest.dao.ModuleMapper;
import com.autotest.model.Module;
import com.autotest.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleServiceImpl  implements ModuleService{

    @Autowired
    ModuleMapper moduleMapper;

    @Override
    public int deleteModuleById(Integer moduleid){
        return moduleMapper.deleteByPrimaryKey(moduleid);
    }

    @Override
    public int insertModule(Module record){
        if(moduleMapper.selectByNameAndPid(record).size() == 0){
            return moduleMapper.insertSelective(record);
        }else{
            return 0;
        }
    }

    @Override
    public Module selectModuleById(Integer moduleid){
        return moduleMapper.selectByPrimaryKey(moduleid);
    }

    @Override
    public int updateModule(Module record){
        List<Module> modules = moduleMapper.selectByNameAndPid(record);
        if (modules.size() == 0){
            return moduleMapper.updateByPrimaryKeySelective(record);
        }
        if (modules.size() == 1 && modules.get(0).getModuleid() == record.getModuleid() ) {
            return moduleMapper.updateByPrimaryKeySelective(record);
        }else{
            //更新失败，数据重复;
            return 0;
        }
    }

    @Override
    public List<Module> selectModuleByName(String name){
        return moduleMapper.selectByName(name);
    }

    @Override
    public List<Module> selectModules() {
        List<Module> list = moduleMapper.selectModules();
        return list;
    }


}
