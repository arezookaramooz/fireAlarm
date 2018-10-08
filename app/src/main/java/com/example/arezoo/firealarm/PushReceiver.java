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
        boolean fireDetected = false;
        int firedID = 0;
        int firedIndex = 0;
        dbHelper = DataBaseHelper.getInstance(context);
        for (int i = 0; i < messages.length; i++) {
            Log.d(TAG, "push is:" + messages[i].getData());
            int id = 0;
            int smoke = 0;
            int co = 0;
            long time = 0;
            try {
                JSONObject jsonObject = new JSONObject(messages[i].getData());
                id = jsonObject.getInt("id");
                smoke = jsonObject.getInt("smoke");
                co = jsonObject.getInt("co");
//            time = jsonObject.getLong("time");
                time = System.currentTimeMillis();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (dbHelper.getAddress("" + id) != null) {
                dbHelper.insertRowToReport(time, id, co, smoke);
                if (co > 250 && smoke > 250) {
                    fireDetected = true;
                    firedID = id;
                    firedIndex = i;
                }
            } else {
                Log.d(TAG, "onPushReceived: address id invalid " + id);
            }
        }

        if (fireDetected) {
            Intent myIntent = new Intent(context, MainActivity.class);
            myIntent.putExtra("pushData", messages[firedIndex].getData());
            myIntent.putExtra("firedId", firedID);
            context.startActivity(myIntent);
        }
    }
}