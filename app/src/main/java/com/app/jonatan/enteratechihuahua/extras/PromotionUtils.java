package com.app.jonatan.enteratechihuahua.extras;

import com.android.volley.RequestQueue;
import com.app.jonatan.enteratechihuahua.json.Endpoints;
import com.app.jonatan.enteratechihuahua.json.Parser;
import com.app.jonatan.enteratechihuahua.json.Requestor;
import com.app.jonatan.enteratechihuahua.pojo.Promotion;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jonatan on 11/05/2016.
 */
public class PromotionUtils {
    public static ArrayList<Promotion> loadPromotions(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestPromotionsJSON(requestQueue, Endpoints.getUrlPromotions());
        ArrayList<Promotion> listPromotions = Parser.parsePromotionsJson(response);

        return listPromotions;
    }
}
