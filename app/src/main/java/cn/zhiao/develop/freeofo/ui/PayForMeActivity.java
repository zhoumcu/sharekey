package cn.zhiao.develop.freeofo.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.OnClick;
import c.b.BP;
import c.b.PListener;
import cn.zhiao.baselib.base.BaseActivity;
import cn.zhiao.develop.freeofo.R;
import cn.zhiao.develop.freeofo.adapter.ChargeAmountAdapter;
import cn.zhiao.develop.freeofo.adapter.ChargeAmountDividerDecoration;

/**
 * author：Administrator on 2017/4/20 11:53
 * company: xxxx
 * email：1032324589@qq.com
 */
public class PayForMeActivity extends BaseActivity implements ChargeAmountAdapter.OnItemClickListener {
    @Bind(R.id.ballance)
    TextView ballance;
    @Bind(R.id.recyclerview_acount)
    RecyclerView recyclerviewAcount;
    @Bind(R.id.charge_layout)
    LinearLayout chargeLayout;
    @Bind(R.id.trancaction_type)
    TextView trancactionType;
    @Bind(R.id.wechat)
    ImageView wechat;
    @Bind(R.id.wechat_layout)
    RelativeLayout wechatLayout;
    @Bind(R.id.alipay)
    ImageView alipay;
    @Bind(R.id.alipay_layout)
    RelativeLayout alipayLayout;
    @Bind(R.id.trancaction_type_layout)
    LinearLayout trancactionTypeLayout;
    @Bind(R.id.book_bt)
    TextView bookBt;
    private ChargeAmountAdapter adapter;
    private ProgressDialog dialog;
    private boolean alipayOrWechatPay;
    private double prices[] = {0.1, 0.3, 0.5, 1.0, 5.0, 10.0};
    private double priceStr = 0.0;

    @Override
    public void initView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        recyclerviewAcount.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new ChargeAmountAdapter(this);
        recyclerviewAcount.setAdapter(adapter);
        adapter.setOnClickListener(this);
        recyclerviewAcount.addItemDecoration(new ChargeAmountDividerDecoration(10));
//        String acount_ballance = getString(R.string.account_ballance);
//        Utils.setSpannableStr(ballance, acount_ballance, acount_ballance.length() - 3, acount_ballance.length() - 2, 1.2f);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.aty_payforme;
    }

    @Override
    public void onItemClick(View v, int position, String s) {
        adapter.setSelectPosition(position);
        priceStr = prices[position];
    }

    @OnClick({R.id.wechat_layout, R.id.alipay_layout, R.id.book_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wechat_layout:
                wechat.setImageResource(R.mipmap.type_select);
                alipay.setImageResource(R.mipmap.type_unselect);
                alipayOrWechatPay = false;
                break;
            case R.id.alipay_layout:
                wechat.setImageResource(R.mipmap.type_unselect);
                alipay.setImageResource(R.mipmap.type_select);
                alipayOrWechatPay = true;
                break;
            case R.id.book_bt:
                pay(alipayOrWechatPay);
                break;
        }
    }

    private static final int REQUESTPERMISSION = 101;
    // 此为微信支付插件的官方最新版本号,请在更新时留意更新说明
    int PLUGINVERSION = 7;

    /**
     * 调用支付
     *
     * @param alipayOrWechatPay 支付类型，true为支付宝支付,false为微信支付
     */
    void pay(final boolean alipayOrWechatPay) {

        if (alipayOrWechatPay) {
            if (!checkPackageInstalled("com.eg.android.AlipayGphone",
                    "https://www.alipay.com")) { // 支付宝支付要求用户已经安装支付宝客户端
                Toast.makeText(getContext(), "请安装支付宝客户端", Toast.LENGTH_SHORT)
                        .show();
                return;
            }
        } else {
            if (checkPackageInstalled("com.tencent.mm", "http://weixin.qq.com")) {// 需要用微信支付时，要安装微信客户端，然后需要插件
                // 有微信客户端，看看有无微信支付插件
                int pluginVersion = BP.getPluginVersion(this);
                if (pluginVersion < PLUGINVERSION) {// 为0说明未安装支付插件,
                    // 否则就是支付插件的版本低于官方最新版
                    Toast.makeText(
                            getContext(),
                            pluginVersion == 0 ? "监测到本机尚未安装支付插件,无法进行支付,请先安装插件(无流量消耗)"
                                    : "监测到本机的支付插件不是最新版,最好进行更新,请先更新插件(无流量消耗)",
                            Toast.LENGTH_SHORT).show();
                    installApk("bp.db");
                    return;
                }
            } else {// 没有安装微信
                Toast.makeText(getContext(), "请安装微信客户端", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        showDialog("正在获取订单...\nSDK版本号:" + BP.getPaySdkVersion());
        final String name = "单次开锁费用/认证费用";

        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName("com.bmob.app.sport",
                    "com.bmob.app.sport.wxapi.BmobActivity");
            intent.setComponent(cn);
            this.startActivity(intent);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        BP.pay(name, "开锁认证费用清单", priceStr, alipayOrWechatPay, new PListener() {

            // 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
            @Override
            public void unknow() {
                Toast.makeText(getContext(), "支付结果未知,请稍后手动查询", Toast.LENGTH_SHORT)
                        .show();
                logE(name + "'s pay status is unknow\n\n");
                hideDialog();
            }

            // 支付成功,如果金额较大请手动查询确认
            @Override
            public void succeed() {
                Toast.makeText(getContext(), "支付成功!", Toast.LENGTH_SHORT).show();
                logE(name + "'s pay status is success\n\n");
                hideDialog();
            }

            // 无论成功与否,返回订单号
            @Override
            public void orderId(String orderId) {
                // 此处应该保存订单号,比如保存进数据库等,以便以后查询
//                order.setText(orderId);
                logE(name + "'s orderid is " + orderId + "\n\n");
                showDialog("获取订单成功!请等待跳转到支付页面~");
            }

            // 支付失败,原因可能是用户中断支付操作,也可能是网络原因
            @Override
            public void fail(int code, String reason) {

                // 当code为-2,意味着用户中断了操作
                // code为-3意味着没有安装BmobPlugin插件
                if (code == -3) {
                    Toast.makeText(
                            getContext(),
                            "监测到你尚未安装支付插件,无法进行支付,请先安装插件(已打包在本地,无流量消耗),安装结束后重新支付",
                            Toast.LENGTH_SHORT).show();
                    installApk("bp.db");
                } else {
                    Toast.makeText(getContext(), "支付中断!", Toast.LENGTH_SHORT)
                            .show();
                }
                logE(name + "'s pay status is fail, error code is \n"
                        + code + " ,reason is " + reason + "\n\n");
                hideDialog();
            }
        });
    }

    void showDialog(String message) {
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(this);
                dialog.setCancelable(true);
            }
            dialog.setMessage(message);
            dialog.show();
        } catch (Exception e) {
            // 在其他线程调用dialog会报错
        }
    }

    void hideDialog() {
        if (dialog != null && dialog.isShowing())
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
    }

    private void installApk(String s) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTPERMISSION);
        } else {
            installBmobPayPlugin(s);
        }
    }

    /**
     * 安装assets里的apk文件
     *
     * @param fileName
     */
    void installBmobPayPlugin(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + fileName + ".apk");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + file),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查某包名应用是否已经安装
     *
     * @param packageName 包名
     * @param browserUrl  如果没有应用市场，去官网下载
     * @return
     */
    private boolean checkPackageInstalled(String packageName, String browserUrl) {
        try {
            // 检查是否有支付宝客户端
            getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            // 没有安装支付宝，跳转到应用市场
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + packageName));
                startActivity(intent);
            } catch (Exception ee) {// 连应用市场都没有，用浏览器去支付宝官网下载
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(browserUrl));
                    startActivity(intent);
                } catch (Exception eee) {
                    Toast.makeText(getContext(),
                            "您的手机上没有没有应用市场也没有浏览器，我也是醉了，你去想办法安装支付宝/微信吧",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        return false;
    }
}
