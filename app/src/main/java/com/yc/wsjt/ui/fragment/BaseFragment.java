package com.yc.wsjt.ui.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yc.wsjt.ui.activity.BaseActivity;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

/**
 * Created by admin on 2017/4/8.
 */

public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    private BaseActivity baseActivity;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (getActivity() instanceof BaseActivity) {
            baseActivity = (BaseActivity) getActivity();
        }

        if (mView == null) {
            mView = inflater.inflate(getContentView(), null);
            ButterKnife.bind(this, mView);
            //StatusBarUtil.setColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.colorPrimary), 0);
            initVars();
            initViews();
            loadData();
        }

        return mView;
    }

    public abstract void initVars();

    public abstract void initViews();

    public abstract void loadData();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * here we can do some initialized work
     */
    protected abstract void initFragmentConfig();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // for bug ---> java.lang.IllegalStateException: Activity has been destroyed
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public BaseActivity getBaseActivity() {
        return baseActivity;
    }

    protected abstract int getContentView();
}
