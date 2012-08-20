package q.project.os.view;

import q.project.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

public class DialogA extends Activity {
	
	private Context ctx;
	private Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = this;
		ScrollView scroll = new ScrollView(this);
		setContentView(scroll);
		//
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		scroll.addView(layout);
		//
		btn = new Button(this);
		btn.setText("AlertDialog");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(ctx)
				.setTitle("Title")
				.setMessage("Message")
				.setNeutralButton("我知道了", null)
				.show();
			}
		});
		layout.addView(btn);
		//
		btn = new Button(this);
		btn.setText("ProgressDialog");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ProgressDialog.show(ctx, "Title", "Message", false, true);
			}
		});
		layout.addView(btn);
		//
		btn = new Button(this);
		btn.setText("屏蔽Dialog系统背景");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog d = new Dialog(ctx);
				d.setTitle("Title");
				d.getWindow().setBackgroundDrawableResource(R.drawable.translucent);
				d.show();
			}
		});
		layout.addView(btn);
		//
		btn = new Button(this);
		btn.setText("自定义显示位置");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog d = new Dialog(ctx);
				d.setTitle("Title");
				//
				Window window = d.getWindow();
				//确定基点
		        window.setGravity(Gravity.LEFT | Gravity.TOP); 
		        //在基点的基础上进行偏移
		        WindowManager.LayoutParams lp = window.getAttributes();
		        lp.x = 10;
		        lp.y = 10;
		        window.setAttributes(lp);
		        //
				d.show();
			}
		});
		layout.addView(btn);
	}
}
