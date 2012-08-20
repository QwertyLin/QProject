package q.project.framework;

import q.project.R;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FrameworkUtil {

	public static final void addTitle(Context ctx, ViewGroup parent, boolean showBackBtn){
		final Activity act = ((Activity)ctx);
		act.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LinearLayout.LayoutParams lpfw = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams lp0w1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
		//
		parent.setBackgroundColor(0xffffffff);
		LinearLayout layout = new LinearLayout(ctx);
		layout.setLayoutParams(lpfw);
		layout.setBackgroundResource(R.drawable.q_framework_title_bg);
		parent.addView(layout, 0);
		//
		Button backBtn = new Button(ctx);
		backBtn.setText("返回");
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				act.finish();
			}
		});
		layout.addView(backBtn);
		//
		TextView tv = new TextView(ctx);
		tv.setLayoutParams(lp0w1);
		layout.addView(tv);
		//
		Button moreBtn = new Button(ctx);
		moreBtn.setText("更多");
		layout.addView(moreBtn);
	}
}
