package com.android.hackslash.inventory.Data.getInventoryDetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post_inventory {

    @SerializedName("result")
    @Expose
    private String result="";

    @SerializedName("name")
    @Expose
    private String name="";

    @SerializedName("color")
    @Expose
    private String color="";

    @SerializedName("type")
    @Expose
    private String type="";

    @SerializedName("shop1")
    @Expose
    private String shop1="";

    @SerializedName("shop2")
    @Expose
    private String shop2="";


    public String getResult() {
        return result;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public String getShop1() {
        return shop1;
    }

    public String getShop2() {
        return shop2;
    }

}