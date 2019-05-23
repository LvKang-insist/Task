package com.admin.realize.sign;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.admin.realize.R;
import com.admin.realize.R2;
import com.admin.realize.mian.database.DatabaseManager;
import com.admin.realize.mian.database.table.DaoSession;
import com.admin.task_core.app.Coke;
import com.admin.task_core.delegate.CoKeDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @description: 登录界面
 */
public class SignInDelegate extends CoKeDelegate {

    @BindView(R2.id.edit_sign_in_number)
    TextInputEditText mNumber = null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword = null;
    @BindView(R2.id.tv_link_sign_chekc)
    CheckBox checkBox = null;

    private UserProfile mUserProfile = null;
    ISignListener mIsignListener = null;

    /**
     * @param activity 当前的Activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //判断 当前的 activity 有没有实现这个接口
        if (activity instanceof ISignListener){
            //向上转型
            mIsignListener = (ISignListener) activity;
        }
    }
    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn(){
        if (checkForm()){
            //验证账号密码
            SignHandler.onSignIn(mUserProfile,mIsignListener);
        }
    }


    @OnClick(R2.id.tv_link_sign_up)
    void onClickLink(){
        getSupportDelegate().start(new SignUpDelegate());
    }

    private boolean checkForm() {
        final String number = mNumber.getText().toString();
        final String password = mPassword.getText().toString();
        boolean isPass = true;

        //判断账号格式是否正确
        if (number.isEmpty() ) {
            mNumber.setError("账户不能为空");
            isPass = false;
        } else {
            mNumber.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请至少填写 6位数的密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        if (isPass){
            mUserProfile = new UserProfile(number,password,"");
        }
        return isPass;
    }
    public void checkSign(){
        SharedPreferences sp = Coke.getAppContext().getSharedPreferences("check",0);
        Boolean isCheck =  sp.getBoolean("isCheck",false);
        checkBox.setChecked(isCheck);
        if (isCheck){
            DaoSession dao = DatabaseManager.getInstance().getDao();
            List<UserProfile> userProfiles = dao.getUserProfileDao().loadAll();
            UserProfile userProfile = userProfiles.get(0);
            String num = userProfile.getNumber();
            String pass = userProfile.getPassword();
            mNumber.setText(num);
            mPassword.setText(pass);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sp = Coke.getAppContext().getSharedPreferences("check",0);
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("isCheck",isChecked);
                edit.apply();
            }
        });
        checkSign();
    }
}
