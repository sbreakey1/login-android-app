package com.example.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DatabaseHelper extends SQLiteOpenHelper{

    //Set up the database elements
    private static final String TABLE_NAME = "users";
    private static final String COL1 = "ID";
    private static final String COL2 = "USERNAME";
    private static final String COL3 = "PASSWORD";
    private static final String COL4 = "LOGGED";

    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT NOT NULL, PASSWORD TEXT NOT NULL, LOGGED INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item, String item2, int item3){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);
        contentValues.put(COL3, item2);
        contentValues.put(COL4, item3);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //If date as inserted correctly it will return -1
        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    //Retrieve elements from database
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //Clear element from the database
    public void clearData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME;
        db.execSQL(query);
    }

    //Get the password for a given username
    public String getPassword(String username){

        String pass = "no";

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT PASSWORD FROM users WHERE USERNAME = " +  "'"  + username + "'";
        Cursor data = db.rawQuery(query, null);

        if (data.getCount() == 0) {
            return ("No users registered");
        } else {
            while (data.moveToNext()) {
                pass = data.getString(0);
            }
        }

        return pass;
    }

    //Get the login status of a user for a given username
    //0 for not logged in
    //1 for logged in
    public int loggedIn(){

        int logged = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);

        if (data.getCount() == 0) {
            return 0;
        } else {
            while (data.moveToNext()) {
                if(data.getString(3).equals("1")){
                    logged=1;
                }
            }
        }

        return logged;

    }

    //Login the user - change the table value from a 0 to a 1
    public void loginUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE users SET LOGGED = 1  WHERE USERNAME = " + "'" + username + "'");
    }

    //Generate a hash of the user password
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String hash(String password) throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        return bytesToHex(encodedhash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public void logout(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE users SET LOGGED = 0");
    }

    public String getLoggedinUser(){

        String uname = "Joe Bloggs";

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT USERNAME FROM users WHERE LOGGED = 1";
        Cursor data = db.rawQuery(query, null);

        if (data.getCount() == 0) {
            return ("No users registered");
        } else {
            while (data.moveToNext()) {
                uname = data.getString(0);
            }
        }

        return uname;

    }

    public void setPassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE users SET PASSWORD = " + "'" + password + "'" + " WHERE USERNAME = " + "'" + username + "'");
    }

}
