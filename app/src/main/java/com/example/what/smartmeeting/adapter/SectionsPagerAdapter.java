package com.example.what.smartmeeting.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.what.smartmeeting.fragment.HistoryFragment;
import com.example.what.smartmeeting.fragment.InformationFragment;
import com.example.what.smartmeeting.fragment.IsComingFragment;

/**
 * Created by ngoc on 3/24/2016.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 2:
                return HistoryFragment.newInstance(position + 1);
            case 1:
                return IsComingFragment.newInstance(position + 1);
            case 0:
                return InformationFragment.newInstance(position + 1);
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 2:
                return "Lịch sử";
            case 1:
                return "Sắp tới";
            case 0:
                return "Thông tin";
            default:
                return null;
        }

    }
}
