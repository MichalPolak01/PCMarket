package com.example.pcmarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CheckOrders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_orders);

        showOrders();

        SwipeRefreshLayout pullToRefresh = findViewById(R.id.swipeRefreshLayout);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefresh.setRefreshing(false);
                showOrders();
            }
        });


        ImageButton CheckOrders = findViewById(R.id.main);
        CheckOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckOrders.this, MainActivityEmployee.class);
                startActivity(intent);
            }
        });
        ImageButton AdminSettings = findViewById(R.id.admin);
        AdminSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckOrders.this, AdminPanel.class);
                startActivity(intent);
            }
        });

        ImageButton filtration = findViewById(R.id.filter);
        filtration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckOrders.this, FiltrationForEmployee.class);
                startActivity(intent);
            }
        });

        ImageButton AddProduct = findViewById(R.id.add);
        AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckOrders.this,AddProduct.class);
                startActivity(intent);
            }
        });
    }

    private void showOrders() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        Connect connect = new Connect();

        String query = "SELECT * FROM psm_computer_store.zamowienia WHERE status_zamowienia like 'kompletowanie'";

        ResultSet result = connect.select(query, connect.getConnection());
        List<Order> orders = new ArrayList<>();

        try {
            while (result.next()) {
                orders.add(new Order(result.getString(1), result.getString(2), result.getString(3),
                        result.getString(4)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        connect.close();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new OrderAdapter(getApplicationContext(), orders));
    }
}