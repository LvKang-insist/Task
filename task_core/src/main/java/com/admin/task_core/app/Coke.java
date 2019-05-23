package com.admin.task_core.app;

import android.content.Context;
import android.os.Handler;

import java.util.HashMap;

/**
  * @description: $初始化
 */
@SuppressWarnings("WeakerAccess")
public class Coke {
    /**
     * 全局handle
     */
    private final static Handler HANDLER  = new Handler();
    public static Handler getHandler() {
        return HANDLER;
    }

    /**
     * 初始化 配置
     *
     * @return 配置对象
     */
    public static Configurator init(Context context) {
        getConfigs()
                .put(ConfigType.APP_CONTEXT, context);
        return Configurator.getInstance();
    }

    public static HashMap<Object, Object> getConfigs() {
        return Configurator.getConfigs();
    }

    public static Object getConfig(Object key) {
        return Configurator.getConfiguration(key);
    }

    public static Context getAppContext() {
        Context context = (Context) Configurator.getConfiguration(ConfigType.APP_CONTEXT);
        if (context == null) {
            throw new NullPointerException("context 未初始化");
        } else {
            return context;
        }
    }
}
