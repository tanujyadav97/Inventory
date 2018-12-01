package com.android.hackslash.inventory.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hackslash.inventory.R;
import com.android.hackslash.inventory.TransactionsActivity;

import java.util.ArrayList;

public class item_adapter extends RecyclerView.Adapter<item_adapter.itemRowHolder> {
    private Context mContext;

    private ArrayList<ArrayList<String>> data;

    public item_adapter(Context context, ArrayList<ArrayList<String>> Data) {
        this.data = Data;
        this.mContext = context;
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
                Intent intent = new Intent(mContext, TransactionsActivity.class);
                intent.putExtra("name", data.get(position).get(0));
                intent.putExtra("color", data.get(position).get(1));
                intent.putExtra("size", data.get(position).get(2));
                intent.putExtra("q1", data.get(position).get(3));
                intent.putExtra("q2", data.get(position).get(4));
                mContext.startActivity(intent);
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

        public itemRowHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.prod_name);
            color = itemView.findViewById(R.id.prod_color);
            size = itemView.findViewById(R.id.prod_size);
            q1 = itemView.findViewById(R.id.quantity1);
            q2 = itemView.findViewById(R.id.quantity2);
            ll = itemView.findViewById(R.id.ll);
        }
    }
}

