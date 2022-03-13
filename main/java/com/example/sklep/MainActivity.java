package com.example.sklep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    String [] opisy = {
            "Intel i5, 8GB RAM, 250GB SSD, 1TB HDD, GTX 1050 Ti 4GB, Cena: 3800 zł",
            "Intel i7, 16GB RAM, 500GB SSD, 2TB HDD, GTX 1660 Ti 6GB, Cena: 6500 zł",
            "Intel i9, 32GB RAM, 500GB SSD, 4TB HDD, GTX 2060 Ti 6GB, Cena: 9000 zł"
    };
    int [] pcty = {
            R.drawable.zestaw1,
            R.drawable.zestaw2,
            R.drawable.zestaw3
    };
    String[] dodatki = new String[3];
    int totalValue;
    Spinner spinner;
    CheckBox myszklaw, monitory, tablety;
    Button zamow;
    Slider slider;
    String komputer;
    EditText name;
    private int orderTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        name = findViewById(R.id.name);
        myszklaw = findViewById(R.id.myszklaw);
        monitory = findViewById(R.id.monit);
        tablety = findViewById(R.id.tablety);
        zamow = findViewById(R.id.zamow);
        slider = findViewById(R.id.slider);
        slider.setValueTo(100);
        zamow.setOnClickListener(v -> this.sendOrder());



        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, opisy);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        MyAdapter myAdapter = new MyAdapter(getApplicationContext(),pcty,opisy);
        spinner.setAdapter(myAdapter);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        komputer = opisy[position];
        switch (position){
            case 0: orderTotal = 3800; break;
            case 1: orderTotal = 6500; break;
            case 2: orderTotal = 9000; break;
            default:
        }
    }
    public void sumUp(){

        totalValue = (orderTotal + (myszklaw.isChecked()?800:0) + (monitory.isChecked()?1000:0) + (tablety.isChecked()?500:0))*(int) slider.getValue();
        if(totalValue==0||komputer == null) {
            Toast.makeText(this,"nie można wysłać zmówienia",Toast.LENGTH_LONG).show();
            return;
        }
        Intent i = new Intent(this,Podsumowanie.class);
        i.putExtra("totalValue",totalValue);
        i.putExtra("komputer", String.valueOf(komputer));
        i.putExtra("name",String.valueOf(name.getText()));
        i.putExtra("myszklaw",myszklaw.isChecked());
        i.putExtra("monitory",monitory.isChecked());
        i.putExtra("tablety",tablety.isChecked());
        i.putExtra("slider",(int) slider.getValue());
        startActivity(i);
        //Toast.makeText(this,String.valueOf(totalValue),Toast.LENGTH_LONG).show();
    }


    public void sendOrder(){
        sumUp();

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}