package com.admin.realize.mian.index.bwl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.admin.realize.R;
import com.admin.realize.R2;
import com.admin.realize.mian.database.DatabaseManager;
import com.admin.task_core.delegate.CoKeDelegate;
import com.admin.task_core.util.SetToolBar;

import butterknife.BindView;
import butterknife.OnClick;

public class Bwl_edit extends CoKeDelegate {


    public static final String BWL = "bwl_edit";

    @BindView(R2.id.toolbar_edit)
    Toolbar mToolbar = null;
    @BindView(R2.id.edit_edit)
    EditText editText = null;

    private int position = -1;

    onSaveListener listener ;

    public onSaveListener getListener() {
        return listener;
    }

    public void setListener(onSaveListener listener) {
        this.listener = listener;
    }

    @OnClick(R2.id.edit_save)
    void onClickEdit() {
        String text = editText.getText().toString();
        if ("".equals(text)){
            getSupportDelegate().pop();
            return;
        }
        if (position != -1){
            BwlTableDao bwlTableDao = DatabaseManager.getInstance().getDao().getBwlTableDao();
            BwlTable load = bwlTableDao.load((long) position);
            load.setText(text);
            DatabaseManager.getInstance().getDao().update(load);
            if (listener!= null){
                listener.onSave();
            }
            position = -1;
            getSupportDelegate().pop();
            return;
        }
        int size = getDao().loadAll().size();
        BwlTable table = new BwlTable();
        table.setId(size);
        table.setText(text);
        getDao().insertOrReplace(table);
        if (listener!= null){
            listener.onSave();
        }
        //退出当前页面
        getSupportDelegate().pop();
    }

    private BwlTableDao getDao() {
        return DatabaseManager.getInstance().getDao().getBwlTableDao();
    }



    @Override
    public Object setLayout() {
        return R.layout.delegate_edit;
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        SetToolBar.setToolBar(mToolbar);
         Bundle bundle = getArguments();
        if (bundle != null) {
            int pos = bundle.getInt(BWL,-1);
            System.out.println("---------------Bundle"+pos);
            if (pos != -1){
                position = pos;
                BwlTable load = getDao().load((long) pos);
                String str = load.getText();
                editText.setText(str);
            }
        }
    }
    @Override
    public boolean onBackPressedSupport() {
        getSupportDelegate().pop();
        return true;
    }
}
