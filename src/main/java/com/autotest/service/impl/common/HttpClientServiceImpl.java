package com.autotest.service.impl.common;

import com.autotest.model.HttpInfo;
import com.autotest.util.JasonUtil;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class HttpClientServiceImpl {

    public static final Logger LOG = LoggerFactory.getLogger(HttpClientServiceImpl.class);

    public HttpInfo sentRequest(int requestType, String requestUrl, String requestHeader, String requestParameters) {
        HttpInfo httpInfo = new HttpInfo();
        Class<?> cls = null;
        Object object = null;
        String className = "";
        switch (requestType) {
            case 0:
                className = "org.apache.commons.httpclient.methods.GetMethod";
                break;
            case 1:
                className = "org.apache.commons.httpclient.methods.PostMethod";
                break;
            case 2:
                className = "org.apache.commons.httpclient.methods.PostMethod";
                break;
            case 3:
                className = "org.apache.commons.httpclient.methods.DeleteMethod";
                break;
            default:
                className = "org.apache.commons.httpclient.methods.GetMethod";
        }
        try {
            cls = Class.forName(className);
            object = cls.newInstance();
        } catch (Exception e) {
            LOG.error("http方法错误"+e.toString());
        }

        HttpMethod method = (HttpMethod) object;

        JSONObject jsonObject;

        //请求头转json
        if (requestHeader != null && !requestHeader.isEmpty()) {
            LOG.info("requestHeader:"+requestHeader);
            if(JasonUtil.isJson(requestHeader)) {
                try {
                    jsonObject = new JSONObject(requestHeader);
                    Iterator iterator = jsonObject.keys();
                    while (iterator.hasNext()) {
                        String key = (String) iterator.next();
                        method.setRequestHeader(key, jsonObject.getString(key));
                    }
                } catch (Exception e) {
                    LOG.error("requestHeader转json失败。" + e.toString());
                }
            }else{
                LOG.error("requestHeader不是json格式，无法转换。" );
            }
        }


        try {
            method.setURI(new URI(requestUrl));

            if (null != requestParameters && !requestParameters.isEmpty()) {
                Header header = method.getRequestHeader("Content-Type");
                //非json
                if (null == header || !header.getValue().contains("application/json")) {
                    List<NameValuePair> lists = new ArrayList<NameValuePair>();
                    String str = requestParameters;
                    String[] strs = str.split(",");
                    Map<String, String> m = new HashMap<String, String>();
                    for (int i = 0; i < strs.length; i++) {
                        String s = strs[i];
                        String[] ms = s.split("=");
                        m.put(ms[0], ms[1]);
                        lists.add(new NameValuePair(ms[0], ms[1]));
                    }
                    NameValuePair[] pairs = new NameValuePair[lists.size()];
                    for (int i = 0; i < lists.size(); i++) {
                        pairs[i] = lists.get(i);
                    }
                    //form表单
                    if (1 == requestType) {
                        Part[] parts = new Part[lists.size()];
                        for (int i =0;i<lists.size();i++){
                            parts[i] = new StringPart(lists.get(i).getName(),lists.get(i).getValue());
                        }
                        method = (PostMethod) method;
                        ((PostMethod) method).setRequestEntity(new MultipartRequestEntity(parts, method.getParams()));
                    } else {
                        method.setQueryString(pairs);
                    }
                    httpInfo.setRequestParam(Arrays.toString(pairs));
                }
                else {
                    //json格式
                    RequestEntity entity = new StringRequestEntity(requestParameters);
                    ((PostMethod) method).setRequestEntity(entity);
                    httpInfo.setRequestParam(requestParameters);
                }
            }
            httpInfo.setRequestUrl(method.getURI().toString());
        } catch (URIException e) {
            httpInfo.setIsSuccess(0);
            StringBuffer buffer = new StringBuffer(httpInfo.getResponseLog());
            buffer = buffer.append("请求头或者参错误，无法发送请求。");
            httpInfo.setResponseLog(buffer.toString());
            LOG.error(e.toString());
            return httpInfo;
        }

        HttpClient httpClient = new HttpClient();


        httpClient.getHttpConnectionManager().getParams()
                .setConnectionTimeout(10000);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(10000);


        try {
            Date begin = new Date();

            httpClient.executeMethod(method);

            if(Integer.valueOf(method.getStatusCode()) >= 400){
                httpInfo.setIsSuccess(0);
                httpInfo.setResponseTime(0L);
                httpInfo.setResponseLog("HttpResponseCode Error:"+method.getStatusCode());
                return httpInfo;
            }

            Date end = new Date();
            long responseTime = end.getTime() - begin.getTime();
            httpInfo.setResponseTime(responseTime);
        } catch (Exception e) {
            //此处用来设置返回error
            httpInfo.setIsSuccess(0);
            if (e.getClass().getName().equals("org.apache.commons.httpclient.ConnectTimeoutException") ||
                    e.getClass().getName().equals("java.net.SocketTimeoutException")){
                LOG.error("链接超时...");
                StringBuffer buffer = new StringBuffer(httpInfo.getResponseLog());
                buffer = buffer.append("链接超时。");
                httpInfo.setResponseLog(buffer.toString());
                return httpInfo;
            }
            if (e.toString().contains("Connection refused"))
            {
                LOG.error("拒绝链接...");
                StringBuffer buffer = new StringBuffer(httpInfo.getResponseLog());
                buffer = buffer.append("拒绝链接...");
                httpInfo.setResponseLog(buffer.toString());
                return httpInfo;
            }

            e.printStackTrace();
        }
        try {
            String response = method.getResponseBodyAsString();
            httpInfo.setResponseCode(String.valueOf(method.getStatusCode()));
            httpInfo.setResponseBody(response.length()<=1024? response:response.substring(0,1024));
            httpInfo.setResponseHeader(Arrays.toString(method.getResponseHeaders()));
            httpInfo.setIsSuccess(1);
            return httpInfo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
