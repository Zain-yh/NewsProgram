package com.example.base.recyclerview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.base.customview.IBaseCustomView;
import com.example.base.customview.BaseCustomViewModel;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private IBaseCustomView itemView;
    public BaseViewHolder(@NonNull IBaseCustomView itemView) {
        super((View) itemView);
        this.itemView = itemView;
    }

    //将设置数据的过程抽出来，使用泛型
    public void bind(BaseCustomViewModel viewModel){
        itemView.setData(viewModel);
    }
}
