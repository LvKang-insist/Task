package com.admin.realize.mian.index;

import android.content.SharedPreferences;

import com.admin.realize.mian.database.DatabaseManager;
import com.admin.realize.mian.database.table.DaoSession;
import com.admin.realize.mian.database.table.IndexTable;
import com.admin.task_core.app.Coke;

public class InitTable {
    public static void initTable(){
        SharedPreferences sp = Coke.getAppContext().getSharedPreferences("index_table",0);
        if (sp.getBoolean("index",true)){
            IndexTable one = new IndexTable(1,"","","","","","","");
            IndexTable two = new IndexTable(2,"","","","","","","");
            IndexTable three = new IndexTable(3,"","","","","","","");
            IndexTable four = new IndexTable(4,"","","","","","","");
            IndexTable five = new IndexTable(5,"","","","","","","");
            IndexTable six = new IndexTable(6,"","","","","","","");
            DaoSession dao = DatabaseManager.getInstance().getDao();
            dao.getIndexTableDao().insert(one);
            dao.getIndexTableDao().insert(two);
            dao.getIndexTableDao().insert(three);
            dao.getIndexTableDao().insert(four);
            dao.getIndexTableDao().insert(five);
            dao.getIndexTableDao().insert(six);
            //以后不再初始化
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean("index",false);
            edit.apply();
        }
    }
}
