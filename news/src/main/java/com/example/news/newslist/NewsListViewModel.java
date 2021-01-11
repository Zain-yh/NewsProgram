package com.example.news.newslist;

import com.example.base.mvvm.viewmodel.BaseMvvmViewModel;

public class NewsListViewModel extends BaseMvvmViewModel<NewsListModel, BaseMvvmViewModel> {


    private final String channel;

    public NewsListViewModel(String channel) {
        this.channel = channel;
    }


    @Override
    public NewsListModel createModel() {
        return new NewsListModel(channel);
    }
}
