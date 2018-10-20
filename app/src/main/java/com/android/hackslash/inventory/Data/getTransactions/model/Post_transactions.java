package com.android.hackslash.inventory.Data.getTransactions.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post_transactions {

    @SerializedName("timestamp")
    @Expose
    private String timestamp="";

    @SerializedName("ttype")
    @Expose
    private String ttype="";

    @SerializedName("name")
    @Expose
    private String name="";

    @SerializedName("color")
    @Expose
    private String color="";

    @SerializedName("type")
    @Expose
    private String type="";

    @SerializedName("quantity")
    @Expose
    private String quantity="";

    public String getTimestamp() {
        return timestamp;
    }

    public String getTtype() {
        return ttype;
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

    public String getQuantity() {
        return quantity;
    }

}