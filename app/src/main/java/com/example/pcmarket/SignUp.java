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

public class SignUp extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNew();
            }
        });

        ImageButton backToLogin = findViewById(R.id.back);
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });
    }

    void addNew() {
        TextView name = findViewById(R.id.name);
        TextView surname = findViewById(R.id.surname);
        TextView phone = findViewById(R.id.phoneNumber);
        TextView email = findViewById(R.id.email);
        TextView password = findViewById(R.id.password);

        TextView street = findViewById(R.id.street);
        TextView zipCode = findViewById(R.id.zipCode);
        TextView city = findViewById(R.id.city);

        String valName = name.getText().toString();
        String valSurname = surname.getText().toString();
        String valPhone = phone.getText().toString();
        String valEmail = email.getText().toString();
        String valPassword = password.getText().toString();

        String valStreet = street.getText().toString();
        String valZipCode = zipCode.getText().toString();
        String valCity = city.getText().toString();

        if(valName.isEmpty() || valSurname.isEmpty() || valPhone.isEmpty() || valEmail.isEmpty() ||
                valPassword.isEmpty() || valStreet.isEmpty() || valZipCode.isEmpty() || valCity.isEmpty()) {

            if(valName.isEmpty()) { name.setError("Podaj imię!"); }
            if(valSurname.isEmpty()) { surname.setError("Podaj nazwisko!"); }
            if(valPhone.isEmpty()) { phone.setError("Podaj numer telefonu!"); }
            if(valEmail.isEmpty()) { email.setError("Podaj adres e-mail!"); }
            if(valPassword.isEmpty()) { password.setError("Podaj hasło!"); }

            if(valStreet.isEmpty()) { street.setError("Podaj ulicę!"); }
            if(valZipCode.isEmpty()) { zipCode.setError("Podaj kod pocztowy!"); }
            if(valCity.isEmpty()) { city.setError("Podaj miasto!"); }

            Toast.makeText(getApplicationContext(), "Uzupełnij wszystkie pola!", Toast.LENGTH_SHORT).show();
        } else {
            String idAdresu = "";

            String queryAddress = "INSERT INTO psm_computer_store.adresy(ulica, kod_pocztowy, miasto) " +
                    "VALUES('"+valStreet+"', '"+valZipCode+"', '"+valCity+"')";

            String queryPersonal = "INSERT INTO psm_computer_store.osoby (imie, nazwisko, email, nr_telefonu, haslo, id_adresu) " +
                    "VALUES ('"+valName+"', '"+valSurname+"', '"+valEmail+"', '"+valPhone+"', '"+valPassword+"'" +
                    ",(SELECT id_adresu FROM psm_computer_store.adresy WHERE ulica='"+valStreet+"' " +
                    "AND kod_pocztowy='"+valZipCode+"' AND miasto='"+valCity+"'))";

            String selectAddress = "SELECT id_adresu FROM psm_computer_store.adresy WHERE ulica='"+valStreet+"' " +
                    "AND kod_pocztowy='"+valZipCode+"' AND miasto='"+valCity+"'";

            String selectUser = "SELECT id_osoby FROM psm_computer_store.osoby WHERE email='"+valEmail+"'";

            try{
                ResultSet isUser = select(selectUser);
                String userId = "";
                while (isUser.next()) {
                    userId = isUser.getString(1);
                }
                if (userId == "") {
                        ResultSet isAddress = select(selectAddress);

                        while (isAddress.next()) {
                            idAdresu = isAddress.getString(1);
                        }
                        if (idAdresu == "") {
                            insert(queryAddress);
                            insert(queryPersonal);
                        } else {
                            String queryPersonalWhenAddressExist = "INSERT INTO psm_computer_store.osoby (imie, nazwisko, email, nr_telefonu, haslo, id_adresu) " +
                                    "VALUES ('"+valName+"', '"+valSurname+"', '"+valEmail+"', '"+valPhone+"', '"+valPassword+"'"+", "+idAdresu+")";

                            insert(queryPersonalWhenAddressExist);
                        }

                        Toast.makeText(getApplicationContext(), "Poprawnie zarejestowano!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUp.this, Login.class);
                        startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Użytkownik z takim adresem e-mail już istenieje!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void insert(String query) {
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        Connect connect = new Connect();

        connect.insert(query, connect.getConnection());

        connect.close();
    }

    private ResultSet select(String query) {
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        Connect connect = new Connect();

        ResultSet person = connect.select(query, connect.getConnection());

        connect.close();

        return person;
    }
}