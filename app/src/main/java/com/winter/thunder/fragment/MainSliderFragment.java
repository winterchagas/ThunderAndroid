package com.winter.thunder.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.winter.thunder.R;
import com.winter.thunder.fragment.CameraFragment;
import com.winter.thunder.fragment.FormFragment;
import com.winter.thunder.fragment.Info_Fragment;

public class MainSliderFragment extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3 ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View x = inflater.inflate(R.layout.fragment_main_slider, null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewPager);

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {

            @Override public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        return x;
    }

    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new Info_Fragment();
            case 1 : return new FormFragment();
            case 2 : return new CameraFragment();
        }
        return null;
    }

        @Override
        public int getCount() {
            return int_items;
        }

        @Override public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 : return "INFO";
                case 1 : return "FORM";
                case 2 : return "CAMERA";
            }
            return null;
        }
    }
}
