package com.example.pcmarket;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainEmployeeViewHolder extends RecyclerView.ViewHolder {

    ImageView productImage;
    TextView productName;
    TextView productMark;
    TextView productPrice;
    TextView productAmount;
    Button productDelete;
    ImageButton productAdd;
    ImageButton productSub;

    public MainEmployeeViewHolder(@NonNull View itemView) {
        super(itemView);

        productImage = itemView.findViewById(R.id.image);
        productName = itemView.findViewById(R.id.name);
        productMark = itemView.findViewById(R.id.mark);
        productPrice = itemView.findViewById(R.id.price);
        productAmount = itemView.findViewById(R.id.amount);
        productDelete = itemView.findViewById(R.id.delete);
        productAdd = itemView.findViewById(R.id.plus);
        productSub = itemView.findViewById(R.id.minus);
    }
}
