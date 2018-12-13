package com.android.hackslash.inventory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hackslash.inventory.Adapter.trans_adapter;
import com.android.hackslash.inventory.Data.ProcessData;
import com.android.hackslash.inventory.Data.getTransactions.model.Post_transactions;
import com.android.hackslash.inventory.Data.getTransactions.remote.APIService;
import com.android.hackslash.inventory.Data.getTransactions.remote.ApiUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TransactionsActivity extends AppCompatActivity {

    String name, color, size, q1, q2;
    TextView tvname, tvcolor, tvsize, tvq1, tvq2;
    Spinner transtype;
    NumberPicker np;
    Button done, cancel;
    LinearLayout lltrans;
    FloatingActionButton dotrans;
    private String TAG = "getTransactions";
    private APIService mAPIService;
    RecyclerView mrecycler;
    private trans_adapter adapter;
    ProgressDialog progressDialog;
    private LinearLayoutManager mLinearLayoutManager;
    String query;
    int selectedQuantity, quan1, quan2, trans_type;
    Boolean successful_trans = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

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
        transtype = findViewById(R.id.trans_type_select);
        cancel = findViewById(R.id.cancel);
        done = findViewById(R.id.done);
        dotrans = findViewById(R.id.transaction);
        lltrans = findViewById(R.id.transview);
        np = findViewById(R.id.trans_quantity);

        tvname.setText(name);
        tvcolor.setText(color);
        tvsize.setText(size);
        tvq1.setText(q1);
        tvq2.setText(q2);

        np.setMaxValue(1000);

        query = "";
        if (!color.equals("") && !size.equals(""))
            query = "4?" + name + "?" + color + "?" + size;
        else if (!color.equals(""))
            query = "3?" + name + "?" + color;
        else if (!size.equals(""))
            query = "5?" + name + "?" + size;
        else
            query = "2?" + name;

        dotrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lltrans.getVisibility() == View.GONE) {
                    lltrans.setVisibility(View.VISIBLE);
                    dotrans.setVisibility(View.GONE);
                    mrecycler.setVisibility(View.GONE);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lltrans.setVisibility(View.GONE);
                dotrans.setVisibility(View.VISIBLE);
                mrecycler.setVisibility(View.VISIBLE);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedQuantity = np.getValue();
                quan1 = Integer.parseInt(q1);
                quan2 = Integer.parseInt(q2);
                trans_type = transtype.getSelectedItemPosition();

                if (selectedQuantity == 0) {
                    Toast.makeText(getApplicationContext(), "Please select non zero value", Toast.LENGTH_SHORT).show();
                } else if (trans_type == 1 && selectedQuantity > quan1) {
                    Toast.makeText(getApplicationContext(), "Insufficient Quantity in Godown", Toast.LENGTH_SHORT).show();
                } else if (trans_type == 2 && selectedQuantity > quan2) {
                    Toast.makeText(getApplicationContext(), "Insufficient Quantity in Shop", Toast.LENGTH_SHORT).show();
                } else if (trans_type == 3 && selectedQuantity > quan2) {
                    Toast.makeText(getApplicationContext(), "Insufficient Quantity in Shop", Toast.LENGTH_SHORT).show();
                } else {
                    int typetrans = trans_type + 1;
                    String timstamp = ((new Timestamp(System.currentTimeMillis())).getTime() / 1000) + "";

                    final String query1 = typetrans + "?" + name + "?" + color + "?" + size + "?" + selectedQuantity + "?" + timstamp;

                    new AlertDialog.Builder(TransactionsActivity.this)
                            .setTitle("Confirmation")
                            .setMessage("Are you sure?")
                            .setIcon(R.drawable.ic_error_outline_black_24dp)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    DoTransaction doTransaction = new DoTransaction(TransactionsActivity.this);
                                    doTransaction.sendpost(query1);
                                }
                            })
                            .setNegativeButton("NO", null).show();
                }
            }
        });

        mAPIService = ApiUtils.getAPIService();

        mrecycler = findViewById(R.id.trans_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mrecycler.setLayoutManager(mLinearLayoutManager);

        sendPost(query);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        if (successful_trans)
            setResult(Activity.RESULT_OK, intent);
        else
            setResult(Activity.RESULT_CANCELED, intent);
        finish();
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

    private void sendPost(String query) {
        progressDialog = ProgressDialog.show(this, "Loading Data",
                "Please wait...", false, false);

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

    public void refresh() {
        successful_trans = true;
        lltrans.setVisibility(View.GONE);
        dotrans.setVisibility(View.VISIBLE);
        mrecycler.setVisibility(View.VISIBLE);

        if (trans_type == 0) {
            q1 = (quan1 + selectedQuantity) + "";
            tvq1.setText(q1);
        } else if (trans_type == 1) {
            q1 = (quan1 - selectedQuantity) + "";
            q2 = (quan2 + selectedQuantity) + "";
            tvq1.setText(q1);
            tvq2.setText(q2);
        } else if (trans_type == 2) {
            q2 = (quan2 - selectedQuantity) + "";
            tvq2.setText(q2);
        } else if (trans_type == 3) {
            q1 = (quan1 + selectedQuantity) + "";
            q2 = (quan2 - selectedQuantity) + "";
            tvq1.setText(q1);
            tvq2.setText(q2);
        } else if (trans_type == 4) {
            q2 = (quan2 + selectedQuantity) + "";
            tvq2.setText(q2);
        }

        sendPost(query);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trans_menu, menu);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint("Search by date...");
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
