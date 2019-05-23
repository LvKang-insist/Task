package com.admin.task_core.ui.recycler;

import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.admin.task_core.R;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * @description: RecyclerView 适配器
 */
@SuppressWarnings("ALL")
public class MultipleRecyclerViewAdapter extends
        BaseMultiItemQuickAdapter<MultipleItemBean, MultipleViewHolder>
        implements BaseQuickAdapter.SpanSizeLookup {

    public static MultipleRecyclerViewAdapter create(List<MultipleItemBean> data) {
        return new MultipleRecyclerViewAdapter(data);
    }

    protected MultipleRecyclerViewAdapter(List<MultipleItemBean> data) {
        super(data);
        addItemType(ItemType.DATE, R.layout.item_index_date);
        addItemType(ItemType.CURRICULUM, R.layout.item_index_curriculum);
        setSpanSizeLookup(this);
        openLoadAnimation();
        isFirstOnly(false);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemBean item) {
        switch (helper.getItemViewType()) {
            case ItemType.DATE:
                String date = item.getField(MultipleFields.TEXT);
                AppCompatTextView textView = helper.getView(R.id.item_index_date);
                textView.setText(date);
                break;
            case ItemType.CURRICULUM:
                String curr = item.getField(MultipleFields.TEXT);
                int color = item.getField(MultipleFields.COLOR);
                AppCompatTextView currTv = helper.getView(R.id.item_index_curr);
                currTv.setBackgroundColor(color);
                if ("".equals(curr)){
                    currTv.setBackgroundColor(Color.TRANSPARENT);
                }
                currTv.setText(curr);
                break;
            default:
                break;
        }
    }
    /**
     * 如果你想在适配器中使用BaseViewHolder的子类，您必须重写该方法来创建新的ViewHolder。
     */
    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    /**
     * 设置宽度
     */
    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

}
