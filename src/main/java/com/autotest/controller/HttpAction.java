package com.autotest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/http")
public class HttpAction {
    public static final Logger LOG = LoggerFactory.getLogger(HttpAction.class);

    static List<Integer> killNumber;
    static List<Thread> threadList;
    public HttpAction(){
        threadList= new ArrayList<>();
    }
    @RequestMapping(value = "run", method = RequestMethod.POST)
    public void run(int start ,int end) {
        killNumber = new ArrayList<>();
        for (int j = start; j <= end; j++) {
            HttpThread thread = new HttpThread(Integer.toString(j));
            threadList.add(thread);
            thread.start();
        }
    }

    @RequestMapping(value="kill",method=RequestMethod.POST)
    public void kill(int start,int end){
        System.out.println("kill:"+start+"-"+end);
        for(start=start-1;start<end;start++){
            killNumber.add(start);
            threadList.get(start).stop();
        }
    }

    @RequestMapping(value="stop",method=RequestMethod.POST)
    public void stop(int start,int end,int stop){
        for(start=start-1;start<end;start++){
            killNumber.add(start);
            HttpThread thread = (HttpThread) threadList.get(start);
            thread.setStop(stop);
        }
    }

    @RequestMapping(value="active",method=RequestMethod.POST)
    public void setActive(int start,int end,int active){
        for(start=start-1;start<end;start++){
            killNumber.add(start);
            HttpThread thread = (HttpThread) threadList.get(start);
            thread.setActive(active);
        }
    }

    @RequestMapping(value="search",method=RequestMethod.POST)
    public void search(int start,int end,int active,int batch){
        List<SearchThread> searchThreads = new ArrayList<>();

        List<List<String>> body = new ArrayList<>();
        for(int i =0;i<batch;i++){
            body.add(new ArrayList<String>());
        }
        for(;start<=end;start++){
            String tempStr="\"id=test-jinkai-merc-"+start+"\"";
            int temp = start%batch;
            body.get(temp).add(tempStr);
        }
        for(int i =0;i<batch;i++) {
            searchThreads.add(new SearchThread(i,body.get(i).toString(),active));
            searchThreads.get(i).start();
        }
    }

}

