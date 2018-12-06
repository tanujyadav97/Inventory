package com.android.hackslash.inventory.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hackslash.inventory.DeleteProduct;
import com.android.hackslash.inventory.MainActivity;
import com.android.hackslash.inventory.R;
import com.android.hackslash.inventory.TransactionsActivity;

import java.util.ArrayList;

public class item_adapter extends RecyclerView.Adapter<item_adapter.itemRowHolder> {
    private Context mContext, mContext2;

    private ArrayList<ArrayList<String>> data;

    public item_adapter(Context context, Context context2, ArrayList<ArrayList<String>> Data) {
        this.data = Data;
        this.mContext = context;
        this.mContext2 = context2;
    }

    @Override
    public item_adapter.itemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new item_adapter.itemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(item_adapter.itemRowHolder holder, final int position) {
        holder.name.setText(data.get(position).get(0));
        holder.color.setText(data.get(position).get(1));
        holder.size.setText(data.get(position).get(2));
        holder.q1.setText(data.get(position).get(3));
        holder.q2.setText(data.get(position).get(4));
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext2, TransactionsActivity.class);
                intent.putExtra("name", data.get(position).get(0));
                intent.putExtra("color", data.get(position).get(1));
                intent.putExtra("size", data.get(position).get(2));
                intent.putExtra("q1", data.get(position).get(3));
                intent.putExtra("q2", data.get(position).get(4));
                ((MainActivity) mContext2).startActivityForResult(intent, 1);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(mContext2)
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you want to delete this product?")
                        .setIcon(R.drawable.ic_warning_black_24dp)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DeleteProduct deleteProduct = new DeleteProduct(mContext2);
                                deleteProduct.sendpost(data.get(position).get(0) + "?" + data.get(position).get(1)
                                        + "?" + data.get(position).get(2));
                            }
                        })
                        .setNegativeButton("NO", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class itemRowHolder extends RecyclerView.ViewHolder {
        TextView name, color, size, q1, q2;
        LinearLayout ll;
        ImageView delete;

        public itemRowHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.prod_name);
            color = itemView.findViewById(R.id.prod_color);
            size = itemView.findViewById(R.id.prod_size);
            q1 = itemView.findViewById(R.id.quantity1);
            q2 = itemView.findViewById(R.id.quantity2);
            ll = itemView.findViewById(R.id.ll);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}

