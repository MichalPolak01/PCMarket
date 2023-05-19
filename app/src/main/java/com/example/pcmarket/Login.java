package com.example.pcmarket;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    private static final String FILE_NAME = "userID.txt";
    private static final String FILE_QUERY = "query.txt";

    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkAndHandleNetworkConnectivity();

        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userValidation();
            }
        });

        Button signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchToSignUp = new Intent(Login.this, SignUp.class);
                startActivity(switchToSignUp);
            }
        });
        saveQuery();
    }

    private void userValidation() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        String valEmail = email.getText().toString();
        String valPassword = password.getText().toString();

        if(valEmail.isEmpty() || valPassword.isEmpty()) {
            if(valEmail.isEmpty()) { email.setError("Podaj e-mail!"); }
            if(valPassword.isEmpty()) { password.setError("Podaj hasło!"); }
            Toast.makeText(getApplicationContext(), "Wprowadź dane!", Toast.LENGTH_SHORT).show();
        } else {
            getUserData(valEmail, valPassword);
        }
    }

    private void getUserData(String valEmail, String valPassword) {
        try {
            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(threadPolicy);
            Connect connect = new Connect();

            String query = "SELECT * FROM psm_computer_store.osoby WHERE email='"+valEmail+"' AND haslo='"+valPassword+"'";

            ResultSet result = connect.select(query, connect.getConnection());

            List<User> users = new ArrayList<>();

            try {
                while (result.next()) {
                    users.add(new User(result.getString(1), result.getString(2), result.getString(3),
                            result.getString(4), result.getString(5), result.getString(6), result.getString(7)
                            , result.getString(8)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            connect.close();

            String role = users.get(0).getRola();

            if(users != null) {
                saveUserID(users.get(0).getId_osoby());
                Toast.makeText(getApplicationContext(), "Witaj "+users.get(0).getImie(), Toast.LENGTH_SHORT).show();

                Intent intent;
                if (role.equals("pracownik")){
                    intent = new Intent(Login.this, MainActivityEmployee.class);
                } else {
                   intent = new Intent(Login.this, MainActivity.class);
                }
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Niepoprawne dane!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Niepoprawne dane!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserID(String userID) {
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fileOutputStream.write(userID.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void saveQuery() {
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = openFileOutput(FILE_QUERY, MODE_PRIVATE);
            fileOutputStream.write("".getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Handler handler = new Handler();
    private boolean isRefreshing = false;
    private static final long REFRESH_INTERVAL = 30000;

    private void checkAndHandleNetworkConnectivity() {
        if (!isNetworkAvailable()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            Toast.makeText(getApplicationContext(), "Brak połączenia z internetem!", Toast.LENGTH_SHORT).show();

            if (!isRefreshing) {
                startRefreshing();
            }
        }
    }

    private void startRefreshing() {
        isRefreshing = true;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isNetworkAvailable()) {
                    refreshActivity();
                } else {
                    startRefreshing();
                }
            }
        }, REFRESH_INTERVAL);
    }

    private void refreshActivity() {
        finish();
        startActivity(getIntent());
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}