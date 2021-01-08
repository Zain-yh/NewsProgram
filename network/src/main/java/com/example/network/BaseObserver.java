package com.example.network;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onFailure(e.getMessage());
    }

    protected abstract void onSuccess(T t);

    protected abstract void onFailure(String message);

    @Override
    public void onComplete() {

    }
}
