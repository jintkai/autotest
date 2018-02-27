package com.autotest.service.impl.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autotest.model.HttpInfo;
import com.autotest.util.JasonUtil;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;

import javax.print.URIException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Service
public class HttpClientServiceImpl {

    public static final Logger LOG = LoggerFactory.getLogger(HttpClientServiceImpl.class);

    public HttpInfo sentRequest(int requestType, String requestUrl, String requestHeader, String requestParameters) {

        HttpInfo httpInfo = new HttpInfo();
        CloseableHttpClient httpClient = HttpClients.createDefault();

        Class<?> cls = null;
        Object object = null;
        String className = "";
        switch (requestType) {
            case 0:
                className = "org.apache.http.client.methods.HttpGet";
                break;
            case 1:
                className = "org.apache.http.client.methods.HttpPost";
                break;
            case 2:
                className = "org.apache.http.client.methods.HttpPost";
                break;
            case 3:
                className = "org.apache.http.client.methods.HttpDelete";
                break;
            default:
                className = "org.apache.http.client.methods.HttpGet";
        }
        try {
            cls = Class.forName(className);
            object = cls.newInstance();
        } catch (Exception e) {
            LOG.error("http方法错误" + e.toString());
        }

        HttpRequestBase method = (HttpRequestBase) object;

        //请求头转json
        if (requestHeader != null && !requestHeader.isEmpty()) {
            List<Map<String, String>> headerMap = (List<Map<String, String>>) JSONArray.parse(requestHeader);
            for (int i = 0; i < headerMap.size(); i++) {
                method.addHeader(headerMap.get(i).get("pkey"), headerMap.get(i).get("pvalue"));
            }
        }

        try {
            URI uri = new URI(requestUrl);
            method.setURI(uri);
            // 1:form-data,2:json,3:form-urlencoded
            if (null != requestParameters && !requestParameters.isEmpty()) {
                JSONObject jsonObject = JSONObject.parseObject(requestParameters);
                int type = jsonObject.getIntValue("type");
                if (type == 2) {
                    method.addHeader("Content-Type", "application/json");
                    StringEntity requestEntity = null;
                    Object o = jsonObject.get("body");
                    requestEntity = new StringEntity(o.toString(),
                            ContentType.create("application/json", "UTF-8"));
                    ((HttpPost) method).setEntity(requestEntity);
                }
                if (type == 3) {
                    method.addHeader("Content-Type", "application/x-www-formurlencoded");
                    StringEntity requestEntity = null;
                    requestEntity = new StringEntity("a=1&b=2", ContentType.create("application/x-www-formurlencoded", "UTF-8"));
                    ((HttpPost) method).setEntity(requestEntity);
                }
                if (type == 1) {
                    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                    JSONArray jsonArray = jsonObject.getJSONArray("body");
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        nvps.add(new BasicNameValuePair(jsonObject1.getString("pkey"), jsonObject1.getString("pvalue")));
                    }
                    try {
                        ((HttpPost) method).setEntity(new UrlEncodedFormEntity(nvps));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
                httpInfo.setRequestParam(requestParameters);
            }
            httpInfo.setRequestUrl(method.getURI().toString());
        } catch (Exception e) {
            httpInfo.setIsSuccess(0);
            StringBuffer buffer = new StringBuffer(httpInfo.getResponseLog());
            buffer = buffer.append("请求头或者参错误，无法发送请求。");
            httpInfo.setResponseLog(buffer.toString());
            LOG.error(e.toString());
            return httpInfo;
        }
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(3000)
                .setConnectTimeout(3000)
                .build();

        method.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        HttpEntity responseEntity = null;
        try {
            Date begin = new Date();
            response = httpClient.execute(method);
            responseEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() >= 400) {
                httpInfo.setIsSuccess(0);
                httpInfo.setResponseTime(0L);
                httpInfo.setResponseLog("HttpResponseCode Error:" + response.getStatusLine().getStatusCode());
                httpInfo.setResponseCode(String.valueOf(response.getStatusLine().getStatusCode()));
            }
            Date end = new Date();
            long responseTime = end.getTime() - begin.getTime();
            httpInfo.setResponseTime(responseTime);
        } catch (Exception e) {
            httpInfo.setIsSuccess(0);
            if (e.getClass().getName().equals("org.apache.commons.httpclient.ConnectTimeoutException") ||
                    e.getClass().getName().equals("java.net.SocketTimeoutException")) {
                LOG.error("链接超时...");
                StringBuffer buffer = new StringBuffer(httpInfo.getResponseLog());
                buffer = buffer.append("链接超时。");
                httpInfo.setResponseLog(buffer.toString());
                return httpInfo;
            }
            if (e.toString().contains("Connection refused")) {
                LOG.error("拒绝链接...");
                StringBuffer buffer = new StringBuffer(httpInfo.getResponseLog());
                buffer = buffer.append("拒绝链接...");
                httpInfo.setResponseLog(buffer.toString());
                return httpInfo;
            }
            if(e.toString().contains("No route to host")){
                LOG.error("No route to host");
                StringBuffer buffer = new StringBuffer(httpInfo.getResponseLog());
                buffer = buffer.append("No route to host");
                httpInfo.setResponseLog(buffer.toString());
                return httpInfo;
            }
            e.printStackTrace();
        }


        String response1 = ""; //method.getResponseBodyAsString();
        try {
            response1 = responseEntity != null ? EntityUtils.toString(responseEntity) : "";

        } catch (IOException e) {
            e.printStackTrace();
        }
        httpInfo.setResponseCode(String.valueOf(response.getStatusLine().getStatusCode()));
        httpInfo.setResponseBody(response1.length() <= 1024 ? response1 : response1.substring(0, 1024));
        Header[] headers = method.getAllHeaders();
        String headersStr = "";
        for (int i = 0; i < headers.length; i++) {
            headersStr += headers[i].getName() + ":" + headers[i].getValue() + ";";
        }
        httpInfo.setResponseHeader(headersStr);
        httpInfo.setIsSuccess(1);
        return httpInfo;
    }
}
