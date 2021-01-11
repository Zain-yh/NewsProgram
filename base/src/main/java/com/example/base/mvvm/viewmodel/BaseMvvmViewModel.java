package com.example.base.mvvm.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.base.customview.BaseCustomViewModel;
import com.example.base.mvvm.model.BaseMvvmModel;
import com.example.base.mvvm.model.IBaseModelListener;
import com.example.base.mvvm.model.LoadResult;

import java.util.List;

public abstract class BaseMvvmViewModel<MODEL extends BaseMvvmModel, DATA> extends ViewModel implements IBaseModelListener<List<DATA>> {
    public MutableLiveData<List<DATA>> dataList = new MutableLiveData<>();
    protected  MODEL model;
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public MutableLiveData<ViewStatus> viewStatus = new MutableLiveData<>();

    public BaseMvvmViewModel(){
        viewStatus.setValue(ViewStatus.LOADING);
    }

    public abstract MODEL createModel();

    public void refresh(){
        checkViewModel();
        if (model != null) {
            model.refresh();
        }
        viewStatus.postValue(ViewStatus.LOADING);
    }

    public void loadMore(){
        checkViewModel();
        if (model != null) {
            model.loadMore();
        }
        viewStatus.postValue(ViewStatus.LOADING);
    }

    private void checkViewModel(){
        if (model == null){
            model = createModel();
            model.register(this);
        }
    }

    @Override
    protected void onCleared(){
        super.onCleared();
    }
    @Override
    public void onLoadSuccess(BaseMvvmModel model, List<DATA> baseCustomViewModels, LoadResult loadResult) {
            if (loadResult.isLoadMore) {
                dataList.getValue().addAll(baseCustomViewModels);
                dataList.postValue(dataList.getValue());
            } else {
                dataList.postValue(baseCustomViewModels);
            }
            viewStatus.postValue(ViewStatus.SHOW_CONTENT);
            if (!loadResult.isHasNext()) {
                viewStatus.postValue(ViewStatus.NO_MORE_DATA);
            }
    }

    @Override
    public void onLoadFailed(BaseMvvmModel model, String message) {
        errorMessage.postValue(message);
        viewStatus.postValue(ViewStatus.REFRESH_ERROR);
    }

}
