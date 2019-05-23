package com.admin.realize.mian.personal;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;

import com.admin.realize.R;
import com.admin.realize.mian.personal.list.ListBean;
import com.admin.realize.mian.personal.settings.SettingDelegate;
import com.admin.task_core.app.Coke;
import com.admin.task_core.delegate.CoKeDelegate;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

/**
 * @description: ${DESCRIPTION}
 */
public class PersonalClickListener extends SimpleClickListener {
    private CoKeDelegate mCokeDelegate;
    private Context context;

    public PersonalClickListener(CoKeDelegate delegate, Context context) {
        this.mCokeDelegate = delegate;
        this.context = context;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ListBean o = (ListBean) baseQuickAdapter.getData().get(position);
        switch (o.getId()) {
            case 0:
                showCustomizeDialog(o, "修改座右铭", "座右铭");
                break;
            case 1:
                showCustomizeDialog(o, "修改目标", "目标");
                break;
            case 2:
                SettingDelegate delegate = new SettingDelegate();
                mCokeDelegate.getParentDelegate().getSupportDelegate().start(delegate);
                break;
            default:
                break;
        }
    }

    private void showCustomizeDialog(final ListBean o, String title, final String name) {
        final SharedPreferences.Editor editor = Coke.getAppContext().getSharedPreferences("xx", 0).edit();

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(0);
        final View dialogView = LayoutInflater.from(context)
                .inflate(R.layout.setting_dialog, null);
        dialog.setContentView(dialogView);
        dialog.show();

        AppCompatTextView head = dialogView.findViewById(R.id.dialog_title);
        head.setText(title);
        AppCompatTextView queding = dialogView.findViewById(R.id.dialog_queding);
        AppCompatTextView quxiao = dialogView.findViewById(R.id.dialog_quxiao);
        final AppCompatEditText editText = dialog.findViewById(R.id.dialog_edit);

        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = editText.getText().toString().trim();
                if (!"".equals(trim)) {
                    if ("座右铭".equals(name)) {
                        editor.putString("zym", trim);
                        dialog.dismiss();
                        baseQuickAdapter.notifyDataSetChanged();
                    }
                    if ("目标".equals(name)) {
                        editor.putString("mb", trim);
                        dialog.dismiss();
                        baseQuickAdapter.notifyDataSetChanged();
                    }
                    editor.apply();
                }
            }
        });
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

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
