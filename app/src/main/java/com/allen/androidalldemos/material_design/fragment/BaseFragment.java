package com.allen.androidalldemos.material_design.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by Allen on 2015/12/14.
 */
public abstract class BaseFragment extends Fragment {
    public boolean isVisible;//判断fragment是否显示

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible() {
    }
}
