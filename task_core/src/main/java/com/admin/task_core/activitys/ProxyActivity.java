package com.admin.task_core.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;


import com.admin.task_core.R;
import com.admin.task_core.delegate.CoKeDelegate;

import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @description:  代理Activity
 */
public abstract class ProxyActivity extends AppCompatActivity implements ISupportActivity {

    public final SupportActivityDelegate DELEGATE = new SupportActivityDelegate(this);
    /**
     * @return 返回根Delegate
     */
    public abstract CoKeDelegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DELEGATE.onCreate(savedInstanceState);
        initFrame(savedInstanceState);
    }

    private void initFrame(@Nullable Bundle savedInstanceState) {
        final FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(R.id.delegate_frameLayout);
        setContentView(frameLayout);

        if (savedInstanceState == null){
            DELEGATE.loadRootFragment(R.id.delegate_frameLayout, setRootDelegate());
        }
    }

    @Override
    public SupportActivityDelegate getSupportDelegate() {
        return DELEGATE;
    }

    @Override
    public ExtraTransaction extraTransaction() {
        return DELEGATE.extraTransaction();
    }

    @Override
    public FragmentAnimator getFragmentAnimator() {
        return DELEGATE.getFragmentAnimator();
    }

    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        DELEGATE.setFragmentAnimator(fragmentAnimator);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return DELEGATE.onCreateFragmentAnimator();
    }

    @Override
    public void onBackPressedSupport() {
        DELEGATE.onBackPressedSupport();
    }
    @Override
    public void onBackPressed() {
        DELEGATE.onBackPressed();
    }
}
