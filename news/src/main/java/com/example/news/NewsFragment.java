package com.example.news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.network.BaseObserver;
import com.example.network.MyNetworkApi;
import com.example.news.base.LazyFragment;
import com.example.news.bean.AllNews;
import com.example.news.bean.NewsBean;
import com.example.news.inter.NewsApiInterface;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class NewsFragment extends LazyFragment {

    private String mChannel;
    private RecyclerView recyclerView;
    private NewsListAdapter newsListAdapter;
    private List<NewsBean> newsList;

    //由于fragment不能自定义构造函数，使用bundle传值
    public static NewsFragment getInstance(String channel){
        NewsFragment newsFragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CHANNEL_NAME, channel);
        newsFragment.setArguments(bundle);
        newsFragment.setTAG(channel);
        return newsFragment;
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_news, container, false);
        //取出传过来的值
        Bundle bundle = getArguments();
        mChannel = bundle.getString(CHANNEL_NAME);
        recyclerView = view.findViewById(R.id.recycler);
        newsListAdapter = new NewsListAdapter(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void lazyInit() {
        NewsApiInterface newsApiInterface = MyNetworkApi.getService(NewsApiInterface.class);
        newsApiInterface.getAllNews(mChannel, 10, MyNetworkApi.key)
                .compose(MyNetworkApi.getInstance().applySchedulers(new BaseObserver<AllNews>() {
                    @Override
                    protected void onSuccess(AllNews allNews) {
                        newsList = allNews.getResult().getList();
                        recyclerView.setAdapter(newsListAdapter);
                        newsListAdapter.setData(newsList);
                    }

                    @Override
                    protected void onFailure(String message) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onFailure: "+ message);
                    }
                }));
    }

    class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> {

        private final Context mContext;
        private List<NewsBean> mNewsList;

        public NewsListAdapter(Context context){
            mContext = context;
            mNewsList = new ArrayList<>();
        }

        public void setData(List<NewsBean> newsList){
            mNewsList = newsList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public NewsListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            NewsBean news = mNewsList.get(position);
            holder.title.setText(news.getTitle());
            holder.src.setText(news.getSrc());
            holder.time.setText(news.getTime());
            Glide.with(mContext).load(news.getPic()).transition(withCrossFade()).into(holder.image);
        }

        @Override
        public int getItemCount() {
            return mNewsList == null ? 0 : mNewsList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView time;
            public TextView src;
            public TextView title;
            public ImageView image;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.img_news);
                title = itemView.findViewById(R.id.title_news);
                src = itemView.findViewById(R.id.src_new);
                time = itemView.findViewById(R.id.time_news);
            }
        }
    }

}
