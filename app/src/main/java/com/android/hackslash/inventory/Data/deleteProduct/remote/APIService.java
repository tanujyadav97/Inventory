package com.android.hackslash.inventory.Data.deleteProduct.remote;

import com.android.hackslash.inventory.Data.deleteProduct.model.Post_deleteproduct;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface APIService {

    @POST("delete_product.php")
    @FormUrlEncoded
    Observable<Post_deleteproduct> savePost(@Field("query") String query);
}
