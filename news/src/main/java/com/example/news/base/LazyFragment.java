package com.example.news.base;

import android.util.Log;

import androidx.fragment.app.Fragment;

public abstract class LazyFragment extends Fragment {
    protected boolean isLoaded = false;
    protected static String CHANNEL_NAME;
    private String TAG = getClass().getSimpleName();

    public abstract void lazyInit();

    @Override
    public void onResume() {
        super.onResume();
        if (!isLoaded && !isHidden()){
            Log.d(TAG, "onResume: start Init");
            lazyInit();
            isLoaded = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLoaded = false;
    }
}
