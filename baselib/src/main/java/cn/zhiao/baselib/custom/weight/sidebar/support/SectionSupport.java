package cn.zhiao.baselib.custom.weight.sidebar.support;

/**
 * Created by zhy on 16/4/9.
 */
public interface SectionSupport<T> {
    public int sectionHeaderLayoutId();

    public int sectionTitleTextViewId();

    public String getTitle(T t);
}
