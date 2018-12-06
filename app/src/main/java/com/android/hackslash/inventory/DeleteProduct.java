package com.android.hackslash.inventory;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.hackslash.inventory.Data.deleteProduct.model.Post_deleteproduct;
import com.android.hackslash.inventory.Data.deleteProduct.remote.APIService;
import com.android.hackslash.inventory.Data.deleteProduct.remote.ApiUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DeleteProduct {
    private String TAG = "DeleteProduct";
    private APIService mAPIService;
    private Context mContext;
    ProgressDialog progressDialog;

    public DeleteProduct(Context mContext) {
        mAPIService = ApiUtils.getAPIService();
        this.mContext = mContext;
    }

    /**
     * this function contains callbacks for the networking done through rxjava and retrofit
     *
     * @param query it  is the query to be passed to the server. It is like:
     *              "name?color?type"
     */
    public void sendpost(String query) {
        progressDialog = ProgressDialog.show(mContext, "Loading Data",
                "Please wait...", false, false);

        mAPIService.savePost(query).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Post_deleteproduct>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorReceived(e);
                    }

                    @Override
                    public void onNext(Post_deleteproduct posts) {
                        onDataReceived(posts);
                    }
                });
    }

    void onErrorReceived(Throwable e) {
        Log.e(TAG, "error received :" + e);
        Toast.makeText(mContext, "Server error", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    void onDataReceived(Post_deleteproduct posts) {
        progressDialog.dismiss();

        switch (posts.getResult()) {
            case "true":
                Log.w(TAG, "Product Deleted Successfully");
                Toast.makeText(mContext, "Product Deleted Successfully", Toast.LENGTH_SHORT).show();
                MainActivity mainActivity = (MainActivity) mContext;
                mainActivity.sendpost("3");
                break;
            case "false":
                Log.w(TAG, "Unable to delete product. Try again!");
                Toast.makeText(mContext, "Unable to delete product. Try again!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
