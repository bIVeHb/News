package biv.ru.news.adapter;

/**
 * Created by bIVeHb on 05.03.2018.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import biv.ru.news.R;
import biv.ru.news.activity.RecycleViewFragment;

/**
 * Created by bIVeHb on 04.03.2018.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private String mTabTitles[] = new String[]{"Час", "Сутки", "Неделя", "Месяц"};
    private Context mContext;
    private Activity mActivity;
    private String mUrlHour = "https://mediametrics.ru/rating/ru/hour.html";
    private String mUrlDay = "https://mediametrics.ru/rating/ru/day.html";
    private String mUrlWeek = "https://mediametrics.ru/rating/ru/week.html";
    private String mUrlMonth = "https://mediametrics.ru/rating/ru/month.html";

    public MyPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public int getCount() {
        return mTabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new RecycleViewFragment(mUrlHour, mContext);
            case 1:
                return new RecycleViewFragment(mUrlDay, mContext);
            case 2:
                return new RecycleViewFragment(mUrlWeek, mContext);
            case 3:
                return new RecycleViewFragment(mUrlMonth, mContext);
        }

        return null;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }

    public View getTabView(int position) {
        View tab = LayoutInflater.from(mContext).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) tab.findViewById(R.id.customText);
        tv.setText(mTabTitles[position]);
        return tab;
    }
}

