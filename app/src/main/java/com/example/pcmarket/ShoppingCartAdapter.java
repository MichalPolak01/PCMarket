package com.example.pcmarket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartViewHolder> {
    private static final String FILE_NAME = "userID.txt";

    Context context;
    List<Shopping_list> shoppingCarts;

    public ShoppingCartAdapter(Context context, List<Shopping_list> shoppingCarts) {
        this.context = context;
        this.shoppingCarts = shoppingCarts;
    }

    @NonNull
    @Override
    public ShoppingCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShoppingCartViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_shopping_cart, parent, false));
    }

    Integer amount;
    @Override
    public void onBindViewHolder(@NonNull ShoppingCartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        List<Item> product = getProduct(shoppingCarts.get(position).getId_produktu());

        Float price = Float.valueOf(product.get(0).getCena());
        amount = Integer.valueOf(shoppingCarts.get(position).getIlosc());

        Float totalProductPrice = price * Float.valueOf(amount);

        holder.productName.setText(product.get(0).getNazwa());
        Picasso.get().load(product.get(0).getZdjecie()).into(holder.productImage);
        holder.productPrice.setText(totalProductPrice.toString()+" zł");
        holder.productAmount.setText(amount.toString());

        holder.addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(product.get(0).getIlosc()) >= amount + 1) {
                    amount += 1;
                    updateShoppingList(shoppingCarts.get(position).getId_produktu_w_koszyku(), amount.toString());
                    Toast.makeText(context.getApplicationContext(), "Dodano do koszyka "+ product.get(0).getNazwa(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ShoppingCart.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context.getApplicationContext(), "Dodawanie do koszyka nie powiodło się", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.subProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount -= 1;
                if (amount == 0) {
                    deleteFromShoppingList(shoppingCarts.get(position).getId_produktu_w_koszyku());
                    Toast.makeText(context.getApplicationContext(), "Usunięto z koszyka "+ product.get(0).getNazwa(), Toast.LENGTH_SHORT).show();
                } else {
                    updateShoppingList(shoppingCarts.get(position).getId_produktu_w_koszyku(), amount.toString());
                    Toast.makeText(context.getApplicationContext(), "Usunięto jedną sztukę "+ product.get(0).getNazwa(), Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(context, ShoppingCart.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppingCarts.size();
    }

    private List<Item> getProduct(String productID) {
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        Connect connect = new Connect();

        String query = "SELECT * FROM psm_computer_store.produkty WHERE id_produktu="+productID+";";
        ResultSet result = connect.select(query, connect.getConnection());

        List<Item> product = new ArrayList<>();

        try {
            while (result.next()) {
                product.add(new Item(result.getString(1), result.getString(2), result.getString(3),
                        result.getString(4), result.getString(5), result.getString(6), result.getString(7)
                        , result.getString(8), result.getDate(9)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        connect.close();

        return product;
    }

    private void updateShoppingList(String productID, String amount) {
        String userID = loadSavedUserID();

        String query = "UPDATE psm_computer_store.lista_zakupow SET ilosc='"+amount+"' WHERE id_produktu_w_koszyku="+productID+" AND id_osoby="+userID;

        update(query);
    }

    private void deleteFromShoppingList(String productID) {
        String userID = loadSavedUserID();

        String query = "DELETE FROM psm_computer_store.lista_zakupow WHERE id_produktu_w_koszyku="+productID+" AND id_osoby="+userID;

        update(query);
    }

    private void update(String query) {
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        Connect connect = new Connect();

        connect.update(query, connect.getConnection());

        connect.close();
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
}
