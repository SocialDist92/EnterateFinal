package com.app.enterate.enteratechihuahua.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jonatan on 11/05/2016.
 */
public class Promotion implements Parcelable {
    private String description;
    private String idAsStr;
    private String name;
    private Place place;

    public Promotion() {
    }

    public Promotion(String description, String idAsStr, String name, Place place) {
        this.description = description;
        this.idAsStr = idAsStr;
        this.name = name;
        this.place = place;
    }


    protected Promotion(Parcel in) {
        description = in.readString();
        idAsStr = in.readString();
        name = in.readString();
        place = in.readParcelable(Place.class.getClassLoader());
    }

    public static final Creator<Promotion> CREATOR = new Creator<Promotion>() {
        @Override
        public Promotion createFromParcel(Parcel in) {
            return new Promotion(in);
        }

        @Override
        public Promotion[] newArray(int size) {
            return new Promotion[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }


    @Override
    public String toString() {
        return this.getName()+this.getPlace().getUrlImageLogo();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(idAsStr);
        dest.writeString(name);
        dest.writeParcelable(place, flags);
    }
}
