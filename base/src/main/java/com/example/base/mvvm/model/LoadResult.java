package com.example.base.mvvm.model;

public class LoadResult {
    public boolean isLoadMore;
    public boolean hasNext;

    public LoadResult(boolean isLoadMore, boolean hasNext) {
        this.isLoadMore = isLoadMore;
        this.hasNext = hasNext;
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
}
