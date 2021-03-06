package com.android.hackslash.inventory.Data.AddNewProduct.remote;

import com.android.hackslash.inventory.Data.AddNewProduct.model.Post_addproduct;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface APIService {

    @POST("add_product.php")
    @FormUrlEncoded
    Observable<Post_addproduct> savePost(@Field("query") String query);
}
