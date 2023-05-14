package com.example.khtsapplicationv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Home extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private  String api_resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getData("http://sparkhts.zapto.org/khtsweb/khts-serv/public/index.php/customers/refresh/1");
        /*URL needs to be created depending on user!!!*/
        Button waterRecharge = findViewById(R.id.btnWaterTopUp);
        Button mainRecharge = findViewById(R.id.btnRecharge);

        waterRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), WaterStats.class);
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

        TextView addr = findViewById(R.id.txtAddress);
        TextView water = findViewById(R.id.lblWaterUsage);
        TextView elec = findViewById(R.id.lblElecUsage);

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
                    JSONObject reading = user.getJSONObject("latestreading");

                    address[0] = customer.getString("address");                                    //getting variables
                    waterUsage[0] = reading.getString("water");
                    elecUsage[0] = reading.getString("electricity");


                    addr.setText(address[0].toString());                                                //displaying usage data
                    water.setText(waterUsage[0].toString());
                    elec.setText(elecUsage[0].toString());

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