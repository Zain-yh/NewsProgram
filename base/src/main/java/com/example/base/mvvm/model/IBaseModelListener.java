package com.example.base.mvvm.model;

public interface IBaseModelListener<DATA> {
    void onLoadSuccess(DATA data, boolean isLoadMore);
    void onLoadFailed(String message);
}
