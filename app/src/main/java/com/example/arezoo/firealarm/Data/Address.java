package com.example.arezoo.firealarm.Data;

import android.net.Uri;

import java.util.Calendar;

/**
 * Created by Arezoo on 07-Sep-18.
 */

public class Address {
    String id;
    String address;

    public Address(String id, String address) {
        this.id = id;
        this.address = address;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



}
