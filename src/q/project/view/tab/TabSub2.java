package q.project.view.tab;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TabSub2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("TabSub2");
		setContentView(tv);
	}

}
