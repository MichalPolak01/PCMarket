package com.example.pcmarket;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    Context context;
    List<Order> orders;

    public OrderAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.orderPrice.setText("Razem:  "+ orders.get(0).getKwota() +" zł");

        List<OrderItem> orderItems = getOrderProduct(orders.get(0).getId_zamowienia());

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(new OrderProductAdapter(context.getApplicationContext(), orderItems));

        holder.orderSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentOrder(orders.get(0).getId_zamowienia());
                Intent intent = new Intent(context, CheckOrders.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                Toast.makeText(context.getApplicationContext(), "Potwierdziłeś wysłanie zamowienia "+orders.get(0).getId_zamowienia(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    private List<OrderItem> getOrderProduct(String orderID) {

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        Connect connect = new Connect();

        String query = "SELECT * FROM psm_computer_store.zamowione_produkty WHERE id_zamowienia like '"+orderID+"'";

        ResultSet result = connect.select(query, connect.getConnection());
        List<OrderItem> orderItems = new ArrayList<>();

        try {
            while (result.next()) {
                orderItems.add(new OrderItem(result.getString(1), result.getString(2), result.getString(3),
                        result.getString(4)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        connect.close();

        return orderItems;
    }

    private void sentOrder(String orderID) {
        String query = "UPDATE psm_computer_store.zamowienia SET status_zamowienia = 'wysłane' WHERE id_zamowienia ='"+orderID+"';";

        update(query);
    }

    private void update(String query) {
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        Connect connect = new Connect();

        connect.update(query, connect.getConnection());

        connect.close();
    }
}
