package com.android.hackslash.inventory;

/**
 * This is a temperory class just to give some instruction on how to call server script
 */
public class instructions_to_deleteProduct {
    /**
     * Add following imports to your class
     *
     * import android.util.Log;
     * import android.widget.Toast;
     *
     * import com.android.hackslash.inventory.Data.deleteProduct.model.Post;
     * import com.android.hackslash.inventory.Data.deleteProduct.remote.APIService;
     * import com.android.hackslash.inventory.Data.deleteProduct.remote.ApiUtils;
     *
     * import rx.Subscriber;
     * import rx.android.schedulers.AndroidSchedulers;
     * import rx.schedulers.Schedulers;
     */

    /**
     * add this variable to on create or any place you want
     *
     * private String TAG = "DeleteProduct";
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
    private void sendpost(String query) {
        mAPIService.savePost(query).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Post>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorReceived(e);
                    }

                    @Override
                    public void onNext(Post posts) {
                        onDataReceived(posts);
                    }
                });
    }

    void onErrorReceived(Throwable e) {
        Log.e(TAG, "error received " + e);
        Toast.makeText(this, "Server error", Toast.LENGTH_SHORT).show();
    }

    void onDataReceived(Post posts) {
        switch (posts.getResult()) {
            case "true":
                Log.w(TAG, "Product Deleted Successfully");
                Toast.makeText(this, "Product Deleted Successfully", Toast.LENGTH_SHORT).show();
                break;
            case "false":
                Log.w(TAG, "Unable to delete product. Try again!");
                Toast.makeText(this, "Unable to delete product. Try again!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
     */
}
