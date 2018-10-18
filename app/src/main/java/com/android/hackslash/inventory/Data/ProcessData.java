package com.android.hackslash.inventory.Data;

import com.android.hackslash.inventory.Data.getTransactions.model.Post;

import java.util.ArrayList;
import java.util.List;

public class ProcessData {

    /**
     * processes data for transactions
     * @param posts list of posts returned by the server
     * @return Array list of array list, which contains data in this order: timestamp, ttype, name, color, type, quantity
     */
    public static ArrayList<ArrayList<String>> processTransactionData(List<Post> posts){
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
}
