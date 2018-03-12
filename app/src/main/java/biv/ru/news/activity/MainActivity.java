package biv.ru.news.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import biv.ru.news.R;
import biv.ru.news.adapter.MyPagerAdapter;
import biv.ru.news.adapter.RvLinksAdapter;

public class MainActivity extends AppCompatActivity {


    private List<String> mListLinks;
    private final List<String> mListTitles = new ArrayList<>();

    private List<String> mHtml;
    private RecyclerView mRecyclerViewLinks;
    private TabLayout mTabLayout;
    private String mUrlHour = "https://mediametrics.ru/rating/ru/hour.html";
    private String mUrlDay = "https://mediametrics.ru/rating/ru/day.html";
    private String mUrlWeek = "https://mediametrics.ru/rating/ru/week.html";
    private String mUrlMonth = "https://mediametrics.ru/rating/ru/month.html";
    private RvLinksAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerViewLinks = (RecyclerView) findViewById(R.id.recyclerViewLinks);
        mListLinks = new ArrayList<>();
        mHtml = new ArrayList<>();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }
    }

}
