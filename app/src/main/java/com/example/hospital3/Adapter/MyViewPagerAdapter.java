package com.example.hospital3.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.hospital3.Fragment.Bookingstep1Fragment;
import com.example.hospital3.Fragment.Bookingstep2Fragment;
import com.example.hospital3.Fragment.Bookingstep3Fragment;
import com.example.hospital3.Fragment.Bookingstep4Fragment;
import com.example.hospital3.Fragment.Bookingstep5Fragment;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    public MyViewPagerAdapter(FragmentManager fm) {

        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {

        switch (i)
        {
            case 0:
                return Bookingstep1Fragment.getInstance();
            case 1:
                return Bookingstep2Fragment.getInstance();
            case 2:
                return Bookingstep3Fragment.getInstance();
            case 3:
                return Bookingstep4Fragment.getInstance();
            case 4:
                return Bookingstep5Fragment.getInstance();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
