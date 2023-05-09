package com.example.khtsapplicationv1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class WaterStats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_stats);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 2),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 3),
                new DataPoint(5, 3),
                new DataPoint(6, 3),
                new DataPoint(7, 2),
                new DataPoint(8, 3),
                new DataPoint(9, 4),
                new DataPoint(10, 2)
        });

        LineGraphSeries<DataPoint> seriesE = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 4),
                new DataPoint(2, 3),
                new DataPoint(3, 1),
                new DataPoint(4, 4),
                new DataPoint(5, 4),
                new DataPoint(6, 3),
                new DataPoint(7, 4),
                new DataPoint(8, 3),
                new DataPoint(9, 4),
                new DataPoint(10, 3)
        });

        series.setColor(Color.parseColor("#3CA9A9"));
        seriesE.setColor(Color.parseColor("#FE6152"));
        series.setAnimated(true);
        seriesE.setAnimated(true);
        graph.getViewport().setScalable(false);
        graph.setTitle("Usage Overview");
        graph.setTitleTextSize(50);
        graph.addSeries(series);
        graph.addSeries(seriesE);
    }
}