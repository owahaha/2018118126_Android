package com.example.ftest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.necer.calendar.BaseCalendar;
import com.necer.calendar.Miui10Calendar;
import com.necer.entity.CalendarDate;
import com.necer.entity.Lunar;
import com.necer.listener.OnCalendarChangedListener;
import com.necer.utils.CalendarUtil;
import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

public class MainActivity extends AppCompatActivity {
    private Miui10Calendar miui10Calendar;
    private TextView tv_result;
    private TextView tv_data;
    private TextView tv_desc;

    private Button tips_new;
    private ListView tipListView;
    private List<TipInfo> tiplist = new ArrayList<>();
    private ListAdapter mListAdapter;
    private TipsDdataBaseHelper dbHelper;
    private Tips tips;
    private String city_now;
    private TipInfo currentTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_result = findViewById(R.id.tv_result);
        tv_data = findViewById(R.id.tv_data);
        tv_desc = findViewById(R.id.tv_desc);
        tips_new=findViewById(R.id.tips_new);
        tipListView =findViewById(R.id.list_tips);

        //创建数据库
        dbHelper =new TipsDdataBaseHelper(this,"MyTips.db",null,1);
        mListAdapter=new com.example.ftest.ListAdapter(MainActivity.this,tiplist);
        setListViewHeightBasedOnChildren(tipListView);
        tipListView.setAdapter(mListAdapter);
        tips = Tips.getInstance();
        getTipList();
        Intent intent = getIntent();
        if (intent != null){
            getTipList();
            mListAdapter=new com.example.ftest.ListAdapter(MainActivity.this,tiplist);
            setListViewHeightBasedOnChildren(tipListView);
            tipListView.setAdapter(mListAdapter);
        }
        //点击修改
        tipListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View view1=getLayoutInflater().inflate(R.layout.edittext,null);
                final EditText et=(EditText)view1.findViewById(R.id.tips_input);
                currentTip = tiplist.get(position);
                et.setText(currentTip.getContent());
                new AlertDialog.Builder(MainActivity.this).setTitle("便利贴")
                        .setIcon(R.drawable.ic_weather
                        ).setView(view1)
                        .setPositiveButton("修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String content =et.getText().toString();
                                new_tip(content,1);
                                getTipList();
                                mListAdapter=new com.example.ftest.ListAdapter(MainActivity.this,tiplist);
                                setListViewHeightBasedOnChildren(tipListView);
                                tipListView.setAdapter(mListAdapter);
                            }
                        }).setNegativeButton("取消",null).show();
            }
        });
        //长按删除
        tipListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final TipInfo tipinfo = tiplist.get(position);
                String title ="警告";
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.ic_weather)
                        .setTitle(title)
                        .setMessage("确定要删除吗?")
                        .setPositiveButton("我想好了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tips = Tips.getInstance();
                                tips.deleteTip(dbHelper,Integer.parseInt(tipinfo.getId()));
                                tiplist.remove(position);
                                getTipList();
                                mListAdapter=new com.example.ftest.ListAdapter(MainActivity.this,tiplist);
                                setListViewHeightBasedOnChildren(tipListView);
                                tipListView.setAdapter(mListAdapter);
                                Toast.makeText(MainActivity.this,"删除成功！",Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                }).create().show();
                return true;
            }
        });

        //日期选择时变化
        miui10Calendar = findViewById(R.id.miui10Calendar);
        miui10Calendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, LocalDate localDate) {
                tv_result.setText(year + "年" + month + "月"+"▼" );

                if (localDate != null) {
                    CalendarDate calendarDate = CalendarUtil.getCalendarDate(localDate);
                    Lunar lunar = calendarDate.lunar;
                    tv_data.setText(localDate.toString("yyyy年MM月dd日"));
                    tv_desc.setText(lunar.chineseEra + lunar.animals + "年" + lunar.lunarMonthStr + lunar.lunarDayStr);
                } else {
                    tv_data.setText("");
                    tv_desc.setText("");
                }
            }
        });

        //实现选择日期跳转
        tv_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                new DatePickerDialog( MainActivity.this,THEME_HOLO_LIGHT,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        miui10Calendar.jumpDate(year, (month+1), dayOfMonth);
                    }
                }
                        ,calendar.get(Calendar.YEAR)
                        ,calendar.get(Calendar.MONTH)
                        ,calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //添加tips按钮事件：弹窗输入（写入数据库）
        tips_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view=getLayoutInflater().inflate(R.layout.edittext,null);
                final EditText et=(EditText)view.findViewById(R.id.tips_input);
                new AlertDialog.Builder(MainActivity.this).setTitle("便利贴")
                        .setIcon(R.drawable.ic_weather
                        ).setView(view)
                        .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String content =et.getText().toString();
                                new_tip(content,0);
                                setListViewHeightBasedOnChildren(tipListView);
                                tipListView.setAdapter(mListAdapter);
                            }
                        }).setNegativeButton("取消",null).show();
            }
        });
    }

    private void new_tip(String content, int i) {
        tips = Tips.getInstance();
        ContentValues values = new ContentValues();
        values.put(Tips.content, content);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        values.put(Tips.time, sdf.format(date));
        if (i==0) {
            tips.insertTip(dbHelper, values);
            Toast.makeText(this, "添加成功！",Toast.LENGTH_SHORT).show();
        }else{
            tips.updataTip(dbHelper,Integer.parseInt(currentTip.getId()),values);
            Toast.makeText(this, "修改成功！",Toast.LENGTH_SHORT).show();
        }
        getTipList();
    }

    private void getTipList() {
        tiplist.clear();
        tips = Tips.getInstance();
        Cursor allNotes= tips.getALLTips(dbHelper);
        for (allNotes.moveToFirst(); !allNotes.isAfterLast(); allNotes.moveToNext()){
            TipInfo tipInfo = new TipInfo();
            tipInfo.setId(allNotes.getString(allNotes.getColumnIndex(Tips._id)));
            tipInfo.setContent(allNotes.getString(allNotes.getColumnIndex(Tips.content)));
            tipInfo.setTime(allNotes.getString(allNotes.getColumnIndex(Tips.time)));
            tiplist.add(tipInfo);
        }
    }

    private void setListViewHeightBasedOnChildren(ListView tipListView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = tipListView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, tipListView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = tipListView.getLayoutParams();
        params.height = totalHeight + (tipListView.getDividerHeight() *(listAdapter.getCount() - 1));
// listView.getDividerHeight()获取子项间分隔符占用的高度
// params.height最后得到整个ListView完整显示需要的高度
        tipListView.setLayoutParams(params);
    }

}
