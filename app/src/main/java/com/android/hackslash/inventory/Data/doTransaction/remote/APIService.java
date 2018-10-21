package com.android.hackslash.inventory.Data.doTransaction.remote;

import com.android.hackslash.inventory.Data.doTransaction.model.Post_doTransaction;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface APIService {

    @POST("transaction.php")
    @FormUrlEncoded
    Observable<Post_doTransaction> savePost(@Field("query") String query);
}
