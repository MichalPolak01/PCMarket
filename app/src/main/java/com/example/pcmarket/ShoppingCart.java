package com.example.pcmarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShoppingCart extends AppCompatActivity {
    private static final String FILE_NAME = "userID.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        showProducts();

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingCart.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton shoppingCat = findViewById(R.id.account);
        shoppingCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingCart.this, AccountSettings.class);
                startActivity(intent);
            }
        });
    }

    private void showProducts() {
        String userID = loadSavedUserID();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        Connect connect = new Connect();

        String query = "SELECT * FROM psm_computer_store.lista_zakupow WHERE id_osoby="+userID;

        ResultSet result = connect.select(query, connect.getConnection());

        List<Shopping_list> items = new ArrayList<>();

        try {
            while (result.next()) {
                items.add(new Shopping_list(result.getString(1), result.getString(2), result.getString(3),
                        result.getString(4)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String query2 = "SELECT SUM(cena * pcsz.ilosc) FROM psm_computer_store.produkty pcsp " +
                "INNER JOIN psm_computer_store.lista_zakupow pcsz ON pcsp.id_produktu = pcsz.id_produktu " +
                "WHERE id_osoby = '"+userID+"';";

        ResultSet result2 = connect.select(query2, connect.getConnection());

        Double totalPrice = 0.0;
        try {
            while (result2.next()) {
                totalPrice = result2.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView price = findViewById(R.id.price);
        price.setText(totalPrice.toString()+" zł");

        connect.close();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ShoppingCartAdapter(getApplicationContext(), items));

        Button order = findViewById(R.id.order);
        Double finalTotalPrice = totalPrice;
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalTotalPrice > 0.0) {
                    createOreder(items, userID, finalTotalPrice);
                } else {
                    Toast.makeText(getApplicationContext(), "Twój koszyk jest pusty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createOreder(List<Shopping_list> items, String userID, Double totalPrice) {
        String orderId = "uid"+userID+"dt"+java.time.LocalTime.now();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String orderDate = formatter.format(date);

        String queryUpdate = "";
        String query = "INSERT INTO psm_computer_store.zamowienia(id_zamowienia, id_klienta, kwota, data_zlozenia_zamowienia) " +
                "VALUES ('"+orderId+"', '"+userID+"', '"+totalPrice+"', '"+orderDate+"'); ";
        for ( Shopping_list item : items) {
            query += " INSERT INTO psm_computer_store.zamowione_produkty(id_zamowienia, id_produktu, ilosc) " +
                    "VALUES ('"+orderId+"', '"+item.getId_produktu()+"', '"+item.getIlosc()+"'); ";

            queryUpdate += " UPDATE psm_computer_store.produkty SET ilosc = ilosc - "+item.getIlosc()+" WHERE id_produktu = '"+item.getId_produktu()+"'; ";
        }
        insert(query);
        update(queryUpdate);
        deleteFromShoppingList(userID);
        Toast.makeText(getApplicationContext(), "Pomyślnie złożono zamowienie!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ShoppingCart.this, ShoppingCart.class);
        startActivity(intent);
    }

    private void insert(String query) {
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        Connect connect = new Connect();

        connect.insert(query, connect.getConnection());

        connect.close();
    }

    private void deleteFromShoppingList(String userID) {
        String query = "DELETE FROM psm_computer_store.lista_zakupow WHERE id_osoby="+userID;

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
}