package com.example.news.newslist;

import android.annotation.SuppressLint;
import com.example.base.customview.BaseCustomViewModel;
import com.example.base.mvvm.model.BaseMvvmModel;
import com.example.common.views.PictureTitleViewModel;
import com.example.network.BaseObserver;
import com.example.network.MyNetworkApi;
import com.example.news.bean.AllNews;
import com.example.news.bean.NewsBean;
import com.example.news.inter.NewsApiInterface;
import java.util.ArrayList;
import java.util.List;


public class NewsListModel extends BaseMvvmModel<List<BaseCustomViewModel>> {
    private final String mChannel;


    public NewsListModel(String channel) {
        mChannel = channel;
    }

    @SuppressLint("CheckResult")
    @Override
    public void load() {
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
                        notifyResultToListener(viewModels);

                    }

                    @Override
                    protected void onFailure(String message) {
                        loadFailed(message);
                    }
                }));
    }


}
