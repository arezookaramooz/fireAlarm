package com.example.arezoo.firealarm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arezoo.firealarm.Data.Address;
import com.example.arezoo.firealarm.Data.AddressManager;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.R.attr.id;

/**
 * Created by Arezoo on 07-Sep-18.
 */

public class CreatAddressActivity extends AppCompatActivity {

    String position;
    int myposition;
    AddressManager myManager;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_address);
        final String key = (getIntent().getStringExtra("key"));

        Button abortButton = (Button) findViewById(R.id.button_abort);
        abortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        final EditText editTextId = (EditText) findViewById(R.id.edit_text_id);
        final EditText editTextAddress = (EditText) findViewById(R.id.edit_text_address);

        if (key.equals("editKey")) {
            position = (getIntent().getStringExtra("position"));
            myposition = Integer.parseInt(position);
            myManager = AddressManager.getInstance(this);

            editTextId.setText(myManager.getAddresses().get(myposition).getId());
            editTextAddress.setText(myManager.getAddresses().get(myposition).getAddress());
        }


        Button saveButton = (Button) findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String id = editTextId.getText().toString();
                final String address = editTextAddress.getText().toString();


                if (key.equals("creatKey")) {
                    if (id == null || id.equals("") || address == null || address.equals("")) {
                        ((TextView) findViewById(R.id.tex_view_id)).setTextColor(0xffff0000);
                        ((TextView) findViewById(R.id.tex_view_address)).setTextColor(0xffff0000);
                        Toast.makeText(CreatAddressActivity.this, "fill the blank fields first", Toast.LENGTH_SHORT).show();

                    } else {
                        Address myAddress = new Address(id, address);
                        AddressManager manager = AddressManager.getInstance(CreatAddressActivity.this);
                        manager.addAddress(myAddress);

                        finish();
                    }

                }

            }
        });
    }

}
