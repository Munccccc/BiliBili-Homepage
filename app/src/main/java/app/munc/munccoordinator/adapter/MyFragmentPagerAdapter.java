package app.munc.munccoordinator.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GD on 2017/12/19.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager fm;
    private final List<Fragment> list;
    private final List<String> titles;

    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, List<String> listT) {
        super(fm);
        this.fm = fm;
        this.list = fragments;
        this.titles = listT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}
