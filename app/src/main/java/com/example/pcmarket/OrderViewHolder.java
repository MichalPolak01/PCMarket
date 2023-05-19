package com.example.pcmarket;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    TextView orderPrice;
    Button orderSent;
    RecyclerView recyclerView;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        orderPrice = itemView.findViewById(R.id.totalPrice);
        orderSent = itemView.findViewById(R.id.orderSent);
        recyclerView = itemView.findViewById(R.id.recyclerview);
    }
}
