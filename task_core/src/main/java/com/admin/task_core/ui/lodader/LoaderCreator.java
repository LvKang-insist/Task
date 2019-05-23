package com.admin.task_core.ui.lodader;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
  * @description: 通过反射获取 loading 的动画
 */
public class LoaderCreator {
    //进行缓存
    private static final WeakHashMap<String,Indicator> LOADING_MAP = new WeakHashMap<>();

    static AVLoadingIndicatorView creawte(String type, Context context){
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        if (LOADING_MAP.get(type) == null){
            //根据传入的 type 进行反射 获取动画
            final Indicator indicator = getIndicator(type);
            LOADING_MAP.put(type,indicator);
        }
        //设置 这个动画
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }

    private static Indicator getIndicator(String name){
        if (name == null || name.isEmpty()){
            return null;
        }
        final StringBuilder drawbleClassName = new StringBuilder();
        //如果类名不包含 . 就说明传入的是一个 整个的类名，否则就是包名
        if (!name.contains(".")){
            // 拼装一个完整的路径，这个路径就是 动画类所在的位置。
            final String defultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            drawbleClassName.append(defultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        drawbleClassName.append(name);
        try {
            //通过反射 拿到给定的类
            final Class<?> drawableClass = Class.forName(drawbleClassName.toString());
            //创建 该对象并返回
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
