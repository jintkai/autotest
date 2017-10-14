package com.autotest.util;

import com.oracle.javafx.jmx.json.JSONException;
import org.json.JSONObject;

public class JasonUtil {

    public static boolean isJson(String str) {
        boolean isJson = false;
        try {
            JSONObject jsonObject = new JSONObject(str);
            isJson = true;
        } catch (JSONException e) {
            isJson = false;
        }
        return isJson;
    }
}
