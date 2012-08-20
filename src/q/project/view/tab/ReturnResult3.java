package q.project.view.tab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

public class ReturnResult3 extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			getParent().setResult(0, new Intent().putExtra("key", "value"));
			getParent().finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
