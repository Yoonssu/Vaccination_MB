package com.example.vaccination

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "testdb", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("""
            CREATE TABLE todo_tb (
                _id INTEGER PRIMARY KEY AUTOINCREMENT,
                vaccineType TEXT,
                vaccineDate TEXT,
                medicalInstitution TEXT,
                otherSideEffects TEXT,
                hasFever INTEGER,
                hasLocalReaction INTEGER,
                hasHeadache INTEGER,
                hasVomiting INTEGER,
                hasMusclePain INTEGER
            )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}
