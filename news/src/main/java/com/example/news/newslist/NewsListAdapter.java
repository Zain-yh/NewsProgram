package com.example.news.newslist;

import android.content.Context;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.base.customview.BaseCustomViewModel;
import com.example.base.recyclerview.BaseViewHolder;
import com.example.common.views.PictureTitleView;
import java.util.ArrayList;
import java.util.List;

class NewsListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final Context mContext;
    private List<BaseCustomViewModel> mNewsList;

    public NewsListAdapter(Context context) {
        mContext = context;
        mNewsList = new ArrayList<>();
    }

    public void setData(List<BaseCustomViewModel> newsList) {
        mNewsList = newsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(new PictureTitleView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
//        ((PictureTitleView)holder.itemView).setData((PictureTitleViewModel) mNewsList.get(position));
        //在Base中可以将上面方法抽出来，使用泛型，在自己的方法中调用，以防有多个布局需要加载
        holder.bind(mNewsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mNewsList == null ? 0 : mNewsList.size();
    }

}
