package com.android.hackslash.inventory;

/**
 * This is a temperory class just to give some instruction on how to call server script
 */
public class instructions_to_getTransactionData {

    /**
     * Add following imports to your class
     *
     * import com.android.hackslash.inventory.Data.ProcessData;
     * import com.android.hackslash.inventory.Data.getTransactions.model.Post_transactions;
     * import com.android.hackslash.inventory.Data.getTransactions.remote.APIService;
     * import com.android.hackslash.inventory.Data.getTransactions.remote.ApiUtils;
     *
     * import java.util.ArrayList;
     * import java.util.List;
     *
     * import rx.Subscriber;
     * import rx.android.schedulers.AndroidSchedulers;
     * import rx.schedulers.Schedulers;
     */

    /**
     * add this variable to on create or any place you want
     *
     * private String TAG = "getTransactions";
     * private APIService mAPIService;
     * mAPIService = ApiUtils.getAPIService();
     */


    /**
     * Add these three functions as it is in your main activity or any activity from where you want
     * to perform some sql query (network call)
     */


    /**
     * this function contains callbacks for the networking done through rxjava and retrofit
     * @param query it  is the query to be passed to the server. It is of following type:
     *              "" : to get all transactions
     *              "1?starttimestamp?endtimestamp" : to get transactions b/w these two time
     *              "2?name" : to get transactions of a particular name
     *              "3?name?color" : to get transactions of a name and color
     *              "4?name?color?type" : to get transactions of name, color and type
     *              "5?name?type" : to get transactions of a name and type
     */

    /*
    public void sendPost(String query) {
        mAPIService.savePost(query).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Post_transactions>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorReceived(e);
                    }

                    @Override
                    public void onNext(List<Post_transactions> posts) {
                        onDataReceived(posts);
                    }
                });
    }

    void onErrorReceived(Throwable e){
        Log.e(TAG, "error received :" + e);
        Toast.makeText(this, "Server error", Toast.LENGTH_SHORT).show();
    }

    void onDataReceived(List<Post_transactions> posts){
        Log.w(TAG, "Data retrieved Successfully");
        Toast.makeText(this, "Data retrieved Successfully", Toast.LENGTH_SHORT).show();

        ArrayList<ArrayList<String>> transactions = ProcessData.processTransactionData(posts);

        //TODO : Do whatever you want to do with the data
    }

    */

}
