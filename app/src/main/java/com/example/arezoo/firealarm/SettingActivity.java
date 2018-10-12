package com.example.arezoo.firealarm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Arezoo on 16-Sep-18.
 */

public class SettingActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public static int checkedSound;
    RadioGroup radioGroup;
    RadioButton radioButtonAlarm1;
    RadioButton radioButtonAlarm2;
    RadioButton radioButtonAlarm3;
    ImageButton play1;
    ImageButton stop1;
    ImageButton play2;
    ImageButton stop2;
    ImageButton play3;
    ImageButton stop3;
    Button saveSoundButton;
    Button abortSoundButton;
    MediaPlayer mediaPlayer1;
    MediaPlayer mediaPlayer2;
    MediaPlayer mediaPlayer3;
    int check;

    EditText editText_co_threshold;
    EditText editText_co_min_x;
    EditText editText_co_max_x;
    EditText editText_co_min_y;
    EditText editText_co_max_y;

    EditText editText_smoke_threshold;
    EditText editText_smoke_min_x;
    EditText editText_smoke_max_x;
    EditText editText_smoke_min_y;
    EditText editText_smoke_max_y;

    int co_threshold;
    int co_min_x;
    int co_max_x;
    int co_min_y;
    int co_max_y;

    int smoke_threshold;
    int smoke_min_x;
    int smoke_max_x;
    int smoke_min_y;
    int smoke_max_y;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioButtonAlarm1 = (RadioButton) findViewById(R.id.radio_button_alarm1);
        radioButtonAlarm2 = (RadioButton) findViewById(R.id.radio_button_alarm2);
        radioButtonAlarm3 = (RadioButton) findViewById(R.id.radio_button_alarm3);
        play1 = (ImageButton) findViewById(R.id.image_button_play1);
        play2 = (ImageButton) findViewById(R.id.image_button_play2);
        play3 = (ImageButton) findViewById(R.id.image_button_play3);
        stop1 = (ImageButton) findViewById(R.id.image_button_stop1);
        stop2 = (ImageButton) findViewById(R.id.image_button_stop2);
        stop3 = (ImageButton) findViewById(R.id.image_button_stop3);

        saveSoundButton = (Button) findViewById(R.id.save_sound_button);
        abortSoundButton = (Button) findViewById(R.id.abort_sound_button);

        editText_co_threshold = (EditText)findViewById(R.id.edit_text_CO_threshold);
        editText_co_min_x = (EditText)findViewById(R.id.edit_text_co_min_x);
        editText_co_max_x = (EditText)findViewById(R.id.edit_text_co_max_x);
        editText_co_min_y = (EditText)findViewById(R.id.edit_text_co_min_y);
        editText_co_max_y = (EditText)findViewById(R.id.edit_text_co_max_y);

        editText_smoke_threshold = (EditText)findViewById(R.id.edit_text_smoke_threshold);
        editText_smoke_min_x = (EditText)findViewById(R.id.edit_text_smoke_min_x);
        editText_smoke_max_x = (EditText)findViewById(R.id.edit_text_smoke_max_x);
        editText_smoke_min_y = (EditText)findViewById(R.id.edit_text_smoke_min_y);
        editText_smoke_max_y = (EditText)findViewById(R.id.edit_text_smoke_max_y);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        editText_co_threshold.setText(Integer.toString(prefs.getInt("co_threshold",250)));
        editText_co_min_x.setText(Integer.toString(prefs.getInt("co_min_x", 0)));
        editText_co_max_x.setText(Integer.toString(prefs.getInt("co_max_x", 100)));
        editText_co_min_y.setText(Integer.toString(prefs.getInt("co_min_y", 100)));
        editText_co_max_y.setText(Integer.toString(prefs.getInt("co_max_y", 400)));

        editText_smoke_threshold.setText(Integer.toString(prefs.getInt("smoke_threshold",250)));
        editText_smoke_min_x.setText(Integer.toString(prefs.getInt("smoke_min_x", 0)));
        editText_smoke_max_x.setText(Integer.toString(prefs.getInt("smoke_max_x", 100)));
        editText_smoke_min_y.setText(Integer.toString(prefs.getInt("smoke_min_y", 100)));
        editText_smoke_max_y.setText(Integer.toString(prefs.getInt("smoke_max_y", 400)));

        check = prefs.getInt("checkedSound", 0);//"No name defined" is the default value

        if (check == 1)
            radioGroup.check(R.id.radio_button_alarm1);
        else if (check == 2)
            radioGroup.check(R.id.radio_button_alarm2);
        else if (check == 3)
            radioGroup.check(R.id.radio_button_alarm3);


        play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer1 = MediaPlayer.create(v.getContext(), R.raw.sound1);
                mediaPlayer1.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer1.setLooping(true);
                mediaPlayer1.start();
            }
        });

        stop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer1.stop();
            }
        });

        play2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer2 = MediaPlayer.create(v.getContext(), R.raw.sound2);
                mediaPlayer2.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer2.setLooping(true);
                mediaPlayer2.start();
            }
        });

        stop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer2.stop();
            }
        });

        play3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer3 = MediaPlayer.create(v.getContext(), R.raw.sound3);
                mediaPlayer3.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer3.setLooping(true);
                mediaPlayer3.start();
            }
        });

        stop3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer3.stop();
            }
        });

        abortSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        saveSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroup.check(radioGroup.getCheckedRadioButtonId());
                if (radioGroup.getCheckedRadioButtonId() == R.id.radio_button_alarm1)
                    checkedSound = 1;
                else if (radioGroup.getCheckedRadioButtonId() == R.id.radio_button_alarm2)
                    checkedSound = 2;
                else if (radioGroup.getCheckedRadioButtonId() == R.id.radio_button_alarm3)
                    checkedSound = 3;

                String value1 = editText_co_threshold.getText().toString();
                co_threshold = Integer.parseInt(value1);

                String value2 = editText_co_min_x.getText().toString();
                co_min_x = Integer.parseInt(value2);

                String value3 = editText_co_max_x.getText().toString();
                co_max_x = Integer.parseInt(value3);

                String value4 = editText_co_min_y.getText().toString();
                co_min_y = Integer.parseInt(value4);

                String value5 = editText_co_max_y.getText().toString();
                co_max_y = Integer.parseInt(value5);

                String value6 = editText_smoke_threshold.getText().toString();
                smoke_threshold = Integer.parseInt(value6);

                String value7 = editText_smoke_min_x.getText().toString();
                smoke_min_x = Integer.parseInt(value7);

                String value8 = editText_smoke_max_x.getText().toString();
                smoke_max_x = Integer.parseInt(value8);

                String value9 = editText_smoke_min_y.getText().toString();
                smoke_min_y = Integer.parseInt(value9);

                String value10 = editText_smoke_max_y.getText().toString();
                smoke_max_y = Integer.parseInt(value10);



                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putInt("checkedSound", checkedSound);
                editor.putInt("co_threshold", co_threshold);
                editor.putInt("co_min_x", co_min_x);
                editor.putInt("co_max_x", co_max_x);
                editor.putInt("co_min_y", co_min_y);
                editor.putInt("co_max_y", co_max_y);

                editor.putInt("smoke_threshold", smoke_threshold);
                editor.putInt("smoke_min_x", smoke_min_x);
                editor.putInt("smoke_max_x", smoke_max_x);
                editor.putInt("smoke_min_y", smoke_min_y);
                editor.putInt("smoke_max_y", smoke_max_y);
                editor.apply();
                finish();

            }
        });

    }
}
