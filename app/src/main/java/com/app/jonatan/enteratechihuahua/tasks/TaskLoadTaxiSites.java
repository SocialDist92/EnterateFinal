package com.app.jonatan.enteratechihuahua.tasks;

import android.os.AsyncTask;


import com.android.volley.RequestQueue;
import com.app.jonatan.enteratechihuahua.callbacks.TaxiSitesLoadedListener;
import com.app.jonatan.enteratechihuahua.extras.PromotionUtils;
import com.app.jonatan.enteratechihuahua.network.VolleySingleton;
import com.app.jonatan.enteratechihuahua.pojo.TaxiSite;

import java.util.ArrayList;

/**
 * Created by Jonatan on 16/07/2016.
 */

public class TaskLoadTaxiSites extends AsyncTask <Void, Void, ArrayList<TaxiSite>> {
    private TaxiSitesLoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public TaskLoadTaxiSites(TaxiSitesLoadedListener myComponent) {
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected ArrayList<TaxiSite> doInBackground(Void... params) {
        ArrayList<TaxiSite> taxiSites = PromotionUtils.loadTaxiSites(requestQueue);
        return taxiSites;
    }

    @Override
    protected void onPostExecute(ArrayList<TaxiSite> taxiSites) {
        if (myComponent != null) {
            myComponent.onTaxiSitesLoaded(taxiSites);
        }
    }
}
