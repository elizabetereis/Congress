package com.example.elizabete.congress;

/**
 * Created by elizabete on 24/09/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class CongressDAO extends SQLiteOpenHelper {

    private static final String DATABASE = "Congress";

    private static final int VERSAO = 1; //TODO: verificar versão

    public CongressDAO(Context context) {

        super(context, DATABASE, null, VERSAO);
    }

    public void onCreate(SQLiteDatabase db) {

        String ddl = "CREATE TABLE Congress (id INTEGER PRIMARY KEY,"
                + " name TEXT UNIQUE NOT NULL, submissionDeadline TEXT, "
                + "reviewDeadline TEXT);";

        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int velha, int nova) {

        String ddl = "DROP TABLE IF EXISTS Congress";
        db.execSQL(ddl);
        onCreate(db);
    }

    public void dropAll(){
        String ddl ="DROP TABLE IF EXISTS Congress";
        getWritableDatabase().execSQL(ddl);
        onCreate( getWritableDatabase());

    }

    public void salvar(CongressValue congressValue) {

        ContentValues values = new ContentValues();
        values.put("id", congressValue.getId());
        values.put("name", congressValue.getName());
        values.put("submissionDeadline", congressValue.getSubmissionDeadline());
        values.put("reviewDeadline", congressValue.getReviewDeadline());

        getWritableDatabase().insert("Congress", null, values);

    }

    public List getLista(){

        List<CongressValue> congressLista = new LinkedList<CongressValue>();

        String query = "SELECT  * FROM Congress order by id";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        CongressValue congressValue = null;

        if (cursor.moveToFirst()) {
            do {
                congressValue = new CongressValue();
                congressValue.setId(cursor.getInt(0));
                congressValue.setName(cursor.getString(1));
                congressValue.setSubmissionDeadline(cursor.getString(2));
                congressValue.setReviewDeadline(cursor.getString(3));

                congressLista.add(congressValue);

            } while (cursor.moveToNext());
        }

        return congressLista;
    }
}

