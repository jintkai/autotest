package com.autotest.controller;

import com.autotest.model.BaseResp;
import com.autotest.model.Suit;
import com.autotest.service.SuitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/")
    BaseResp getSuits(){
        BaseResp baseResp = new BaseResp();
        List<Suit> suitList = suitService.selectSuitList();
        baseResp.setCode(200);
        baseResp.setData(suitList);
        return baseResp;
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public BaseResp deleteSuitById(Integer suitid){
        BaseResp baseResp = new BaseResp();
        if(suitService.deleteSuitById(suitid) == 1)
        {
            baseResp.setCode(200);
        }else {
            baseResp.setCode(500);
            baseResp.setMsg("数据库影响数据不为1");
        }
        return baseResp;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public BaseResp insertSuit(Suit record){
        BaseResp baseResp = new BaseResp();
        if(record.getSuitname().equals("")){
            baseResp.setCode(500);
            baseResp.setMsg("suitName不允许为空");
            return baseResp;
        }
        record.setStatus(0);
        record.setLastbuildid(0);
        if ( record != null){
            if(suitService.insertSuit(record) == 1) {
                baseResp.setCode(200);
            }else{
                baseResp.setCode(500);
                baseResp.setMsg("插入失败，请检查套件名称是否重复!");
            }
        }
        return  baseResp;
    }

    @RequestMapping("/selectById")
    public BaseResp selectSuitById(Integer suitid){
        BaseResp baseResp = new BaseResp();
        Suit suit = suitService.selectSuitById(suitid);
        if (suit == null){
            baseResp.setCode(500);
            baseResp.setMsg("查询数据错误!");
        }else{
            baseResp.setCode(200);
            baseResp.setData(suit);
        }
        return baseResp;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public BaseResp updateSuit(Suit record){
        BaseResp baseResp = new BaseResp();
        if (record != null){
            if (suitService.updateSuit(record) == 1){
                baseResp.setCode(200);
            }else{
                baseResp.setCode(500);
                baseResp.setMsg("更新失败，检查套件名称是否唯一！");
            }
        }
        return baseResp;
    }

    @RequestMapping("/selectByName")
    List<Suit> selectSuitByName(String suitname){
        return suitService.selectSuitByName(suitname);
    }
}
