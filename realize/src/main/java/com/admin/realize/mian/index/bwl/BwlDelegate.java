package com.admin.realize.mian.index.bwl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.admin.realize.R;
import com.admin.realize.R2;
import com.admin.task_core.delegate.CoKeDelegate;
import com.admin.task_core.util.SetToolBar;

import butterknife.BindView;
import butterknife.OnClick;

public class BwlDelegate extends CoKeDelegate implements onSaveListener {

    @BindView(R2.id.bwl_rv)
    RecyclerView mRecyclerView = null;

    @BindView(R2.id.toolbar_bwl)
    Toolbar mToolbar = null;
    private Adapter adapter;

    /**
     * 添加 备忘录
     */
    @OnClick(R2.id.bwl_add)
    void onClickAdd() {
        Bwl_edit edit = new Bwl_edit();
        edit.setListener(this);
        getSupportDelegate().start(edit);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_bwl;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        SetToolBar.setToolBar(mToolbar);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        adapter = new Adapter(new BwlDataConvert().convert(),this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onSave() {
        adapter.getData().clear();
        adapter.setNewData(new BwlDataConvert().convert());
        adapter.notifyItemRangeChanged(0, adapter.getItemCount());
    }
}
