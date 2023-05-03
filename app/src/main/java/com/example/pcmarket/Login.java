package com.example.pcmarket;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView email = findViewById(R.id.email);
        TextView password = findViewById(R.id.password);

        Button login = findViewById(R.id.login);
        Button signUp = findViewById(R.id.signUp);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valEmail = email.getText().toString();
                String valPassword = password.getText().toString();

                if(valEmail.isEmpty() || valPassword.isEmpty()) {
                    if(valEmail.isEmpty()) { email.setError("Podaj e-mail!"); }
                    if(valPassword.isEmpty()) { password.setError("Podaj hasło!"); }
                    Toast.makeText(getApplicationContext(), "Wprowadź dane!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(threadPolicy);
                        Connect connect = new Connect();

                        String selectUser = "SELECT id_osoby, imie FROM psm_computer_store.osoby WHERE email='"+valEmail+"' AND haslo='"+valPassword+"'";
                        ResultSet userId = connect.select(selectUser, connect.getConnection());
                        String valUserId = "";
                        String valImie = "";
                        while (userId.next()) {
                            valUserId = userId.getString(1);
                            valImie = userId.getString(2);
                        }
                        if(valUserId != "") {
                            Toast.makeText(getApplicationContext(), "Witaj "+valImie, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.putExtra("userId", valUserId);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Niepoprawne dane!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Coś poszło nie tak jak powinno!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchToSignUp = new Intent(Login.this, SignUp.class);
                startActivity(switchToSignUp);
            }
        });
    }
}