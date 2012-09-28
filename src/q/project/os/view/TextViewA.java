package q.project.os.view;

import q.project.QProjectItem;
import q.project.R;
import q.util.Q;
import q.view.text.QRotateTextView;
import android.app.AlertDialog;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TextViewA extends QProjectItem {
	
	TextView tvOld, tvNew;

	@Override
	protected void onInit() {
		layout.setPadding(0, 0, 0, 10);
		//
		tvOld = new TextView(this);
		layout.addView(tvOld, 0);
		//
		tvNew = new TextView(this);
		layout.addView(tvNew, 1);
		//
		btn = getNextButton();
		btn.setText("去HTML化");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String src = "==<font color='red'>红字</font>==<a href='http://www.baidu.com'>超链接</a>==";
				tvOld.setText(src);
				tvNew.setText(src);
				Q.view.text.util.clearHtml(tvNew);
			}
		});
		//
		btn = getNextButton();
		btn.setText("HTML化");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String src = "==<font color='red'>红字</font>==<a href='http://www.baidu.com'>超链接</a>==";
				tvOld.setText(src);
				tvNew.setText(src);
				Q.view.text.util.formatHtml(tvNew);
			}
		});
		//
		btn = getNextButton();
		btn.setText("HTML化，含超链接");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String src = "==<font color='red'>红字</font>==<a href='http://www.baidu.com'>超链接</a>==";
				tvOld.setText(src);
				tvNew.setText(src);
				Q.view.text.util.formatHtmlWithAnchor(mCtx, tvNew, 0xFFFF0000, true, new Q.view.text.util.AnchorClickListener() {
					@Override
					public void onClick(Uri uri, String text) {
						Toast.makeText(mCtx, text, Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		//
		btn = getNextButton();
		btn.setText("HTML化，替代文字为图片");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String src = "=={图片}=={图片}==";
				tvOld.setText(src);
				tvNew.setText(src);
				Q.view.text.util.formatHtmlWithImage(mCtx, tvNew, "{图片}", R.drawable.ic_launcher);
			}
		});
		//
		btn = getNextButton();
		btn.setText("QRotateTextView 旋转的TextView");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				QRotateTextView tv = new QRotateTextView(mCtx, 45);
				tv.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
				tv.setBackgroundColor(0xFFFF0000);
				tv.setText("旋转的TextView");
				layout.addView(tv, 0);
			}
		});
	}
	
	private void show(View v){
		new AlertDialog.Builder(this).setMessage("111").setView(v).show();
	}
}
