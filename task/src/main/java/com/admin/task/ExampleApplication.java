package com.admin.task;

import android.app.Application;

import com.admin.realize.mian.database.DatabaseManager;
import com.admin.realize.mian.index.InitTable;
import com.admin.task_core.app.Coke;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
  * @description: ${DESCRIPTION}
 */
public class ExampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        Coke.init(getApplicationContext())
                .withIcon(new FontAwesomeModule())
                .configure();
        //初始化数据库
        DatabaseManager.getInstance().init(this);
        InitTable.initTable();

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
