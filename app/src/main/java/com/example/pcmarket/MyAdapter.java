package com.example.pcmarket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private RecyclerViewInterface recyclerViewInterface;

    Context context;
    List<Item> items;

    public MyAdapter(Context context, List<Item> items, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.items = items;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false), recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(items.get(position).getZdjecie()).into(holder.productImage);
        holder.productName.setText(items.get(position).getNazwa());
        holder.productMark.setText(items.get(position).getMarka());
        holder.productPrice.setText(items.get(position).getCena());

        holder.itemView.setOnClickListener(view -> {
            recyclerViewInterface.onItemClick(items.get(position));
        });

        holder.addToBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(), "Dodano! "+ position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
