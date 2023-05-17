package com.example.pcmarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MainActivityEmployee extends AppCompatActivity {

    private static final String FILE_QUERY = "query.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_employee);

        showProducts();

        ImageButton AdminSettings = findViewById(R.id.admin);
        AdminSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityEmployee.this, AdminPanel.class);
                startActivity(intent);
            }
        });

        ImageButton filtration = findViewById(R.id.filter);
        filtration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityEmployee.this, FiltrationForEmployee.class);
                startActivity(intent);
            }
        });

        ImageButton CheckOrders = findViewById(R.id.orders);
        CheckOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
     //           Intent intent = new Intent(MainActivityEmployee.this,CheckOrders.class);
     //           startActivity(intent);
            }
        });

        ImageButton AddProduct = findViewById(R.id.add);

        AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityEmployee.this,AddProduct.class);
                startActivity(intent);
            }
        });
    }

    private void showProducts() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        Connect connect = new Connect();

        String where = loadSavedUserID();

        if(where == null) {
            where = "";
        }

        String query = "SELECT * FROM psm_computer_store.produkty "+where;

        ResultSet result = connect.select(query, connect.getConnection());

        List<Item> items = new ArrayList<>();

        try {
            while (result.next()) {
                items.add(new Item(result.getString(1), result.getString(2), result.getString(3),
                        result.getString(4), result.getString(5), result.getString(6), result.getString(7)
                        , result.getString(8), result.getDate(9)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        connect.close();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), items, new RecyclerViewInterface() {
            @Override
            public void onItemClick(Item details) {
                Intent intent = new Intent(MainActivityEmployee.this, ProductDetails.class);
                intent.putExtra("productID", details.getId_produktu());
                startActivity(intent);
            }
        }));
    }

    private String loadSavedUserID() {
        FileInputStream fileInputStream = null;

        String userID = "";
        try {
            fileInputStream = openFileInput(FILE_QUERY);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            userID = bufferedReader.readLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return userID;
        }
    }
}

