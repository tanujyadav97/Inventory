package com.android.hackslash.inventory.Data.getTransactions.remote;

import com.android.hackslash.inventory.Data.getTransactions.model.Post_transactions;

import java.util.List;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface APIService {

    @POST("get_transactions.php")
    @FormUrlEncoded
    Observable<List<Post_transactions>> savePost(@Field("query") String query);
}
