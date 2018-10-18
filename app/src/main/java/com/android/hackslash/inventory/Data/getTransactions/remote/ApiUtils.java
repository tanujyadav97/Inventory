package com.android.hackslash.inventory.Data.getTransactions.remote;

import com.android.hackslash.inventory.Data.RetrofitClient;

public class ApiUtils {

    private ApiUtils() {
    }

    public static final String BASE_URL = "https://nitd.000webhostapp.com/inventory_app/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);

    }
}