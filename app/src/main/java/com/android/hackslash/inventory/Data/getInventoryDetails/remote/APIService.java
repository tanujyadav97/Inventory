package com.android.hackslash.inventory.Data.getInventoryDetails.remote;

import com.android.hackslash.inventory.Data.getInventoryDetails.model.Post_inventory;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface APIService {

    @POST("get_inventory_details.php")
    @FormUrlEncoded
    Observable<List<Post_inventory>> savePost(@Field("query") String query);
}
