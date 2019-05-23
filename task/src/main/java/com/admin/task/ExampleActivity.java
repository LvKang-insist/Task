package com.admin.task;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.admin.realize.mian.ClothDelegate;
import com.admin.realize.sign.ISignListener;
import com.admin.realize.sign.SignInDelegate;
import com.admin.task_core.activitys.ProxyActivity;
import com.admin.task_core.app.Coke;
import com.admin.task_core.delegate.CoKeDelegate;

import qiu.niorgai.StatusBarCompat;


/**
 * @author Lv
 */

/**
 * 根Activity 。全局 只有一个Activity。继承自抽象的 ProxyActivity
 */
public class ExampleActivity extends ProxyActivity implements ISignListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.hide();
        }
        //沉浸式状态栏
        StatusBarCompat.translucentStatusBar(this,true);
    }

    @Override
    public CoKeDelegate setRootDelegate() {
        SharedPreferences sp = Coke.getAppContext().getSharedPreferences("check",0);
        Boolean isCheck =  sp.getBoolean("isCheck",false);
        if (!isCheck) {
            return new SignInDelegate();
        }else {
            return new ClothDelegate();
        }
    }


    @Override
    public void onSignInSuccess() {
        getSupportDelegate().start(new ClothDelegate());
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
    }
}
