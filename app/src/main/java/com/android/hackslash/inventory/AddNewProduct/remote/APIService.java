package com.android.hackslash.inventory.AddNewProduct.remote;

import com.android.hackslash.inventory.Data.getTransactions.model.Post;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface APIService {

    @POST("add_product.php")
    @FormUrlEncoded
    Observable<List<Post>> savePost(@Field("query") String query);
}
