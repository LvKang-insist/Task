package com.admin.realize.mian.personal.list;

import android.content.SharedPreferences;

import com.admin.realize.R;
import com.admin.task_core.app.Coke;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @description: ${DESCRIPTION}
 */
public class ListAdapter extends BaseMultiItemQuickAdapter<ListBean, BaseViewHolder> {

    public ListAdapter(List<ListBean> data) {
        super(data);
        addItemType(ListItemType.PER_ITEM, R.layout.arrow_item_layout);
        addItemType(ListItemType.SWITCH_ITEM, R.layout.arrow_switch_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListBean item) {
        switch (helper.getItemViewType()) {
            case ListItemType.PER_ITEM:
                SharedPreferences sp = Coke.getAppContext().getSharedPreferences("xx", 0);
                if ("座右铭:  ".equals(item.getText())) {
                    helper.setText(R.id.tv_arrow_text, item.getText() + sp.getString("zym", ""));
                    helper.setText(R.id.tv_arrow_value, item.getValue());
                    return;
                } else if ("目标:  ".equals(item.getText())) {
                    helper.setText(R.id.tv_arrow_text, item.getText() + sp.getString("mb", ""));
                    helper.setText(R.id.tv_arrow_value, item.getValue());
                    return;
                }
                helper.setText(R.id.tv_arrow_text, item.getText());
                helper.setText(R.id.tv_arrow_value, item.getValue());
                break;
            case ListItemType.SWITCH_ITEM:
                helper.setText(R.id.tv_arrow_switch_text, item.getText());
                break;
            default:
                break;
        }
    }
}
