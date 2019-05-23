package com.admin.realize.mian.note;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.admin.realize.R;
import com.admin.realize.R2;
import com.admin.realize.mian.note.edit.EditDelegate;
import com.admin.realize.mian.note.edit.OnResetEditListener;
import com.admin.task_core.delegate.bottom.BottomItemDelegate;

import butterknife.BindView;
import butterknife.OnClick;

public class NoteSortDelegate extends BottomItemDelegate implements OnResetEditListener {

    @BindView(R2.id.note_rv)
    RecyclerView mRecyclerView = null;
    private NoteRvAdapter adapter;

    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        adapter = new NoteRvAdapter(new NoteDataConvert().convert());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new NoteRvClickListener(this));
    }

    @OnClick(R2.id.note_add)
    void onClickAdd() {
        EditDelegate delegate = new EditDelegate();
        delegate.setOnReSetListener(this);
        getParentDelegate().getSupportDelegate().start(delegate);
    }

    @Override
    public void onRestEdit() {
        adapter.getData().clear();
        adapter.setNewData(new NoteDataConvert().convert());
        adapter.notifyDataSetChanged();
    }
}
