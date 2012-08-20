package q.project.os.view;

import q.project.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PopupWindowA extends Activity {

	private Context ctx;
	private Button btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.q_project);
		ctx = this;
		//
		btn = (Button)findViewById(R.id.btn1);
		btn.setText("普通PopupWindow");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView tv = new TextView(ctx);
				tv.setText("Text");
				tv.setHeight(100);
				final PopupWindow pw = new PopupWindow(tv, LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT, true);
				pw.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_launcher));//必须设置background才能消失
				pw.setOutsideTouchable(true);
				pw.setAnimationStyle(android.R.style.Animation_Dialog);
				pw.update();
				pw.setTouchable(true);
				pw.setFocusable(true);
				//
				pw.showAtLocation(btn, Gravity.BOTTOM, 0, 0);
			}
		});
	}
}
