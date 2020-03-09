package com.feuoy.tally.adapter;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.feuoy.tally.activity.MainFragment;
import com.feuoy.tally.util.DateUtil;
import com.feuoy.tally.util.GlobalUtil;

import java.util.LinkedList;


// 做查阅界面viewpager的Adapter
public class MainViewPagerAdapter extends FragmentPagerAdapter {


    LinkedList<MainFragment> fragments = new LinkedList<>();

    LinkedList<String> dates = new LinkedList<>();

    LinkedList<String> ifNewDates = new LinkedList<>();

    Context mContext;

//    String newDate = "";
//
//    int newIndex = -1;
//
//    MainFragment newFragment;


    public MainViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);

        this.mContext = context;
        initFragments(mContext);
    }


    public void initFragments(Context context) {
        this.mContext = context;

        dates = GlobalUtil.getInstance().dbHelper.getValidDate();

        // 如果没有有记录的日期，那么把今天加上
        if (dates.size() == 0) {
            dates.addLast(DateUtil.getNowYearMonthDay());
        }

        // 把每个date放到一个MainFragment里，拿到fragments
        for (String date : dates) {
            MainFragment fragment = new MainFragment(date, mContext);
            fragments.add(fragment);
        }
    }


/*    // 重新加载所有fragment
    // 暂时没有用这个方法，如果要用，fragmentManager可以处理
    public void reloadMainViewPagerAdapter(Context context) {

//        FragmentManager fragmentManager = getFragmentManager();
//        int count = fragmentManager.getBackStackEntryCount();
//        for (int i = 0; i < count; ++i) {
//            fragmentManager.popBackStack();
//        }

        this.mContext = context;

        for (MainFragment fragment : fragments) {
            fragment.reloadMainFragment(mContext);
        }

        ifNewDates = GlobalUtil.getInstance().dbHelper.getValidDate();

        if (ifNewDates.size() > dates.size()) {

            newDate = "";
            newIndex = -1;

            for (int i = 0; i < dates.size(); i++) {
                if (!ifNewDates.get(i).equals(dates.get(i))) {
                    newDate = ifNewDates.get(i);
                    newIndex = i;
                    break;
                }
            }

            if (newDate.equals("")) {
                newDate = ifNewDates.get(ifNewDates.size() - 1);
                newIndex = ifNewDates.size() - 1;
            }

            dates.add(newIndex, newDate);

            newFragment = new MainFragment(newDate, mContext);
            fragments.add(newIndex, newFragment);
        }

        notifyDataSetChanged();
    }*/


    // 传入索引，返回对应Date支出总数
    public int getTotalCost(int index) {
        return fragments.get(index).getTotalCost();
    }


    // 传入Date，返回索引
    public int getIndexByDate(String readDate) {
        for (int i = 0; i < dates.size(); i++) {
            if (dates.get(i).equals(readDate)) {
                return i;
            }
        }
        return -1;
    }


    // 传入索引，返回对应Date
    public String getDate(int index) {
        return dates.get(index);
    }


    // 传入索引，返回对应fragment
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    // 返回最后一个fragment的索引
    public int getLatsIndex() {
        return fragments.size() - 1;
    }


    // 返回fragment数量
    @Override
    public int getCount() {
        return fragments.size();
    }

}
