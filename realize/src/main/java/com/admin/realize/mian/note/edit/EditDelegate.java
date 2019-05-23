package com.admin.realize.mian.note.edit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.admin.realize.R;
import com.admin.realize.R2;
import com.admin.realize.mian.database.DatabaseManager;
import com.admin.realize.mian.note.NoteDelegate;
import com.admin.realize.mian.note.table.EditTable;
import com.admin.realize.mian.note.table.EditTableDao;
import com.admin.task_core.delegate.CoKeDelegate;
import com.admin.task_core.util.SetToolBar;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class EditDelegate extends CoKeDelegate {


    @BindView(R2.id.toolbar_edit)
    Toolbar mToolbar = null;
    @BindView(R2.id.edit_edit)
    EditText editText = null;

    OnResetEditListener listener = null;
    public static final String EDIT_TEXT = "edit_text";
    public static final String EDIT_ID = "edit_id";
    private boolean flag = false;
    private int position = -1;

    @OnClick(R2.id.edit_save)
    void onClickEdit() {
        String text = editText.getText().toString();
        if (flag){
            flag = false;
            EditTable load = getDao().load((long) position);
            position = -1;
            load.setText(text);
            getDao().update(load);
            //退出当前页面
            if (listener != null){
                listener.onRestEdit();
            }
            getSupportDelegate().pop();
            return;
        }
        if ("".equals(text)){
            getSupportDelegate().pop();
            return;
        }
        int size = getDao().loadAll().size();
        EditTableDao dao = getDao();
        String date = getDate();
        EditTable table = new EditTable();
        table.setPosition(NoteDelegate.POS);
        table.setDate(date);
        table.setText(text);
        table.setId(size);
        dao.insertOrReplace(table);
        //退出当前页面
        if (listener != null){
            listener.onRestEdit();
        }
        getSupportDelegate().pop();
    }

    @SuppressLint("SimpleDateFormat")
    private String getDate() {
        long l = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yy-MM/dd HH:mm:ss");
        String date = format.format(new Date(l));
        return date;
    }

    private EditTableDao getDao() {
        return DatabaseManager.getInstance().getDao().getEditTableDao();
    }

    public void setOnReSetListener(OnResetEditListener listener){
        this.listener = listener;
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
            String string = bundle.getString(EDIT_TEXT);
            if (string != null){
                position = bundle.getInt(EDIT_ID);
                editText.setText(string);
                flag = true;
            }
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        getSupportDelegate().pop();
        return true;
    }
}
