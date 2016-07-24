package com.app.enterate.enteratechihuahua.extras;

import com.android.volley.RequestQueue;
import com.app.enterate.enteratechihuahua.json.Endpoints;
import com.app.enterate.enteratechihuahua.json.Parser;
import com.app.enterate.enteratechihuahua.json.Requestor;
import com.app.enterate.enteratechihuahua.pojo.Promotion;
import com.app.enterate.enteratechihuahua.pojo.TaxiSite;

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

    public static ArrayList<TaxiSite> loadTaxiSites(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestTaxiSitesJSON(requestQueue, Endpoints.getUrlTaxiSites());
        ArrayList<TaxiSite> listTaxiSites = Parser.parseTaxiSitesJson(response);
        System.out.println("entro a taxi"+listTaxiSites);

        return listTaxiSites;
    }
}
