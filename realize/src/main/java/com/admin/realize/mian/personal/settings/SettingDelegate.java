package com.admin.realize.mian.personal.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.admin.realize.R;
import com.admin.realize.R2;
import com.admin.realize.mian.personal.list.ListAdapter;
import com.admin.realize.mian.personal.list.ListBean;
import com.admin.realize.mian.personal.list.ListItemType;
import com.admin.task_core.delegate.CoKeDelegate;
import com.admin.task_core.util.SetToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @description: ${DESCRIPTION}
 */
public class SettingDelegate extends CoKeDelegate {

    @BindView(R2.id.settings_toolbar)
    Toolbar mToolbar = null;
    @BindView(R2.id.rv_settings)
    RecyclerView mRecyclerView = null;

    private ListAdapter adapter;

    @Override
    public Object setLayout() {
        return R.layout.delegate_setting;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        SetToolBar.setToolBar(mToolbar);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        setting();
    }

    private void setting() {
        ListBean updata = new ListBean.Builder()
                .setmItemType(ListItemType.PER_ITEM)
                .setmId(2)
                .setmText("修改密码")
                .build();
        ListBean pass = new ListBean.Builder()
                .setmItemType(ListItemType.PER_ITEM)
                .setmId(3)
                .setmText("忘记密码")
                .build();
        ListBean about = new ListBean.Builder()
                .setmItemType(ListItemType.PER_ITEM)
                .setmId(4)
                .setmText("退出登录")
                .build();

        List<ListBean> list = new ArrayList<>();
        list.add(updata);
        list.add(pass);
        list.add(about);

        adapter = new ListAdapter(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new SettingOnclickListener(getContext(),this));
    }

}
