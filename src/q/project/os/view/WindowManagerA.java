package q.project.os.view;

import q.project.R;
import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;

public class WindowManagerA extends Activity {
	
	ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WindowManager.LayoutParams wlp = new WindowManager.LayoutParams();
	    wlp.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
	    wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
	    wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
	    wlp.x = -100;
	    wlp.y = 0;
	    wlp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
	    wlp.format = PixelFormat.TRANSLUCENT;
	    wlp.windowAnimations = 0;
	    iv = new ImageView(this);
	    iv.setBackgroundResource(R.drawable.ic_launcher);
	    getWindowManager().addView(iv, wlp);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		getWindowManager().removeView(iv);//记得退出时清除
	}
}
