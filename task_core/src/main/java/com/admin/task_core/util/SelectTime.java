package com.admin.task_core.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
@SuppressLint("SimpleDateFormat")
public class SelectTime {

    public interface onTimeListener{
        void onTime(long string);
    }

    private Date curDate = new Date(System.currentTimeMillis());
    private SimpleDateFormat formatterYear = new SimpleDateFormat("yyyy ");
    private String yearStr = formatterYear.format(curDate);
    int yearInt = (int) Double.parseDouble(yearStr);

    private SimpleDateFormat formatterMouth = new SimpleDateFormat("MM ");
    private String mouthStr = formatterMouth.format(curDate);
    int mouthInt = (int) Double.parseDouble(mouthStr);

    private SimpleDateFormat formatterDay = new SimpleDateFormat("dd ");
    private String dayStr = formatterDay.format(curDate);
    int dayInt = (int) Double.parseDouble(dayStr);

    private onTimeListener listener;

    public void setListener(onTimeListener listener) {
        this.listener = listener;
    }

    public void getT(Context context){
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);
//        Calendar endDate = Calendar.getInstance();
//        endDate.set(yearInt, mouthInt - 1, dayInt);

        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (listener != null){
                    listener.onTime(date.getTime());
                }
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, true, true, true})
                //默认设置为年月日时分秒
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false)
                //设置选中项的颜色
                .setTextColorCenter(Color.RED)
                //设置没有被选中项的颜色
                .setTextColorOut(Color.BLUE)
                .setDate(selectedDate)
                .setContentTextSize(20)
                .setLineSpacingMultiplier(1.2f)
                //设置X轴倾斜角度[ -90 , 90°]
                .setTextXOffset(-10, 0, 10, 0, 0, 0)
//                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build();
        pvTime.show();
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
