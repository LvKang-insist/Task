package com.admin.task_core.delegate;

/**
 * @description: 要使用的Delegate
 */
public abstract class CoKeDelegate extends PermissionCheckerDelegate {
    public <T extends CoKeDelegate>  T getParentDelegate(){
        return (T) getParentFragment();
    }
}
