package com.example.contactstudent.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.contactstudent.Models.Contact;
import com.example.contactstudent.Models.Student;

import java.util.ArrayList;
import java.util.List;

public class DBhelper extends SQLiteOpenHelper {

    public DBhelper(Context context) {
        super(context, "DATABASE_NAME", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE STUDENT_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, FNAME TEXT, SNAME TEXT, ROLL_NO TEXT)");

        db.execSQL("CREATE TABLE CONTACT_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, NUMBER TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void contact_add(String name, String number) {

        SQLiteDatabase database = this.getWritableDatabase();

        String query = "INSERT INTO CONTACT_TABLE (NAME, NUMBER) VALUES ('" + name + "', '" + number + "')";
        database.execSQL(query);
    }

    public void student_add(String name, String fname, String sname, String rollno) {

        SQLiteDatabase database = this.getWritableDatabase();

        String query = "INSERT INTO STUDENT_TABLE (NAME, FNAME, SNAME, ROLL_NO) VALUES ('" + name + "', '" + fname + "', '" + sname + "', '" + rollno + "')";
        database.execSQL(query);
    }


    public void contactdeletedata(int id) {

        SQLiteDatabase database = this.getReadableDatabase();

        String query = "DELETE FROM CONTACT_TABLE WHERE ID = " + id;
        database.execSQL(query);
    }

    public void studentdeletedata(int id) {

        SQLiteDatabase database = this.getReadableDatabase();

        String query = "DELETE FROM STUDENT_TABLE WHERE ID = " + id;
        database.execSQL(query);
    }

    public void editcontact(Contact contact) {

        SQLiteDatabase database = this.getWritableDatabase();

        String query = "UPDATE CONTACT_TABLE SET NAME = '" + contact.getName() + "', NUMBER = '" + contact.getNumber() + "' WHERE ID = " + contact.getId();

        database.execSQL(query);
    }

    public void editstudent(Student student) {

        SQLiteDatabase database = this.getWritableDatabase();

        String query = "UPDATE STUDENT_TABLE SET NAME = '" + student.getName() + "', FNAME = '" + student.getFname() + "', SNAME = '" + student.getSname() + "',ROLL_NO = '" + student.getRollno() + "' WHERE ID = " + student.getId();
        database.execSQL(query);
    }

    public List<Contact> getallcontact() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<Contact> contactlist = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM CONTACT_TABLE", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String number = cursor.getString(2);
            contactlist.add(new Contact(id, name, number));
        }
        return contactlist;
    }

    public List<Student> getallstudent() {

        List<Student> studentList = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM STUDENT_TABLE", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String fname = cursor.getString(2);
            String sname = cursor.getString(3);
            String rollno = cursor.getString(4);
            studentList.add(new Student(id, name, fname, sname, rollno));
        }
        return studentList;
    }

}
