package com.admin.task_core.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Toast;

import com.admin.task_core.R;
import com.admin.task_core.app.Coke;

import java.util.Calendar;
import java.util.TimeZone;

public class ClendarThree {

    //Android2.2版本以后的URL，之前的就不写了
    private static String calanderURL = "content://com.android.calendar/calendars";
    private static String calanderEventURL = "content://com.android.calendar/events";
    private static String calanderRemiderURL = "content://com.android.calendar/reminders";

    public void Read() {
        Cursor userCursor = Coke.getAppContext().getContentResolver().query(Uri.parse(calanderURL), null, null, null, null);

        System.out.println("Count: " + userCursor.getCount());
        Toast.makeText(Coke.getAppContext(), "Count: " + userCursor.getCount(), Toast.LENGTH_LONG).show();

        for (userCursor.moveToFirst(); !userCursor.isAfterLast(); userCursor.moveToNext()) {
            System.out.println("name: " + userCursor.getString(userCursor.getColumnIndex("ACCOUNT_NAME")));

            String userName1 = userCursor.getString(userCursor.getColumnIndex("name"));
            String userName0 = userCursor.getString(userCursor.getColumnIndex("ACCOUNT_NAME"));
//            Toast.makeText(this, "NAME: " + userName1 + " -- ACCOUNT_NAME: " + userName0, Toast.LENGTH_LONG).show();
        }
    }

    public void delete(){
        int rownum =Coke.getAppContext(). getContentResolver().delete(Uri.parse(calanderURL), "_id!=-1", null);  //注意：会全部删除所有账户，新添加的账户一般从id=1开始，
    }

    public void inputaccount(){
        initCalendars();
    }

    private void readEvent(){
        Cursor eventCursor = Coke.getAppContext().getContentResolver().query(Uri.parse(calanderEventURL), null, null, null, null);
        if (eventCursor.getCount() > 0) {
            eventCursor.moveToLast();             //注意：这里与添加事件时的账户相对应，都是向最后一个账户添加
            String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));
        }
    }

    public void write(){
        // 获取要出入的gmail账户的id
        String calId = "";
        try {
            Cursor userCursor = Coke.getAppContext().getContentResolver().query(Uri.parse(calanderURL), null, null, null, null);
            if (userCursor.getCount() > 0) {
                userCursor.moveToLast();  //注意：是向最后一个账户添加，开发者可以根据需要改变添加事件 的账户
                calId = userCursor.getString(userCursor.getColumnIndex("_id"));
            } else {
                initCalendars();
                Toast.makeText(Coke.getAppContext(), "没有账户，请先添加账户", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ContentValues event = new ContentValues();
        event.put("title", "提醒测试Title");
        event.put("description", "这是一条日历提醒测试内容.lol~");
        // 插入账户
        event.put("calendar_id", calId);
        System.out.println("calId: " + calId);
        event.put("eventLocation", "测试地点");

        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.HOUR_OF_DAY, 14);
        mCalendar.set(Calendar.MINUTE, 53);
        long start = mCalendar.getTime().getTime();
        mCalendar.set(Calendar.HOUR_OF_DAY, 15);
        long end = mCalendar.getTime().getTime();
        event.put(CalendarContract.Events.DTSTART, start);//开始时间
        event.put(CalendarContract.Events.DTEND, end);//结束时间
        //设置一个全天事件的条目
        //event.put("allDay", 1); // 0 for false, 1 for true
        //事件状态暂定(0)，确认(1)或取消(2)
        //event.put("eventStatus", 1);
        //控制是否事件触发报警，提醒如下
        event.put(CalendarContract.Events.HAS_ALARM, 1); // 0 for false, 1 for true
        //设置时区,否则会报错
        event.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
        //添加事件
        Uri newEvent = null;
        try {
            newEvent = Coke.getAppContext().getContentResolver().insert(Uri.parse(calanderEventURL), event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (newEvent == null){
            return;
        }
        //事件提醒的设定
        long id = Long.parseLong(newEvent.getLastPathSegment());
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, id);
        // 提前5分钟有提醒(提前0分钟时，值为0)
        values.put(CalendarContract.Reminders.MINUTES, 5);
        values.put(CalendarContract.Reminders.METHOD, 1);
        Uri uri = Coke.getAppContext().getContentResolver().insert(Uri.parse(calanderRemiderURL), values);
        if (uri == null) {
            // 添加闹钟提醒失败直接返回
            Toast.makeText(Coke.getAppContext(), "插入事件失败!!!", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(Coke.getAppContext(), "插入事件成功!!!", Toast.LENGTH_LONG).show();
    }


    //添加账户
    private void initCalendars() {

        TimeZone timeZone = TimeZone.getDefault();
        ContentValues value = new ContentValues();
        value.put(CalendarContract.Calendars.NAME, "yy");

        value.put(CalendarContract.Calendars.ACCOUNT_NAME, "mygmailaddress@gmail.com");
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, "com.android.exchange");
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "mytt");
        value.put(CalendarContract.Calendars.VISIBLE, 1);
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, -9206951);
        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, "mygmailaddress@gmail.com");
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);

        Uri calendarUri = CalendarContract.Calendars.CONTENT_URI;
        calendarUri = calendarUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, "mygmailaddress@gmail.com")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, "com.android.exchange")
                .build();

        Coke.getAppContext().getContentResolver().insert(calendarUri, value);
    }


}
