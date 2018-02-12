package com.autotest.controller;

import com.autotest.model.BaseResp;
import com.autotest.model.Suit;
import com.autotest.model.SuitCase;
import com.autotest.service.SuitCaseService;
import com.autotest.service.SuitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/suitcase")
public class SuitCaseAction {

    @Autowired
    SuitCaseService suitCaseService;

    @Autowired
    SuitService suitService;


    @RequestMapping("/{id}")
    public Map<String,Object> getSuitCases(@PathVariable( name = "id") Integer id){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        SuitCase o = suitCaseService.selectSuitCaseById(id);
        resultMap.put("results",o);
        return resultMap;
    }

    @RequestMapping("/")
    public BaseResp getSuitCasesBySuitID(Integer suitID){
        BaseResp baseResp = new BaseResp();
        List<SuitCase> caseList = suitCaseService.selectBySuitID(suitID);
        baseResp.setCode(200);
        baseResp.setData(caseList);
        return baseResp;
    }
    @RequestMapping("/getByid")
    public BaseResp getSuitCaseByID(Integer id){
        BaseResp baseResp = new BaseResp();
        SuitCase suitCase = suitCaseService.selectSuitCaseById(id);
        if (suitCase == null){
            baseResp.setCode(500);
            baseResp.setMsg("查询结果空");
        }else{
            baseResp.setCode(200);
            baseResp.setData(suitCase);
        }
        return baseResp;
    }

    @RequestMapping("/getMainCase")
    public BaseResp getMainCase(Integer suitID){
        BaseResp baseResp = new BaseResp();
        List<SuitCase> caseList = suitCaseService.selectMainCaseBySuitID(suitID);
        baseResp.setCode(200);
        baseResp.setData(caseList);
        return baseResp;
    }
    @RequestMapping("/getSubCase")
    public BaseResp getSubCaseByMainCase(Integer caseid){
        BaseResp baseResp = new BaseResp();
        List<SuitCase> suitCases = suitCaseService.selectSubCase(caseid);
        baseResp.setCode(200);
        baseResp.setData(suitCases);
        return baseResp;
    }

    @RequestMapping(value = "/run" ,method = RequestMethod.POST)
    public BaseResp runCase( Integer id,Integer buildid){
        BaseResp baseResp = new BaseResp();
        SuitCase o = suitCaseService.selectSuitCaseById(id);
        Object result = suitCaseService.suitCaseRun(o,buildid);
        baseResp.setCode(200);
        baseResp.setData(result);
        return baseResp;
    }



    @RequestMapping(value = "/runsuit" ,method = RequestMethod.POST)
    public BaseResp runSuit( Integer id,Integer buildid,boolean debug){
        BaseResp baseResp = new BaseResp();
        if (debug){
            buildid = 0;
        }else{
            Suit suit = suitService.selectSuitById(id);
            if ( null == suit ){
                buildid = 1;
            }else{
                if (suit.getStatus() == 1){
                    baseResp.setCode(500);
                    baseResp.setMsg("Suit is running now.Run it after finished please!");
                    return baseResp;
                }else
                    buildid = suit.getLastbuildid()+1;
            }
            suit.setLastbuildid(buildid);
            suit.setStatus(1);
            suitService.updateSuit(suit);
        }

        Map<String,Object> resultMap = new HashMap<String,Object>();
        List<SuitCase> lists = suitCaseService.selectBySuitID(id);
        for (SuitCase suitCase : lists) {
            runCase(suitCase.getId(),buildid);
        }
        resultMap.put("result",lists);
        if (!debug){
            Suit suit = new Suit();
            suit.setSuitid(id);
            suit.setStatus(0);
            suitService.updateSuit(suit);
        }

        baseResp.setCode(200);
        baseResp.setData(resultMap);
        return baseResp;
    }




    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public BaseResp deleteSuitCaseById(Integer id){
        BaseResp baseResp = new BaseResp();
        if (suitCaseService.deleteSuitCaseById(id) == 1){
            baseResp.setCode(200);
        }else{
            baseResp.setCode(500);
            baseResp.setMsg("删除失败");
        }
        return baseResp;
    }

    @RequestMapping("/insert")
    public BaseResp insertSuitCase(SuitCase record){
        BaseResp baseResp = new BaseResp();
        int i = suitCaseService.insertSuitCase(record);
        if (i == 1){
            baseResp.setCode(200);
        }else{
            baseResp.setCode(500);
            baseResp.setMsg("插入数据库失败!");
        }
        return baseResp;
    }


    @RequestMapping("/selectBySuitIdCaseId")
    List<SuitCase> selectBySuitIdCaseId(Integer suitid,Integer caseid){
        if (suitid == null && caseid == null){
            return null;
        }
        return suitCaseService.selectBySuitIdCaseId(suitid,caseid);
    }

    @RequestMapping("/update")
    public BaseResp updateSuitCase(SuitCase record){
        BaseResp baseResp = new BaseResp();
        int i = suitCaseService.updateSuitCase(record);
        if (i == 1){
            baseResp.setCode(200);
        }else{
            baseResp.setCode(500);
            System.out.println("更新数据库失败："+record);
            baseResp.setMsg("更新数据库失败："+record);
        }
        return baseResp;
    }
}
