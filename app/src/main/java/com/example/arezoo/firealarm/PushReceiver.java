package com.example.arezoo.firealarm;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import rest.bef.BefrestMessage;
import rest.bef.BefrestPushReceiver;

/**
 * Created by Arezoo on 05-Oct-18.
 */
public class PushReceiver extends BefrestPushReceiver {
    private static final String TAG = "PushReceiver";
    @Override
    public void onPushReceived(Context context, BefrestMessage[] messages) {
        int id = 0;
        int smoke = 0;
        int CO = 0;
        Log.d(TAG, "push is:" + messages[0].getData());

        try {
            JSONObject jsonObject = new JSONObject(messages[0].getData());
            id = jsonObject.getInt("id");
            smoke = jsonObject.getInt("smoke");
            CO = jsonObject.getInt("CO");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "parsed values are:" + id + smoke + CO);

        if (smoke > 200 && CO > 200) {
            Intent myIntent = new Intent(context, MainActivity.class);
            myIntent.putExtra("pushData", messages[0].getData());
            myIntent.putExtra("firedId", id);
            context.startActivity(myIntent);
        }

    }
}