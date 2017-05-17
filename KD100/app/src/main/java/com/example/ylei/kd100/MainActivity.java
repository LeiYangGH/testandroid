package com.example.ylei.kd100;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
public class MainActivity extends AppCompatActivity {

    Button btnQuery;
    EditText txtNO;
    TextView txtMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnQuery = (Button) findViewById(R.id.btnQuery);
        txtNO = (EditText) findViewById(R.id.txtNO);
        txtMsg = (TextView) findViewById(R.id.txtMsg);

        btnQuery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               String no= txtNO.getText().toString();
                txtMsg.setText(no);
            }
        });

    }
}
