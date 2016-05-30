package com.app.jonatan.enteratechihuahua.json;

import org.json.JSONObject;

/**
 * Created by Jonatan on 11/05/2016.
 */
public class Utils {
    public static boolean contains(JSONObject jsonObject, String key) {
        return jsonObject != null && jsonObject.has(key) && !jsonObject.isNull(key) ? true : false;
    }
}
