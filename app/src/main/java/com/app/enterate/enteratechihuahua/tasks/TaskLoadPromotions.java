package com.app.enterate.enteratechihuahua.tasks;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.app.enterate.enteratechihuahua.callbacks.PromotionsLoadedListener;
import com.app.enterate.enteratechihuahua.extras.PromotionUtils;
import com.app.enterate.enteratechihuahua.network.VolleySingleton;
import com.app.enterate.enteratechihuahua.pojo.Promotion;

import java.util.ArrayList;

/**
 * Created by Jonatan on 11/05/2016.
 */
public class TaskLoadPromotions extends AsyncTask<Void, Void, ArrayList<Promotion>> {
    private PromotionsLoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public TaskLoadPromotions(PromotionsLoadedListener myComponent) {
        this.myComponent = myComponent;
        volleySingleton = volleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected ArrayList<Promotion> doInBackground(Void... params) {
        ArrayList<Promotion> listPromotions = PromotionUtils.loadPromotions(requestQueue);
        return listPromotions;
    }

    @Override
    protected void onPostExecute(ArrayList<Promotion> promotions) {
        if (myComponent != null) {
            myComponent.onPromotionsLoaded(promotions);
        }
    }
}
