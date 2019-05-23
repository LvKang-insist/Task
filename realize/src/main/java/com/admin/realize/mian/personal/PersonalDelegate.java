package com.admin.realize.mian.personal;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.admin.realize.R;
import com.admin.realize.R2;
import com.admin.realize.mian.personal.list.ListAdapter;
import com.admin.realize.mian.personal.list.ListBean;
import com.admin.realize.mian.personal.list.ListItemType;
import com.admin.realize.mian.personal.settings.SettingDelegate;
import com.admin.realize.sign.SignInDelegate;
import com.admin.task_core.app.Coke;
import com.admin.task_core.delegate.bottom.BottomItemDelegate;
import com.admin.task_core.util.callback.CallBackType;
import com.admin.task_core.util.callback.CallbackManager;
import com.admin.task_core.util.callback.IGlobalCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @description: 我的
 */
public class PersonalDelegate extends BottomItemDelegate {

    @BindView(R2.id.rv_personal_setting)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.persoal_name)
    AppCompatTextView per_name = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_personal;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        CallbackManager.getInstance().addCallback(CallBackType.PASSWORD, new IGlobalCallback() {
            @Override
            public void executeCallBack(Object args) {
                getParentDelegate().getSupportDelegate().replaceFragment(new SignInDelegate(),false);
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        ListBean name = new ListBean.Builder()
                .setmItemType(ListItemType.PER_ITEM)
                .setmText("座右铭:  ")
                .setmId(0)
                .build();

        ListBean mb = new ListBean.Builder()
                .setmItemType(ListItemType.PER_ITEM)
                .setmText("目标:  ")
                .setmId(1)
                .build();

        ListBean setting = new ListBean.Builder()
                .setmItemType(ListItemType.PER_ITEM)
                .setmText("设置")
                .setmCokeDelegate(new SettingDelegate())
                .setmId(2)
                .build();
        final List<ListBean> list = new ArrayList<>();
        list.add(name);
        list.add(mb);
        list.add(setting);
        ListAdapter adapter = new ListAdapter(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new PersonalClickListener(this,getContext()));
        setName();
    }

    public void setName(){
        SharedPreferences sp = getContext().getSharedPreferences("xx",0);
        per_name.setText(sp.getString("name","点击进行设置"));
        per_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomizeDialog("设置姓名");
            }
        });
    }

    private void showCustomizeDialog( String title) {
        final SharedPreferences.Editor editor = Coke.getAppContext().getSharedPreferences("xx", 0).edit();


        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(0);
        final View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.setting_dialog, null);
        dialog.setContentView(dialogView);
        dialog.show();

        AppCompatTextView head = dialogView.findViewById(R.id.dialog_title);
        head.setText(title);
        AppCompatTextView queding = dialogView.findViewById(R.id.dialog_queding);
        AppCompatTextView quxiao = dialogView.findViewById(R.id.dialog_quxiao);
        final AppCompatEditText editText = dialog.findViewById(R.id.dialog_edit);

        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = editText.getText().toString().trim();
                if (!"".equals(trim)) {
                    per_name.setText(trim);
                    editor.putString("name",trim);
                    editor.apply();
                    dialog.dismiss();
                }

            }
        });
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}
