/*
package com.example.foda_.follow_your_son;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Sqlite extends SQLiteOpenHelper {
    public static final String database_name ="Follow.db";
    public static final String table_name="MyTable";

    public static final String ID = "Id";
    public static final String NAME = "Student_Name";
    public static final String PARENTiD = "parent_id";
    public static final String PARENTEMAIL = "parent_Email";
    public static final String PARENTMOBILE = "parent_mobile";

    public static final int version = 4;

    public Sqlite(Context context) {
        super(context, database_name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String Sql_query="CREATE TABLE " + table_name + " ( "
                +ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +NAME+" TEXT ,"
                +PARENTiD +" TEXT ,"
                +PARENTEMAIL+" TEXT ,"
                +PARENTMOBILE+" TEXT "
                +")";
        db.execSQL(Sql_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + table_name;
        db.execSQL(query);
        onCreate(db);
    }


    public boolean insert_data(Student_data student_data)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,student_data.name );
        contentValues.put(PARENTiD,student_data.parent_id );
        contentValues.put(PARENTEMAIL,student_data.parent_email);
        contentValues.put(PARENTMOBILE, student_data.parent_mobile);
        long x = db.insert(table_name, null, contentValues);

        if (x==-1)
            return false;
        else
            return true;

    }
    public Cursor select()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT * FROM "+table_name;
        Cursor x=db.rawQuery(query, null);
        return x;
    }


    public Integer Delete(String name)
    {     SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(table_name,"Student_Name = ?" , new String[]{name});
    }


}
*/
