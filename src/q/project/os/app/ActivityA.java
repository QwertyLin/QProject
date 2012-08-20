package q.project.os.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityA extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText(this.toString());
		setContentView(tv);
		System.out.println("onCreate " + this.toString());
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		System.out.println("onStart " + this.toString());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("onResume " + this.toString());
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		System.out.println("onRestart " + this.toString());
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		System.out.println("onPause " + this.toString());
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		System.out.println("onStop " + this.toString());
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("onDestroy " + this.toString());
	}
	
}
