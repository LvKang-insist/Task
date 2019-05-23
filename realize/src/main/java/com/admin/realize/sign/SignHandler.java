package com.admin.realize.sign;

import android.widget.Toast;

import com.admin.realize.mian.database.DatabaseManager;
import com.admin.realize.mian.database.table.DaoSession;
import com.admin.task_core.app.Coke;

import java.util.List;


/**
 * Copyright (C)
 *
 * @file: SignHandler
 * @author: 345
 * @Time: 2019/4/22 19:39
 * @description: ${DESCRIPTION}
 */
public class SignHandler {

    private static UserProfileDao getDao(){
        DaoSession dao = DatabaseManager.getInstance().getDao();
        return dao.getUserProfileDao();
    }

    /**
     * @param userProfile 用户注册的数据
     * @param signListener
     */
    public static void onSignUp(UserProfile userProfile,ISignListener signListener){
        //插入数据
        getDao().insertOrReplace(userProfile);
        // 注册成功 回调到activity中
        signListener.onSignUpSuccess();
    }
    /**
     * @param userProfile 用户登录的数据
     * @param signListener
     */
    public static void onSignIn(UserProfile userProfile,ISignListener signListener){
        List<UserProfile> userProfiles = getDao().loadAll();
        for(UserProfile up : userProfiles){
            if (userProfile.getNumber().equals(up.getNumber())){
                if (userProfile.getPassword().equals(up.getPassword())){
                    Toast.makeText(Coke.getAppContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    signListener.onSignInSuccess();
                    return;
                }
            }
        }
        Toast.makeText(Coke.getAppContext(), "账号或密码错误", Toast.LENGTH_SHORT).show();
    }
}
