package com.admin.realize.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import com.admin.realize.R;
import com.admin.realize.R2;
import com.admin.task_core.delegate.CoKeDelegate;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C)
 *
 * @file: SignUpDelegate
 * @author: 345
 * @Time: 2019/4/21 21:21
 * @description: 注册界面
 */
public class SignUpDelegate extends CoKeDelegate {

    private ISignListener mIsignListener = null;
    @BindView(R2.id.edit_sign_up_passwrod)
    TextInputEditText password = null;
    @BindView(R2.id.edit_sign_up_number)
    TextInputEditText number = null;

    @BindView(R2.id.edit_sign_in_Tpassword)
    TextInputEditText passTwo = null;

    @BindView(R2.id.edit_sign_up_mibao)
    TextInputEditText mibao = null;

    UserProfile userProfile = null;


    /**
     * @param activity 当前的Activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //判断 当前的 activity 有没有实现这个接口
        if (activity instanceof ISignListener) {
            //向上转型
            mIsignListener = (ISignListener) activity;
        }
    }

    @OnClick(R2.id.btn_sign_up)
    void onClickSignUp() {
        if (checkForm()) {
            //进行注册
            SignHandler.onSignUp(userProfile, mIsignListener);
        }
    }

    @OnClick(R2.id.tv_link_sign_in)
    void onClickLink() {
        //进入登录界面
        getSupportDelegate().pop();
    }

    private boolean checkForm() {
        final String name = number.getText().toString();
        final String rePassword = password.getText().toString();
        final String pasTow = passTwo.getText().toString();
        final String mb = mibao.getText().toString();
        boolean isPass = true;
        if (name.isEmpty()) {
            number.setError("请输入账号");
            isPass = false;
        } else {
            number.setError(null);
        }

        if ("".equals(password) || password.length() < 6) {
            password.setError("请至少填写 6位数的密码");
            isPass = false;
        } else {
            password.setError(null);
        }
        if (!pasTow.equals(rePassword)){
            passTwo.setError("密码重复");
        }else {
            passTwo.setError(null);
        }

        userProfile = new UserProfile(name, rePassword,mb);
        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    }


}
