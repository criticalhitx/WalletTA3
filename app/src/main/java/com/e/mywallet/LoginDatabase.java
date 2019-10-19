package com.e.mywallet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LoginDatabase extends AppCompatActivity {
    EditText ET_NAME, ET_PASS;
    String login_name, login_pass;
    Button btnReg;
    TextView txTestResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_database);
        btnReg=(Button)findViewById(R.id.Login_Register);
        ET_NAME=(EditText)findViewById(R.id.Login_username);
        ET_PASS=(EditText)findViewById(R.id.Login_password);


        btnReg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginDatabase.this, Register.class));
            }
        });
    }


    public void userLogin(View view) {
     login_name = ET_NAME.getText().toString();
     login_pass = ET_PASS.getText().toString();
     String method = "login";
     BackgroundTask backgroundTask = new BackgroundTask(this);
     backgroundTask.execute(method,login_name,login_pass);
    }



}

