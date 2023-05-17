package com.example.pcmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;

public class AddProduct extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
    }

    private void AddNewProduct() {
        TextView name = findViewById(R.id.name);
        TextView brand = findViewById(R.id.brand);
        TextView price = findViewById(R.id.price);
        TextView amount = findViewById(R.id.amount);
        TextView link = findViewById(R.id.link);
        TextView description = findViewById(R.id.description);

        Button AddProduct = findViewById(R.id.confirm_button);
        ImageButton back = findViewById(R.id.back);

        // Nawiązanie połączenia z bazą danych
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        Connect connect = new Connect();

        AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String valName = name.getText().toString();
                String valBrand = brand.getText().toString();
                String valPrice = price.getText().toString();
                String valAmount = amount.getText().toString();
                String valLink = link.getText().toString();
                String valDescription = description.getText().toString();

                if(valName.isEmpty() || valBrand.isEmpty() || valPrice.isEmpty() || valAmount.isEmpty() ||
                        valLink.isEmpty() || valDescription.isEmpty()) {

                    if(valName.isEmpty()) { name.setError("Podaj nazwę produktu!"); }
                    if(valBrand.isEmpty()) { brand.setError("Podaj markę produktu!"); }
                    if(valPrice.isEmpty()) { price.setError("Podaj cenę produktu!"); }
                    if(valAmount.isEmpty()) { amount.setError("Podaj liczbę sztuk!"); }
                    if(valLink.isEmpty()) { link.setError("Podaj link do zdjęcia!"); }
                    if(valDescription.isEmpty()) { description.setError("Podaj opis produktu!"); }


                    Toast.makeText(getApplicationContext(), "Uzupełnij wszystkie pola!", Toast.LENGTH_SHORT).show();
                } else {

                    String insertProduct = "INSERT INTO psm_computer_store.produkty (nazwa, marka, cena, zdjecie, ilosc, opis) " +
                            "VALUES ('"+valName+"', '"+valBrand+"', '"+valPrice+"', '"+valLink+"', '"+valAmount+"', '"+valDescription+"'))";

                    try{
                        connect.insert(insertProduct, connect.getConnection());
                    }
                     catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        connect.close();
                        Intent intent = new Intent(AddProduct.this, MainActivityEmployee.class);
                        startActivity(intent);

                    }
                }
            }
        });


    }
}