package com.android.hackslash.inventory.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.hackslash.inventory.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class trans_adapter extends RecyclerView.Adapter<trans_adapter.transRowHolder> {
    private Context mContext;

    private ArrayList<ArrayList<String>> data;

    private ArrayList<String> ttype = new ArrayList<>();

    public trans_adapter(Context context, ArrayList<ArrayList<String>> Data) {
        this.data = Data;
        this.mContext = context;
        ttype.add("To Godown : ");
        ttype.add("Godown to Shop : ");
        ttype.add("Shop to Customer : ");
        ttype.add("Shop to Godown : ");
        ttype.add("Customer to Shop : ");
    }

    @Override
    public trans_adapter.transRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_trans, parent, false);
        return new trans_adapter.transRowHolder(v);
    }

    @Override
    public void onBindViewHolder(trans_adapter.transRowHolder holder, final int position) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(data.get(position).get(0)) * 1000);
        String date = DateFormat.format("hh:mm dd-MM-yyyy", cal).toString();
        holder.datetime.setText(date);
        holder.transtype.setText(ttype.get(Integer.parseInt(data.get(position).get(1)) - 1));
        holder.transquan.setText(data.get(position).get(5));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class transRowHolder extends RecyclerView.ViewHolder {
        TextView datetime, transtype, transquan;

        public transRowHolder(View itemView) {
            super(itemView);
            datetime = itemView.findViewById(R.id.datetime);
            transtype = itemView.findViewById(R.id.trans_type);
            transquan = itemView.findViewById(R.id.trans_quan);
        }
    }
}

