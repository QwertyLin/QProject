package q.project;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class TabHostActivity extends TabActivity implements OnCheckedChangeListener {
	
	private TabHost tabHost;
	private final Class<?>[] clazz = {
			MoreActivity.class,
			MoreActivity.class,
			MoreActivity.class,
			MoreActivity.class
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);
		
		//getViews
		this.tabHost = getTabHost();
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		
		//setViews		
		tabHost.addTab(tabHost.newTabSpec("").setIndicator("")
				.setContent(new Intent(this, clazz[0])));
		tabHost.addTab(tabHost.newTabSpec("").setIndicator("")
				.setContent(new Intent(this, clazz[1])));
		tabHost.addTab(tabHost.newTabSpec("").setIndicator("")
				.setContent(new Intent(this, clazz[2])));
		tabHost.addTab(tabHost.newTabSpec("").setIndicator("")
				.setContent(new Intent(this, clazz[3])));
	    tabHost.setCurrentTab(0);
	    
	    //setListeners
		radioGroup.setOnCheckedChangeListener(this);
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio_1:
			tabHost.setCurrentTab(0);
			break;
		case R.id.radio_2:
			tabHost.setCurrentTab(1);
			break;
		case R.id.radio_3:
			tabHost.setCurrentTab(2);
			break;
		case R.id.radio_4:
			tabHost.setCurrentTab(3);
			break;
		default:
			break;
		}
	}
}


