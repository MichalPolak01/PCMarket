package com.example.pcmarket;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.sql.Connection;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        DBFunctions dataBase = new DBFunctions();
//        dataBase.dbConnection();
//        dataBase.dbCloseConnection();

        System.out.println("aaca");

        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        Button signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchToSignUp = new Intent(Login.this, SignUp.class);
//                switchToSignUp.putExtra("dataBase", dataBase);
                startActivity(switchToSignUp);
            }
        });
    }
}