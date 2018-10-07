package com.example.arezoo.firealarm;

import android.app.NotificationManager;
import android.app.admin.SystemUpdatePolicy;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.arezoo.firealarm.Data.DataBaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import rest.bef.BefrestMessage;
import rest.bef.BefrestPushReceiver;

/**
 * Created by Arezoo on 05-Oct-18.
 */
public class PushReceiver extends BefrestPushReceiver {
    private static final String TAG = "PushReceiver";
    DataBaseHelper dbHelper;
    @Override
    public void onPushReceived(Context context, BefrestMessage[] messages) {
        int id = 0;
        int smoke = 0;
        int co = 0;
//        int time = 0;
        Log.d(TAG, "push is:" + messages[0].getData());

        try {
            JSONObject jsonObject = new JSONObject(messages[0].getData());
            id = jsonObject.getInt("id");
            smoke = jsonObject.getInt("smoke");
            co = jsonObject.getInt("co");
//            time = jsonObject.getInt("time");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "parsed values are:" + id + "" + smoke + "" +co);

        dbHelper = new DataBaseHelper(context, "myDatabase", null, 1);
        Long time = System.currentTimeMillis();
        Log.d("TAG", "time is:" + time);
        dbHelper.insertRowToReport(time, id, co, smoke);

        if (smoke > 200 && co > 200) {
            Intent myIntent = new Intent(context, MainActivity.class);
            myIntent.putExtra("pushData", messages[0].getData());
            myIntent.putExtra("firedId", id);
            context.startActivity(myIntent);
        }

    }
}