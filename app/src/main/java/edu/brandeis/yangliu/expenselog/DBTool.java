package edu.brandeis.yangliu.expenselog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBTool extends SQLiteOpenHelper {

    private static final String FILE_NAME = "MYDATEBASE.db";
    public final String DB_NAME = "ExpenseLog";
    public final String ID = "_id";
    public final String DATE = "date";
    public final String DESCRIPTION = "description";
    public final String NOTE = "note";

    public DBTool(Context context) {
        super(context, FILE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DB_NAME + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DATE + " TEXT, " + DESCRIPTION + " TEXT, " + NOTE + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        onCreate(db);
    }

    public void delete(long id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(DB_NAME, ID + " = ? ", new String[]{Long.toString(id)});
    }

    public void insertData(ExpenseLogEntryData data) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DATE, data.getDate());
        values.put(DESCRIPTION, data.getDescription());
        values.put(NOTE, data.getNote());
        database.insert(DB_NAME, null, values);

    }

    public Cursor getAllData() {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + DB_NAME + " order by " + DATE;
        return database.rawQuery(query, null);
    }

}