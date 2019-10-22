//---- Pada Menu ini, user mengganti username dan passwordnya apabila private key didapatkan

package com.e.mywallet;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.physicaloid.lib.Physicaloid;
import com.physicaloid.lib.usb.driver.uart.ReadLisener;

public class RecoverSK extends AppCompatActivity {
    ImageView imagebackpin;
    EditText etUsername,etPassword, etInsertedSK; // Input parameter from user
    TextView tvTest; // response
    Button btVerify,btPutWallet, btOpen; // btVerify untuk cek ke server, PutWallet untuk taruh wallet, open untuk conect
//---------------

    boolean canexit = false;
    Spinner spBauds;
    CheckBox cbAutoscrolls;
    Physicaloid mPhysicaloid; // initialising library


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_sk);

        etUsername=(EditText)findViewById(R.id.RecoverSK_newUsername);
        etPassword=(EditText)findViewById(R.id.RecoverSK_newPassword);
        etInsertedSK=(EditText)findViewById(R.id.RecoverSK_nilaisk);
        //---------------------------------------------
        imagebackpin=(ImageView)findViewById(R.id.RecoverSK_image);
        tvTest=(TextView)findViewById(R.id.RecoverSK_testResponse);//response
        btOpen =(Button)findViewById(R.id.RecoverSK_connect); //Tombol Connect
        spBauds = (Spinner) findViewById(R.id.RecoverSK_spinners);
        cbAutoscrolls = (CheckBox)findViewById(R.id.RecoverSK_autoscrolls);
        btPutWallet = (Button)findViewById(R.id.RecoverSK_btPut); // Send button
        //-------------------------------------
        setEnabledUi(false);
        ////////////////////////////////
        mPhysicaloid = new Physicaloid(this);

        btOpen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String baudtext = spBauds.getSelectedItem().toString();
                switch (baudtext) {
                    case "300 baud":
                        mPhysicaloid.setBaudrate(300);
                        break;
                    case "1200 baud":
                        mPhysicaloid.setBaudrate(1200);
                        break;
                    case "2400 baud":
                        mPhysicaloid.setBaudrate(2400);
                        break;
                    case "4800 baud":
                        mPhysicaloid.setBaudrate(4800);
                        break;
                    case "9600 baud":
                        mPhysicaloid.setBaudrate(9600);
                        break;
                    case "19200 baud":
                        mPhysicaloid.setBaudrate(19200);
                        break;
                    case "38400 baud":
                        mPhysicaloid.setBaudrate(38400);
                        break;
                    case "576600 baud":
                        mPhysicaloid.setBaudrate(576600);
                        break;
                    case "744880 baud":
                        mPhysicaloid.setBaudrate(744880);
                        break;
                    case "115200 baud":
                        mPhysicaloid.setBaudrate(115200);
                        break;
                    case "230400 baud":
                        mPhysicaloid.setBaudrate(230400);
                        break;
                    case "250000 baud":
                        mPhysicaloid.setBaudrate(250000);
                        break;
                    default:
                        mPhysicaloid.setBaudrate(9600);
                }

                if(mPhysicaloid.open()) {

                    setEnabledUi(true);
                    String kirim = "9"; //Mengirim case 9 ke while loop [ Mode Send ]
                    if(kirim.length()>0) {
                        byte[] buf = kirim.getBytes();
                        mPhysicaloid.write(buf, buf.length);
                    }

                    if(cbAutoscrolls.isChecked())
                    {
                        tvTest.setMovementMethod(new ScrollingMovementMethod());
                    }
                    mPhysicaloid.addReadListener(new ReadLisener() {
                        @Override
                        public void onRead(int size) {
                            byte[] buf = new byte[size];
                            mPhysicaloid.read(buf, size);
                            tvAppend(tvTest, Html.fromHtml( new String(buf) ));
                        }
                    });

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),"Not Connect",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        imagebackpin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String kirim = "moemoe";
                if(kirim.length()>0) {
                    byte[] buf = kirim.getBytes();
                    mPhysicaloid.write(buf, buf.length);}


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        canexit=true;
                        mPhysicaloid.close();
                        onBackPressed();
                    }
                }, 1500);

            }
        });
    }

    public void onBackPressed(){
        if (canexit) {
            super.onBackPressed();

        }
    }

    private void setEnabledUi(boolean on) {
        if(on) {
            btOpen.setEnabled(false); // Connect
            btPutWallet.setEnabled(true); // Put to Wallet PButton
            etInsertedSK.setEnabled(true);
            etUsername.setEnabled(true);
            etPassword.setEnabled(true);
            spBauds.setEnabled(false);
            cbAutoscrolls.setEnabled(false);


        } else {
            btOpen.setEnabled(true);
            btPutWallet.setEnabled(false);
            etInsertedSK.setEnabled(false);
            etUsername.setEnabled(false);
            etPassword.setEnabled(false);
            spBauds.setEnabled(true);
            cbAutoscrolls.setEnabled(true);

        }
    }




    Handler mHandler = new Handler();
    private void tvAppend(TextView tv, CharSequence text) {
        final TextView ftv = tv;
        final CharSequence ftext = text;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ftv.append(ftext);
            }
        });
    }
}
