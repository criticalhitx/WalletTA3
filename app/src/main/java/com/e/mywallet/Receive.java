package com.e.mywallet;

import com.physicaloid.lib.Physicaloid;
import com.physicaloid.lib.usb.driver.uart.ReadLisener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Receive extends Activity {
    Button btOpen,  btWrite;
    EditText etWrite;
    TextView tvRead;
    Spinner spBaud;
    CheckBox cbAutoscroll;

    Physicaloid mPhysicaloid; // initialising library

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        btOpen  = (Button) findViewById(R.id.btOpen);
        btWrite = (Button) findViewById(R.id.btWrite);
        etWrite = (EditText) findViewById(R.id.etWrite);
        tvRead  = (TextView) findViewById(R.id.tvRead);
        spBaud = (Spinner) findViewById(R.id.spinner);
        cbAutoscroll = (CheckBox)findViewById(R.id.autoscroll);
        setEnabledUi(false);
        mPhysicaloid = new Physicaloid(this);

    }

    public void onClickOpen(View v) {
        String baudtext = spBaud.getSelectedItem().toString();
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

            if(cbAutoscroll.isChecked())
            {
                tvRead.setMovementMethod(new ScrollingMovementMethod());
            }
            mPhysicaloid.addReadListener(new ReadLisener() {
                @Override
                public void onRead(int size) {
                    byte[] buf = new byte[size];
                    mPhysicaloid.read(buf, size);
                    tvAppend(tvRead, Html.fromHtml( new String(buf) ));
                }
            });
        } else {
            Toast.makeText(this, "Please Check Your Connection", Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed(){
        super.onBackPressed();
        if(mPhysicaloid.close()) {
            mPhysicaloid.clearReadListener();
            setEnabledUi(false);
        }
    }
    public void onClickWrite(View v) {
        tvRead.setText(null);
        String str = getText(R.string.generate).toString()+"\r\n";
        if(str.length()>0) {
            byte[] buf = str.getBytes();
            mPhysicaloid.write(buf, buf.length);
        }
    }

    private void setEnabledUi(boolean on) {
        if(on) {
            btOpen.setEnabled(false);
            spBaud.setEnabled(false);
            cbAutoscroll.setEnabled(false);
            btWrite.setEnabled(true);
        } else {
            btOpen.setEnabled(true);
            spBaud.setEnabled(true);
            cbAutoscroll.setEnabled(true);
            btWrite.setEnabled(false);
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
