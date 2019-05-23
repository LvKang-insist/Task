package com.admin.task_core.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.admin.task_core.ui.recycler.DataConvert;
import com.admin.task_core.ui.recycler.MultipleItemBean;
import com.admin.task_core.ui.recycler.MultipleRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * @description: ${DESCRIPTION}
 */
public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private MultipleRecyclerViewAdapter mAdapter;
    private final RecyclerView RECYCLERVIEW;
    private final DataConvert DATA_CONVERT;
    ArrayList<MultipleItemBean> convert;
    private RefreshHandler(SwipeRefreshLayout refreshLayout,
                           RecyclerView recyclerView,
                           DataConvert convert) {
        this.REFRESH_LAYOUT = refreshLayout;
        this.RECYCLERVIEW = recyclerView;
        this.DATA_CONVERT = convert;
        //监听滑动事件
    }


    public static RefreshHandler create(SwipeRefreshLayout refreshLayout,
                                  RecyclerView recyclerView,
                                  DataConvert convert){
        return new RefreshHandler(refreshLayout,recyclerView,convert);
    }

    public void firstPage(){
        convert = DATA_CONVERT.convert();
        mAdapter =MultipleRecyclerViewAdapter.create(convert);
        RECYCLERVIEW.setAdapter(mAdapter);
    }
    public void setTable(){
        if (convert != null){
            convert .clear();
            mAdapter.setNewData(DATA_CONVERT.convert());
            mAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onRefresh() {

    }
}
