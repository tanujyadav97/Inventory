package com.android.hackslash.inventory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.hackslash.inventory.Data.AddNewProduct.remote.APIService;
import com.android.hackslash.inventory.Data.AddNewProduct.remote.ApiUtils;
import com.android.hackslash.inventory.Data.AddNewProduct.model.Post_addproduct;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class addNewProduct extends AppCompatActivity {
    private EditText ename, etype, ecolor;
    private Button button;
    private String sname, stype, scolor;
    private APIService mAPIService;
    private String query;
    private String TAG = "addNewProduct";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newproduct);
        init();
        onclick();
    }

    private void init() {
        ename = (EditText) findViewById(R.id.nameEditText);
        etype = (EditText) findViewById(R.id.typeEditText);
        ecolor = (EditText) findViewById(R.id.colorEditText);
        button = (Button) findViewById(R.id.submit_button);
        mAPIService = ApiUtils.getAPIService();
    }

    private void onclick() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sname = ename.getText().toString();
                stype = etype.getText().toString();
                scolor = ecolor.getText().toString();
                query = sname + "?" + stype + "?" + scolor;
                if (sname.equals("")) {
                    Toast.makeText(getApplicationContext(), "Name can not be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    sendpost(query);
                }
            }
        });
    }

    /**
     * this function contains callbacks for the networking done through rxjava and retrofit
     *
     * @param query it  is the query to be passed to the server. It is like:
     *              "name?color?type"
     */
    private void sendpost(String query) {
        mAPIService.savePost(query).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Post_addproduct>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorReceived(e);
                    }

                    @Override
                    public void onNext(Post_addproduct posts) {
                        onDataReceived(posts);
                    }
                });
    }

    void onErrorReceived(Throwable e) {
        Log.e(TAG, "error received " + e);
        Toast.makeText(this, "Server error", Toast.LENGTH_SHORT).show();
    }

    void onDataReceived(Post_addproduct posts) {
        switch (posts.getResult()) {
            case "true":
                ename.setText("");
                ecolor.setText("");
                etype.setText("");
                Toast.makeText(this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                break;
            case "duplicate":
                Toast.makeText(this, "Product already exists", Toast.LENGTH_SHORT).show();
                break;
            case "false":
                Toast.makeText(this, "Unable to add product. Try again!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
