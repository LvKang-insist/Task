package com.admin.realize.mian.note;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.admin.realize.R;
import com.admin.task_core.ui.recycler.MultipleFields;
import com.admin.task_core.ui.recycler.MultipleItemBean;
import com.admin.task_core.ui.recycler.MultipleRecyclerViewAdapter;
import com.admin.task_core.ui.recycler.MultipleViewHolder;

import java.util.List;

public class NoteRvAdapter extends MultipleRecyclerViewAdapter {

    public interface OnSortListener {
        void onSort(int position);
    }

    private OnSortListener listener;

    public void setListener(OnSortListener listener) {
        this.listener = listener;
    }

    protected NoteRvAdapter(List<MultipleItemBean> data) {
        super(data);
        addItemType(NoteItemType.NOTE_ITEM, R.layout.item_node);
        addItemType(NoteItemType.NOTE_SORT, R.layout.node_sort_item);
    }

    @Override
    protected void convert(MultipleViewHolder helper, final MultipleItemBean item) {
        super.convert(helper, item);
        switch (helper.getItemViewType()) {
            case NoteItemType.NOTE_ITEM:
                TextView mText = helper.getView(R.id.item_node_tv);
                TextView mDate = helper.getView(R.id.item_node_date);
                final String text = item.getField(MultipleFields.TEXT);
                String date = item.getField(MultipleFields.DATE);
                mText.setText(text);
                mDate.setText(date);
                break;
            case NoteItemType.NOTE_SORT:
                String name = item.getField(MultipleFields.TEXT);
                boolean flag = item.getField(MultipleFields.TAG);
                final TextView textView = helper.getView(R.id.sort_item);
                textView.setText(name);
                if (flag) {
                    textView.setTextColor(Color.RED);
                } else {
                    textView.setTextColor(Color.GRAY);
                }
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < getData().size(); i++) {
                            getData().get(i).setField(MultipleFields.TAG,false);
                        }
                        item.setField(MultipleFields.TAG,true);
                        listener.onSort((Integer) item.getField(MultipleFields.ID));
                        notifyItemRangeChanged(0,getData().size());
                    }
                });

                break;
            default:
                break;
        }
    }
}
