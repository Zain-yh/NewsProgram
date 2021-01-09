package com.example.base.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseCustomView<VIEW extends ViewDataBinding, DATA extends BaseCustomViewModel> extends LinearLayout implements IBaseCustomView<DATA>{
    protected VIEW mBinding;
    protected DATA viewModel;

    //构造，在new时调用
    public BaseCustomView(Context context) {
        super(context);
        init();
    }

    //在XML中调用---反射
    public BaseCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //主题style
    public BaseCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //第四个参数------用于自定义属性
    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    //databinding 简单示例
    public void init(){
        //简单工厂设计模式
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //使用dataBinding将布局绑定
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), this, false);
        mBinding.getRoot().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRootClicked(v);
            }
        });
        //再将binding中的布局添加进去
        addView(mBinding.getRoot());
    }

    public abstract int getLayoutId();

    public abstract void onRootClicked(View view);

    @Override
    public void setData(DATA viewModel) {
        //由于setViewModel的名字是由子了ide布局中定义的，需要从子类中去取
        this.viewModel = viewModel;
        setDataToView(viewModel);
        mBinding.executePendingBindings();
    }

    protected abstract void setDataToView(DATA viewModel);
}
