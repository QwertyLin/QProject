package q.project.view.tab;

import q.project.R;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class ReturnResult2 extends TabActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_tab_return_result);
		
		//getViews
		TabHost tabHost = getTabHost();
		
		//setViews		
		tabHost.addTab(tabHost.newTabSpec("").setIndicator("")
				.setContent(new Intent(this, ReturnResult3.class)));
		tabHost.setCurrentTab(0);
	}

}
