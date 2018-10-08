package com.example.arezoo.firealarm.Data;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Arezoo on 07-Sep-18.
 */

public class AddressManager {
    private static AddressManager instance;
    DataBaseHelper dbHelper;

    private ArrayList<Address> addresses = new ArrayList();

    private AddressManager(Context context) {
        dbHelper = DataBaseHelper.getInstance(context);
        addresses.addAll(dbHelper.getAllAddresses());
    }

    public static AddressManager getInstance(Context context) {
        if (instance == null)
            instance = new AddressManager(context);
        return instance;
    }

    public void addAddress(Address address) {
        addresses.add(address);
        dbHelper.insertAddress(address);
    }

    //    public void deleteTodo (ToDo todo){
    //        toDos.remove(todo);
    //    }
    public void deleteAddressAndReports(int position) {
        dbHelper.deleteAddress(getAddresses().get(position).getId());
        dbHelper.deleteReports(getAddresses().get(position).getId());
        addresses.remove(position);


        //dbHelper.deleteAddressAndReports(position);
    }

    public ArrayList<Address> getAddresses() {
        return addresses;
    }
}