package com.example.sklep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.Nullable;


import androidx.annotation.Nullable;

public class MyDB extends SQLiteOpenHelper {

    public MyDB(@Nullable Context context, @Nullable String name,
                @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "bazasklepu.db", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE orders (" +
                "orderId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "komputer TEXT NOT NULL, myszklaw Boolean, monitory Boolean, tablety Boolean, numb INTEGER NOT NULL," +
                "price INTEGER NOT NULL)"
        );
    }


    public boolean insertInto(String komputer, boolean mouse, boolean monitory, boolean tablety
            , int numb, int price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("komputer", komputer);
        contentValues.put("myszklaw", mouse);
        contentValues.put("monitory", monitory);
        contentValues.put("tablety", tablety);
        contentValues.put("numb", numb);
        contentValues.put("price", price);
        db.insert("orders", null, contentValues);
        return true;
    }

    public Cursor selectAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT komputer,myszklaw,monitory,tablety,numb,price FROM orders WHERE 1", null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS orders;");
        onCreate(db);
    }
}
