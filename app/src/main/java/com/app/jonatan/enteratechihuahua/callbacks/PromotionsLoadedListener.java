package com.app.jonatan.enteratechihuahua.callbacks;

import com.app.jonatan.enteratechihuahua.pojo.Promotion;

import java.util.ArrayList;

/**
 * Created by Jonatan on 11/05/2016.
 */
public interface PromotionsLoadedListener {
    public void onPromotionsLoaded(ArrayList<Promotion> listPromotions);
}
