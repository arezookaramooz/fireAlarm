package com.example.arezoo.firealarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.arezoo.firealarm.Data.AddressManager;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button add_button;
    private TextView emptyText;
    private AddressManager manager;
    AddressAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_alarm);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        add_button = (Button) findViewById(R.id.add_todo_button);
        emptyText = (TextView) findViewById(R.id.empty_text);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AddressAdapter(this);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
