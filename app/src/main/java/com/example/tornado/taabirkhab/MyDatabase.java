package com.example.tornado.taabirkhab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by tornado on 10/16/2018.
 */

public class MyDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "tabirkhab.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
