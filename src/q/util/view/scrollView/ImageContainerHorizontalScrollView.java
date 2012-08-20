package q.util.view.scrollView;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.FrameLayout;

public class ImageContainerHorizontalScrollView extends BaseHorizontalScrollView {
	
	public ImageContainerHorizontalScrollView(Context ctx, FrameLayout parentLayout) {
		super(ctx, parentLayout);
		//
		this.setHorizontalScrollBarEnabled(false);
	}
	
	public View getItemAt(int index) {
		return insideLayout.getChildAt(index);
	}
	
	public int getItemCount(){
		return insideLayout.getChildCount();
	}
	
	public void removeItemView(View v){
		insideLayout.removeView(v);
	}
	
	public void removeAllItemViews(){
		insideLayout.removeAllViews();
	}
	
	public void fullScrollToRight(){
		new Thread(){
			@Override
			public void run() {
				SystemClock.sleep(100);
				handler.sendEmptyMessage(0);
			}
		}.start();
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			fullScroll(View.FOCUS_RIGHT);
		};
	};
	
}
