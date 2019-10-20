package com.e.mywallet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;
import android.os.Handler;
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

public class Check extends Activity {
    Button btCek;
    TextView tvResult,tvResultDouble,tvTulisanBesar;
    String firstOcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        btCek=(Button)findViewById(R.id.Check_Submit);
        tvResult=(TextView)findViewById(R.id.Check_key_tv);
        tvResultDouble=(TextView)findViewById(R.id.Check_increment_tv);
        tvTulisanBesar=(TextView)findViewById(R.id.Check_TulisanBesar);
    }



    public void CheckButton(View view) {
        String user_name = "aiharakotoko"; // !!!!Query LAH DARI ARDUINO
        String method = "balance";
        new MyTask(this).execute(method,user_name); //Jalankan AsyncTaskPertama
        // Harus ada Delay dengan handler , kalau tidak , belum keupdate nya.

        /*Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                firstOcc=tvResult.getText().toString();
                float f=Float.parseFloat(firstOcc)*2;
                String fromfloat=""+f;
                tvResultDouble.setText(fromfloat);
            }
        }, 2000);*/

        firstOcc=tvResult.getText().toString();
      //  tvTulisanBesar.setText(firstOcc);

        //float f=Float.parseFloat(result);
        //String fromfloat=""+f;
        //Toast.makeText(ctx,fromfloat, Toast.LENGTH_SHORT).show();

    }

    private void onBackgroundTaskDataObtained(String result) {
        //do stuff with the results here..
        tvResult.setText(result);
    }
    private class MyTask extends AsyncTask<String,Void,String>
    {
        private View rootView;
        Context ctx;
        MyTask(Context ctx)
        {
            this.ctx=ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String balance_url ="http://3.13.196.24/TA/balance.php";
            String method = params[0];
            if(method.equals("balance"))
            {
                String user_name= params[1];

                try {
                    URL url =new URL(balance_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    /// This is to get response from Server
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
            //float f=Float.parseFloat(result);
            //String fromfloat=""+f;
            //Toast.makeText(ctx,fromfloat, Toast.LENGTH_SHORT).show();
            Check.this.onBackgroundTaskDataObtained(result);
        }
    } // Akhir dari AsyncTask
}
