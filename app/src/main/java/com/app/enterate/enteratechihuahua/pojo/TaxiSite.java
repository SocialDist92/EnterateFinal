package com.app.enterate.enteratechihuahua.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jonatan on 16/07/2016.
 */
public class TaxiSite implements Parcelable {
    private String name;
    private String latitude;
    private String longitude;
    private String phone;

    public TaxiSite() {
    }

    public TaxiSite(String name, String latitude, String longitude, String phone) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
    }

    protected TaxiSite(Parcel in) {
        name = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        phone = in.readString();
    }

    public static final Creator<TaxiSite> CREATOR = new Creator<TaxiSite>() {
        @Override
        public TaxiSite createFromParcel(Parcel in) {
            return new TaxiSite(in);
        }

        @Override
        public TaxiSite[] newArray(int size) {
            return new TaxiSite[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(phone);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static Creator<TaxiSite> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
