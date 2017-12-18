package com.autotest.util;

import org.json.JSONObject;

public class JasonUtil {

    public static boolean isJson(String str) {
        boolean isJson = false;
        try {
            JSONObject jsonObject = new JSONObject(str);
            isJson = true;
        } catch (Exception e) {
            isJson = false;
        }
        return isJson;
    }
}
