package com.example.pcmarket;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShoppingCartViewHolder extends RecyclerView.ViewHolder{
    ImageView productImage;
    TextView productName;
    TextView productPrice;
    TextView productAmount;
    ImageButton addProduct;
    ImageButton subProduct;


    public ShoppingCartViewHolder(@NonNull View itemView) {
        super(itemView);

        productImage = itemView.findViewById(R.id.image);
        productName = itemView.findViewById(R.id.name);
        productPrice = itemView.findViewById(R.id.price);
        productAmount = itemView.findViewById(R.id.amount);
        addProduct = itemView.findViewById(R.id.plus);
        subProduct = itemView.findViewById(R.id.minus);
    }
}
