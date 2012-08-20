package q.project.os.app;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class ActivityGroupA extends ActivityGroup {
	
	Window window;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout);
		//
		final FrameLayout frame = new FrameLayout(this);
		layout.addView(frame);
		//
		Button btn = new Button(this);
		btn.setText("TabA");
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				frame.removeAllViews();
				Intent intent = new Intent(ActivityGroupA.this, ActivityA.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				window = getLocalActivityManager().startActivity("a", intent);
				frame.addView(window.getDecorView());
			}
		});
		layout.addView(btn, 0);
		//
		btn = new Button(this);
		btn.setText("另一个TabA");
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				frame.removeAllViews();
				Intent intent = new Intent(ActivityGroupA.this, ActivityA.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				window = getLocalActivityManager().startActivity("b", intent);
				frame.addView(window.getDecorView());
			}
		});
		layout.addView(btn, 1);
		//
		btn = new Button(this);
		btn.setText("第一个TabA");
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				frame.removeAllViews();
				Intent intent = new Intent(ActivityGroupA.this, ActivityA.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				window = getLocalActivityManager().startActivity("a", intent);
				frame.addView(window.getDecorView());
			}
		});
		layout.addView(btn, 2);
		
	}
	

}
