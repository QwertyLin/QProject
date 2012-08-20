package q.project.os.view;

import q.project.QProjectItem;
import q.project.R;
import a.view.QTextView;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
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
				QTextView.clearHtml(tvNew);
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
				QTextView.formatHtml(tvNew);
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
				QTextView.formatHtmlWithAnchor(ctx, tvNew, 0xFFFF0000, true, new QTextView.AnchorClickListener() {
					@Override
					public void onClick(Uri uri, String text) {
						Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show();
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
				QTextView.formatHtmlWithImage(ctx, tvNew, "{图片}", R.drawable.ic_launcher);
			}
		});
	}
}
