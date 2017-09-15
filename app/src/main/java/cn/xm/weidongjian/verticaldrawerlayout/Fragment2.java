package cn.xm.weidongjian.verticaldrawerlayout;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * Created by ibm on 2017/7/13.
 */

public class Fragment2 extends Fragment implements DragListener {

    private View rootView;
    private Context mContext;

    private DragLayout mDragLayout;
    private View mScrollView;
    private View mContainer;
    private int mContentHeight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            mContext = getActivity();
            rootView = View.inflate(mContext, R.layout.fragment2, null);
            initView();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    private void initView() {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mScrollView = rootView.findViewById(R.id.scrollView);
        mContainer = rootView.findViewById(R.id.container);

        mDragLayout = (DragLayout) rootView.findViewById(R.id.dragLayout);
        mDragLayout.setDragListener(this);
        mScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mScrollView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mContentHeight = mScrollView.getHeight();
            }
        });
        Fragment fragment = new Fragment1();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    @Override
    public void setSize(int height) {
        if (mContentHeight > 0) {
            ViewGroup.LayoutParams params = mScrollView.getLayoutParams();
            params.height = mContentHeight - height;
            mScrollView.setLayoutParams(params);
            ViewGroup.LayoutParams params2 = mContainer.getLayoutParams();
            params2.height = height;
            mContainer.setLayoutParams(params2);
        }
    }
}
