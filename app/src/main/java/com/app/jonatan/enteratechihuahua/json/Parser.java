package com.app.jonatan.enteratechihuahua.json;

import com.app.jonatan.enteratechihuahua.extras.Constants;
import com.app.jonatan.enteratechihuahua.pojo.Place;
import com.app.jonatan.enteratechihuahua.pojo.Promotion;
import com.app.jonatan.enteratechihuahua.pojo.Ubication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.app.jonatan.enteratechihuahua.extras.Keys.EndpointPlacePromotion.KEY_CATEGORY;
import static com.app.jonatan.enteratechihuahua.extras.Keys.EndpointPlacePromotion.KEY_DESPCRIPTION;
import static com.app.jonatan.enteratechihuahua.extras.Keys.EndpointPlacePromotion.KEY_ID;
import static com.app.jonatan.enteratechihuahua.extras.Keys.EndpointPlacePromotion.KEY_ITEMS;
import static com.app.jonatan.enteratechihuahua.extras.Keys.EndpointPlacePromotion.KEY_LATITUDE;
import static com.app.jonatan.enteratechihuahua.extras.Keys.EndpointPlacePromotion.KEY_LONGITUDE;
import static com.app.jonatan.enteratechihuahua.extras.Keys.EndpointPlacePromotion.KEY_NAME;
import static com.app.jonatan.enteratechihuahua.extras.Keys.EndpointPlacePromotion.KEY_PLACE;
import static com.app.jonatan.enteratechihuahua.extras.Keys.EndpointPlacePromotion.KEY_UBICATIONS;
import static com.app.jonatan.enteratechihuahua.extras.Keys.EndpointPlacePromotion.KEY_URL_IMAGE_LOGO;

/**
 * Created by Jonatan on 11/05/2016.
 */
public class Parser {
    public static ArrayList<Promotion> parsePromotionsJson(JSONObject response) {
        ArrayList<Promotion> listPromotions = new ArrayList<>();

        if (response != null && response.length() > 0) {
            try {
                JSONArray arrayPromotions = response.getJSONArray(KEY_ITEMS);
                for (int i = 0; i < arrayPromotions.length(); i++) {
                    String name = Constants.NA;
                    String description = Constants.NA;
                    String id = Constants.NA;
                    String placeName = Constants.NA;
                    String placeCategory = Constants.NA;
                    String placeUrlImageLogo = Constants.NA;
                    String placeId = Constants.NA;
                    ArrayList<Ubication> ubications = new ArrayList<>();

                    JSONObject currentPromotion = arrayPromotions.getJSONObject(i);

                    if (Utils.contains(currentPromotion, KEY_ID)) {
                        id = currentPromotion.getString(KEY_ID);
                    }

                    if (Utils.contains(currentPromotion, KEY_NAME)) {
                        name = currentPromotion.getString(KEY_NAME);
                    }

                    if (Utils.contains(currentPromotion, KEY_DESPCRIPTION)) {
                        description = currentPromotion.getString(KEY_DESPCRIPTION);
                    }

                    if (Utils.contains(currentPromotion, KEY_PLACE)) {
                        JSONObject objectPlace = currentPromotion.getJSONObject(KEY_PLACE);
                        System.out.println(objectPlace);
                        if (Utils.contains(objectPlace, KEY_NAME)) {
                            placeName = objectPlace.getString(KEY_NAME);
                        }

                        if (Utils.contains(objectPlace, KEY_CATEGORY)) {
                            placeCategory = objectPlace.getString(KEY_CATEGORY);
                        }

                        if (Utils.contains(objectPlace, KEY_URL_IMAGE_LOGO)) {
                            placeUrlImageLogo = objectPlace.getString(KEY_URL_IMAGE_LOGO);
                            System.out.println(placeUrlImageLogo);
                        }

                        if (Utils.contains(objectPlace, KEY_ID)) {
                            placeId = objectPlace.getString(KEY_ID);
                        }

                        if (Utils.contains(objectPlace, KEY_UBICATIONS)) {
                            JSONArray arrayUbications = objectPlace.getJSONArray(KEY_UBICATIONS);

                            for (int j = 0; j < arrayUbications.length(); j++) {
                                String latitude = Constants.NA;
                                String longitude = Constants.NA;

                                JSONObject currentUbication = arrayUbications.getJSONObject(j);

                                if (Utils.contains(currentUbication, KEY_LATITUDE)){
                                    latitude = currentUbication.getString(KEY_LATITUDE);
                                }

                                if (Utils.contains(currentUbication, KEY_LONGITUDE)){
                                    longitude = currentUbication.getString(KEY_LONGITUDE);
                                }

                                Ubication ubication = new Ubication();
                                ubication.setLatitud(latitude);
                                ubication.setLongitude(longitude);
                                ubications.add(ubication);
                            }

                        }
                    }

                    Place place = new Place();
                    Promotion promotion = new Promotion();

                    place.setCategory(placeCategory);
                    place.setUrlImageLogo(placeUrlImageLogo);
                    place.setName(placeName);
                    place.setIdAsStr(placeId);
                    place.setUbications(ubications);
                    System.out.println("ubications ---->" + ubications);

                    promotion.setName(name);
                    promotion.setDescription(description);
                    promotion.setIdAsStr(id);
                    promotion.setPlace(place);

                    listPromotions.add(promotion);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return listPromotions;
    }
}
