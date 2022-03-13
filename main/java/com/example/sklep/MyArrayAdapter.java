package com.example.sklep;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<String> {
    List<String> lista;
    Activity activity;

    public MyArrayAdapter(@NonNull Activity context, List<String> lista) {
        super(context, R.layout.arrayelement, lista);
        this.lista = lista;
        this.activity = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.arrayelement, null, true);
        TextView textView = view.findViewById(R.id.elementum);
        System.out.println(lista.get(position));
        textView.setText(lista.get(position));

        return view;
    }
}
