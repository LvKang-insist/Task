package com.admin.realize.mian.match;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import com.admin.realize.mian.match.detail.DetailDelegate;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

public class MatchOnclickListener extends SimpleClickListener {

    public static int pos = 0;

    private MatchDelegate delegate = null;
    private Context context;

    public MatchOnclickListener(MatchDelegate delegate, Context context) {
        this.delegate = delegate;
        this.context = context;
    }

    @SuppressLint("InlinedApi")
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        pos = position;
        delegate.getParentDelegate().getSupportDelegate().start(new DetailDelegate());
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
