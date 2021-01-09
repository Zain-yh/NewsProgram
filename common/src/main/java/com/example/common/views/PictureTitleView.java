package com.example.common.views;

import android.content.Context;
import android.util.Log;
import android.view.View;
import com.example.base.customview.BaseCustomView;
import com.example.common.databinding.PictureTitleViewBinding;
import com.example.common.R;


public class PictureTitleView extends BaseCustomView<PictureTitleViewBinding, PictureTitleViewModel>{

    private String TAG = getClass().getSimpleName();

    public PictureTitleView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.picture_title_view;
    }

    @Override
    public void onRootClicked(View view) {
        Log.d(TAG, "onRootClicked: " + viewModel.webUrl);
    }

    @Override
    protected void setDataToView(PictureTitleViewModel viewModel) {
        mBinding.setViewModel(viewModel);
    }


}
