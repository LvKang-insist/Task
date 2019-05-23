package com.admin.realize.mian.index.bwl;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.realize.R;
import com.admin.realize.mian.database.DatabaseManager;
import com.admin.task_core.app.Coke;
import com.admin.task_core.delegate.PermissionCheckerDelegate;
import com.admin.task_core.ui.recycler.MultipleFields;
import com.admin.task_core.ui.recycler.MultipleItemBean;
import com.admin.task_core.ui.recycler.MultipleRecyclerViewAdapter;
import com.admin.task_core.ui.recycler.MultipleViewHolder;
import com.admin.task_core.util.CalendarUtils;
import com.admin.task_core.util.Cleandar;
import com.admin.task_core.util.ClendarThree;
import com.admin.task_core.util.SelectTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Adapter extends MultipleRecyclerViewAdapter {

    List<Integer> color = new ArrayList<>();

    BwlDelegate delegate;

    protected Adapter(List<MultipleItemBean> data ,BwlDelegate delegate) {
        super(data);
        this.delegate = delegate;
        addItemType(BwlItemType.ITEM_BWM, R.layout.item_bwl);
        setSpanSizeLookup(this);
        color.clear();
        color.add(Color.parseColor("#33B4E4"));
        color.add(Color.parseColor("#98CB01"));
        color.add(Color.parseColor("#FDBA33"));
        color.add(Color.parseColor("#FD4444"));
        color.add(Color.parseColor("#FAFCFD"));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemBean item) {
        super.convert(helper, item);
        switch (helper.getItemViewType()) {
            case BwlItemType.ITEM_BWM:
                LinearLayout back = helper.getView(R.id.item_bwl_back);
                TextView bg = helper.getView(R.id.item_bwl_bg);
                final TextView text = helper.getView(R.id.item_bwl_text);
                final long position = item.getField(MultipleFields.ID);
                Random random = new Random();
                bg.setBackgroundColor(color.get(random.nextInt(color.size())));
                final String Str = item.getField(MultipleFields.TEXT);
                text.setText(Str );

                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(Bwl_edit.BWL, (int) position);
                        Bwl_edit edit = new Bwl_edit();
                        edit.setListener(delegate);
                        edit.setArguments(bundle);
                        delegate.getSupportDelegate().start(edit);
                    }
                });

                text.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        SelectTime time = new SelectTime();
                        time.setListener(new SelectTime.onTimeListener() {
                            @Override
                            public void onTime(final long time) {
                                final String[] str = {Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR};
                                delegate.checkPermission(str, new PermissionCheckerDelegate.ICheckPermission() {
                                    @Override
                                    public void onAllow() {
                                        Cleandar.addCalendarEvent(Coke.getAppContext(),
                                                Str+"   时间到了","时间到了",time,1);
                                        Toast.makeText(delegate.getContext(), "提醒成功", Toast.LENGTH_SHORT).show();

                                    }
                                    @Override
                                    public void onReject() {

                                    }
                                });
                            }
                        });
                        time.getT(delegate.getContext());
                        return true;
                    }
                });

               back.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       List<String> text = new ArrayList<>();
                       List<Long> id = new ArrayList<>();
                       List<BwlTable> bwlTables = DatabaseManager.getInstance().getDao().getBwlTableDao().loadAll();
                       for (int i = 0; i < bwlTables.size(); i++) {
                           text.add(bwlTables.get(i).getText());
                           id.add(bwlTables.get(i).getId());
                       }
                       DatabaseManager.getInstance().getDao().getBwlTableDao().deleteAll();
                       text.remove((int) position);
                       id.remove((int) position);
                       for (int i = 0; i < text.size(); i++) {
                           BwlTable table = new BwlTable(i, text.get(i));
                           DatabaseManager.getInstance().getDao().getBwlTableDao().insertOrReplace(table);
                       }
                       getData().clear();
                       setNewData(new BwlDataConvert().convert());
                       notifyItemRangeChanged(0,getData().size());
                   }
               });
                break;
            default:
                break;
        }
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }
}
