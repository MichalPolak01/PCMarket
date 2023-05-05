package com.example.pcmarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(items.get(position).getZdjecie()).into(holder.productImage);
        holder.productName.setText(items.get(position).getNazwa());
        holder.productMark.setText(items.get(position).getMarka());
        holder.productPrice.setText(items.get(position).getCena());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
