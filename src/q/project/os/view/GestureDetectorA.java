package q.project.os.view;

import q.util.a.QLog;
import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class GestureDetectorA extends Activity {

	GestureDetector gd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout);
		//
		gd = new GestureDetector(this, gpListener);
	}
	
	GestureDetector.OnGestureListener gpListener = new GestureDetector.OnGestureListener() {
		
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			QLog.log("onSingleTapUp:" + e.toString());
			return true;
		}
		
		@Override
		public void onShowPress(MotionEvent e) {
			QLog.log("onShowPress");
		}
		
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
				float distanceY) {
			QLog.log("onScroll:" + e1.toString());
			return true;
		}
		
		@Override
		public void onLongPress(MotionEvent e) {
			QLog.log("onLongPress");
		}
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			QLog.log("onFling:" + e1.toString() + " " + e2.toString());
			QLog.log("velocityX=" + velocityX + " velocityY=" + velocityY);
			return true;
		}
		
		@Override
		public boolean onDown(MotionEvent e) {
			QLog.log("onDown");
			return true;
		}
	};

	public boolean onTouchEvent(MotionEvent event) {
		if(gd.onTouchEvent(event)){
			return true;
		}
		return false;
	};
}
