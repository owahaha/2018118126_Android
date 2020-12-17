package com.example.ftest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TipsDdataBaseHelper extends SQLiteOpenHelper {

    //必要的构造函数
    public TipsDdataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,name,factory,version);
    }
    //表创建接口
    public static interface TableCreateInterface{
        //创建
        public void onCreate(SQLiteDatabase db);
        //更新
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    }
    //第一次创建数据库时，调用该方法
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Tips.getInstance().onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Tips.getInstance().onUpgrade(sqLiteDatabase,oldVersion,newVersion);
    }
}
