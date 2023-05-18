package com.example.pcmarket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainEmployeeAdapter extends RecyclerView.Adapter<MainEmployeeViewHolder> {

    Context context;
    List<Item> items;

    public MainEmployeeAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MainEmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainEmployeeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_main_employee, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainEmployeeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(items.get(position).getZdjecie()).into(holder.productImage);
        holder.productName.setText(items.get(position).getNazwa());
        holder.productMark.setText(items.get(position).getMarka());
        holder.productPrice.setText(items.get(position).getCena());
        holder.productAmount.setText(items.get(position).getIlosc());

        holder.productAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer amount = Integer.valueOf(items.get(position).getIlosc());
                amount += 1;
                updateProductsList(items.get(position).getId_produktu(), amount.toString());
                Toast.makeText(context.getApplicationContext(), "Dodano jedną sztukę "+ items.get(position).getNazwa(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivityEmployee.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.productSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer amount = Integer.valueOf(items.get(position).getIlosc());
                amount -= 1;
                if (amount == 0) {
                    deleteFromProductsList(items.get(position).getId_produktu());
                    Toast.makeText(context.getApplicationContext(), "Usunięto produkt "+ items.get(position).getNazwa(), Toast.LENGTH_SHORT).show();
                } else {
                    updateProductsList(items.get(position).getId_produktu(), amount.toString());
                    Toast.makeText(context.getApplicationContext(), "Usunięto jedną sztukę "+ items.get(position).getNazwa(), Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(context, MainActivityEmployee.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.productDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFromProductsList(items.get(position).getId_produktu());
                Toast.makeText(context.getApplicationContext(), "Usunięto produkt "+ items.get(position).getNazwa(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivityEmployee.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void updateProductsList(String productID, String amount) {

        String query = "UPDATE psm_computer_store.produkty SET ilosc='"+amount+"' WHERE id_produktu="+productID;

        update(query);
    }

    private void deleteFromProductsList(String productID) {

        String query = "DELETE FROM psm_computer_store.produkty WHERE id_produktu="+productID;

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
