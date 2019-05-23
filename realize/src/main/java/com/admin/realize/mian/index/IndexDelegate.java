package com.admin.realize.mian.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.admin.realize.R;
import com.admin.realize.R2;
import com.admin.realize.mian.index.bwl.BwlDelegate;
import com.admin.task_core.delegate.bottom.BottomItemDelegate;
import com.admin.task_core.ui.refresh.RefreshHandler;
import com.admin.task_core.util.SetToolBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @description: 课程界面
 */
public class IndexDelegate extends BottomItemDelegate {

    @BindView(R2.id.index_toolbar)
    Toolbar mToolbar = null;
    @BindView(R2.id.index_rv)
    RecyclerView mRecyclerView = null;

    @BindView(R2.id.index_bwl)
    AppCompatTextView mTextView = null;

    @OnClick(R2.id.index_bwl)
    void onClick(){
        getParentDelegate().getSupportDelegate().start(new BwlDelegate());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }
    RefreshHandler refreshHandler = null;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        SetToolBar.setToolBar(mToolbar);
        refreshHandler = RefreshHandler.create(null, mRecyclerView, new IndexDataConvert());
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        GridLayoutManager manager = new GridLayoutManager(getContext(),8);
        mRecyclerView.setLayoutManager(manager);
        refreshHandler.firstPage();
        mRecyclerView.addOnItemTouchListener(new IndexOnClickListener(getContext(),this));
    }

    public void setTable() {
        refreshHandler.setTable();
    }

}
