package com.example.khtsapplicationv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class Login_2 extends AppCompatActivity {


    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String api_resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        Button login = findViewById(R.id.btnLogin);
        EditText ID = findViewById(R.id.edtIDNum);
        EditText pass = findViewById(R.id.edtPass);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String saID = ID.getText().toString();
                Intent i = new Intent(getApplicationContext(), Home.class);
                i.putExtra("userId",saID);
                startActivity(i);

                /*String saID = ID.getText().toString();
                String password = pass.getText().toString();
                String hashPass = null;

                try {
                    hashPass = encryptStr(password);


                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }

                Toast.makeText(getApplicationContext(),hashPass.toString(),Toast.LENGTH_LONG).show();

                //Login code to retrieve hashed password --> server

                String url = "http://sparkhts.zapto.org/khtsweb/khts-serv/public/index.php/auth/login/" + saID.toString();
                Log.d("url", url.toString());


                String returned_str = getData(url);

                Log.d("received", returned_str);
                /*try {
                    JSONObject data = new JSONObject(returned_str);

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

                //Toast.makeText(getApplicationContext(), returned_pass,Toast.LENGTH_LONG).show();

                    /*if (returned_pass.equals(hashPass)){
                        Intent i = new Intent(getApplicationContext(), Home.class);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"INVALID PASSWORD!",Toast.LENGTH_LONG).show();
                    }*/

                //Log.d("returned_password", .toString());*/


            }
        });
    }

    public String encryptStr(String inputStr) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] messageDigest = md.digest(inputStr.getBytes());                   //Convert hashed input to a byte array

        BigInteger bigInt = new BigInteger(1,messageDigest);            //Convert byte array to Big Integer

        return bigInt.toString(16);
    }

    private synchronized String getData(String urlIN){

        //initialize RequestQueue
        mRequestQueue = Volley.newRequestQueue(this);
        Log.d("volley", mRequestQueue.toString());

        //Initialize Request String
        mStringRequest = new StringRequest(Request.Method.GET, urlIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("response", response);
                api_resp = response.toString();
                //Toast.makeText(getApplicationContext(), "Response: " + response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error: " + error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        mRequestQueue.add(mStringRequest);
        return api_resp;

    }

    /*private String getPass(String url, String cuts_ID){
        try {
            JSONObject jsonParams;
            jsonParams = new JSONObject()
            .put("saID", cuts_ID);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        api_resp = response.getString("password");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "error: " + e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "error: " + error.toString(),Toast.LENGTH_LONG).show();
                }
            });

            Volley.newRequestQueue(getApplicationContext()).add(request);
        }
        catch (JSONException e){
            Toast.makeText(getApplicationContext(), "error: " + e.toString(),Toast.LENGTH_LONG).show();
        }

        return api_resp;
    }*/
}