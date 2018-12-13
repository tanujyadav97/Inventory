package com.android.hackslash.inventory.Data.EditProduct.model.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post_editproduct {

    @SerializedName("result")
    @Expose
    private String result;

    public String getResult() {
        return result;
    }

}
