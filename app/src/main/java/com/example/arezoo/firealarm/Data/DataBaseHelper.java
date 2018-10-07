package com.example.arezoo.firealarm.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public static final String SELECT = "SELECT ";
    public static final String FROM = "FROM ";
    public static final String STAR = "* ";
    public static final String WHERE = "WHERE ";
    public static final String EQUAL = "= ";
    public static final String DELETE = "DELETE ";
    public static final String VALUES = "VALUES ";
    public static final String AUTO_INCREMENT = "AUTOINCREMENT ";
    public static final String PRIMARY_KEY = "PRIMARY KEY ";
    public static final String LESS_THAN = "< ";
    public static final String ORDER_BY = "order by ";
    public static final String DESC = "DESC ";
    public static final String ASC = "ASC ";
    public static final String LIMIT = "limit ";

    public static final String ADDRESSES_TABLE = "addresses ";
    public static final String COLUMN_ID = "id ";
    public static final String COLUMN_ADDRESS_ID = "addressId ";
    public static final String COLUMN_ADDRESS = "address ";

    public static final String REPORT_TABLE = "report ";
    //    public static final String COLUMN_ID = "id ";
    public static final String COLUMN_REPORT_ID = "reportID ";
    public static final String COLUMN_CO = "co ";
    public static final String COLUMN_SMOKE = "smoke ";
    public static final String COLUMN_TIME = "time ";


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
                + COLUMN_ID + DATA_TYPE_INTEGER + PRIMARY_KEY + AUTO_INCREMENT + COMMA
                + COLUMN_ADDRESS_ID + DATA_TYPE_STRING + COMMA
                + COLUMN_ADDRESS + DATA_TYPE_STRING
                + PARENTHESIS_CLOSE + SEMI_COLON;
        Log.d(TAG, "onCreate: query=" + q);
        db.execSQL(q);


        String s = CREATE + TABLE + REPORT_TABLE + PARENTHESIS_OPEN
//                + COLUMN_ID + DATA_TYPE_INTEGER + PRIMARY_KEY + AUTO_INCREMENT + COMMA
                + COLUMN_TIME + DATA_TYPE_INTEGER + COMMA
                + COLUMN_REPORT_ID + DATA_TYPE_INTEGER + COMMA
                + COLUMN_CO + DATA_TYPE_INTEGER + COMMA
                + COLUMN_SMOKE + DATA_TYPE_INTEGER
                + PARENTHESIS_CLOSE + SEMI_COLON;
        Log.d(TAG, "create report table query=" + s);
        db.execSQL(s);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ADDRESSES_TABLE);
        onCreate(db);

    }

    public void insertAddress(Address address) {
        String i = INSERT + INTO + ADDRESSES_TABLE + PARENTHESIS_OPEN
                + COLUMN_ADDRESS_ID + COMMA
                + COLUMN_ADDRESS
                + PARENTHESIS_CLOSE + VALUES + PARENTHESIS_OPEN
                + "\"" + address.getId() + "\"" + COMMA
                + "\"" + address.getAddress() + "\""
                + PARENTHESIS_CLOSE + SEMI_COLON;
        getWritableDatabase().execSQL(i);

    }

    public void insertRowToReport(Long time, Integer id, Integer co, Integer smoke) {
        String i = INSERT + INTO + REPORT_TABLE + PARENTHESIS_OPEN
                + COLUMN_TIME + COMMA
                + COLUMN_REPORT_ID + COMMA
                + COLUMN_CO + COMMA
                + COLUMN_SMOKE
                + PARENTHESIS_CLOSE + VALUES + PARENTHESIS_OPEN
                + "\"" + time + "\"" + COMMA
                + "\"" + id + "\"" + COMMA
                + "\"" + co + "\"" + COMMA
                + "\"" + smoke + "\""
                + PARENTHESIS_CLOSE + SEMI_COLON;
        getWritableDatabase().execSQL(i);

    }


    public ArrayList<Address> getAllAddresses() {
        String q = SELECT + STAR + FROM + ADDRESSES_TABLE + SEMI_COLON;
        Cursor c = getReadableDatabase().rawQuery(q, null);
        c.moveToFirst();
        ArrayList<Address> a = new ArrayList<>();
        for (int i = 1; i <= c.getCount(); i++) {
            Address myAddress = new Address(c.getString(1), c.getString(2));
            a.add(myAddress);
            c.moveToNext();
        }
        return a;

    }

    public String getAddress(String id) {
        String q = SELECT + COLUMN_ADDRESS + FROM + ADDRESSES_TABLE + WHERE + COLUMN_ADDRESS_ID + EQUAL + "\"" + id + "\"" + SEMI_COLON;
        Cursor c = getReadableDatabase().rawQuery(q, null);
        c.moveToFirst();
        return c.getString(0);
    }

    public void deleteAddress(String id) {
        String q = DELETE + FROM + ADDRESSES_TABLE + WHERE + COLUMN_ADDRESS_ID + EQUAL + "\"" + id + "\"" + SEMI_COLON;
        getWritableDatabase().execSQL(q);


    }

    public ArrayList<Integer> get10LastCOValue( int repID) {

//        select * from (select * from tblmessage where reportId = repID order by time desc limit 10) order by time asc ;

        String q = SELECT + STAR + FROM + PARENTHESIS_OPEN + SELECT + STAR + FROM + REPORT_TABLE + WHERE + COLUMN_REPORT_ID + EQUAL
                + "\"" + repID + "\"" + ORDER_BY + COLUMN_TIME + DESC + LIMIT + "10 " + PARENTHESIS_CLOSE + ORDER_BY
                + COLUMN_TIME + ASC + SEMI_COLON;
        Cursor c = getReadableDatabase().rawQuery(q, null);
        c.moveToFirst();
        ArrayList<Integer> a = new ArrayList<>();
        for (int i = 1; i <= c.getCount(); i++) {
            a.add(c.getInt(2));
            c.moveToNext();
        }
        return a;
    }


    public ArrayList<Integer> get10LastSmokeValue(int repID) {

        String q = SELECT + STAR + FROM + PARENTHESIS_OPEN + SELECT + STAR + FROM + REPORT_TABLE + WHERE + COLUMN_REPORT_ID + EQUAL
                + "\"" + repID + "\"" + ORDER_BY + COLUMN_TIME + DESC + LIMIT + "10 " + PARENTHESIS_CLOSE + ORDER_BY
                + COLUMN_TIME + ASC + SEMI_COLON;
        Cursor c = getReadableDatabase().rawQuery(q, null);
        c.moveToFirst();
        ArrayList<Integer> a = new ArrayList<>();
        for (int i = 1; i <= c.getCount(); i++) {
            a.add(c.getInt(3));
            c.moveToNext();
        }
        return a;
    }

}
