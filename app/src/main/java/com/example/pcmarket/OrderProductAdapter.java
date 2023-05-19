package com.example.pcmarket;

import android.content.Context;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductViewHolder> {
    Context context;
    List<OrderItem> orderItems;

    public OrderProductAdapter(Context context, List<OrderItem> orderItems) {
        this.context = context;
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public OrderProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderProductViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductViewHolder holder, int position) {
        List<Item> product = getProduct(orderItems.get(position).getId_produktu());

        Picasso.get().load(product.get(0).getZdjecie()).into(holder.orderItemImage);
        holder.orderItemName.setText(product.get(0).getNazwa());
        holder.orderItemPrice.setText(product.get(0).getCena());
        holder.orderItemAmount.setText(orderItems.get(0).getIlosc());
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    private List<Item> getProduct(String productID) {
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        Connect connect = new Connect();

        String query = "SELECT * FROM psm_computer_store.produkty WHERE id_produktu="+productID+";";
        ResultSet result = connect.select(query, connect.getConnection());

        List<Item> product = new ArrayList<>();

        try {
            while (result.next()) {
                product.add(new Item(result.getString(1), result.getString(2), result.getString(3),
                        result.getString(4), result.getString(5), result.getString(6), result.getString(7)
                        , result.getString(8), result.getDate(9)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        connect.close();

        return product;
    }
}
