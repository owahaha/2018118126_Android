package com.example.calendar;

import com.example.emumeration.MultipleNumModel;
import com.example.emumeration.SelectedModel;
import com.example.listener.OnCalendarChangedListener;
import com.example.painter.CalendarPainter;

import java.util.List;

interface ICalendar {
    /**
     * 设置选中模式
     *
     * @param selectedMode SINGLE_SELECTED-单个默认选中  默认模式
     *                     SINGLE_UNSELECTED-单个不选中
     *                     MULTIPLE-多选
     */
    void setSelectedMode(SelectedModel selectedMode);

    /**
     * 多选个数和模式
     *
     * @param multipleNum      多选个数
     * @param multipleNumModel FULL_CLEAR-超过清除所有
     *                         FULL_REMOVE_FIRST-超过清除第一个
     */
    void setMultipleNum(int multipleNum, MultipleNumModel multipleNumModel);


    //默认选中时，是否翻页选中第一个，只在selectedMode==SINGLE_SELECTED有效
    void setDefaultSelectFitst(boolean isDefaultSelectFitst);


    //跳转日期
    void jumpDate(String formatDate);

    //上一页 上一周 上一月
    void toLastPager();

    //下一页 下一周 下一月
    void toNextPager();

    //回到今天
    void toToday();

    //设置自定义绘制类
    void setCalendarPainter(CalendarPainter calendarPainter);

    //刷新日历
    void notifyCalendar();

    //设置初始化日期
    void setInitializeDate(String formatInitializeDate);

    //设置初始化日期和可用区间
    void setDateInterval(String startFormatDate, String endFormatDate, String formatInitializeDate);

    //设置可用区间
    void setDateInterval(String startFormatDate, String endFormatDate);

    //单选日期变化监听
    void setOnCalendarChangedListener(OnCalendarChangedListener onCalendarChangedListener);

    //多选日期变化监听
    void setOnCalendarMultipleChangedListener(OnCalendarMultipleChangedListener onCalendarMultipleChangedListener);

    //设置点击了不可用日期监听
    void setOnClickDisableDateListener(OnClickDisableDateListener onClickDisableDateListener);

    //获取xml参数
    Attrs getAttrs();

    //获取绘制类
    CalendarPainter getCalendarPainter();

    //获取全部选中的日期集合
    List<LocalDate> getAllSelectDateList();

    //获取当前页面选中的日期集合
    List<LocalDate> getCurrectSelectDateList();

    //获取当前页面的数据 如果是月周折叠日历 周状态下获取的是一周的数据，月状态下获取的一月的数据
    List<LocalDate> getCurrectDateList();
}
