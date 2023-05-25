package com.example.khtsapplicationv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Locale;

public class Home extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private  String api_resp;
    String meterNum;
    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String saID = getIntent().getStringExtra("userId");
        String url_get = "http://sparkhts.zapto.org/khtsweb/khts-serv/public/index.php/customers/refresh/" + saID;
        getData(url_get);

        /*URL needs to be created depending on user!!!*/
        Button waterRecharge = findViewById(R.id.btnWaterTopUp);
        Button mainRecharge = findViewById(R.id.btnRecharge);
        Button refresh = findViewById(R.id.btnRefresh);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(url_get);
                Toast.makeText(getApplicationContext(),"Refreshed", Toast.LENGTH_LONG).show();
            }
        });

        waterRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), WaterStats.class);
                i.putExtra("meter", meterNum);
                startActivity(i);
            }
        });

        mainRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), recharge.class);
                startActivity(i);
            }
        });
    }

    private void getData(String url){

        final String[] address = new String[1];
        final String[] waterUsage = new String[1];
        final String[] elecUsage = new String[1];
        final String[] TotalUnitsE = new String[1];
        final String[] TotalUnitsW = new String[1];
        final int[] waterState = new int[1];
        final int[] elecState = new int[1];

        TextView addr = findViewById(R.id.txtAddress);
        TextView water = findViewById(R.id.lblWaterUsage);
        TextView elec = findViewById(R.id.lblElecUsage);
        TextView TotalW = findViewById(R.id.txtTotalW);
        TextView TotalE = findViewById(R.id.txtTotalE);
        TextView WaterStatus = findViewById(R.id.lblwaterStatus);
        TextView ElecStatus = findViewById(R.id.lblelecStatus);
        TextView meterNumber = findViewById(R.id.txtMeterNum);
        TextView currentWater = findViewById(R.id.txtCurrentWater);
        TextView currentElec = findViewById(R.id.txtCurrentElec);
        ProgressBar progWater = findViewById(R.id.prgWater);
        ProgressBar progElec = findViewById(R.id.prgElec);



        //initialize RequestQueue
        mRequestQueue = Volley.newRequestQueue(this);


        //Initialize Request String
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                api_resp = response.toString();

                try {
                    JSONObject user = new JSONObject(api_resp);                                          //separating JSON structure
                    JSONObject customer = user.getJSONObject("customer");
                    JSONArray reading = user.getJSONArray("latestreading");

                    address[0] = customer.getString("address");                                    //getting variables
                    TotalUnitsE[0] = customer.getString("TotalUnitsE");
                    TotalUnitsW[0] = customer.getString("TotalUnitsW");
                    waterState[0] = customer.getInt("water");
                    elecState[0] = customer.getInt("elec");
                    meterNum = customer.getString("meterNumber");
                    int maxWater = Integer.parseInt(TotalUnitsW[0].toString());
                    int maxElec = Integer.parseInt(TotalUnitsE[0].toString());
                    //int usageWater = Integer.parseInt(waterUsage[0].toString());
                    //int usageElec = Integer.parseInt(elecUsage[0].toString());
                    double tempWater = 0;
                    double tempElec = 0;

                    /*for(int i=0;i<reading.length();i++){
                        JSONObject current = reading.getJSONObject(i);
                        tempWater += current.getDouble("water");
                        tempElec += current.getDouble("electricity");
                    }*/
                    JSONObject current = new JSONObject();
                    for (int i = reading.length()-10;i< reading.length();i+=1) {
                        current = reading.getJSONObject(reading.length() - 1);
                        JSONObject recharge = reading.getJSONObject(0);

                        tempWater = Math.abs(current.getDouble("water") - recharge.getDouble("water"));
                        tempElec = Math.abs(current.getDouble("electricity") - recharge.getDouble("electricity"));
                    }


                    //JSONObject last = reading.getJSONObject(user.length());
                    currentElec.setText(String.format(Locale.US,"%.2f",current.getDouble("electricity")));
                    currentWater.setText(String.format(Locale.US,"%.2f",current.getDouble("water")));

                    waterUsage[0] = String.format(Locale.US,"%.2f",tempWater);
                    elecUsage[0] = String.format(Locale.US,"%.2f",tempElec);

                    addr.setText(address[0].toString());                                                //displaying usage data
                    water.setText(waterUsage[0].toString());
                    elec.setText(elecUsage[0].toString());
                    TotalW.setText(TotalUnitsW[0].toString());
                    TotalE.setText(TotalUnitsE[0].toString());
                    meterNumber.setText(meterNum.toString());



                    if (waterState[0] == 1){                                                            //Setting progress bars
                        WaterStatus.setText("ACTIVE");
                        progWater.setMax(maxWater);
                        progWater.setProgress((int) (maxWater-tempWater));

                    }
                    else{
                        WaterStatus.setText("INACTIVE");
                        progWater.setProgress(0);
                    }

                    if (elecState[0] == 1){
                        ElecStatus.setText("ACTIVE");
                        progElec.setMax(maxElec);
                        progElec.setProgress((int) (maxElec-tempElec));

                    }
                    else{
                        ElecStatus.setText("INACTIVE");
                        progElec.setProgress(0);
                    }

                    /*For progress bars:
                     * get total units for each
                     * work out percentage used
                     * set progress = percentage
                     *           OR
                     * set max = total units
                     * set progress = usage
                     *          ALSO
                     * Get water + elec status and
                     * change label text active/inactive*/



                } catch (JSONException e) {
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