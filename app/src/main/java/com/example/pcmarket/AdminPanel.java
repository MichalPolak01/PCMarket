package com.example.pcmarket;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminPanel extends AppCompatActivity {
    private static final String FILE_NAME = "userID.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        String addressID = loadDataPerosnal();
        loadDataAddress(addressID);

        Button changePersonalData = findViewById(R.id.changePersonalData);
        changePersonalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePersonalData();
                String addressID = loadDataPerosnal();
                loadDataAddress(addressID);
                Toast.makeText(getApplicationContext(), "Dane osobowe zostały zmienione!", Toast.LENGTH_SHORT).show();
            }
        });

        Button changeAddressData = findViewById(R.id.changeAddressData);
        changeAddressData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAddressData(addressID);
                String addressID = loadDataPerosnal();
                loadDataAddress(addressID);
                Toast.makeText(getApplicationContext(), "Dane adresowe zostały zmienione!", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanel.this, MainActivityEmployee.class);
                startActivity(intent);
            }
        });

        ImageButton shopingCart = findViewById(R.id.logout);
        shopingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanel.this, Login.class);
                startActivity(intent);
            }
        });

        EditText adminEmail = findViewById(R.id.adminEmail);
        Button addEmployee = findViewById(R.id.addEmployee);
        addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valEmail = adminEmail.getText().toString();

                if(valEmail.isEmpty()) {

                    if(valEmail.isEmpty()) { adminEmail.setError("Podaj adres e-mail!"); }

                } else {
                    String query = "UPDATE psm_computer_store.osoby SET rola = 'pracownik' WHERE email like '"+valEmail+"';";
                    update(query);

                    Toast.makeText(getApplicationContext(), "Nowy pracownik został pomyślnie dodany!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String loadDataPerosnal() {
        List<User> user = getPersonalData();

        EditText name = findViewById(R.id.name);
        EditText surname = findViewById(R.id.surname);
        EditText phone = findViewById(R.id.phone);
        EditText email = findViewById(R.id.email);

        name.setText(user.get(0).getImie());
        surname.setText(user.get(0).getNazwisko());
        phone.setText(user.get(0).getNr_telefonu());
        email.setText(user.get(0).getEmail());

        return user.get(0).getId_adresu();
    }

    private void loadDataAddress(String addressID) {
        List<Address> address = getUserAdress(addressID);

        EditText street = findViewById(R.id.street);
        EditText city = findViewById(R.id.city);
        EditText zipCode = findViewById(R.id.zipCode);

        street.setText(address.get(0).getUlica());
        city.setText(address.get(0).getMiasto());
        zipCode.setText(address.get(0).getKod_pocztowy());
    }

    private List<User> getPersonalData() {
        String userID = loadSavedUserID();

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        Connect connect = new Connect();

        String query = "SELECT * FROM psm_computer_store.osoby WHERE id_osoby="+userID+";";
        ResultSet result = connect.select(query, connect.getConnection());

        List<User> user = new ArrayList<>();

        try {
            while (result.next()) {
                user.add(new User(result.getString(1), result.getString(2), result.getString(3),
                        result.getString(4), result.getString(5), result.getString(6), result.getString(7)
                        , result.getString(8)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        connect.close();

        return user;
    }

    private List<Address> getUserAdress(String addressID) {

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        Connect connect = new Connect();

        String query = "SELECT * FROM psm_computer_store.adresy WHERE id_adresu="+addressID+";";
        ResultSet result = connect.select(query, connect.getConnection());

        List<Address> address = new ArrayList<>();

        try {
            while (result.next()) {
                address.add(new Address(result.getString(1), result.getString(2), result.getString(3),
                        result.getString(4)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        connect.close();

        return address;
    }

    private void updatePersonalData() {
        String userID = loadSavedUserID();

        EditText name = findViewById(R.id.name);
        EditText surname = findViewById(R.id.surname);
        EditText phone = findViewById(R.id.phone);
        EditText email = findViewById(R.id.email);

        String valName = name.getText().toString();
        String valSurname = surname.getText().toString();
        String valPhone = phone.getText().toString();
        String valEmail = email.getText().toString();

        String query = "UPDATE psm_computer_store.osoby SET imie='"+valName+"', nazwisko='"+valSurname+"', " +
                "nr_telefonu='"+valPhone+"', email='"+valEmail+"' WHERE id_osoby="+userID ;

        update(query);
    }

    private void updateAddressData(String addressID) {
        EditText street = findViewById(R.id.street);
        EditText city = findViewById(R.id.city);
        EditText zipCode = findViewById(R.id.zipCode);

        String valStreet = street.getText().toString();
        String valCity = city.getText().toString();
        String valZipCode = zipCode.getText().toString();

        String query = "UPDATE psm_computer_store.adresy SET ulica='"+valStreet+"', kod_pocztowy='"+valZipCode+"', " +
                "miasto='"+valCity+"' WHERE id_adresu="+addressID;

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