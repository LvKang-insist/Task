package com.admin.realize.mian.match;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.admin.realize.R;
import com.admin.realize.R2;
import com.admin.task_core.delegate.bottom.BottomItemDelegate;
import com.admin.task_core.util.SetToolBar;

import butterknife.BindView;

/**
 * @description: 学习计划
 */
public class MatchDelegate extends BottomItemDelegate {

    @BindView(R2.id.toolbar_match)
    Toolbar mToolbar = null;
    @BindView(R2.id.match_rv)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_match;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        SetToolBar.setToolBar(mToolbar);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        MatchAdapter adapter = new MatchAdapter(new MatchDataConvert().convert());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new MatchOnclickListener(this,getContext()));
    }
}
