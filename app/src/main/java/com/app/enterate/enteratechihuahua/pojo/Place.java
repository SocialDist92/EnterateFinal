package com.app.enterate.enteratechihuahua.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Jonatan on 11/05/2016.
 */
public class Place implements Parcelable {
    private String category;
    private String subCategory;
    private String idAsStr;
    private String name;
    private String urlFacebook;
    private String urlPinterest;
    private String urlInstagram;
    private String urlImageLogo;
    private String urlLocation;
    private String urlTwitter;
    private ArrayList<Ubication> ubications;


    public Place() {
    }

    public Place(String category, String subCategory, String idAsStr, String name, String urlFacebook,
                 String urlImageLogo, String urlLocation, String urlTwitter) {
        this.category = category;
        this.subCategory = subCategory;
        this.idAsStr = idAsStr;
        this.name = name;
        this.urlFacebook = urlFacebook;
        this.urlImageLogo = urlImageLogo;
        this.urlLocation = urlLocation;
        this.urlTwitter = urlTwitter;
        this.ubications = new ArrayList<>();
    }


    protected Place(Parcel in) {
        category = in.readString();
        subCategory = in.readString();
        idAsStr = in.readString();
        name = in.readString();
        urlFacebook = in.readString();
        urlPinterest = in.readString();
        urlInstagram = in.readString();
        urlImageLogo = in.readString();
        urlLocation = in.readString();
        urlTwitter = in.readString();
        ubications = in.createTypedArrayList(Ubication.CREATOR);
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getIdAsStr() {
        return idAsStr;
    }

    public void setIdAsStr(String idAsStr) {
        this.idAsStr = idAsStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlFacebook() {
        return urlFacebook;
    }

    public void setUrlFacebook(String urlFacebook) {
        this.urlFacebook = urlFacebook;
    }

    public String getUrlImageLogo() {
        return urlImageLogo;
    }

    public void setUrlImageLogo(String urlImageLogo) {
        this.urlImageLogo = urlImageLogo;
    }

    public String getUrlLocation() {
        return urlLocation;
    }

    public void setUrlLocation(String urlLocation) {
        this.urlLocation = urlLocation;
    }

    public String getUrlTwitter() {
        return urlTwitter;
    }

    public void setUrlTwitter(String urlTwitter) {
        this.urlTwitter = urlTwitter;
    }

    public ArrayList<Ubication> getUbications() {
        return ubications;
    }

    public void setUbications(ArrayList<Ubication> ubications) {
        this.ubications = ubications;
    }

    public String getUrlPinterest() {
        return urlPinterest;
    }

    public void setUrlPinterest(String urlPinterest) {
        this.urlPinterest = urlPinterest;
    }

    public String getUrlInstagram() {
        return urlInstagram;
    }

    public void setUrlInstagram(String urlInstagram) {
        this.urlInstagram = urlInstagram;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(subCategory);
        dest.writeString(idAsStr);
        dest.writeString(name);
        dest.writeString(urlFacebook);
        dest.writeString(urlPinterest);
        dest.writeString(urlInstagram);
        dest.writeString(urlImageLogo);
        dest.writeString(urlLocation);
        dest.writeString(urlTwitter);
        dest.writeTypedList(ubications);
    }
}
