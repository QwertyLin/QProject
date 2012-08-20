package q.project;

import q.util.view.AlignCenterScrollView;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TestScrollActivity extends Activity {
		
	static int SCROLL_RIGHT_ITEM_HEIGHT = 20, SCROLL_LEFT_ITEM_HEIGHT = 80;
	
	AlignCenterScrollView scrollLeft, scrollRight;
	
	int count = 200;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scroll);

		View[] viewsRight = new View[count];
		View[] viewsLeft = new View[count];
		for(int i = 0; i < count; i++){
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, SCROLL_RIGHT_ITEM_HEIGHT);
			TextView tv = new TextView(this);
			tv.setLayoutParams(lp);
			tv.setText(String.valueOf(i));
			viewsRight[i] = tv;
			
			tv = new TextView(this);
			lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, SCROLL_LEFT_ITEM_HEIGHT);
			tv.setLayoutParams(lp);
			tv.setText(String.valueOf(i));
			tv.setBackgroundColor(Color.rgb(i*8, i*8, i*8));
			viewsLeft[i] = tv;
		}
		
		//getViews
		scrollLeft = new AlignCenterScrollView(this, SCROLL_LEFT_ITEM_HEIGHT, viewsLeft, ((FrameLayout)findViewById(R.id.scroll_left)), new AlignCenterScrollView.Callback() {

			@Override
			public void onItemSelected(int index) {
				if(index != scrollRight.getCurrentIndex()){//避免重复交错调用
					scrollRight.setCurrentIndex(index);
				}
			}

			@Override
			public View initSelectedView(int index) {
				TextView tv = new TextView(TestScrollActivity.this);
				tv.setLayoutParams(new FrameLayout.LayoutParams(100, 100));
				tv.setText("选中了："+index);
				tv.setBackgroundColor(0x55FF0000);
				return tv;
			}
		});
		
		scrollRight = new AlignCenterScrollView(this, SCROLL_RIGHT_ITEM_HEIGHT, viewsRight, ((FrameLayout)findViewById(R.id.scroll_right)), new AlignCenterScrollView.Callback() {

			@Override
			public void onItemSelected(int index) {
				if(index != scrollLeft.getCurrentIndex()){//避免重复交错调用
					scrollLeft.setCurrentIndex(index);
				}
			}

			@Override
			public View initSelectedView(int index) {
				TextView tv = new TextView(TestScrollActivity.this);
				tv.setLayoutParams(new FrameLayout.LayoutParams(100, 100));
				tv.setText("选中了："+index);
				tv.setBackgroundColor(0x55FF0000);
				return tv;
			}
		});
	}
}

