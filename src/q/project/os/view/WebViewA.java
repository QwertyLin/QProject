package q.project.os.view;

import q.util.a.QLog;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

public class WebViewA extends Activity {
	
	//WebView实现Javascript调用Java
	//http://www.eoeandroid.com/case/2012/0504/1552.html
	
	Context ctx;
	Button btn ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = this;
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout);
		//
		final WebView mWebView = new WebView(this);
		mWebView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 200));
		mWebView.setVerticalScrollbarOverlay(true);
		final WebSettings settings = mWebView.getSettings();
		settings.setSupportZoom(true);
		//WebView启用Javascript脚本执行
		settings.setJavaScriptEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		//映射Java对象到一个名为”js2java“的Javascript对象上
		//JavaScript中可以通过"window.js2java"来调用Java对象的方法
		mWebView.addJavascriptInterface(new JSInvokeClass(), "HTMLOUT");
		mWebView.loadUrl("http://www.baidu.com");
		layout.addView(mWebView);
		//
		btn = new Button(this);
		btn.setText("show html");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mWebView.loadUrl("javascript:window.HTMLOUT.showHtml(document.documentElement.innerHTML);");
			}
		});
		layout.addView(btn);
		//
		btn = new Button(this);
		btn.setText("back");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mWebView.loadUrl("javascript:window.HTMLOUT.back();");
			}
		});
		layout.addView(btn);
	}
	
	/**网页Javascript调用接口**/
	class JSInvokeClass {
		public void showHtml(String html){
			new AlertDialog.Builder(ctx).setMessage(html).show();
		}
		public void back() {
			QLog.log("back");
			finish();
		}
	}

}
