package com.example.sklep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListaZamowien extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_zamowien);
        ListView listView = findViewById(R.id.lista);
        List<String> zamowienia = new ArrayList<>();
        MyDB myDB = new MyDB(this,null,null,1);
        Cursor cursor = myDB.selectAll();

        Button powrot = findViewById(R.id.powrot);
        powrot.setOnClickListener(v -> {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        });

        if (cursor.moveToFirst()){
            do {
                String zamowienie = cursor.getString(0) + "\n" +
                        "myszki i klawiatury: "+ (cursor.getInt(1)==1?" tak":"nie") + "\n" +
                        "monitory: "+ (cursor.getInt(2)==1?" tak":"nie") + "\n" +
                        "tablety: "+ (cursor.getInt(3)==1?" tak":"nie") + "\n" +
                        "ilość: " + cursor.getInt(4) + "\n" +
                        "cena: " + cursor.getInt(5) + "\n";
                zamowienia.add(zamowienie);
                System.out.println();
            }while (cursor.moveToNext());


        }
        System.out.println(zamowienia);
        if (!zamowienia.isEmpty()){
            listView.setAdapter(new MyArrayAdapter(this, zamowienia));
        }


    }
}