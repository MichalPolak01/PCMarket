package com.example.pcmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Filtration extends AppCompatActivity {
    private static final String FILE_QUERY = "query.txt";

    RadioGroup radioGroup;
    RadioButton radioButton;
    Button applyFilters;
    EditText name;
    String checkedCategory="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtration);

        selector();

        radioGroup = findViewById(R.id.radioGroup);

        applyFilters = findViewById(R.id.applyFilters);
        applyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioID = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioID);
                String option = String.valueOf(radioButton.getText());
                String orderBy = checkedOption(option);

                name = findViewById(R.id.name);
                String nameValue = name.getText().toString();

                String whereName = "";
                String whereCategory = "";
                String where = "";
                if(nameValue.length() > 0) {
                    whereName = " nazwa like '%"+nameValue+"%'";

                    if(checkedCategory.length() > 0) {
                        whereCategory = " AND kategoria like '"+checkedCategory+"'";
                    }
                    where = " AND "+ whereName+""+whereCategory;
                } else {
                    if(checkedCategory.length() > 0) {
                        whereCategory = " kategoria like '"+checkedCategory+"'";
                    }
                    where = " AND "+whereCategory;
                }

                String query = where+""+orderBy;
                saveQuery(query);

                Intent intent = new Intent(Filtration.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Filtration.this, MainActivity.class);
                intent.putExtra("query", "");
                startActivity(intent);
            }
        });
    }

    private void selector() {
        String[] category = {"", "Laptop", "Monitor", "Procesor", "Karta graficzna", "Płyta główna", "Pamięć RAM", "Dysk SSD",
                "Obudowa komputerowa", "Słuchawki", "Mysz", "Klawiatura", "Mikrofon", "Głośniki", "Router", "Kamera", "Inne"};

        Spinner spinner;
        spinner = findViewById(R.id.selector);

        ArrayAdapter<String> adapter = new ArrayAdapter(Filtration.this, R.layout.spinner_my_selected_item, category);
        adapter.setDropDownViewResource(R.layout.spinner_my_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                checkedCategory = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private String checkedOption(String option) {
        String orderBy ="";
        switch (option) {
            case "Po cenie malejąco":
                orderBy = " ORDER BY cena DESC";
                break;
            case "Po cenie rosnąco":
                orderBy = " ORDER BY cena ASC";
                break;
            case "Po dacie dodania - od najnowszych":
                orderBy = " ORDER BY data_dodania DESC";
                break;
            case "Po dacie dodania - od najstarszych":
                orderBy = " ORDER BY data_dodania ASC";
                break;
        }
        return orderBy;
    }

    private void saveQuery(String query) {
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = openFileOutput(FILE_QUERY, MODE_PRIVATE);
            fileOutputStream.write(query.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}