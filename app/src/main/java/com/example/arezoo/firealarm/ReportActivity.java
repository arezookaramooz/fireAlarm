package com.example.arezoo.firealarm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.arezoo.firealarm.Data.DataBaseHelper;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

/**
 * Created by Arezoo on 06-Oct-18.
 */

public class ReportActivity extends AppCompatActivity {
    private static final String TAG = "ReportActivity";
    DataBaseHelper myDbHelper;
    String addressID;
    GraphView graph1;
    GraphView graph2;
    private LineGraphSeries<DataPoint> coSeries;
    private LineGraphSeries<DataPoint> smokeSeries;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    int co_min_x;
    int co_max_x;
    int co_min_y;
    int co_max_y;
    int smoke_min_x;
    int smoke_max_x;
    int smoke_min_y;
    int smoke_max_y;

    Runnable updateGraphrunnable = new Runnable() {
        @Override
        public void run() {
            updateGraphs();
        }
    };
    Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        myDbHelper = DataBaseHelper.getInstance(this);
        addressID = getIntent().getStringExtra("addressID");

        Button ok_button = (Button) findViewById(R.id.ok_button_report);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        graph1 = (GraphView) findViewById(R.id.graph1);
        graph2 = (GraphView) findViewById(R.id.graph2);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        co_min_x = prefs.getInt("co_min_x", 0);
        co_max_x = prefs.getInt("co_max_x", 100);
        co_min_y = prefs.getInt("co_min_y", 100);
        co_max_y = prefs.getInt("co_max_y", 300);
        smoke_min_x = prefs.getInt("smoke_min_x", 0);
        smoke_max_x = prefs.getInt("smoke_max_x", 100);
        smoke_min_y = prefs.getInt("smoke_min_y", 100);
        smoke_max_y = prefs.getInt("smoke_max_y", 300);

        graph1.getViewport().setMinX(co_min_x);
        graph1.getViewport().setMaxX(co_max_x);
        graph1.getViewport().setMinY(co_min_y);
        graph1.getViewport().setMaxY(co_max_y);
        graph1.getViewport().setYAxisBoundsManual(true);
        graph1.getViewport().setXAxisBoundsManual(true);

        graph2.getViewport().setMinX(smoke_min_x);
        graph2.getViewport().setMaxX(smoke_max_x);
        graph2.getViewport().setMinY(smoke_min_y);
        graph2.getViewport().setMaxY(smoke_max_y);
        graph2.getViewport().setYAxisBoundsManual(true);
        graph2.getViewport().setXAxisBoundsManual(true);


        updateGraphs();
    }

    public void updateGraphs() {
        Log.d(TAG, "updateGraphs: ");
        ArrayList<Integer> coArrayList = myDbHelper.getLastCOValues(addressID, 100);
        Log.d(TAG, "updateGraphs: coArraList=" + coArrayList);
        Log.d(TAG, "updateGraphs: coSeries=" + coSeries + " smokeSeries=" + smokeSeries);
        DataPoint[] coDataPoints = new DataPoint[coArrayList.size()];
        for (int i = 0; i < coArrayList.size(); i++) {
            coDataPoints[i] = new DataPoint(i, coArrayList.get(i));
        }
        if (coSeries == null) {
            coSeries = new LineGraphSeries<>();
            graph1.addSeries(coSeries);
        }
        coSeries.resetData(coDataPoints);

        ArrayList<Integer> smokeArrayList = myDbHelper.getLastSmokeValues(addressID, 100);
        DataPoint[] smokeDataPoints = new DataPoint[smokeArrayList.size()];
        for (int i = 0; i < smokeArrayList.size(); i++) {
            smokeDataPoints[i] = new DataPoint(i, smokeArrayList.get(i));
        }
        if (smokeSeries == null) {
            smokeSeries = new LineGraphSeries<>();
            graph2.addSeries(smokeSeries);
        }
        smokeSeries.resetData(smokeDataPoints);

        handler.postDelayed(updateGraphrunnable, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateGraphrunnable);
    }
}
