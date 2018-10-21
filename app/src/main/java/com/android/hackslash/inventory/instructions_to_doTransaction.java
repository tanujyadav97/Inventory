package com.android.hackslash.inventory;

/**
 * This is a temperory class just to give some instruction on how to call server script
 */
public class instructions_to_doTransaction {
    /**
     * Add following imports to your class
     *
     * import android.util.Log;
     * import android.widget.Toast;
     *
     * import com.android.hackslash.inventory.Data.doTransaction.model.Post_doTransaction;
     * import com.android.hackslash.inventory.Data.doTransaction.remote.APIService;
     * import com.android.hackslash.inventory.Data.doTransaction.remote.ApiUtils;
     *
     * import rx.Subscriber;
     * import rx.android.schedulers.AndroidSchedulers;
     * import rx.schedulers.Schedulers;
     */

    /**
     * add this variable to on create or any place you want
     *
     * private String TAG = "DoTransaction";
     * private APIService mAPIService;
     * mAPIService = ApiUtils.getAPIService();
     */

    /**
     * Add these three functions as it is in your main activity or any activity from where you want
     * to perform some sql query (network call)
     */

    /**
     * this function contains callbacks for the networking done through rxjava and retrofit
     * @param query it  is the query to be passed to the server. It is like:
     *              "1?name?color?type?quantity?timestamp" : to shop1
     *              "2?name?color?type?quantity?timestamp" : shop1 to shop2
     *              "3?name?color?type?quantity?timestamp" : shop2 to customer
     *              "4?name?color?type?quantity?timestamp" : shop2 to shop1
     *              "5?name?color?type?quantity?timestamp" : customer to shop2
     */

    /*
    private void sendpost(String query) {
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
        Toast.makeText(this, "Server error", Toast.LENGTH_SHORT).show();
    }

    void onDataReceived(Post_doTransaction posts) {
        switch (posts.getResult()) {
            case "true":
                Log.w(TAG, "Transaction Successful");
                Toast.makeText(this, "Transaction Successful", Toast.LENGTH_SHORT).show();
                break;
            case "false":
                Log.w(TAG, "Transaction failed. Try again!");
                Toast.makeText(this, "Transaction failed. Try again!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
     */
}
