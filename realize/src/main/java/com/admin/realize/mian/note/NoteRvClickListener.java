package com.admin.realize.mian.note;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.admin.realize.mian.database.DatabaseManager;
import com.admin.realize.mian.note.edit.EditDelegate;
import com.admin.realize.mian.note.table.EditTable;
import com.admin.realize.mian.note.table.EditTableDao;
import com.admin.task_core.ui.recycler.MultipleFields;
import com.admin.task_core.ui.recycler.MultipleItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class NoteRvClickListener extends SimpleClickListener {

    private NoteSortDelegate mDelegate;

    public NoteRvClickListener(NoteSortDelegate delegate) {
        this.mDelegate = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<EditTable> editList = getEditList();
        List<MultipleItemBean> data = baseQuickAdapter.getData();
        for (int i = 0; i < editList.size(); i++) {
            for (int j = 0; j < data.size(); j++) {
                if (editList.get(i).getId() == (int)data.get(j).getField(MultipleFields.ID)){
                    String text = editList.get(i).getText();
                    Bundle bundle = new Bundle();
                    bundle.putString(EditDelegate.EDIT_TEXT,text);
                    bundle.putInt(EditDelegate.EDIT_ID,(int)data.get(j).getField(MultipleFields.ID));
                    EditDelegate delegate = new EditDelegate();
                    delegate.setOnReSetListener(mDelegate);
                    delegate.setArguments(bundle);
                    mDelegate.getParentDelegate().getSupportDelegate().start(delegate);
                    return;
                }
            }

        }
    }
    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {


        AlertDialog.Builder builder=new AlertDialog.Builder(mDelegate.getContext());
        builder.setTitle("警告");
        builder.setMessage("是否删除?");
        builder.setNegativeButton("否",null);
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MultipleItemBean  o = (MultipleItemBean) baseQuickAdapter.getData().get(position);
                int count = o.getField(MultipleFields.ID);

                List<String> list = new ArrayList<>();
                List<String> date = new ArrayList<>();
                List<Integer> pos = new ArrayList<>();

                for (int x = 0; x < getEditList().size(); x++) {
                    list.add(getEditList().get(x).getText());
                    date.add(getEditList().get(x).getDate());
                    pos.add(getEditList().get(x).getPosition());
                }
                list.remove(count);
                date.remove(count);
                pos.remove(count);
                DatabaseManager.getInstance().getDao().getEditTableDao().deleteAll();
                for (int x = 0; x < list.size(); x++) {
                    EditTable table = new EditTable(x,pos.get(x),list.get(x),date.get(x));
                    DatabaseManager.getInstance().getDao().getEditTableDao().insertOrReplace(table);
                }
                mDelegate.onRestEdit();
            }
        });
        builder.show();


     }
    private List<EditTable> getEditList() {
        EditTableDao editTableDao = DatabaseManager.getInstance().getDao().getEditTableDao();
        return editTableDao.loadAll();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
