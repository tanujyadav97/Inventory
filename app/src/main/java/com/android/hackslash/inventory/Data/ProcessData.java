package com.android.hackslash.inventory.Data;

import com.android.hackslash.inventory.Data.getInventoryDetails.model.Post_inventory;
import com.android.hackslash.inventory.Data.getTransactions.model.Post_transactions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProcessData {

    /**
     * processes data for transactions
     *
     * @param posts list of posts returned by the server
     * @return Array list of array list, which contains data in this order: timestamp, ttype, name, color, type, quantity
     */
    public static ArrayList<ArrayList<String>> processTransactionData(List<Post_transactions> posts) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        for (int i = 0; i < posts.size(); i++) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(posts.get(i).getTimestamp() == null ? "" : posts.get(i).getTimestamp());
            temp.add(posts.get(i).getTtype() == null ? "" : posts.get(i).getTtype());
            temp.add(posts.get(i).getName() == null ? "" : posts.get(i).getName());
            temp.add(posts.get(i).getColor() == null ? "" : posts.get(i).getColor());
            temp.add(posts.get(i).getType() == null ? "" : posts.get(i).getType());
            temp.add(posts.get(i).getQuantity() == null ? "" : posts.get(i).getQuantity());

            data.add(temp);
        }

        Collections.sort(data, new ProcessData.Sorttrans());
        return data;
    }

    public static ArrayList<ArrayList<String>> processInventoryData(List<Post_inventory> posts, String query) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        for (int i = 0; i < posts.size(); i++) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(posts.get(i).getName() == null ? "" : posts.get(i).getName());
            temp.add(posts.get(i).getColor() == null ? "" : posts.get(i).getColor());
            temp.add(posts.get(i).getType() == null ? "" : posts.get(i).getType());

            switch (query) {
                case "1":
                    temp.add(posts.get(i).getShop1() == null ? "" : posts.get(i).getShop1());
                    break;
                case "2":
                    temp.add(posts.get(i).getShop2() == null ? "" : posts.get(i).getShop2());
                    break;
                case "3":
                    temp.add(posts.get(i).getShop1() == null ? "" : posts.get(i).getShop1());
                    temp.add(posts.get(i).getShop2() == null ? "" : posts.get(i).getShop2());
                    break;
            }

            data.add(temp);
        }

        Collections.sort(data, new ProcessData.Sortinven());
        return data;
    }

    public static class Sorttrans implements Comparator<ArrayList<String>> {
        public int compare(ArrayList<String> a, ArrayList<String> b) {
            if (Long.parseLong(a.get(0)) < Long.parseLong(b.get(0)))
                return 1;
            else if (Long.parseLong(a.get(0)) == Long.parseLong(b.get(0)))
                return 0;
            else
                return -1;
        }
    }

    public static class Sortinven implements Comparator<ArrayList<String>> {
        public int compare(ArrayList<String> a, ArrayList<String> b) {
            if (a.get(0).compareToIgnoreCase(b.get(0)) == 0) {
                if (a.get(1).compareToIgnoreCase(b.get(1)) == 0) {
                    return a.get(2).compareToIgnoreCase(b.get(2));
                } else
                    return a.get(1).compareToIgnoreCase(b.get(1));

            } else
                return a.get(0).compareToIgnoreCase(b.get(0));
        }
    }
}
