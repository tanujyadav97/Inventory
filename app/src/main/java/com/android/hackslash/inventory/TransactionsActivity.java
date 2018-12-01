package com.android.hackslash.inventory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hackslash.inventory.Adapter.trans_adapter;
import com.android.hackslash.inventory.Data.ProcessData;
import com.android.hackslash.inventory.Data.getTransactions.model.Post_transactions;
import com.android.hackslash.inventory.Data.getTransactions.remote.APIService;
import com.android.hackslash.inventory.Data.getTransactions.remote.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TransactionsActivity extends AppCompatActivity {

    String name, color, size, q1, q2;
    TextView tvname, tvcolor, tvsize, tvq1, tvq2;
    private String TAG = "getTransactions";
    private APIService mAPIService;
    RecyclerView mrecycler;
    private RecyclerView.Adapter adapter;
    ProgressDialog progressDialog;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);


        progressDialog = ProgressDialog.show(this, "Loading Data",
                "Please wait...", false, false);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        color = intent.getStringExtra("color");
        size = intent.getStringExtra("size");
        q1 = intent.getStringExtra("q1");
        q2 = intent.getStringExtra("q2");

        tvname = findViewById(R.id.prod_name_trans);
        tvcolor = findViewById(R.id.prod_color_trans);
        tvsize = findViewById(R.id.prod_size_trans);
        tvq1 = findViewById(R.id.quantity1_trans);
        tvq2 = findViewById(R.id.quantity2_trans);

        tvname.setText(name);
        tvcolor.setText(color);
        tvsize.setText(size);
        tvq1.setText(q1);
        tvq2.setText(q2);

        mAPIService = ApiUtils.getAPIService();

        mrecycler = findViewById(R.id.trans_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mrecycler.setLayoutManager(mLinearLayoutManager);
        String query = "";
        if (!color.equals("") && !size.equals(""))
            query = "4?" + name + "?" + color + "?" + size;
        else if (!color.equals(""))
            query = "3?" + name + "?" + color;
        else if (!size.equals(""))
            query = "5?" + name + "?" + size;
        else
            query = "2?" + name;
        sendPost(query);
    }

    /**
     * this function contains callbacks for the networking done through rxjava and retrofit
     *
     * @param query it  is the query to be passed to the server. It is of following type:
     *              "" : to get all transactions
     *              "1?starttimestamp?endtimestamp" : to get transactions b/w these two time
     *              "2?name" : to get transactions of a particular name
     *              "3?name?color" : to get transactions of a name and color
     *              "4?name?color?type" : to get transactions of name, color and type
     *              "5?name?type" : to get transactions of a name and type
     */

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

    void onErrorReceived(Throwable e) {
        Log.e(TAG, "error received :" + e);
        Toast.makeText(this, "Server error", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    void onDataReceived(List<Post_transactions> posts) {
        Log.w(TAG, "Data retrieved Successfully");
        Toast.makeText(this, "Data retrieved Successfully", Toast.LENGTH_SHORT).show();

        ArrayList<ArrayList<String>> transactions = ProcessData.processTransactionData(posts);
        updateUi(transactions);
    }

    private void updateUi(ArrayList<ArrayList<String>> data) {
        adapter = new trans_adapter(getApplicationContext(), data);
        mrecycler.setAdapter(adapter);
        progressDialog.dismiss();
    }
}
