package edu.mobidev.labnotesdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by courtneyngo on 10/24/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String SCHEMA = "notebook";
    public static int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, SCHEMA, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // called once when SCHEMA has not been created.
        String noteTable = "CREATE TABLE " + Note.TABLE_NAME + " ( "
                + Note.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Note.COLUMN_TITLE + " STRING, "
                + Note.COLUMN_NOTE + " STRING );";
        db.execSQL(noteTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method will be called when the version is incremented
    }

    public void insertNote(Note n){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Note.COLUMN_TITLE, n.getTitle());
        contentValues.put(Note.COLUMN_NOTE, n.getNote());
        db.insert(Note.TABLE_NAME, null, contentValues);
        db.close();
    }

    public Note queryNote(int id){
        SQLiteDatabase db = getReadableDatabase();
        Note note = new Note();
        Cursor cursor = db.query(Note.TABLE_NAME, null,
                Note.COLUMN_ID + " =? ", new String[]{String.valueOf(id)},
                null, null, null);
        if(cursor.moveToFirst()){
            note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
            note.setTitle(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TITLE)));
            note.setNote(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)));
        }
        cursor.close();
        db.close();
        return note;
    }

    public void updateNote(Note n){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Note.COLUMN_TITLE, n.getTitle());
        contentValues.put(Note.COLUMN_NOTE, n.getNote());
        db.update(Note.TABLE_NAME, contentValues,
                Note.COLUMN_ID + " =? ", new String[]{String.valueOf(n.getId())});
        db.close();
    }

    public void deleteNote(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Note.TABLE_NAME, Note.COLUMN_ID + " =? ", new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<Note> queryAllNotes(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(Note.TABLE_NAME, null,
                null, null, null, null, null, null);

        ArrayList<Note> noteArrayList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TITLE)));
                note.setNote(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)));
                noteArrayList.add(note);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return noteArrayList;
    }

    public Cursor queryAllNotesAsCursor(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(Note.TABLE_NAME, null,
                null, null, null, null, null, null);

        return cursor;
    }
}
