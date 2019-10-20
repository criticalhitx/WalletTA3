package com.e.mywallet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Send extends Activity {
    EditText stealthaddress, amount;
    String user_name="aiharakotoko"; // Parameter ini DIKIRIMKAN OLEH
    TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        stealthaddress = (EditText)findViewById(R.id.Send_et_key);
        amount = (EditText)findViewById(R.id.Send_et_amount);
        tvTest = (TextView)findViewById(R.id.SendTesto);
    }

    public void Send_buttonsubmit(View view) { //Dibuat UI off agar tidak bisa pencet sebelum dapet usernmae.
        String method = "send";
        String stealth_address = stealthaddress.getText().toString();
        String transfer = amount.getText().toString();
        tvTest.setText(stealth_address);

        new Send.MyTask(this).execute(method,user_name,stealth_address,transfer);
    }


    private class MyTask extends AsyncTask<String,Void,String>
    {
        AlertDialog alertDialog;
        Context ctx;
        MyTask(Context ctx)
        {
            this.ctx=ctx;
        }

        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(ctx).create();
            alertDialog.setTitle("Sending....");
        }

        @Override
        protected String doInBackground(String... params) {
            String send_url ="http://3.13.196.24/TA/send.php";
            String method = params[0];
            if(method.equals("send"))
            {
                user_name=params[1];
                String stealth_key=params[2];
                String transfer =params[3];

                try {
                    URL url =new URL(send_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true); //Pass some Info to SQL
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String data = URLEncoder.encode("user_name","UTF-8") +"="+URLEncoder.encode(user_name,"UTF-8")+"&"+
                                  URLEncoder.encode("stealth_key","UTF-8") +"="+URLEncoder.encode(stealth_key,"UTF-8")+"&"+
                                  URLEncoder.encode("transfer","UTF-8") +"="+URLEncoder.encode(transfer,"UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    //This Code to Get the response
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    String response = "";
                    String line = "";
                    while((line = bufferedReader.readLine())!=null)
                    {
                        response+= line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return response;



                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }
}
