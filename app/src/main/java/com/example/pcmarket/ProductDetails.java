package com.example.pcmarket;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDetails extends AppCompatActivity {
    private static final String FILE_NAME = "userID.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        ImageView productImage = findViewById(R.id.image);
        TextView productName = findViewById(R.id.name);
        TextView productMark = findViewById(R.id.mark);
        TextView productPrice = findViewById(R.id.price);
        TextView productDescription = findViewById(R.id.description);
        ImageButton basket = findViewById(R.id.basket);
        ImageButton back = findViewById(R.id.back);
        ImageButton shoppingCart = findViewById(R.id.shoppingCart);

        Bundle extras = getIntent().getExtras();
        String productID = extras.getString("productID");

        List<Item> product = showProduct(productID);

        productName.setText(product.get(0).getNazwa());
        Picasso.get().load(product.get(0).getZdjecie()).into(productImage);
        productMark.setText(product.get(0).getMarka());
        productPrice.setText(product.get(0).getCena()+" zł");
        productDescription.setText(product.get(0).getOpis());

        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do zrobienia!
                String userID = loadSavedUserID();

                String query = "INSERT INTO psm_computer_store.lista_zakupow(id_osoby, id_produktu, ilosc)" +
                        " VALUES("+userID+", "+productID+", 1)";
                addToBasket(query);

                Toast.makeText(getApplicationContext(), "Dodano do koszyka "+ product.get(0).getNazwa(), Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetails.this, MainActivity.class);
                startActivity(intent);
            }
        });

        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetails.this, ShoppingCart.class);
                startActivity(intent);
            }
        });
    }

    private List<Item> showProduct(String productID) {
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

    private String loadSavedUserID() {
        FileInputStream fileInputStream = null;

        String userID = "";
        try {
            fileInputStream = openFileInput(FILE_NAME);
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