package com.dante.knowledge.news.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.dante.knowledge.R;
import com.dante.knowledge.news.model.FreshItem;
import com.dante.knowledge.news.other.NewsListAdapter;
import com.dante.knowledge.ui.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;

public class FreshDetailActivity extends BaseActivity {

    @Bind(R.id.pager)
    ViewPager pager;
    public ArrayList<FreshItem> freshItems;

    @Override
    protected void initLayoutId() {
        layoutId = R.layout.activity_fresh_detail;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void initViews() {
        super.initViews();
        freshItems = (ArrayList<FreshItem>) getIntent().getSerializableExtra(NewsListAdapter.FRESH_ITEMS);
        int position = getIntent().getIntExtra(NewsListAdapter.FRESH_ITEM_POSITION, 0);

        pager.setAdapter(new FreshDetailPagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(2);
        pager.setCurrentItem(position);
    }

    private class FreshDetailPagerAdapter extends FragmentPagerAdapter {

        public FreshDetailPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FreshDetailFragment.newInstance(freshItems.get(position));
        }

        @Override
        public int getCount() {
            return freshItems.size();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);// TODO: 2016/2/12
    }
}
