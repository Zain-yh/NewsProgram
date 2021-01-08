package com.example.news.base;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class LazyFragment extends Fragment {
    protected boolean isLoaded = false;
    protected static String CHANNEL_NAME;
    protected String TAG = getClass().getSimpleName() + "-----LazyFragment-------->";

    public abstract void lazyInit();
    protected boolean isFirstInit = true;

    @Override
    public void onResume() {
        super.onResume();
        if (!isLoaded && !isHidden()){
            Log.d(TAG, " onResume: start Init");
            lazyInit();
            isLoaded = true;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
        isLoaded = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    protected void setTAG(String TAG){
        this.TAG = "-----LazyFragment-------->" + TAG;
    }
}
