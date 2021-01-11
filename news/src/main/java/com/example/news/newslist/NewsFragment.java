package com.example.news.newslist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.base.mvvm.model.BaseMvvmModel;
import com.example.base.mvvm.model.IBaseModelListener;
import com.example.base.mvvm.model.LoadResult;
import com.example.base.mvvm.viewmodel.ViewStatus;
import com.example.news.R;
import com.example.base.customview.BaseCustomViewModel;
import com.example.base.customview.LazyFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends LazyFragment implements Observer{

    private String mChannel;
    private RecyclerView recyclerView;
    private NewsListAdapter newsListAdapter;
    private List<BaseCustomViewModel> newsList = new ArrayList<>();
    private SmartRefreshLayout refreshLayout;
    NewsListViewModel newsListViewModel;

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
                newsListViewModel.refresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                newsListViewModel.loadMore();
            }
        });
        newsListViewModel = new NewsListViewModel(mChannel);
        initLiveData();
        return view;
    }

    private void initLiveData() {
        newsListViewModel.dataList.observe(this, this);
        newsListViewModel.viewStatus.observe(this, new Observer<ViewStatus>() {
            @Override
            public void onChanged(ViewStatus viewStatus) {
                switch (viewStatus){
                    case EMPTY:
                        break;
                    case LOADING:
                        refreshLayout.setEnableLoadMore(true);
                        break;
                    case NO_MORE_DATA:
                        Toast.makeText(getContext(), "没有更多了!!", Toast.LENGTH_LONG).show();
                        refreshLayout.setEnableLoadMore(false);
                        break;
                    case SHOW_CONTENT:
                        Toast.makeText(getContext(), "加载成功!!", Toast.LENGTH_LONG).show();
                        break;
                    case REFRESH_ERROR:
                        break;
                    case LOAD_MORE_FAILED:
                        break;
                }
            }
        });
        newsListViewModel.errorMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                Log.d(TAG, "onFailure: " + errorMessage);
            }
        });
    }

    @Override
    public void lazyInit() {
        newsListViewModel.refresh();
    }

    @Override
    public void onChanged(Object o) {
        refreshLayout.finishRefresh(true);
        refreshLayout.finishLoadMore(true);
        newsListAdapter.setData((List<BaseCustomViewModel>) o);
    }
}
