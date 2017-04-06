package com.example.ylei.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
public class MainActivity extends AppCompatActivity {

    Button btnCalc;
    EditText txtA;
    EditText txtB;
    TextView txtResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCalc = (Button) findViewById(R.id.btnCalc);
        txtA = (EditText) findViewById(R.id.txtA);
        txtB = (EditText) findViewById(R.id.txtB);
        txtResult = (TextView) findViewById(R.id.txtResult);

        btnCalc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int a=Integer.parseInt(txtA.getText().toString());
                int b=Integer.parseInt(txtB.getText().toString());
                int c=a+b;
                txtResult.setText(String.valueOf(c));

            }
        });

    }
}
