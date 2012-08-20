package q.project.os.intent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class IntentFromOutA extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//
		Intent intent = getIntent();
		String action = intent.getAction();
		//
		if(action == null){
			return;
		}
		if(action.equals(Intent.ACTION_SEND)){
			Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
			show("Action Send: " + uri.toString());
		}else if(action.equals(Intent.ACTION_VIEW)){
			Uri uri = intent.getData();
			show("Action View: " + uri.toString());
		}
	}
	
	private void show(String text){
		TextView tv = new TextView(this);
		tv.setText(text);
		setContentView(tv);
	}
}
