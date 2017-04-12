package cn.zhiao.baselib.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import cn.zhiao.baselib.R;
import cn.zhiao.baselib.utils.CommonUtil;

/**
 * 公共的dialog模板
 * @author haochen
 *
 */
public class CommonDialog extends Dialog implements
		View.OnClickListener {

	/** 电话 */
	private String phone;
	/** 内容前缀 */
	private String prefix;
	/** 标题 */
	private String title;

	private Context context;
	/** 监听 */
	private OKListener okListener;
	/** 内容text */
	private TextView contentText;
	/** 标题text */
	private TextView titleText;

	private Button okBtn;

	private Button cancelBtn;
	private SimpleDraweeView profileView;

	public void setCancelBtnGone() {

		okBtn.setVisibility(View.VISIBLE);

		cancelBtn.setVisibility(View.GONE);
	}

	public void onlyShowOkBtn() {
		okBtn.setBackgroundResource(R.drawable.dialog_btn_selector);
		cancelBtn.setVisibility(View.GONE);
	}

	public void setAppDownload() {
		findViewById(R.id.content_view).setVisibility(View.GONE);
		findViewById(R.id.dialog_divider).setVisibility(View.GONE);
	}

	public void setOkListener(OKListener okListener) {
		this.okListener = okListener;
	}

	public CommonDialog(Context context, String prefix, String phone,
						String title) {
		this(context, R.style.MyDialog);
		this.phone = phone;
		this.prefix = prefix;
		this.context = context;
		this.title = title;
	}

	public CommonDialog(Context context, String title) {
		this(context, R.style.MyDialog);
		this.title = title;
		this.context = context;
	}

	public CommonDialog(Context context, int theme) {
		super(context, theme);
	}

	public void setTitleText(String text) {
		titleText.setText(text);
	}

	public void setmessage(String text) {
		
		if(CommonUtil.isExistValue(text)&&text.length()>10){
			contentText.setGravity(Gravity.LEFT);
		}else{
			contentText.setGravity(Gravity.CENTER);
		}

		contentText.setText(text);
	}

	public void setmessage(Spanned text1) {
		contentText.setText(text1);
	}
	
	public void setmessageLeft() {
		contentText.setGravity(Gravity.LEFT);
	}
	
	public void setmessageCenter() {
		contentText.setGravity(Gravity.CENTER);
	}

	public void setPositiveText(String str) {
		okBtn.setText(str);
	}

	public void setPositiveColor(int color) {
		okBtn.setTextColor(context.getResources().getColor(color));
	}

	public void setNegativeText(String str) {
		cancelBtn.setText(str);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_pass_dialog);
		// this.setCancelable(true);
		titleText = (TextView) findViewById(R.id.title_find_pass);
		contentText = (TextView) findViewById(R.id.content);
		contentText.setText(String.format("%s%s", prefix, phone));
		titleText.setText(title);
		okBtn = (Button) findViewById(R.id.ok_btn);
		cancelBtn = (Button) findViewById(R.id.cancel_btn);
		findViewById(R.id.ok_btn).setOnClickListener(this);
		findViewById(R.id.cancel_btn).setOnClickListener(this);
		profileView = (SimpleDraweeView) findViewById(R.id.profile);

	}

	public void setContentLeft() {
		contentText.setGravity(Gravity.LEFT);
	}
	
	public void showProfile(String url) {
		profileView.setVisibility(View.VISIBLE);
		profileView.setImageURI(Uri.parse(url));
	}



	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.ok_btn) {

			if (okListener != null) {
				okListener.positiveAction();
			}

		} else if (v.getId() == R.id.cancel_btn) {
			if (okListener != null) {
				okListener.negativeAction();
			}

		}
		this.dismiss();
	}

	public interface OKListener {
		void positiveAction();

		void negativeAction();
	}
	
	@Override
	public void show() {
		if(isShowing()){
			return;
		}
		super.show();
	}
	
}
