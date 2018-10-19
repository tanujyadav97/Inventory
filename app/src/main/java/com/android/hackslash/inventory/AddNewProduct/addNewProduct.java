package com.android.hackslash.inventory.AddNewProduct;

import android.os.Bundle;
import android.renderscript.Short4;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.hackslash.inventory.AddNewProduct.remote.APIService;
import com.android.hackslash.inventory.AddNewProduct.remote.ApiUtils;
import com.android.hackslash.inventory.Data.getTransactions.model.Post;
import com.android.hackslash.inventory.R;
import java.util.List;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class addNewProduct extends AppCompatActivity {
    private EditText ename, etype, ecolor;
    private Button button;
    private String sname, stype, scolor;
    private APIService mAPIService;
    private String query;

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
                sendpost(query);
            }
        });
    }

    private void sendpost(String query) {
        mAPIService.savePost(query).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Post>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorReceived();
                    }

                    @Override
                    public void onNext(List<Post> posts) {
                        onDataReceived(posts);
                    }
                });
    }

    void onErrorReceived() {
        Log.w("completeeeeeee","eeee");
    }

    void onDataReceived(List<Post> posts) {
//        ArrayList<ArrayList<String>> result = ProcessData.processTransactionData(posts);
        ename.setText("");ecolor.setText("");etype.setText("");
        Toast.makeText(this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
//        Log.w("completeeeeeee","nnnnnnnn");

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
