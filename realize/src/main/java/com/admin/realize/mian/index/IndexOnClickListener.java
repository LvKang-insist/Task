package com.admin.realize.mian.index;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.admin.realize.R;
import com.admin.realize.mian.database.DatabaseManager;
import com.admin.realize.mian.database.table.DaoSession;
import com.admin.realize.mian.database.table.IndexTable;
import com.admin.task_core.delegate.PermissionCheckerDelegate;
import com.admin.task_core.ui.recycler.MultipleFields;
import com.admin.task_core.ui.recycler.MultipleItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

public class IndexOnClickListener extends SimpleClickListener {

    private Context context;
    private IndexDelegate delegate;


    IndexOnClickListener(Context context, IndexDelegate delegate) {
        this.context = context;
        this.delegate = delegate;
    }

    @Override
    public void onItemClick(final BaseQuickAdapter adapter, View view, final int position) {

        delegate.checkPermission(new String[]{Manifest.permission.WRITE_CALENDAR}, new PermissionCheckerDelegate.ICheckPermission() {
            @Override
            public void onAllow() {

                MultipleItemBean bean = (MultipleItemBean) baseQuickAdapter.getData().get(position);
                final String str = bean.getField(MultipleFields.TEXT);
                if ("".equals(str)|| str == null) {
                    Toast.makeText(context, "长按输入课程", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onReject() {
            }
        });
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        MultipleItemBean bean = (MultipleItemBean) baseQuickAdapter.getData().get(position);
        final int row = bean.getField(MultipleFields.ROW);
        final int rank = bean.getField(MultipleFields.RANK);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.dialog);
        dialog.setTitle("请输入课程");
        final AppCompatEditText editText = new AppCompatEditText(context);
        dialog.setView(editText);

        dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.setNeutralButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String trim = editText.getText().toString().trim();
                DaoSession dao = DatabaseManager.getInstance().getDao();
                IndexTable load = dao.getIndexTableDao().load((long) row + 1);
                /*
                 *  更新数据库
                 */
                switch (rank) {
                    case 1:
                        load.setOne(trim);
                        break;
                    case 2:
                        load.setTwo(trim);
                        break;
                    case 3:
                        load.setThree(trim);
                        break;
                    case 4:
                        load.setFour(trim);
                        break;
                    case 5:
                        load.setFive(trim);
                        break;
                    case 6:
                        load.setSix(trim);
                        break;
                    case 7:
                        load.setSeven(trim);
                        break;
                    default:
                        break;
                }
                dao.update(load);
                dialog.cancel();
                //更新界面
                delegate.setTable();
            }
        });
        dialog.show();

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
