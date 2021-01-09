package com.example.common.databinding;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

public class CommonBindingAdapters {


    //该方法对应xml中使用app命名空间的方法 必须是public static
    @BindingAdapter("loadImageUrl")
    public static void loadImageUrl(ImageView imageView, String picUrl){ //需要传入使用该方法的空间
        if (!TextUtils.isEmpty(picUrl)) {
            Glide.with(imageView.getContext())
                    .load(picUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }
    }
}
