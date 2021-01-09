package com.example.news;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.network.BaseObserver;
import com.example.network.MyNetworkApi;
import com.example.news.bean.Channels;
import com.example.news.inter.NewsApiInterface;
import com.example.news.newslist.NewsFragment;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    String[] tabs = new String[]{"头条", "新闻", "国内", "国际", "政治", "财经", "体育",
            "娱乐", "军事", "教育", "科技", "NBA", "股票", "星座", "女性", "健康", "育儿"};
    List<String> tabList = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Map<String, Fragment> fragmentMap = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
//        loadTab();
        return view;
    }

    @SuppressLint("CheckResult")
    private void loadTab() {
        ((NewsApiInterface)MyNetworkApi.getService(NewsFragment.class))
                .getChannels(MyNetworkApi.key)
                .compose(MyNetworkApi.getInstance().applySchedulers(new BaseObserver<Channels>() {
                    @Override
                    protected void onSuccess(Channels channels) {
                        tabList.clear();
                        tabList.addAll(channels.getChannels());
                    }

                    @Override
                    protected void onFailure(String message) {

                    }
                }));
    }

    private void initView(View view) {
        tabLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewpager);
        tabList.addAll(Arrays.asList(tabs));
        for (String s : tabList) {
            tabLayout.addTab(tabLayout.newTab().setText(s));
            NewsFragment newsFragment = NewsFragment.getInstance(s);
            fragments.add(newsFragment);
        }

        FragmentManager fm = getActivity().getSupportFragmentManager();
        NewsFragmentAdapter fragmentAdapter = new NewsFragmentAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        fragmentAdapter.setData(fragments);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(0);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    class NewsFragmentAdapter extends FragmentPagerAdapter{

        private List<Fragment> fragments;

        public NewsFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void setData(List<Fragment> fragments){
            this.fragments = fragments;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        }
    }
}