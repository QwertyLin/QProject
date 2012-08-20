package q.project;

import q.util.view.AlignCenterScrollView;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AlignCenterScrollViewActivity extends Activity {
	
	static int itemHeight = 80;
	
	AlignCenterScrollView scroll;
	
	int currentIndex, previousIndex;
	final int COVER_RIGHT_WEIGHT = 3;//覆盖视图的位置
	final int COVER_LEFT_WEIGHT = 2;//处于中间
	int count = 200;
	int screenWidth, screenHeight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FrameLayout f = new FrameLayout(this);
		f.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		setContentView(f);
				
		View[] childViews = new View[count];
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, itemHeight);
		for(int i = 0; i < count; i++){
			TextView tv = new TextView(this);
			tv = new TextView(this);
			tv.setLayoutParams(lp);
			tv.setText(String.valueOf(i));
			tv.setBackgroundColor(Color.rgb(i*8, i*8, i*8));
			childViews[i] = tv;
		}
		
		scroll = new AlignCenterScrollView(this, itemHeight, childViews, f, new AlignCenterScrollView.Callback() {

			@Override
			public void onItemSelected(int index) {
				System.out.println(index);
			}

			@Override
			public View initSelectedView(int index) {
				TextView tv = new TextView(AlignCenterScrollViewActivity.this);
				tv.setLayoutParams(new FrameLayout.LayoutParams(100, 100));
				tv.setText("选中了："+index);
				tv.setBackgroundColor(0x55FF0000);
				return tv;
			}
		});
	}
}

