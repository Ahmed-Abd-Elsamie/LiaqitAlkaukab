package com.fitkeke.root.socialapp.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fitkeke.root.socialapp.modules.ItemFood;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "dfood.db";
    public static final String FOOD_TABLE_NAME = "foods";
    public static final String FOOD_ID = "id";
    public static final String FOOD_CARB = "carb";
    public static final String FOOD_PROTIN = "protin";
    public static final String FOOD_FATS = "fats";
    public static final String FOOD_ALIAF = "aliaf";
    public static final String FOOD_TIME = "time";
    public static final String FOOD_REQUEST = "request";
    public static final String FOOD_STATE = "state";



    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table foods " +
                        "(id integer primary key, carb text, protin text, fats text, aliaf text, time text, request integer, state text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS foods");
        onCreate(db);
    }

    public boolean insertFood(String carb, String protin,String fats,String aliaf, String time, int request, String state){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("carb", carb);
        contentValues.put("protin", protin);
        contentValues.put("fats", fats);
        contentValues.put("aliaf", aliaf);
        contentValues.put("time", time);
        contentValues.put("request", request);
        contentValues.put("state", state);

        db.insert("foods", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from foods where id = "+id+"",null);
        return res;
    }

    public Cursor getDataByRequest(int request){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query("foods", new String[]{"id", "name", "time", "request", "state"}, "request=?", new String[]{String.valueOf(request)}, null, null, null, null);
        return res;
    }

    public int numberofRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRow = (int) DatabaseUtils.queryNumEntries(db, FOOD_TABLE_NAME);
        return numRow;
    }

    public boolean updateFood(Integer id,String carb, String protin,String fats,String aliaf, String time, int request, String state){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("carb", carb);
        contentValues.put("protin", protin);
        contentValues.put("fats", fats);
        contentValues.put("aliaf", aliaf);
        contentValues.put("time", time);
        contentValues.put("request", request);
        contentValues.put("state", state);
        db.update("foods", contentValues, "id = ? ", new String[] {Integer.toString(id)});
        return true;

    }

    public boolean updateFoodByRequest(Integer request,String name, String time, String state){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("time", time);
        contentValues.put("request", request);
        contentValues.put("state", state);
        db.update("foods", contentValues, "request = ? ", new String[] {Integer.toString(request)});
        return true;

    }

    public Integer deleteFood(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("foods", "id = ?", new String[]{Integer.toString(id)});
    }

    public ArrayList<ItemFood> getAllFoods(){
        ArrayList<ItemFood> arrayList = new ArrayList<ItemFood>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from foods", null);
        res.moveToFirst();


        while(res.isAfterLast() == false){
            ItemFood itemFood = new ItemFood();
            itemFood.setId(res.getInt(res.getColumnIndex(FOOD_ID)));
            itemFood.setCarb(res.getString(res.getColumnIndex(FOOD_CARB)));
            itemFood.setProtin(res.getString(res.getColumnIndex(FOOD_PROTIN)));
            itemFood.setFats(res.getString(res.getColumnIndex(FOOD_FATS)));
            itemFood.setAliaf(res.getString(res.getColumnIndex(FOOD_ALIAF)));
            itemFood.setTime(res.getString(res.getColumnIndex(FOOD_TIME)));
            itemFood.setRequest(res.getInt(res.getColumnIndex(FOOD_REQUEST)));
            itemFood.setState(res.getString(res.getColumnIndex(FOOD_STATE)));
            arrayList.add(itemFood);
            res.moveToNext();
        }

        return arrayList;
    }
}
