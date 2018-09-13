package com.example.arezoo.firealarm.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.preference.PreferenceFragment;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Arezoo on 07-Sep-18.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String CREATE = "CREATE ";
    public static final String TABLE = "TABLE ";
    public static final String INSERT = "INSERT ";
    public static final String INTO = "INTO ";
    public static final String  SELECT = "SELECT ";
    public static final String FROM = "FROM ";
    public static final String STAR = "*";
    public static final String VALUES = "VALUES ";
    public static final String AUTO_INCREMENT = "AUTOINCREMENT ";
    public static final String PRIMARY_KEY = "PRIMARY KEY ";

    public static final String ADDRESSES_TABLE = "addresses ";
    public static final String COLUMN_ID = "id ";
    public static final String COLUMN_ADDRESS_ID = "addressId ";
    public static final String COLUMN_ADDRESS = "address ";



    public static final String DATA_TYPE_INTEGER = "INTEGER ";
    public static final String DATA_TYPE_STRING = "VARCHAR(1000) ";

    public static final String COMMA = ", ";
    public static final String PARENTHESIS_OPEN = "( ";
    public static final String PARENTHESIS_CLOSE = ") ";
    public static final String SEMI_COLON = "; ";
    private static final String TAG = "DatabaseHelper ";

    int id = 1;

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String q = CREATE + TABLE + ADDRESSES_TABLE + PARENTHESIS_OPEN
                + COLUMN_ID + DATA_TYPE_INTEGER  + PRIMARY_KEY + AUTO_INCREMENT +  COMMA
                + COLUMN_ADDRESS_ID + DATA_TYPE_STRING + COMMA
                + COLUMN_ADDRESS + DATA_TYPE_STRING
                + PARENTHESIS_CLOSE + SEMI_COLON;
        Log.d(TAG, "onCreate: query=" + q);
        db.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertAddress( Address address) {
        String i = INSERT + INTO + ADDRESSES_TABLE + PARENTHESIS_OPEN
                + COLUMN_ADDRESS_ID +COMMA
                + COLUMN_ADDRESS
                + PARENTHESIS_CLOSE + VALUES + PARENTHESIS_OPEN
                + "\"" +address.getId() + "\"" + COMMA
                + "\"" + address.getAddress() + "\""
                + PARENTHESIS_CLOSE + SEMI_COLON;
        getWritableDatabase().execSQL(i);

    }

    public ArrayList<Address> getAllAddresses() {
        String q = SELECT + STAR + FROM + ADDRESSES_TABLE;
        Cursor c = getReadableDatabase().rawQuery(q, null);
       c.moveToFirst();
        ArrayList<Address>  a = new ArrayList<>();
        for(int i = 1 ; i<= c.getCount() ; i++){
            Address myAddress = new Address(c.getString(1), c.getString(2));
            a.add(myAddress);
            c.moveToNext();
        }
        return a;

    }

    public void deleteAddress(int position) {
    }
}
