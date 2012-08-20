package q.project.other;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

public class KeyListener extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode){
		case KeyEvent.KEYCODE_BACK:
			Toast.makeText(this, "KEYCODE_BACK", Toast.LENGTH_SHORT).show();
			return false;
		}
		return super.onKeyDown(keyCode, event);//默认返回true，效果类似finish();
	}
}
