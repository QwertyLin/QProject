package q.project.view.tab;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TabSub1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("TabSub1");
		setContentView(tv);
	}
}
