package com.feuoy.tally.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.feuoy.tally.bean.RecordBean;

import java.util.LinkedList;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Record";

    private static final String CREATE_RECORD_DB =
            "create table Record ("
                    + "id integer primary key autoincrement, "
                    + "amount double, "
                    + "type integer, "
                    + "category, "
                    + "remark text, "
                    + "date date,"
                    + "timeStamp integer,"
                    + "uuid text"
                    + ")";


    // read，所有支出或收入的记录
    public LinkedList<RecordBean> getRecordsByType(int type_need) {
        LinkedList<RecordBean> records = new LinkedList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(
                "select DISTINCT * from Record where type = ? order by date asc",
                new String[]{""+ type_need}
        );

        if (cursor.moveToFirst()) {
            do {
                double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
                int type = cursor.getInt(cursor.getColumnIndex("type"));
                String category = cursor.getString(cursor.getColumnIndex("category"));
                String remark = cursor.getString(cursor.getColumnIndex("remark"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                long timeStamp = cursor.getLong(cursor.getColumnIndex("timeStamp"));
                String uuid = cursor.getString(cursor.getColumnIndex("uuid"));

                RecordBean record = new RecordBean();
                record.setType(type);
                record.setCategory(category);
                record.setRemark(remark);
                record.setAmount(amount);
                record.setDate(date);
                record.setTimeStamp(timeStamp);
                record.setUuid(uuid);

                records.add(record);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return records;
    }


    // read，有记录的日期
    public LinkedList<String> getValidDate() {
        LinkedList<String> dates = new LinkedList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(
                "select DISTINCT * from Record order by date asc",
                new String[]{}
        );

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));

                if (!dates.contains(date)) {
                    dates.add(date);
                }

            } while (cursor.moveToNext());
        }

        cursor.close();

        Log.d("db-dates----", dates.toString());

        return dates;
    }


    // read，某日期的所有记录
    public LinkedList<RecordBean> getRecordsByDate(String dateStr) {
        LinkedList<RecordBean> records = new LinkedList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(
                "select DISTINCT * from Record where date = ? order by timeStamp asc",
                new String[]{dateStr}
        );

        if (cursor.moveToFirst()) {
            do {
                double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
                int type = cursor.getInt(cursor.getColumnIndex("type"));
                String category = cursor.getString(cursor.getColumnIndex("category"));
                String remark = cursor.getString(cursor.getColumnIndex("remark"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                long timeStamp = cursor.getLong(cursor.getColumnIndex("timeStamp"));
                String uuid = cursor.getString(cursor.getColumnIndex("uuid"));

                RecordBean record = new RecordBean();
                record.setType(type);
                record.setCategory(category);
                record.setRemark(remark);
                record.setAmount(amount);
                record.setDate(date);
                record.setTimeStamp(timeStamp);
                record.setUuid(uuid);

                records.add(record);

            } while (cursor.moveToNext());
        }

        cursor.close();

        Log.d("db-records----", records.toString());

        return records;
    }


    // update by uuid and record
    public void updateRecord(String uuid, RecordBean record) {
        // 先删除原来的uuid行
        deleteRecord(uuid);

        // 再给新的record设置回这个uuid，插入
        record.setUuid(uuid);
        insertRecord(record);
    }


    // delete by uuid
    public void deleteRecord(String uuid) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(DB_NAME, "uuid = ?", new String[]{uuid});
    }


    // create by record
    public void insertRecord(RecordBean recordBean) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("amount", recordBean.getAmount());
        contentValues.put("type", recordBean.getType());
        contentValues.put("category", recordBean.getCategory());
        contentValues.put("remark", recordBean.getRemark());
        contentValues.put("date", recordBean.getDate());
        contentValues.put("timeStamp", recordBean.getTimeStamp());
        contentValues.put("uuid", recordBean.getUuid());

        db.insert(DB_NAME, null, contentValues);

        contentValues.clear();
    }


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RECORD_DB);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
