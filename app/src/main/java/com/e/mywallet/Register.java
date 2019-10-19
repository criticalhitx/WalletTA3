package com.e.mywallet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    EditText etUsername, etPassword, etEmail,etFirstName,etLastName;
    TextView tv1,tv2,tv3,tv4,tv5;
    String firstname,lastname,user_name,user_pass,email;
    Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etUsername=(EditText)findViewById(R.id.registerUsername);
        etPassword=(EditText)findViewById(R.id.registerPassword);
        etEmail=(EditText)findViewById(R.id.registerEmail);
        etFirstName=(EditText)findViewById(R.id.registerFirstName);
        etLastName = (EditText)findViewById(R.id.registerLastName);
        btnReg = (Button)findViewById(R.id.registerBtnReg);

        tv1=(TextView)findViewById(R.id.registerText1);
        tv2=(TextView)findViewById(R.id.registerText2);
        tv3=(TextView)findViewById(R.id.registerText3);
        tv4=(TextView)findViewById(R.id.registerText4);
        tv5=(TextView)findViewById(R.id.registerText5);


       /* btnReg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tv1.setText(etUsername.getText().toString());
                tv1.setVisibility(View.VISIBLE);
                tv2.setText(etPassword.getText().toString());
                tv2.setVisibility(View.VISIBLE);
                tv3.setText(etEmail.getText().toString());
                tv3.setVisibility(View.VISIBLE);
                tv4.setText(etFirstName.getText().toString());
                tv4.setVisibility(View.VISIBLE);
                tv5.setText(etLastName.getText().toString());
                tv5.setVisibility(View.VISIBLE);

                firstname=etFirstName.getText().toString();
                user_name=etUsername.getText().toString();
                user_pass=etPassword.getText().toString();
                String method ="register";
                BackgroundTask backgroundTask =new BackgroundTask(this);
                backgroundTask.execute(method,firstname,user_name,user_pass);



            }
        });*/
    }



    // Di luar protected void
    public void userReg(View view)
    {
        firstname=etFirstName.getText().toString();
        lastname=etLastName.getText().toString();
        user_name=etUsername.getText().toString();
        user_pass=etPassword.getText().toString();
        email=etEmail.getText().toString();

        String method ="register";
        BackgroundTask backgroundTask =new BackgroundTask(this);
        backgroundTask.execute(method,firstname,user_name,user_pass,email,lastname);
        finish();
    }

}
