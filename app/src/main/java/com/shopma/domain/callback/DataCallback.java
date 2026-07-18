package com.shopma.domain.callback;

public interface DataCallback<T> {
    void onSuccess(T result);
    void onError(String message);
}
