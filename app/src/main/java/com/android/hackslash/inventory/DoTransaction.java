package com.android.hackslash.inventory;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.hackslash.inventory.Data.doTransaction.model.Post_doTransaction;
import com.android.hackslash.inventory.Data.doTransaction.remote.APIService;
import com.android.hackslash.inventory.Data.doTransaction.remote.ApiUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DoTransaction {
    private String TAG = "DoTransaction";
    private APIService mAPIService;
    Context mContext;
    ProgressDialog progressDialog;

    public DoTransaction(Context context) {
        mContext = context;
        mAPIService = ApiUtils.getAPIService();
    }

    /**
     * this function contains callbacks for the networking done through rxjava and retrofit
     *
     * @param query it  is the query to be passed to the server. It is like:
     *              "1?name?color?type?quantity?timestamp" : to shop1
     *              "2?name?color?type?quantity?timestamp" : shop1 to shop2
     *              "3?name?color?type?quantity?timestamp" : shop2 to customer
     *              "4?name?color?type?quantity?timestamp" : shop2 to shop1
     *              "5?name?color?type?quantity?timestamp" : customer to shop2
     */
    public void sendpost(String query) {
        progressDialog = ProgressDialog.show(mContext, "Loading Data",
                "Please wait...", false, false);

        mAPIService.savePost(query).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Post_doTransaction>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorReceived(e);
                    }

                    @Override
                    public void onNext(Post_doTransaction posts) {
                        onDataReceived(posts);
                    }
                });
    }

    void onErrorReceived(Throwable e) {
        Log.e(TAG, "error received :" + e);
        Toast.makeText(mContext, "Server error", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    void onDataReceived(Post_doTransaction posts) {
        progressDialog.dismiss();

        switch (posts.getResult()) {
            case "true":
                Log.w(TAG, "Transaction Successful");
                Toast.makeText(mContext, "Transaction Successful", Toast.LENGTH_SHORT).show();
                TransactionsActivity transactionsActivity = (TransactionsActivity) mContext;
                transactionsActivity.refresh();
                break;
            case "false":
                Log.w(TAG, "Transaction failed. Try again!");
                Toast.makeText(mContext, "Transaction failed. Try again!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
