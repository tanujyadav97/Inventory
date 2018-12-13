package com.android.hackslash.inventory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.hackslash.inventory.Data.EditProduct.model.model.Post_editproduct;
import com.android.hackslash.inventory.Data.EditProduct.model.remote.APIService;
import com.android.hackslash.inventory.Data.EditProduct.model.remote.ApiUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditProduct extends AppCompatActivity {
    private EditText ename, etype, ecolor;
    private Button button, cancelButton;
    private String sname, stype, scolor;
    private APIService mAPIService;
    private String query;
    private String TAG = "EditProduct";
    private String oldname, oldcolor, oldtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        Intent intent = getIntent();
        oldname = intent.getStringExtra("name");
        oldcolor = intent.getStringExtra("color");
        oldtype = intent.getStringExtra("type");

        init();
        onclick();
    }

    private void init() {
        ename = findViewById(R.id.editnameEditText);
        etype = findViewById(R.id.edittypeEditText);
        ecolor = findViewById(R.id.editcolorEditText);
        button = findViewById(R.id.editsubmit_button);
        cancelButton = findViewById(R.id.editcancel_button);

        ename.setText(oldname);
        ecolor.setText(oldcolor);
        etype.setText(oldtype);

        mAPIService = ApiUtils.getAPIService();
    }

    private void onclick() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sname = ename.getText().toString();
                stype = etype.getText().toString();
                scolor = ecolor.getText().toString();
                query = oldname + "?" + oldcolor + "?" + oldtype + "?" + sname + "?" + scolor + "?" + stype;
                if (sname.equals("")) {
                    Toast.makeText(getApplicationContext(), "Name can not be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(EditProduct.this)
                            .setTitle("Confirmation")
                            .setMessage("Are you sure?")
                            .setIcon(R.drawable.ic_error_outline_black_24dp)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    sendpost(query);
                                }
                            })
                            .setNegativeButton("NO", null).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
                .subscribe(new Subscriber<Post_editproduct>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorReceived(e);
                    }

                    @Override
                    public void onNext(Post_editproduct posts) {
                        onDataReceived(posts);
                    }
                });
    }

    void onErrorReceived(Throwable e) {
        Log.e(TAG, "error received " + e);
        Toast.makeText(this, "Server error", Toast.LENGTH_SHORT).show();
    }

    void onDataReceived(Post_editproduct posts) {
        switch (posts.getResult()) {
            case "true":
                ename.setText("");
                ecolor.setText("");
                etype.setText("");
                Toast.makeText(this, "Product Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
            case "duplicate":
                Toast.makeText(this, "Product already exists", Toast.LENGTH_SHORT).show();
                break;
            case "false":
                Toast.makeText(this, "Unable to update product. Try again!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
