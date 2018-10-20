package com.android.hackslash.inventory;

/**
 * This is a temperory class just to give some instruction on how to call server script
 */
public class instructions_to_getInventoryDetails {

    /**
     * Add following imports to your class
     *
     * import com.android.hackslash.inventory.Data.ProcessData;
     * import com.android.hackslash.inventory.Data.getInventoryDetails.model.Post_inventory;
     * import com.android.hackslash.inventory.Data.getInventoryDetails.remote.APIService;
     * import com.android.hackslash.inventory.Data.getInventoryDetails.remote.ApiUtils;
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
     * private String TAG = "GetInventoryDetails";
     * private APIService mAPIService;
     * mAPIService = ApiUtils.getAPIService();
     */


    /**
     * Add these three functions as it is in your main activity or any activity from where you want
     * to perform some sql query (network call)
     */


    /**
     * this function contains callbacks for the networking done through rxjava and retrofit
     * @param query it  is the query to be passed to the server. It is either
     *              "1" to get shop1 data
     *              "2" to get shop2 data
     *              "3" to get both shops data
     */

    /*
    private void sendpost(final String query) {
        mAPIService.savePost(query).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Post_inventory>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorReceived(e);
                    }

                    @Override
                    public void onNext(List<Post_inventory> posts) {
                        onDataReceived(posts, query);
                    }
                });
    }

    void onErrorReceived(Throwable e) {
        Log.e(TAG, "error received :" + e);
        Toast.makeText(this, "Server error", Toast.LENGTH_SHORT).show();
    }

    void onDataReceived(List<Post_inventory> posts, String query) {
        if (posts.get(0).getResult().equals("false")) {
            Log.w(TAG, "Unable to retrieve data. Try again!");
            Toast.makeText(this, "Unable to retrieve data. Try again!", Toast.LENGTH_SHORT).show();
        }else{
            Log.w(TAG, "Data retrieved Successfully");
            Toast.makeText(this, "Data retrieved Successfully", Toast.LENGTH_SHORT).show();

            ArrayList<ArrayList<String>> transactions = ProcessData.processInventoryData(posts, query);
            Log.w(TAG, transactions.get(0).toString());

            //TODO: Do whatever you want to do with data
        }
    }
     */
}
