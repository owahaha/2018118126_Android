package com.example.listener;

import com.example.calendar.BaseCalendar;

import java.time.LocalDate;

public interface OnCalendarChangedListener {
    void onCalendarChange(BaseCalendar baseCalendar, int year, int month, LocalDate localDate);
}
