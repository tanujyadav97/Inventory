package com.android.hackslash.inventory.Data.AddNewProduct.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("result")
    @Expose
    private String result;

    public String getResult() {
        return result;
    }

}
