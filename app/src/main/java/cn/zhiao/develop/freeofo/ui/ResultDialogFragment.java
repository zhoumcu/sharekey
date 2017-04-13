package cn.zhiao.develop.freeofo.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhiao.develop.freeofo.R;
import cn.zhiao.develop.freeofo.adapter.ResultAdapter;
import cn.zhiao.develop.freeofo.bean.Keys;

/**
 * author：Administrator on 2017/4/13 09:49
 * company: xxxx
 * email：1032324589@qq.com
 */

public class ResultDialogFragment extends DialogFragment {
    public static final String MY_TAG = "ResultDialogFragment";
    @Bind(R.id.recycler)
    EasyRecyclerView recycler;
    private List<Keys> keyses = new ArrayList<>();

    private static ResultDialogFragment getInstance(List<Keys> chooseData) {
        ResultDialogFragment mCategoryDialogFragment = new ResultDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) chooseData);
        mCategoryDialogFragment.setArguments(bundle);
        return mCategoryDialogFragment;
    }

    public static ResultDialogFragment showDialog(AppCompatActivity activity, List<Keys> chooseData) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(MY_TAG);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }
        fragmentTransaction.addToBackStack(null);
        // Create and show the dialog.
        ResultDialogFragment categoryDialogFragment = ResultDialogFragment.getInstance(chooseData);
        categoryDialogFragment.show(fragmentTransaction, MY_TAG);
        return categoryDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keyses = (List<Keys>) getArguments().getSerializable("data");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_result, container);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        initEvent();
        return view;
    }

    private void initEvent() {

    }

    private void initData() {

    }

    private void initView(View view) {
        recycler.setEmptyView(R.layout.error_view);
        recycler.setErrorView(R.layout.error_view);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(new ResultAdapter(getContext(), keyses));
        if (keyses.size() == 0)
            recycler.showEmpty();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.dialog_result);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setWindowAnimations(R.style.DialogBottom);
        window.setAttributes(lp);
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick(R.id.close)
    public void onClick() {
        dismiss();
    }
}
