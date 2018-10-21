package com.android.hackslash.inventory.Data.doTransaction.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post_doTransaction {

    @SerializedName("result")
    @Expose
    private String result;

    public String getResult() {
        return result;
    }

}