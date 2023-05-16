package com.example.khtsapplicationv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class recharge extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);


        EditText rechargeCode = findViewById(R.id.editTopup);
        Button process = findViewById(R.id.btnValidateCode);
        TextView type = findViewById(R.id.lblType);
        TextView cost = findViewById(R.id.lblUnitCost);
        TextView Total = findViewById(R.id.lblTotalCost);
        TextView date = findViewById(R.id.lblPurchaseDate);
        Button confirm = findViewById(R.id.btnRedeem);
        Button cancel = findViewById(R.id.btnCancel);


        process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String topup = rechargeCode.getText().toString();

                if (topup.equals("2989386737819828392")){

                    type.setText("TYPE: WATER");
                    cost.setText("per Unit: 182.37 c/kWh");
                    Total.setText("R1094.22");
                    date.setText("2023-05-16");
                }
                else{
                    Toast.makeText(getApplicationContext(),"Invalid Recharge, Please check that you have entered the token correctly", Toast.LENGTH_LONG).show();
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Unit Recharged", Toast.LENGTH_LONG).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Recharge Cancelled", Toast.LENGTH_LONG).show();
                finish();
            }
        });






    }
}