package q.project;

import q.util.view.imageView.BorderImageView;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.LinearLayout;

public class ViewImageView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_image_view);
		
		//getViews
		LinearLayout layout = (LinearLayout)findViewById(R.id.layout);
		
		//setViews
		final BorderImageView borderImageView = new BorderImageView(this, Color.RED, 10);
		borderImageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		borderImageView.setImageResource(R.drawable.ic_launcher);
		layout.addView(borderImageView);
		
		final Handler handler = new Handler(){
			public void handleMessage(android.os.Message msg) {
				borderImageView.setBorderColor(Color.BLUE);
			};
		};
		
		new Thread(){
			public void run() {
				SystemClock.sleep(500);
				handler.sendEmptyMessage(0);
			};
		}.start();
		
		
	}
	
	
}
