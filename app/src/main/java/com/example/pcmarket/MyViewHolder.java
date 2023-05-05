package com.example.pcmarket;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView productImage;
    TextView productName;
    TextView productMark;
    TextView productPrice;


    public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
        super(itemView);

        productImage = itemView.findViewById(R.id.image);
        productName = itemView.findViewById(R.id.name);
        productMark = itemView.findViewById(R.id.mark);
        productPrice = itemView.findViewById(R.id.price);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerViewInterface != null) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(position);
                    }
                }
            }
        });

    }
}
