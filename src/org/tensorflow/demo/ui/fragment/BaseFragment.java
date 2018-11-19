package org.tensorflow.demo.ui.fragment;

import android.annotation.SuppressLint;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import org.tensorflow.demo.ui.fragment.view.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

public class BaseFragment extends Fragment {
    protected View m_view;
    protected Context context;
    protected int currentPosition;
    protected List<Fragment> fragments = new ArrayList<>();

    public void init(ViewGroup container)
    {
        if(container == null) context = getActivity();
        else context = container.getContext();
    }

    public void setCurrentPosition(int position)
    {
        Bundle args = new Bundle();
        args.putInt("position", position);
        setArguments(args);
        currentPosition = position;
    }

    public void showKeyboard(View view, boolean isShow)
    {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);

        if(isShow) imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        else imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    public void addFragment(int resId, Fragment fragment)
    {
        fragments.add(fragment);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(resId, fragment);
        ft.commitAllowingStateLoss();
    }

    public void replaceFragment(int resId, Fragment fragment)
    {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(resId, fragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    public Fragment getFragment(int position)
    {
        try {
            return fragments.get(position);
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    @SuppressLint("LongLogTag")
    public void backFragment()
    {
        FragmentManager fm = getChildFragmentManager();
        Log.e("backStackFragment onBackPressed==>", "==>" + fm.getBackStackEntryCount());
        int count = fm.getBackStackEntryCount();
        if (count > 0)
        {
            Log.e("popping backstack", "==>");
            fm.popBackStack();
        }
    }

    public void startIndicator(final ProgressWheel wheel) {
        if(wheel != null && wheel.getVisibility() != View.VISIBLE)
        {
            wheel.setVisibility(View.VISIBLE);
            wheel.spin();
        }
    }

    public void stopIndicator(ProgressWheel wheel) {
        if(wheel != null && wheel.getVisibility() == View.VISIBLE)
        {
            wheel.setVisibility(View.INVISIBLE);
            wheel.stopSpinning();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
