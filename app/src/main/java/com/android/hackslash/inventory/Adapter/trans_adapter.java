package com.android.hackslash.inventory.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.hackslash.inventory.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class trans_adapter extends RecyclerView.Adapter<trans_adapter.transRowHolder> implements Filterable {
    private Context mContext;

    private ArrayList<ArrayList<String>> data, mFilteredList;

    private ArrayList<String> ttype = new ArrayList<>();

    public trans_adapter(Context context, ArrayList<ArrayList<String>> Data) {
        this.data = Data;
        this.mFilteredList = Data;
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
        String date = getDate(data.get(position).get(0));
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
                        String date = getDate(obj.get(0)).split(" ")[1];

                        if (charString.equals(date)) {
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

    private String getDate(String timestamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(timestamp) * 1000);
        String date = DateFormat.format("HH:mm dd-MM-yyyy", cal).toString();
        return date;
    }
}

