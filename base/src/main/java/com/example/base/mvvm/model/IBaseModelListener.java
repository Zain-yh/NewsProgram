package com.example.base.mvvm.model;

public interface IBaseModelListener<DATA> {
    void onLoadSuccess(BaseMvvmModel model, DATA data, LoadResult loadResult);
    void onLoadFailed(BaseMvvmModel model, String message);
}
