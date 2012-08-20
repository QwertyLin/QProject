package q.project.framework;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class TitleA extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout layout = new LinearLayout(this);
		FrameworkUtil.addTitle(this, layout, true);
		setContentView(layout);
		//
		
	}
}
