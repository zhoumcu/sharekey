package cn.zhiao.baselib.custom.weight.sidebar;

public interface MultiItemTypeSupport<T>
{
	int getLayoutId(int itemType);

	int getItemViewType(int position, T t);
}