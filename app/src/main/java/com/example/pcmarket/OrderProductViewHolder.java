package com.example.pcmarket;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderProductViewHolder extends RecyclerView.ViewHolder {
    ImageView orderItemImage;
    TextView orderItemName;
    TextView orderItemAmount;
    TextView orderItemPrice;

    public OrderProductViewHolder(@NonNull View itemView) {
        super(itemView);

        orderItemImage = itemView.findViewById(R.id.image);
        orderItemName = itemView.findViewById(R.id.name);
        orderItemAmount = itemView.findViewById(R.id.amount);
        orderItemPrice = itemView.findViewById(R.id.price);
    }
}
