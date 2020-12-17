package com.example.ftest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;

public class Tips implements TipsDdataBaseHelper.TableCreateInterface {
    //表名
    public static String tablename = "Tip";
    //各字段名
    public static String _id="_id";
    public static String content="content";//内容
    public static String time="time";//时间
    //私有化构造方法
    private Tips(){}
    //初始化实例
    private static Tips tip =new Tips();
    //只提供一个实例
    public static  Tips getInstance(){
        if(tip==null){
            tip = new Tips();
        }
        return tip;

    }
    //表的创建
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql= String.format("CREATE TABLE %s(_id integer primary key autoincrement,%s TEXT,%s TEXT);", Tips.tablename, Tips.content, Tips.time);
        System.out.println("qqqq");
        System.out.println(sql);
        db.execSQL(sql);
    }
    //表的更新
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion<newVersion){
            String sql = String.format("DROP TABLE IF EXISTS%s", Tips.tablename);
            db.execSQL(sql);
            this.onCreate(db);
        }
    }
    //插入
    public void insertTip(TipsDdataBaseHelper dbHelper, ContentValues userValues){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        db.insert(Tips.tablename,null,userValues);
        db.close();
    }
    //删除一条tip
    public void deleteTip(TipsDdataBaseHelper dbHelper, int _id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(Tips.tablename,Tips._id +"=?",new String[]{_id+""});
        db.close();
    }
    //删除所有tips
    public void deleteALLTip(TipsDdataBaseHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Tips.tablename,null,null);
        db.close();
    }
    //修改
    public void updataTip(TipsDdataBaseHelper dbHelper,int _id,ContentValues infoValues){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.update(Tips.tablename,infoValues,Tips._id+"=?",new String[]{_id+""});
        db.close();
    }
    //以HashMap<String,Object>形式获取一条信息
    public HashMap<String,Object> getTip(TipsDdataBaseHelper dbHelper, int _id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        HashMap<String, Object> TipMap = new HashMap<String, Object>();
        // 此处要求查询Tip._id为传入参数_id的对应记录，使游标指向此记录
        Cursor cursor = db.query( Tips.tablename, null, Tips._id + " =? ", new String[]{ _id + "" }, null, null, null);
        cursor.moveToFirst();
        TipMap.put(Tips.content, cursor.getString(cursor.getColumnIndex(Tips.content)));
        TipMap.put(Tips.time, cursor.getString(cursor.getColumnIndex(Tips.time)));
        return TipMap;
    }
    //获得查询指向Tip表的游标
    public Cursor getALLTips(TipsDdataBaseHelper dbHelper){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Tips.tablename, null, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }
}
