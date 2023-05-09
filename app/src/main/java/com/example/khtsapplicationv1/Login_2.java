package com.example.khtsapplicationv1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class Login_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        Button login = findViewById(R.id.btnLogin);
        EditText ID = findViewById(R.id.edtIDNumber);
        EditText pass = findViewById(R.id.edtPass);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String saID = ID.getText().toString();
                String password = pass.getText().toString();
                String hashPass = "";

                try {
                    hashPass = encryptStr(password);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(),hashPass,Toast.LENGTH_LONG).show();

                //Login code to retrieve hashed password --> server


            }
        });
    }

    public String encryptStr(String inputStr) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] messageDigest = md.digest(inputStr.getBytes());                   //Convert hashed input to a byte array

        BigInteger bigInt = new BigInteger(1,messageDigest);            //Convert byte array to Big Integer

        return bigInt.toString(16);
    }
}