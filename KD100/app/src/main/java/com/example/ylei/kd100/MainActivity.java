package com.example.ylei.kd100;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.content.SharedPreferences;
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

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String no = prefs.getString("no", "112233");
        txtMsg.setText(no);

        btnQuery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               String no= txtNO.getText().toString();
                txtMsg.setText(no);

                SharedPreferences sharedPref =  getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("no", no);
                editor.commit();


            }
        });

    }
}
