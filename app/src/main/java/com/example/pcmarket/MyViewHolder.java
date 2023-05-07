package com.example.pcmarket;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView productImage;
    TextView productName;
    TextView productMark;
    TextView productPrice;
    ImageButton addToBasket;


    public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
        super(itemView);

        productImage = itemView.findViewById(R.id.image);
        productName = itemView.findViewById(R.id.name);
        productMark = itemView.findViewById(R.id.mark);
        productPrice = itemView.findViewById(R.id.price);
        addToBasket = itemView.findViewById(R.id.shoppingBasket);
    }
}
