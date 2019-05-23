package com.admin.realize.mian.note;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.admin.realize.R;
import com.admin.realize.R2;
import com.admin.task_core.delegate.bottom.BottomItemDelegate;
import com.admin.task_core.util.SetToolBar;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @description: 笔记
 */
public class NoteDelegate extends BottomItemDelegate implements NoteRvAdapter.OnSortListener {

    @BindView(R2.id.toolbar_drvwing)
    Toolbar mToolbar = null;
    @BindView(R2.id.node_sort_rv)
    RecyclerView mRecyclerView = null;
    private NoteRvAdapter mAdapter;

    @BindView(R2.id.note_paly)
    IconTextView icon = null;

    public static int POS = 0;
    private int index = 0;
    private ISupportFragment[] iSupportFragments;
    private boolean stop = true;
    MediaPlayer mediaPlayer;
    @OnClick(R2.id.note_paly)
    void onPlay() {
        if (stop) {
            //播放
            mediaPlayer.start();
            icon.setText("{fa-stop}");
            stop = false;
        } else {
            //暂停
            mediaPlayer.pause();
            Toast.makeText(getContext(), "暂停", Toast.LENGTH_SHORT).show();
            icon.setText("{fa-play}");
            stop = true;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_node;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        SetToolBar.setToolBar(mToolbar);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new NoteRvAdapter(new NoteSortConvert().convert());
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mediaPlayer  = MediaPlayer.create(getContext(), R.raw.bg);
        onRestEdit();
    }

    public void onRestEdit() {
        mAdapter.getData().clear();
        mAdapter.setNewData(new NoteSortConvert().convert());
        mAdapter.notifyDataSetChanged();
        List<NoteSortDelegate> list = new ArrayList<>();
        for (int i = 0; i < mAdapter.getData().size(); i++) {
            NoteSortDelegate delegate = new NoteSortDelegate();
            list.add(delegate);
        }
        if (iSupportFragments != null) {
            iSupportFragments.clone();
        }
        iSupportFragments = list.toArray(new ISupportFragment[mAdapter.getData().size()]);
        getSupportDelegate().loadMultipleRootFragment(R.id.delegate_sort, index, iSupportFragments);
    }

    @Override
    public void onSort(int position) {
        POS = position;
        getSupportDelegate().showHideFragment(iSupportFragments[position], iSupportFragments[index]);
        index = position;
    }
}
