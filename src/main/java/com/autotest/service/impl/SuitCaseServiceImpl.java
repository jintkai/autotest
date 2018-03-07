package com.autotest.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.autotest.dao.SuitCaseMapper;
import com.autotest.model.*;
import com.autotest.service.SuitCaseService;
import com.autotest.service.impl.common.HttpClientServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SuitCaseServiceImpl implements SuitCaseService {

    @Autowired
    SuitCaseMapper suitCaseMapper;
    @Autowired
    SuitCaseResultServiceImpl suitCaseResultService;
    @Autowired
    HttpClientServiceImpl httpClientService;
    @Autowired
    VariableServiceImpl variableService;
    @Autowired
    VariableResultServiceImpl variableResultService;
    @Autowired
    VariableResolverServerImpl resolverServer;

    private int success = 1;

    @Override
    public SuitCase selectById(Integer id) {
        return suitCaseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SuitCase> selectBySuitID(Integer suitID) {
        return suitCaseMapper.selectBySuitID(suitID);
    }

    @Override
    public List<SuitCase> selectMainCaseBySuitID(Integer suitID) {
        return suitCaseMapper.selectMainCaseBySuitID(suitID);

    }

    @Override
    public int deleteSuitCaseById(Integer id) {
        return suitCaseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSuitCase(SuitCase record) {
        return suitCaseMapper.insertSelective(record);
    }

    @Override
    public SuitCase selectSuitCaseById(Integer id) {
        return suitCaseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SuitCase> selectBySuitIdCaseId(Integer suitid, Integer caseid) {
        return suitCaseMapper.selectBySuitIdCaseId(suitid, caseid);
    }

    @Override
    public List<SuitCase> selectSubCase(Integer caseid) {
        return suitCaseMapper.selectSubCase(caseid);
    }

    @Override
    public int updateSuitCase(SuitCase record) {
        return suitCaseMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int countCaseByTime(Long time) {
        return suitCaseMapper.countCase(time);
    }

    /**
     * 根据result是否已经存在，判断当前是insert还是update数据；
     *
     * @param suitCaseResult 需要存储的suitresult信息
     */
    private void updateResult(SuitCaseResult suitCaseResult) {
        List<SuitCaseResult> lists = suitCaseResultService.selectList(suitCaseResult);
        if (lists.size() == 1) {
            suitCaseResult.setId(lists.get(0).getId());
            suitCaseResultService.updateSuitResult(suitCaseResult);
        } else {
            suitCaseResultService.insertSuitResult(suitCaseResult);
            suitCaseResult.setId(suitCaseResultService.selectSuitResult(suitCaseResult.getSuitcaseid(), suitCaseResult.getBuildid()).getId());
        }
    }

    /**
     * 根据变量解析的map结果，提取map中对应的结果。解析的最终结果返回给对应的变量；
     *
     * @param resultMap     map中为变量解析的原始数据；
     * @param resultRequest 存储解析的map中对应的result信息；
     */
    private AssertModel resolveVariable(Map resultMap, BaseResp resultRequest) {
        AssertModel resultAssert = new AssertModel();
        if ((Integer) resultMap.get("success") == 0) {
            resultAssert.setSuccess(0);
            resultAssert.setMessage((String) resultMap.get("message"));
            success = 0;
            resultRequest.setMsg((String) resultMap.get("message"));
            resultRequest.setCode(500);
        } else {
            resultRequest.setMsg((String) resultMap.get("result"));
            resultRequest.setCode(200);
            resultAssert.setSuccess(1);
        }
        return resultAssert;
    }

    /**
     * 根据suitcase，httpinfo信息，判断断言是否通过
     *
     * @param suitCase 用例信息，存储断言信息；
     * @param httpInfo info中记录了case的运行结果。
     * @return 用于存储断言的验证结果;
     */
    private  List<AssertExpModal> resoleveAssertExp(AssertModel expAssert, SuitCase suitCase, HttpInfo httpInfo) {
        expAssert.setAssertType("assert");
        expAssert.setSuccess(1);
        List<AssertExpModal> assertExpModals = new ArrayList<>();
        String assertExpStr = suitCase.getAssertexp();
        List<Map<String, Object>> assertMapList = (List<Map<String, Object>>) JSONArray.parse(assertExpStr.equals("")?"[]":assertExpStr);
        for(int i =0;i<assertMapList.size();i++){
            Map<String, Object> assertMap=assertMapList.get(i);
            assertExpModals.add(
                    new AssertExpModal((Integer) assertMap.get("id"), (String) assertMap.get("aType"),
                            (String) assertMap.get("variable"), (boolean) assertMap.get("negation"),
                            (String) assertMap.get("rule"), assertMap.get("aValue").toString()));
        }

        List<Map<String, Object>> assertResults = new ArrayList<>();
        for (AssertExpModal assertExpModal : assertExpModals) {
            Map<String, Object> singleResult = new HashMap<>();
            singleResult.put("id", assertExpModal.getId());
            String type = assertExpModal.getType();
            boolean result = false;
            if (type.equals("code")) {
                switch (assertExpModal.getRule()) {
                    case "equal":
                        if (assertExpModal.getValue().equals(httpInfo.getResponseCode())) {
                            result = true;
                        }
                        break;
                    case "unequal":
                        if (!assertExpModal.getValue().equals(httpInfo.getResponseCode())) {
                            result = true;
                        }
                        break;
                    default:
                        break;
                }
                singleResult.put("message", httpInfo.getResponseCode());
                if (assertExpModal.isNegation()) {
                    result = !result;
                }
            } else if (type.equals("spendTime")) {
                switch (assertExpModal.getRule()) {
                    case "equal":
                        if (Long.valueOf(assertExpModal.getValue()) == httpInfo.getResponseTime()) {
                            result = true;
                        }
                        break;
                    case "unequal":
                        if (Long.valueOf(assertExpModal.getValue()) != httpInfo.getResponseTime()) {
                            result = true;
                        }
                        break;
                    case "less":
                        if (Long.valueOf(assertExpModal.getValue()) > httpInfo.getResponseTime()) {
                            result = true;
                        }
                        break;
                    case "greater":
                        if (Long.valueOf(assertExpModal.getValue()) < httpInfo.getResponseTime()) {
                            result = true;
                        }
                        break;
                    default:
                        break;
                }
                singleResult.put("message",httpInfo.getResponseTime());
                if (assertExpModal.isNegation()) {
                    result = !result;
                }
            }else if( type.equals("responseBody")){
                switch (assertExpModal.getRule()) {
                    case "equal":
                        if (assertExpModal.getValue().equals(httpInfo.getResponseBody())) {
                            result = true;
                        }
                        break;
                    case "contain":
                        if (httpInfo.getResponseBody().contains(assertExpModal.getValue())) {
                            result = true;
                        }
                        break;
                    default:
                        break;
                }
                singleResult.put("message", httpInfo.getResponseBody());
                if (assertExpModal.isNegation()) {
                    result = !result;
                }
            }

            if (result == false) {
                singleResult.put("success", 0);
                expAssert.setSuccess(0);
                httpInfo.setIsSuccess(2);
            } else {
                singleResult.put("success", 1);
            }
            assertResults.add(singleResult);
            assertExpModal.setResult(singleResult);
            expAssert.setMessage(assertResults);
        }
        return assertExpModals;
    }

    @Override
    public SuitCaseResult suitCaseRun(SuitCase suitCase, int buildid, boolean skip) {
        BaseResp urlRequest = new BaseResp();
        BaseResp headerRequest = new BaseResp();
        BaseResp bodyRequest = new BaseResp();
        List<AssertExpModal> assertExpModals = new ArrayList<>();
        //拷贝用例自定义变量到每次只需的自定义result表
        variableResultService.copyVariable(suitCase.getSuitid(), buildid);
        if (skip == true) {
            SuitCaseResult suitCaseResult = new SuitCaseResult();
            suitCaseResult.setSuitcaseid(suitCase.getId());
            suitCaseResult.setBuildid(buildid);
            suitCaseResult.setSuitid(suitCase.getSuitid());
            suitCaseResult.setRequestHeader(headerRequest.getMsg());
            suitCaseResult.setRequestUrl(urlRequest.getMsg());
            suitCaseResult.setRequestBody(bodyRequest.getMsg());
            suitCaseResult.setStatus(3);
            updateResult(suitCaseResult);
            return suitCaseResult;
        }

        Map<String, Object> urlMap = resolverServer.resolver(suitCase.getSuitid(), buildid, suitCase.getRequesturl() == null ? "" : suitCase.getRequesturl());
        Map<String, Object> headerMap = resolverServer.resolver(suitCase.getSuitid(), buildid, suitCase.getRequestheader() == null ? "" : suitCase.getRequestheader());
        Map<String, Object> bodyMap = resolverServer.resolver(suitCase.getSuitid(), buildid, suitCase.getRequestbody() == null ? "" : suitCase.getRequestbody());
        String message = "";
        int success = 1;
        List<AssertModel> assertModels = new ArrayList<>();
        //判断请求头、请求体、url解析是否正确。
        AssertModel urlAssert = new AssertModel();
        AssertModel headerAssert = new AssertModel();
        AssertModel bodyAssert = new AssertModel();


        urlAssert = resolveVariable(urlMap, urlRequest);
        headerAssert = resolveVariable(headerMap, headerRequest);
        bodyAssert = resolveVariable(bodyMap, bodyRequest);
        urlAssert.setAssertType("url");
        headerAssert.setAssertType("header");
        bodyAssert.setAssertType("body");

        if (success == 0) {
            message = "Case解析变量失败!";
        }
        assertModels.add(urlAssert);
        assertModels.add(headerAssert);
        assertModels.add(bodyAssert);

        SuitCaseResult suitCaseResult = new SuitCaseResult();
        //参数解析失败，直接退出程序。保存执行结果
        if (success == 0) {
            suitCaseResult.setResponsecode("");
            ObjectMapper mapper = new ObjectMapper();
            String jsonStr = "";
            try {
                jsonStr = mapper.writeValueAsString(assertModels);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            suitCaseResult.setAssertlog(jsonStr);
            suitCaseResult.setSuitcaseid(suitCase.getId());
            suitCaseResult.setBuildid(buildid);
            suitCaseResult.setRequestBody(bodyRequest.getMsg());
            suitCaseResult.setRequestUrl(urlRequest.getMsg());
            suitCaseResult.setRequestHeader(headerRequest.getMsg());
            List<SuitCaseResult> lists = suitCaseResultService.selectList(suitCaseResult);
            suitCaseResult.setSuitid(suitCase.getSuitid());
            suitCaseResult.setStatus(0);
            if (lists.size() == 1) {
                suitCaseResult.setId(lists.get(0).getId());
                suitCaseResultService.updateSuitResult(suitCaseResult);
            } else {
                suitCaseResultService.insertSuitResult(suitCaseResult);
            }
            return suitCaseResult;
        }
        //解析成功，继续执行
        HttpInfo httpInfo = new HttpInfo();
        httpInfo = httpClientService.sentRequest(Integer.valueOf(suitCase.getRequesttype()), (String) urlMap.get("result"), suitCase.getRequestheader(), (String) bodyMap.get("result"));
        //发送请求后，根据caseid，实例化参数；

        if (httpInfo.getIsSuccess() == 1) {
            /**
             * 变量解析逻辑,变量不与case绑定，与suit绑定
             */
//            List<Variable> caseVariables = variableService.selectBySuitCaseId(suitCase.getId());
//            for (Variable variable : caseVariables) {
//                variableService.resolveExpress(variable, buildid, httpInfo);
//            }
            /**
             * 断言逻辑，断言解析，断言判断
             */
            AssertModel expAssert =new AssertModel() ;
            assertExpModals = resoleveAssertExp(expAssert,suitCase, httpInfo);
            assertModels.add(expAssert);

        } else {
            //http执行失败
            success = 0;
            message = httpInfo.getResponseLog();
            AssertModel httpAssert = new AssertModel();
            httpAssert.setAssertType("http");
            httpAssert.setSuccess(0);
            httpAssert.setMessage(message);
            assertModels.add(httpAssert);
        }

        /**
         * 存储结果逻辑
         */
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = "";
        try {
            jsonStr = mapper.writeValueAsString(assertModels);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        suitCaseResult.setSuitcaseid(suitCase.getId());
        suitCaseResult.setBuildid(buildid);
        suitCaseResult.setSuitid(suitCase.getSuitid());
        suitCaseResult.setRequestHeader(headerRequest.getMsg());
        suitCaseResult.setRequestUrl(urlRequest.getMsg());
        suitCaseResult.setRequestBody(bodyRequest.getMsg());
        suitCaseResult.setResponsecode(httpInfo.getResponseCode());
        suitCaseResult.setResponsebody(httpInfo.getResponseBody());
        suitCaseResult.setResponseheader(httpInfo.getResponseHeader());
        suitCaseResult.setAssertlog(jsonStr);
        suitCaseResult.setResponsetime(Integer.valueOf(String.valueOf(httpInfo.getResponseTime())));
        suitCaseResult.setStatus(httpInfo.getIsSuccess());
        if(assertExpModals.isEmpty()){
            suitCaseResult.setAssertExp("[]");
        }else{
            suitCaseResult.setAssertExp(JSON.toJSONString(assertExpModals));
        }
        updateResult(suitCaseResult);
        return suitCaseResult;
    }

}
