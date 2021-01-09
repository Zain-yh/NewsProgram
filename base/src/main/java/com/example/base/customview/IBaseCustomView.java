package com.example.base.customview;

//将ViewMode作为参数传入View
public interface IBaseCustomView<DATA extends BaseCustomViewModel> {
    void setData(DATA data);
}
