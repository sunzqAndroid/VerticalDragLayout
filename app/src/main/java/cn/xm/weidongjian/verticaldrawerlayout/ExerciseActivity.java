package cn.xm.weidongjian.verticaldrawerlayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;

/**
 * Created by ibm on 2017/7/13.
 */

public class ExerciseActivity extends AppCompatActivity {
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        FragmentManager fm = getSupportFragmentManager();
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new NewsFragmentPagerAdapter(fm));
    }

    public static class NewsFragmentPagerAdapter extends FragmentPagerAdapter {

        public HashMap<String, Fragment> fragments;

        public NewsFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            if (fragments == null) {
                fragments = new HashMap<String, Fragment>();
            }
        }

        @Override
        public Fragment getItem(int position) {
            if (position % 4 == 0) {
                return new Fragment1();
            } else {
                return new Fragment2();
            }
        }

        @Override
        public int getCount() {
            return 20;
        }
    }
}
