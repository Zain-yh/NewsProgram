package com.example.base.mvvm.model;

import java.lang.ref.WeakReference;

//基类中可以将共用的参数或方法抽取出来
public abstract class BaseMvvmModel<DATA> {
    protected WeakReference<IBaseModelListener> mReferenceIBaseModelListener;
    protected int currentPage = 1;
    protected boolean isLoading;
    protected int start;
    protected boolean isLoadMore = false;
    private static int MAX_PAGE = 20;

    public void register(IBaseModelListener listener) {
        if (listener != null) {
            mReferenceIBaseModelListener = new WeakReference<>(listener);
        }
    }

    protected abstract void load();

    public void refresh() {
        if (mReferenceIBaseModelListener.get() == null) {
            throw new RuntimeException("Not register listener before use refresh!!!");
        }
        if (isLoading) {
            return;
        }
        isLoading = true;
        start = 0;
        isLoadMore = false;
        load();
    }

    public void loadMore() {
        if (mReferenceIBaseModelListener.get() == null) {
            throw new RuntimeException("Not register listener before use load more!!!");
        }
        if (isLoading) {
            return;
        }
        isLoading = true;
        isLoadMore = true;
        start = currentPage * 20;
        load();
    }

    protected void notifyResultToListener(DATA data){
        IBaseModelListener listener = mReferenceIBaseModelListener.get();
        if (listener == null) {
            return;
        }
        if (isLoadMore) {
            currentPage++;
        } else {
            currentPage = 1;
        }
        listener.onLoadSuccess(this, data, new LoadResult(isLoadMore, currentPage != MAX_PAGE));
        isLoading = false;
    }

    protected void loadFailed(String errorMessage){
        IBaseModelListener listener = mReferenceIBaseModelListener.get();
        if (listener == null) {
            return;
        }
        listener.onLoadFailed(this, errorMessage);
        isLoading = false;
    }
}
