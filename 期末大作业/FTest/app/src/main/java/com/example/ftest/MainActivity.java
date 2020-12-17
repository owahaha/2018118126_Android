package com.example.ftest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.necer.calendar.BaseCalendar;
import com.necer.calendar.Miui10Calendar;
import com.necer.entity.CalendarDate;
import com.necer.entity.Lunar;
import com.necer.listener.OnCalendarChangedListener;
import com.necer.utils.CalendarUtil;
import org.joda.time.LocalDate;

import java.util.Calendar;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

public class MainActivity extends AppCompatActivity {
    private Miui10Calendar miui10Calendar;
    private TextView tv_result;
    private TextView tv_data;
    private TextView tv_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_result = findViewById(R.id.tv_result);
        tv_data = findViewById(R.id.tv_data);
        tv_desc = findViewById(R.id.tv_desc);
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

    }
}
