package com.example.labfinals;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class Login extends Activity {
    Button loginBtn;
    EditText username , password;
    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.Username);
        password = (EditText) findViewById(R.id.Password);
        dbHandler = new DBHandler(Login.this);

        loginBtn = (Button) findViewById(R.id.loginButton);

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(dbHandler.validate(username.getText().toString() , password.getText().toString())){
                        Intent intent = new Intent(Login.this,HomePage.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Login.this,"Incorrect Username or Password!!",Toast.LENGTH_LONG).show();
                    }

//                    Intent intent = new Intent(Login.this,HomePage.class);
//                    startActivity(intent);
                }
            });
    }
}