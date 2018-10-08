package com.example.arezoo.firealarm;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.arezoo.firealarm.Data.DataBaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    AddressAdapter adapter;
    DataBaseHelper db;
    MediaPlayer mMediaPlayer;
    View contentView;
    Dialog d;
    int check;
    boolean isAlerting = false;

    private RecyclerView recyclerView;
    private Button add_button;
    private TextView emptyText;
    private ImageButton setting_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_alarm);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        add_button = (Button) findViewById(R.id.add_address_button);
        emptyText = (TextView) findViewById(R.id.empty_text);
        setting_button = (ImageButton) findViewById(R.id.setting_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AddressAdapter(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAddress();
            }
        });


        setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetting();
            }
        });

        chechNewIntent(getIntent());
    }

    private  void chechNewIntent(Intent intent){
        String extera =  intent.getStringExtra("pushData");
        if(extera != null){
            fireDetected(intent);
            isAlerting = true;
        }
        Log.d("message","extera is:" + extera);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
       chechNewIntent(intent);
    }

    private void openSetting() {
        Intent myIntent = new Intent(MainActivity.this, SettingActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    private void fireDetected(Intent intent) {
        if (!isAlerting){

            db = DataBaseHelper.getInstance(this);

            int id = intent.getIntExtra("firedId", 0);

            String fired_id = "" + id;

            d = new Dialog(MainActivity.this);
            contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_fire_detected, null, false);
            d.setContentView(contentView);

            TextView t = (TextView) contentView.findViewById(R.id.fired_address);
            t.setText(db.getAddress(fired_id));

            Button ok_button = (Button) contentView.findViewById(R.id.ok_button);
            ok_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    d.dismiss();
                }
            });

            d.show();

            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            check = prefs.getInt("checkedSound", 1);
            if (check == 1)
                mMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.sound1);
            else if (check == 2)
                mMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.sound2);
            else if (check == 3)
                mMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.sound3);

            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.start();

        }
    }

    private void createNewAddress() {
        Intent myIntent = new Intent(MainActivity.this, CreatAddressActivity.class);
        myIntent.putExtra("key", "creatKey");
        MainActivity.this.startActivity(myIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        updateEmptyTextState();
    }

    private void updateEmptyTextState() {
        if (adapter.getItemCount() != 0)
            emptyText.setVisibility(View.GONE);
    }
}
