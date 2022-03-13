package com.example.sklep;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Podsumowanie extends AppCompatActivity {

    public EditText number, email;
    private Button send;
    Intent intent;
    String sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podsumowanie);
        intent = getIntent();
        sms = "Zamówienie: "+intent.getStringExtra("komputer").trim() +
                "\n"+"Cena: "+ intent.getIntExtra("totalValue",0) + " zł" +
                "\n"+"Imię i nazwisko: "+ intent.getStringExtra("name").trim();
        number = findViewById(R.id.number);
        email = findViewById(R.id.email);
        send = findViewById(R.id.button);

        Button button = findViewById(R.id.zapisz);
        button.setOnClickListener(v -> save());
        send.setOnClickListener(view -> {
            if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                sendSMS();
                sendMail(sms);
            } else {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
            }
        });
    }
    public void save(){

        MyDB myDB = new MyDB(this,null,null,1);
        String komp = intent.getStringExtra("komputer");
        boolean myszklaw = intent.getBooleanExtra("myszklaw",false);
        boolean monitory = intent.getBooleanExtra("monitory",false);
        boolean tablety = intent.getBooleanExtra("tablety",false);
        int slider = intent.getIntExtra("slider",1);
        int totalValue = intent.getIntExtra("totalValue",0);
        System.out.println(komp +"\n"+ myszklaw +"\n"+ monitory +"\n"+ tablety +"\n"+ slider +"\n"+ totalValue);
        myDB.insertInto(komp,
                myszklaw,
                monitory,
                tablety,
                slider,
                totalValue);
        Intent i = new Intent(this, ListaZamowien.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
    private void sendSMS(){
        String nrTelefonu = number.getText().toString().trim();
        try {
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> sm = smsManager.divideMessage(sms);
            smsManager.sendMultipartTextMessage(nrTelefonu,null,sm,null,null);
            Toast.makeText(this, "Wiadomość wysłana", Toast.LENGTH_LONG).show();
            Toast.makeText(this, String.valueOf(sms), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Błąd wysyłania",Toast.LENGTH_LONG).show();
            Toast.makeText(this, String.valueOf(sms), Toast.LENGTH_SHORT).show();

        }
    }
    public  void sendMail(String text){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
        i.putExtra(Intent.EXTRA_SUBJECT, "Order");
        i.putExtra(Intent.EXTRA_TEXT, text);
        try {
            this.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }
    }
