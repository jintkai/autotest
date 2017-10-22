package com.autotest.controller;

import com.autotest.model.Suit;
import com.autotest.service.SuitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ying.zhang on 17/10/15.
 */
@RestController
@RequestMapping("/suit")
public class SuitAction {

    @Autowired
    SuitService suitService;

    @RequestMapping("/delete")
    int deleteSuitById(Integer suitid){
        return suitService.deleteSuitById(suitid);
    }

    @RequestMapping("/insert")
    int insertSuit(Suit record){
        if ( record != null){
            return suitService.insertSuit(record);
        }
        return  0;
    }

    @RequestMapping("/selectById")
    Suit selectSuitById(Integer suitid){
        return suitService.selectSuitById(suitid);
    }

    @RequestMapping("/update")
    int updateSuit(Suit record){
        if (record != null){
            return suitService.updateSuit(record);
        }
        return 0;
    }

    @RequestMapping("/selectByName")
    List<Suit> selectSuitByName(String suitname){
        return suitService.selectSuitByName(suitname);
    }
}
