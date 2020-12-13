package com.example.ftest;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class Weekbar extends AppCompatTextView {

    public String[] days = {"日", "一", "二", "三", "四", "五", "六"};
    private int type;//一周的第一天是周几
    private TextPaint textPaint;
    public Weekbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FTest);
    }
}
