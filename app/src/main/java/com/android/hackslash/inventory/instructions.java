package com.android.hackslash.inventory;

/**
 * This is a temperory class just to give some instruction on how to call server script
 */
public class instructions {

    /**
     * Add following imports to your class
     *
     * import com.android.hackslash.inventory.Data.ProcessData;
     * import com.android.hackslash.inventory.Data.getTransactions.model.Post;
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
     * private APIService mAPIService;
     * mAPIService = ApiUtils.getAPIService();
     */


    /**
     * Add these three functions as it is in your main activity or any activity from where you want
     * to perform some sql query (network call)
     */


    /**
     * this function contains callbacks for the networking done through rxjava and retrofit
     * @param query it  is the query to be passed to the server
     */

    /*
    public void sendPost(String query) {
        mAPIService.savePost(query).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Post>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorReceived(e);
                    }

                    @Override
                    public void onNext(List<Post> posts) {
                        onDataReceived(posts);
                    }
                });
    }

    void onErrorReceived(Throwable e){
        Log.e("tag", "error received :" + e);
    }

    void onDataReceived(List<Post> posts){
        ArrayList<ArrayList<String>> transactions = ProcessData.processTransactionData(posts);

        //TODO : Do whatever you want to do with the data
    }

    */

}
