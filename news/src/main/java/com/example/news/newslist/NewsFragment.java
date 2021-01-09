package com.example.news.newslist;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.base.mvvm.model.IBaseModelListener;
import com.example.news.R;
import com.example.base.customview.BaseCustomViewModel;
import com.example.base.customview.LazyFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends LazyFragment implements IBaseModelListener<List<BaseCustomViewModel>> {

    private String mChannel;
    private RecyclerView recyclerView;
    private NewsListAdapter newsListAdapter;
    private List<BaseCustomViewModel> newsList = new ArrayList<>();
    private SmartRefreshLayout refreshLayout;
    NewsListModel newsListModel;

    //由于fragment不能自定义构造函数，使用bundle传值
    public static NewsFragment getInstance(String channel) {
        NewsFragment newsFragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CHANNEL_NAME, channel);
        newsFragment.setArguments(bundle);
        newsFragment.setTAG(channel);
        return newsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        //取出传过来的值
        Bundle bundle = getArguments();
        mChannel = bundle.getString(CHANNEL_NAME);
        recyclerView = view.findViewById(R.id.recycler);

        newsListAdapter = new NewsListAdapter(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(newsListAdapter);

        refreshLayout = view.findViewById(R.id.refreshlayout);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                newsListModel.load(false);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                newsListModel.load(true);
            }
        });
        newsListModel = new NewsListModel(this, mChannel);
        return view;
    }

    @Override
    public void lazyInit() {
        newsListModel.load(false);
    }

    @Override
    public void onLoadSuccess(List<BaseCustomViewModel> viewModels, boolean isLoadMore) {
        if (!isLoadMore) newsList.clear();
        newsList.addAll(viewModels);
        newsListAdapter.setData(newsList);
        refreshLayout.finishRefresh(true);
        refreshLayout.finishLoadMore(true);
    }

    @Override
    public void onLoadFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        Log.d(TAG, "onFailure: " + message);
        refreshLayout.finishRefresh(false);
        refreshLayout.finishLoadMore(false);
    }
}
