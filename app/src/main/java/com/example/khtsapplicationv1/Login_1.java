package com.example.khtsapplicationv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Login_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        final boolean[] stageOne = {false};

        Button button3 = findViewById(R.id.button3);
        EditText meterNumber = findViewById(R.id.edtMeterNumber);
        EditText saIDNumber = findViewById(R.id.edtIDNumber);
        LinearLayout pageOne = findViewById(R.id.registerLayout1);

        final String[] meterNum = new String[1];
        final String[] IDNumb = new String[1];
        final String[] name = new String[1];
        final String[] password = new String[1];

        meterNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meterNumber.setText("");
                meterNumber.setFocusable(true);
            }
        });

        saIDNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saIDNumber.setText("");
                saIDNumber.setFocusable(true);
            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent i = new Intent(getApplicationContext(),Home.class);
                //startActivity(i);
                if (!stageOne[0]){

                    meterNum[0] = meterNumber.getText().toString();
                    IDNumb[0] = saIDNumber.getText().toString();

                    meterNumber.setText("FULL NAME");
                    meterNumber.setFocusable(true);
                    saIDNumber.setText("PASSWORD");
                    button3.setText("PROCEED");
                    stageOne[0] = true;

                }
                else if (stageOne[0]){

                    name[0] = meterNumber.getText().toString();
                    password[0] = saIDNumber.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Home.class);
                    startActivity(i);

                }

            }
        });
    }
}