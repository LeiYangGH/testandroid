package com.example.ylei.kd100;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by YLei on 2017-05-17.
 */

public class CallAPI extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... params) {
        HashMap<String, String> ps=new HashMap<String, String>();
                //*
                ps.put("type", "jd");
                ps.put("postid", "50460919160");
                ps.put("id", "1");
                //*/
        String webcontent=performPostCall("http://www.kuaidi100.com/query",ps);
        // String webcontent=performPostCall("http://www.baidu.com/",null);

        //String webcontent=performPostCall("http://www.baidu.com/",ps);
        //String webcontent=performPostCall("http://www.sina.com.cn/",ps);
        return webcontent;
    }

    private static URLConnection reload(URLConnection uc) throws Exception {

        HttpURLConnection huc = (HttpURLConnection) uc;

        if (huc.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP
                || huc.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM)// 302, 301
            return reload(new URL(huc.getHeaderField("location")).openConnection());

        return uc;
    }

    public String  performPostCall(String requestURL,
                                   HashMap<String, String> postDataParams) {

        URL url;

        String response = "";
        response+="try";
        try {
            url = new URL(requestURL);
            //response+=requestURL;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            //conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

///*
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            //*/
            conn = (HttpURLConnection)reload(conn);////////

            int responseCode=conn.getResponseCode();

            response+="responseCode="+String.valueOf(responseCode)+"=";
            //if (responseCode == HttpsURLConnection.HTTP_OK) {
                if (true) {
                response+="responseCode";
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response+="!responseCode";

            }
        } catch (Exception e) {
            e.printStackTrace();
            response+=e.getMessage();
            response+=e.toString();
        }

        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
