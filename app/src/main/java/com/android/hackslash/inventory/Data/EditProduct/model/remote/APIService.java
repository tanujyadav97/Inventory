package com.android.hackslash.inventory.Data.EditProduct.model.remote;

import com.android.hackslash.inventory.Data.EditProduct.model.model.Post_editproduct;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface APIService {
    @POST("edit_product.php")
    @FormUrlEncoded
    Observable<Post_editproduct> savePost(@Field("query") String query);
}
