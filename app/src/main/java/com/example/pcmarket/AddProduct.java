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

public class AddProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Button AddProduct = findViewById(R.id.confirm_button);
        AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewProduct();
            }
        });

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProduct.this, MainActivityEmployee.class);
                startActivity(intent);
            }
        });
    }

    private void addNewProduct() {
        TextView name = findViewById(R.id.name);
        TextView brand = findViewById(R.id.brand);
        TextView price = findViewById(R.id.price);
        TextView amount = findViewById(R.id.amount);
        TextView link = findViewById(R.id.link);
        TextView description = findViewById(R.id.description);

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
            String query = "INSERT INTO psm_computer_store.produkty (nazwa, marka, cena, zdjecie, ilosc, opis) " +
                    "VALUES ('"+valName+"', '"+valBrand+"', '"+valPrice+"', '"+valLink+"', '"+valAmount+"', '"+valDescription+"')";

            insert(query);

            Toast.makeText(getApplicationContext(), "Pomyślnie dodano produkt "+valName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddProduct.this, MainActivityEmployee.class);
            startActivity(intent);
        }
    }

    private void insert(String query) {
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        Connect connect = new Connect();

        connect.insert(query, connect.getConnection());

        connect.close();
    }
}