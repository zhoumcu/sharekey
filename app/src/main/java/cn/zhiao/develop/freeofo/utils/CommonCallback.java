package cn.zhiao.develop.freeofo.utils;

/**
 * Created by ymn on 2017/4/13.
 */
public interface CommonCallback<T> {
    void onSucess(T data);
    void onError(String errorMsg);
}
