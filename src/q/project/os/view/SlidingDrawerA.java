package q.project.os.view;

import q.project.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SlidingDrawerA extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.view_slidingdrawer);
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.layout);
        //
        ImageButton iv = new ImageButton(this);
		iv.setBackgroundResource(R.drawable.ic_launcher);
        TextView tv = new TextView(this);
        tv.setText("自定义的SlidingDrawer");
        tv.setBackgroundColor(0xFF00FF00);
        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT));
        SlidingDrawerC sdc = new SlidingDrawerC(this, SlidingDrawerC.LEFT, iv, tv);
        layout.addView(sdc);  
        //
        iv = new ImageButton(this);
		iv.setBackgroundResource(R.drawable.ic_launcher);
        tv = new TextView(this);
        tv.setText("简化的自定义的SlidingDrawer");
        tv.setBackgroundColor(0xFF00FF00);
        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT));
        SlidingDrawerCSimple sdcs = new SlidingDrawerCSimple(this, SlidingDrawerC.LEFT, iv, tv);
        layout.addView(sdcs);  
	}
	
}
