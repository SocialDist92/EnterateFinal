package com.app.jonatan.enteratechihuahua.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jonatan on 30/05/2016.
 */
public class Ubication  implements Parcelable {
    private String latitud;
    private String longitude;

    public Ubication() {
    }

    public Ubication(String latitud, String longitude) {
        this.latitud = latitud;
        this.longitude = longitude;
    }

    protected Ubication(Parcel in) {
        latitud = in.readString();
        longitude = in.readString();
    }

    public static final Creator<Ubication> CREATOR = new Creator<Ubication>() {
        @Override
        public Ubication createFromParcel(Parcel in) {
            return new Ubication(in);
        }

        @Override
        public Ubication[] newArray(int size) {
            return new Ubication[size];
        }
    };

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(latitud);
        dest.writeString(longitude);
    }

    @Override
    public String toString() {
        return latitud+ " " +longitude;
    }
}
