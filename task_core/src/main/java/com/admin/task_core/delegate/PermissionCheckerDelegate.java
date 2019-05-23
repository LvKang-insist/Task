package com.admin.task_core.delegate;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * @description: 权限管理
 */
@SuppressWarnings("ALL")
public abstract class PermissionCheckerDelegate extends BaseDelegate {
    public ICheckPermission mICheckPermission = null;

    public interface ICheckPermission {
        void onAllow();

        void onReject();
    }

    public void checkPermission(String[] permission, ICheckPermission iCheckPermission) {
        if (Build.VERSION.SDK_INT < 23 || permission.length == 0) {
            if (iCheckPermission != null) {
                iCheckPermission.onAllow();
            }
        } else {
            if (iCheckPermission != null) {
                mICheckPermission = iCheckPermission;
            }
            if (permission[0] != null && permission[1] != null) {
                if (ContextCompat.checkSelfPermission(getContext(), permission[0]) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getProxyActivity(), permission, 0);
                }else  if (ContextCompat.checkSelfPermission(getContext(), permission[1]) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getProxyActivity(), permission, 0);
                } else {
                    mICheckPermission.onAllow();
                }
            }else {
                if (ContextCompat.checkSelfPermission(getContext(), permission[0]) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getProxyActivity(), permission, 0);
                }else {
                    mICheckPermission.onAllow();
                }
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mICheckPermission != null && requestCode == 0) {
            for (int i = 0; i < grantResults.length; i++) {
                //判断权限是否被允许，只要又一次拒绝就算失败
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    // 1：用户拒绝了该权限，没有勾选"不再提醒"，此方法将返回true。
                    // 2：用户拒绝了该权限，有勾选"不再提醒"，此方法将返回 false。
                    // 3：如果用户同意了权限，此方法返回false
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getProxyActivity(), permissions[i])) {
                        Toast.makeText(getProxyActivity(), "你已拒绝此权限，如果需要，可以在设置中打开此权限", Toast.LENGTH_SHORT).show();
                    } else {
                        mICheckPermission.onReject();
                    }
                    return;
                }
            }
            mICheckPermission.onAllow();
        }
    }
}
