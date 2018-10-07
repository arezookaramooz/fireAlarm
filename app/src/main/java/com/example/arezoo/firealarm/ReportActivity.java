package com.example.arezoo.firealarm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.arezoo.firealarm.Data.DataBaseHelper;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by Arezoo on 06-Oct-18.
 */

public class ReportActivity extends AppCompatActivity {
    DataBaseHelper myDbHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Button ok_button = (Button)findViewById(R.id.ok_button_report);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myDbHelper = new DataBaseHelper(this, "dbHelper", null, 1);
        Log.d("ReportActivity", "is:" + myDbHelper.get10LastCOValue(12));

        GraphView graph1 = (GraphView) findViewById(R.id.graph1);
        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 150),
                new DataPoint(1, 160),
                new DataPoint(2, 176),
                new DataPoint(3, 175),
                new DataPoint(4, 200)
        });
        graph1.addSeries(series1);

        GraphView graph2 = (GraphView) findViewById(R.id.graph2);
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph2.addSeries(series2);
    }
}
