package q.project.os.view;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewA extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout);
		//
		layout.addView(new TextView(this){
			private int width, height;
			@Override
			protected void onLayout(boolean changed, int left, int top, int right,
					int bottom) {
				super.onLayout(changed, left, top, right, bottom);
				if(width == 0){
					width = getWidth();
					height = getHeight();
					setText("组件 width=" + getWidth() + " height=" + height);
				}
			}
		});
	}
	
}
