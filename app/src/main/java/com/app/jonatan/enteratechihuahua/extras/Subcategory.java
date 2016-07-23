package com.app.jonatan.enteratechihuahua.extras;

/**
 * Created by armando on 6/26/16.
 */
public class Subcategory {

    private String subName;
    private int icon;

    public Subcategory(int icon, String subName) {
        this.setIcon(icon);
        this.setSubName(subName);
    }


    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
