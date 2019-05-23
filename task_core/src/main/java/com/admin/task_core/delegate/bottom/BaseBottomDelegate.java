package com.admin.task_core.delegate.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.admin.task_core.R;
import com.admin.task_core.R2;
import com.admin.task_core.delegate.CoKeDelegate;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @description: 初始化 界面
 */
public abstract class BaseBottomDelegate extends CoKeDelegate implements View.OnClickListener {

    private final ArrayList<BottomItemDelegate> ITEM_DELEGATE = new ArrayList<>();
    private final ArrayList<BottomTabBean> TAB_BEAN = new ArrayList<>();

    private final LinkedHashMap<BottomTabBean,BottomItemDelegate> ITEMS =new LinkedHashMap<>();

    private int mCurrentDelegate = 0;
    private int mIndexDelegate = 0;
    private int mBottomColor;

    public abstract int setIndexDelegate();
    public abstract int setTabColor();

    public abstract LinkedHashMap<BottomTabBean,BottomItemDelegate> setItems(BottomFactory factory);

    @BindView(R2.id.bottom_tab)
    LinearLayoutCompat mBottomTab = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_bottom;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndexDelegate = setIndexDelegate();
        if (setTabColor() != 0){
            mBottomColor = setTabColor();
        }
        ITEMS.putAll(setItems(BottomFactory.builder()));

        for (Map.Entry<BottomTabBean, BottomItemDelegate> map : ITEMS.entrySet()){
            TAB_BEAN.add(map.getKey());
            ITEM_DELEGATE.add(map.getValue());
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text_layout,mBottomTab);
            final RelativeLayout item = (RelativeLayout) mBottomTab.getChildAt(i);
            item.setTag(i);
            item.setOnClickListener(this);
            final IconTextView icon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView title = (AppCompatTextView) item.getChildAt(1);
            icon.setText(TAB_BEAN.get(i).getIcon());
            title.setText(TAB_BEAN.get(i).getTitle());

            if (i == mIndexDelegate){
                icon.setTextColor(mBottomColor);
                title.setTextColor(mBottomColor);
            }
        }
        ISupportFragment[] delegates = ITEM_DELEGATE.toArray(new ISupportFragment[ITEM_DELEGATE.size()]);
        getSupportDelegate().loadMultipleRootFragment(R.id.bottom_bar_delegate_container,mIndexDelegate,delegates);
    }

    private void resetColor(){
        final int size = mBottomTab.getChildCount();
        for (int i = 0; i < size; i++) {
            RelativeLayout item = (RelativeLayout) mBottomTab.getChildAt(i);
            final IconTextView icon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView title = (AppCompatTextView) item.getChildAt(1);
            icon.setTextColor(Color.GRAY);
            title.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View v) {
        resetColor();
        RelativeLayout item = (RelativeLayout) v;
        final IconTextView icon = (IconTextView) item.getChildAt(0);
        final AppCompatTextView title = (AppCompatTextView) item.getChildAt(1);
        icon.setTextColor(mBottomColor);
        title.setTextColor(mBottomColor);
        final int tag = (int) v.getTag();
        getSupportDelegate().showHideFragment(ITEM_DELEGATE.get(tag),ITEM_DELEGATE.get(mCurrentDelegate));
        mCurrentDelegate = tag;
    }
}
