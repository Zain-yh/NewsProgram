package com.example.news.newslist;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Toast;

import com.example.base.customview.BaseCustomViewModel;
import com.example.base.mvvm.model.IBaseModelListener;
import com.example.common.views.PictureTitleViewModel;
import com.example.network.BaseObserver;
import com.example.network.MyNetworkApi;
import com.example.news.bean.AllNews;
import com.example.news.bean.NewsBean;
import com.example.news.inter.NewsApiInterface;

import java.util.ArrayList;
import java.util.List;

public class NewsListModel {
    private final String mChannel;
    IBaseModelListener<List<BaseCustomViewModel>> listener;
    int currentPage = 1;
    private int page;

    public NewsListModel(IBaseModelListener listener, String channel){
        this.listener = listener;
        mChannel = channel;
    }

    @SuppressLint("CheckResult")
    public void load(final boolean isLoadMore){
        int start = 0;
        if (isLoadMore) {
            start = currentPage * 20;
        }
        NewsApiInterface newsApiInterface = MyNetworkApi.getService(NewsApiInterface.class);
        newsApiInterface.getAllNews(mChannel, start, 20, MyNetworkApi.key)
                .compose(MyNetworkApi.getInstance().applySchedulers(new BaseObserver<AllNews>() {
                    @Override
                    protected void onSuccess(AllNews allNews) {
                        List<BaseCustomViewModel> viewModels = new ArrayList<>();
                        for (NewsBean newsBean : allNews.getResult().getList()) {
                            PictureTitleViewModel viewModel = new PictureTitleViewModel();
                            viewModel.webUrl = newsBean.getWeburl();
                            viewModel.picUrl = newsBean.getPic();
                            viewModel.time = newsBean.getTime();
                            viewModel.title = newsBean.getTitle();
                            viewModel.src = newsBean.getSrc();
                            viewModels.add(viewModel);
                        }
                        if (isLoadMore){
                            currentPage++;
                        }else {
                            currentPage = 1;
                        }
                        listener.onLoadSuccess(viewModels, isLoadMore);
                    }

                    @Override
                    protected void onFailure(String message) {
                        listener.onLoadFailed(message);
                    }
                }));
    }
}
