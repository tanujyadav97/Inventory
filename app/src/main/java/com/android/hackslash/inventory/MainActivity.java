package com.android.hackslash.inventory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.hackslash.inventory.Adapter.item_adapter;
import com.android.hackslash.inventory.Data.ProcessData;
import com.android.hackslash.inventory.Data.getInventoryDetails.model.Post_inventory;
import com.android.hackslash.inventory.Data.getInventoryDetails.remote.APIService;
import com.android.hackslash.inventory.Data.getInventoryDetails.remote.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private String TAG = "GetInventoryDetails";
    private APIService mAPIService;
    RecyclerView mrecycler;
    private item_adapter adapter;
    ProgressDialog progressDialog;
    private LinearLayoutManager mLinearLayoutManager;
    FloatingActionButton add;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAPIService = ApiUtils.getAPIService();

        add = findViewById(R.id.add_item);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), addNewProduct.class);
                startActivityForResult(intent, 1);
            }
        });

        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendpost("3");
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mrecycler = findViewById(R.id.items_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mrecycler.setLayoutManager(mLinearLayoutManager);
        sendpost("3");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            sendpost("3");
        }
    }

    /**
     * this function contains callbacks for the networking done through rxjava and retrofit
     *
     * @param query it  is the query to be passed to the server. It is either
     *              "1" to get shop1 data
     *              "2" to get shop2 data
     *              "3" to get both shops data
     */

    void sendpost(final String query) {
        progressDialog = ProgressDialog.show(this, "Loading Data",
                "Please wait...", false, false);

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
        progressDialog.dismiss();
    }

    void onDataReceived(List<Post_inventory> posts, String query) {
        if (posts.get(0).getResult().equals("false")) {
            Log.w(TAG, "Unable to retrieve data. Try again!");
            Toast.makeText(this, "Unable to retrieve data. Try again!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else {
            Log.w(TAG, "Data retrieved Successfully");
            Toast.makeText(this, "Data retrieved Successfully", Toast.LENGTH_SHORT).show();

            ArrayList<ArrayList<String>> inventories = ProcessData.processInventoryData(posts, query);
            Log.w(TAG, inventories.size() + "");

            updateUi(inventories);
        }
    }

    private void updateUi(ArrayList<ArrayList<String>> data) {
        adapter = new item_adapter(getApplicationContext(), this, data);
        mrecycler.setAdapter(adapter);
        progressDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trans_menu, menu);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint("Search by name...");
        search(searchView);

        return true;
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
