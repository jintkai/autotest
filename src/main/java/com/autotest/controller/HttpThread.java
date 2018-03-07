package com.autotest.controller;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

class HttpThread extends Thread {
    public static final Logger LOG = LoggerFactory.getLogger(HttpThread.class);

    String name;
    int active =1;
    int stop;



    public int getStop() {
        return stop;
    }

    public void setStop(int stop) {
        this.stop = stop;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public HttpThread(String name) {
        this.name = name;
    }



    @Async
    public void sentActive() {

        HttpPost httpPost = new HttpPost();
        try {
            httpPost.setURI(new URI("http://192.168.94.191:8989/instance/heartBeat"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        httpPost.addHeader("Content-Type", "application/x-www-formurlencoded");
        Date begin = new Date();
        String body = "{\"ins\":\"test-jinkai-merc-" + name + "\",\"active\":\""+active+"\",\"checkErr\":\"" + begin + "\",\"reloadErr\":\"rrrrrdddddeeeeee\"}";
        StringEntity entity = new StringEntity(body, ContentType.create("application/json", "UTF-8"));
        httpPost.setEntity(entity);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(10000)
                .setConnectTimeout(10000)
                .build();

        httpPost.setConfig(requestConfig);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date beginTime = new Date();
            System.out.println("test-jinkai-merc-"+name+":"+sdf1.format(beginTime));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            Date endTime = new Date();
            System.out.println("responseTime:" + Long.valueOf(endTime.getTime() - beginTime.getTime()));
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("responseCode：" + response.getStatusLine().getStatusCode());
            }
            if (!EntityUtils.toString(response.getEntity()).equals("")) {
                System.out.println(EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (stop != 1) {
                sentActive();
            }
        }

    }
}

class SearchThread extends Thread {

    String body;
    int id;
    int active;
    int errorActive;

    public SearchThread(int id, String body, int active) {
        this.body = body;
        this.id = id;
        this.active = active;
    }

    public int getErrorActive() {
        return errorActive;
    }

    public void setErrorActive(int errorActive) {
        this.errorActive = errorActive;
    }

    public int searchActive() {
        HttpPost httpPost = new HttpPost();
        try {
            httpPost.setURI(new URI("http://192.168.231.174:8090/table/instance?method=batch_query"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        httpPost.addHeader("Content-Type", "application/x-www-formurlencoded");
        StringEntity entity = new StringEntity(body, ContentType.create("application/json", "UTF-8"));
        httpPost.setEntity(entity);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            httpEntity.getContent();
            String result = EntityUtils.toString(httpEntity);
            String temp = "\"active\":\"" + (active == 1 ? 0 : 1) + "\"";
            int index;
            String result2 = result;
            if (result2.indexOf("active") == -1) {
                errorActive += 999999;
                System.out.println("errorActiveERROR...");
            } else
                while ((index = result2.indexOf(temp)) != -1) {
                    result2 = result2.substring(index + temp.length());
                    errorActive++;
                }
            System.out.println("result:->" + result);
            return errorActive;
        } catch (IOException e) {
            e.printStackTrace();
            return errorActive++;
        }
    }

    public void run() {
        errorActive = searchActive();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd hh:mm:ss.SSS");
        Date beginTime = new Date();
        System.out.println(sdf1.format(beginTime)+",errorActive:" + errorActive);
    }
}

