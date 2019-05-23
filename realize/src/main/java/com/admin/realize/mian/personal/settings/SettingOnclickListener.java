package com.admin.realize.mian.personal.settings;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.admin.realize.R;
import com.admin.realize.mian.database.DatabaseManager;
import com.admin.realize.mian.database.table.DaoSession;
import com.admin.realize.mian.personal.list.ListBean;
import com.admin.realize.sign.SignInDelegate;
import com.admin.realize.sign.UserProfile;
import com.admin.task_core.app.Coke;
import com.admin.task_core.util.callback.CallBackType;
import com.admin.task_core.util.callback.CallbackManager;
import com.admin.task_core.util.callback.IGlobalCallback;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;


/**
 * @description: ${DESCRIPTION}
 */
@SuppressWarnings("unchecked")
public class SettingOnclickListener extends SimpleClickListener {

    private Context context;
    SettingDelegate delegate;

    public SettingOnclickListener(Context context, SettingDelegate delegate) {
        this.context = context;
        this.delegate = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ListBean list = (ListBean) baseQuickAdapter.getData().get(position);
        switch (list.getId()) {
            case 2:
                showCustomizeDialog();
                break;
            case 3:
                showWPassDialog();
                break;
            case 4:

                AlertDialog.Builder builder = new AlertDialog.Builder(delegate.getContext());
                builder.setTitle("警告");
                builder.setMessage("是否退出登录?");
                builder.setNegativeButton("否", null);
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sp = Coke.getAppContext().getSharedPreferences("check", 0);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putBoolean("isCheck", false);
                        edit.apply();
                        delegate.getSupportDelegate().pop();
                        delegate.getSupportDelegate().replaceFragment(new SignInDelegate(), false);
                    }
                });
                builder.show();
                break;
            default:
        }
    }

    private void showWPassDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(0);
        final View dialogView = LayoutInflater.from(context)
                .inflate(R.layout.up_wj_pass_dialog, null);
        dialog.setContentView(dialogView);
        dialog.show();

        AppCompatTextView head = dialogView.findViewById(R.id.dialog_title);
        head.setText("修改密码");
        AppCompatTextView queding = dialogView.findViewById(R.id.dialog_queding);
        AppCompatTextView quxiao = dialogView.findViewById(R.id.dialog_quxiao);
        final AppCompatEditText up = dialog.findViewById(R.id.up_passwrod);
        final AppCompatEditText upT = dialog.findViewById(R.id.up_passwrodT);
        final AppCompatEditText xing = dialog.findViewById(R.id.up_xin_pass);
        dialog.show();
        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str1 = up.getText().toString().trim();
                String strMb = upT.getText().toString().trim();
                String str2 = xing.getText().toString().trim();
                DaoSession dao = DatabaseManager.getInstance().getDao();
                UserProfile load = dao.getUserProfileDao().load(str1);
                if (load != null) {
                    String p = load.getMibao();
                    if (!p.equals(strMb)) {
                        Toast.makeText(context, "修改失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    load.setPassword(str2);
                    dao.update(load);
                    Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = Coke.getAppContext().getSharedPreferences("check", 0);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("isCheck", false);
                    edit.apply();
                    dialog.cancel();
                    delegate.getSupportDelegate().pop();
                    delegate.getSupportDelegate().replaceFragment(new SignInDelegate(), false);
                }
                dialog.cancel();
            }
        });
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }


    private void showCustomizeDialog() {
        /* @setView 装入自定义View ==> R.layout.dialog_customize
         * 由于dialog_customize.xml只放置了一个EditView，因此和图8一样
         * dialog_customize.xml可自定义更复杂的View
         */
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(0);
        final View dialogView = LayoutInflater.from(context)
                .inflate(R.layout.up_pass_dialog, null);
        dialog.setContentView(dialogView);
        dialog.show();

        AppCompatTextView head = dialogView.findViewById(R.id.dialog_title);
        head.setText("修改密码");
        AppCompatTextView queding = dialogView.findViewById(R.id.dialog_queding);
        AppCompatTextView quxiao = dialogView.findViewById(R.id.dialog_quxiao);
        final AppCompatEditText up = dialog.findViewById(R.id.up_passwrod);
        final AppCompatEditText upT = dialog.findViewById(R.id.up_passwrodT);
        final AppCompatEditText xing = dialog.findViewById(R.id.up_xin_pass);

        dialog.show();

        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str1 = up.getText().toString().trim();
                String strT = upT.getText().toString().trim();
                String str2 = xing.getText().toString().trim();
                DaoSession dao = DatabaseManager.getInstance().getDao();
                UserProfile load = dao.getUserProfileDao().load(str1);
                if (load != null) {
                    String p = load.getPassword();
                    if (!p.equals(strT)) {
                        Toast.makeText(context, "修改失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    load.setPassword(str2);
                    dao.update(load);
                    Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = Coke.getAppContext().getSharedPreferences("check", 0);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("isCheck", false);
                    edit.apply();
                    dialog.cancel();
                    delegate.getSupportDelegate().pop();
                    delegate.getSupportDelegate().replaceFragment(new SignInDelegate(), false);
                }
                dialog.cancel();
            }
        });
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
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
