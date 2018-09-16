package com.example.arezoo.firealarm;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arezoo.firealarm.Data.AddressManager;
import com.example.arezoo.firealarm.Data.DataBaseHelper;

public class MainActivity extends AppCompatActivity {
    AddressAdapter adapter;
    DataBaseHelper db;
    private RecyclerView recyclerView;
    private Button add_button;
    private Button fire_button;
    private Button ok_button;
    private TextView emptyText;
    MediaPlayer mMediaPlayer;
    private AddressManager manager;
    View contentView;
    Dialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_fire_detect, null, false);
        setContentView(R.layout.activity_fire_alarm);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        add_button = (Button) findViewById(R.id.add_todo_button);
        fire_button = (Button) findViewById(R.id.fire_button);
        emptyText = (TextView) findViewById(R.id.empty_text);
        ok_button = (Button)contentView.findViewById(R.id.ok_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AddressAdapter(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        manager = AddressManager.getInstance(this);
        recyclerView.setAdapter(adapter);
        if (adapter.getItemCount() > 0)
            emptyText.setVisibility(View.GONE);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, CreatAddressActivity.class);
                myIntent.putExtra("key", "creatKey");
                MainActivity.this.startActivity(myIntent);
            }
        });


        fire_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DataBaseHelper(MainActivity.this, "myDatabase", null, 1);
                String fired_id = "10";

                d = new Dialog(MainActivity.this);
                d.setContentView(contentView);
                TextView t = (TextView) contentView.findViewById(R.id.fired_address);
                t.setText(db.getAddress(fired_id));
                d.show();

                mMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.sound1);
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setLooping(true);
                mMediaPlayer.start();
            }
        });

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.stop();
                d.dismiss();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        if (adapter.getItemCount() != 0)
            emptyText.setVisibility(View.GONE);

    }

}
