package com.example.pcmarket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private static final String FILE_NAME = "userID.txt";
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
        holder.productPrice.setText(items.get(position).getCena()+" zł");

        holder.itemView.setOnClickListener(view -> {
            recyclerViewInterface.onItemClick(items.get(position));
        });

        holder.addToBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = loadSavedUserID();
                String productID = items.get(position).getId_produktu();

                String query = "INSERT INTO psm_computer_store.lista_zakupow(id_osoby, id_produktu, ilosc)" +
                        " VALUES("+userID+", "+productID+", 1)";
                addToBasket(query);

                Toast.makeText(context.getApplicationContext(), "Dodano do koszyka "+ items.get(position).getNazwa(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private String loadSavedUserID() {
        FileInputStream fileInputStream = null;

        String userID = "";
        try {
            fileInputStream = context.openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            userID = bufferedReader.readLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return userID;
        }
    }

    private void addToBasket(String query) {
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        Connect connect = new Connect();

        connect.insert(query, connect.getConnection());

        connect.close();
    }
}
