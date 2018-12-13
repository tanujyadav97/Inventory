package com.android.hackslash.inventory.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hackslash.inventory.DeleteProduct;
import com.android.hackslash.inventory.EditProduct;
import com.android.hackslash.inventory.MainActivity;
import com.android.hackslash.inventory.R;
import com.android.hackslash.inventory.TransactionsActivity;

import java.util.ArrayList;

public class item_adapter extends RecyclerView.Adapter<item_adapter.itemRowHolder> implements Filterable {
    private Context mContext, mContext2;

    private ArrayList<ArrayList<String>> data, mFilteredList;

    public item_adapter(Context context, Context context2, ArrayList<ArrayList<String>> Data) {
        this.data = Data;
        this.mFilteredList = Data;
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

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(mContext2)
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you want to update this product?")
                        .setIcon(R.drawable.ic_error_outline_black_24dp)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent intent = new Intent(mContext2, EditProduct.class);
                                intent.putExtra("name", data.get(position).get(0));
                                intent.putExtra("color", data.get(position).get(1));
                                intent.putExtra("type", data.get(position).get(2));
                                ((MainActivity) mContext2).startActivityForResult(intent, 1);
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
        ImageView delete, edit;

        public itemRowHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.prod_name);
            color = itemView.findViewById(R.id.prod_color);
            size = itemView.findViewById(R.id.prod_size);
            q1 = itemView.findViewById(R.id.quantity1);
            q2 = itemView.findViewById(R.id.quantity2);
            ll = itemView.findViewById(R.id.ll);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);
        }
    }


    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    data = mFilteredList;
                } else {

                    ArrayList<ArrayList<String>> filteredList = new ArrayList<>();

                    for (ArrayList<String> obj : mFilteredList) {
                        String name = obj.get(0);

                        if (name.toLowerCase().startsWith(charString.toLowerCase())) {
                            filteredList.add(obj);
                        }
                    }
                    data = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data = (ArrayList<ArrayList<String>>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}

