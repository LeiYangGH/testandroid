package com.example.ylei.kd100;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.content.SharedPreferences;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    Button btnQuery;
    EditText txtNO;
    EditText txtType;
    TextView txtMsg;
String msg="";
    String no="";
    String typ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnQuery = (Button) findViewById(R.id.btnQuery);
        txtNO = (EditText) findViewById(R.id.txtNO);
        txtType = (EditText) findViewById(R.id.txtType);
        txtMsg = (TextView) findViewById(R.id.txtMsg);

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
             no = prefs.getString("no", "112233");
            typ = prefs.getString("typ", "jd");
        txtNO.setText(no);
        txtType.setText(typ);
        btnQuery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnQuery.setEnabled(false);
                  no= txtNO.getText().toString();
                typ= txtType.getText().toString();

                SharedPreferences sharedPref =  getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("no", no);
                editor.putString("typ", typ);
                editor.commit();

                String webcontent= null;
                try {
                    KdData data =new KdData(no,typ);
                    webcontent = (new CallAPI()).execute(data,null,null).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            //    webcontent+="done in main";
                txtMsg.setText(webcontent);
                btnQuery.setEnabled(true);
                //txtNO.setText(msg);
            }
        });

    }


}
