package com.example.khtsapplicationv1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class WaterStats extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private  String api_resp;
    LineGraphSeries<DataPoint> series;
    LineGraphSeries<DataPoint> seriesE;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_stats);

        Switch water = findViewById(R.id.swtWater);
        Switch elec = findViewById(R.id.swtElec);

        String meterNum = getIntent().getStringExtra("meter");
        url = "http://sparkhts.zapto.org/khtsweb/khts-serv/public/index.php/customers/usage/" + meterNum;

        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 0)
        });

        seriesE = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 0)
        });
        series.setColor(Color.parseColor("#3CA9A9"));
        seriesE.setColor(Color.parseColor("#FE6152"));
        series.setAnimated(true);
        seriesE.setAnimated(true);
        graph.getViewport().setScalable(true);
        graph.setTitle("Usage Overview");
        graph.setTitleTextSize(50);
        graph.addSeries(series);
        graph.addSeries(seriesE);
        graph.setPivotY(1);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graph.getViewport().setDrawBorder(true);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3);
        graph.getGridLabelRenderer().setNumVerticalLabels(10);
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getViewport().setMaxYAxisSize(5);
        graph.getViewport().setYAxisBoundsManual(true);
        getData(url);

        water.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (!b){
                    graph.removeSeries(series);
                }
                else if (b){
                    graph.addSeries(series);
                }
            }
        });

        elec.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b){
                    graph.removeSeries(seriesE);
                }
                else if (b){
                    graph.addSeries(seriesE);
                }
            }
        });
    }


    private void getData(String url){
        //initialize RequestQueue
        mRequestQueue = Volley.newRequestQueue(this);
        TextView maxW = findViewById(R.id.maxW);
        TextView maxE = findViewById(R.id.maxE);
        TextView averageW = findViewById(R.id.avgW);
        TextView averageE = findViewById(R.id.avgE);

        //Initialize Request String
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                api_resp = response.toString();
                double tempWater, tempElec;
                double totalWater = 0;
                double totalElec = 0;
                double avgW = 0;
                double avgE = 0;
                String tempDate ="";
                int count = 0;
                @SuppressLint("SimpleDateFormat") SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                try {
                    JSONArray data = new JSONArray(api_resp);

                    for (int i = 0;i< data.length();i+=1){
                        JSONObject current = data.getJSONObject(i);
                        tempWater = current.getDouble("water");
                        tempElec = current.getDouble("electricity");
                        tempDate = current.getString("DateTime");
                        //totals
                        //totalWater += tempWater;
                        //totalElec += tempElec;
                        Date entryDate = f1.parse(tempDate.toString());

                        if (entryDate != null && tempWater>=0 && tempWater<=100 && tempElec>=0 && tempElec<=100) {

                            count++;
                            series.appendData(new DataPoint(entryDate, tempWater), true, data.length());
                            seriesE.appendData(new DataPoint(entryDate, tempElec), true, data.length());
                            totalWater += tempWater;
                            totalElec += tempElec;

                        }

                    }
                    maxW.setText(Double.toString(totalWater));
                    maxE.setText(Double.toString(totalElec));

                    avgW = Math.round(totalWater/ count);
                    avgE = Math.round(totalElec/ count);

                    averageW.setText(Double.toString(avgW));
                    averageE.setText(Double.toString(avgE));



                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(getApplicationContext(), "Response: " + response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error: " + error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        mRequestQueue.add(mStringRequest);

    }
}