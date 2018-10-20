package com.android.hackslash.inventory.Data;

import com.android.hackslash.inventory.Data.getInventoryDetails.model.Post_inventory;
import com.android.hackslash.inventory.Data.getTransactions.model.Post_transactions;

import java.util.ArrayList;
import java.util.List;

public class ProcessData {

    /**
     * processes data for transactions
     * @param posts list of posts returned by the server
     * @return Array list of array list, which contains data in this order: timestamp, ttype, name, color, type, quantity
     */
    public static ArrayList<ArrayList<String>> processTransactionData(List<Post_transactions> posts){
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        for (int i = 0; i < posts.size() ; i++){
            ArrayList<String> temp = new ArrayList<>();
            temp.add(posts.get(i).getTimestamp());
            temp.add(posts.get(i).getTtype());
            temp.add(posts.get(i).getName());
            temp.add(posts.get(i).getColor());
            temp.add(posts.get(i).getType());
            temp.add(posts.get(i).getQuantity());

            data.add(temp);
        }

        return data;
    }

    public static ArrayList<ArrayList<String>> processInventoryData(List<Post_inventory> posts, String query) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        for (int i = 0; i < posts.size() ; i++){
            ArrayList<String> temp = new ArrayList<>();
            temp.add(posts.get(i).getName());
            temp.add(posts.get(i).getColor());
            temp.add(posts.get(i).getType());

            switch (query) {
                case "1":
                    temp.add(posts.get(i).getShop1());
                    break;
                case "2":
                    temp.add(posts.get(i).getShop2());
                    break;
                case "3":
                    temp.add(posts.get(i).getShop1());
                    temp.add(posts.get(i).getShop2());
                    break;
            }

            data.add(temp);
        }

        return data;
    }
}
