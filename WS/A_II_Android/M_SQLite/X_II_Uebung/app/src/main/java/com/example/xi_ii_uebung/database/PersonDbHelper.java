package com.example.xi_ii_uebung.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.xi_ii_uebung.MainActivity;

public class PersonDbHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "persons.db";
    private final static int DB_VERSION = 1;

    public PersonDbHelper(Context context) {
        super(context, DB_NAME , null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UebungTbl.PSQL_CREATE);
        db.execSQL(UebungTbl.CSQL_CREATE);

        db.execSQL(UebungTbl.PSTMT_INSERT, new Object[]{1, "Ali", "Mayr", 37});
        db.execSQL(UebungTbl.PSTMT_INSERT, new Object[]{2, "Aloisius", "Huber", 26});

        db.execSQL(UebungTbl.CSTMT_INSERT, new Object[]{1, "Musterstrasse", "Musterhause", 4600});
        db.execSQL(UebungTbl.CSTMT_INSERT, new Object[]{2, "Musterstrasse", "Huber", 4921});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
