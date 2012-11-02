package q.project;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class QTestA extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("QTestA");
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		WebView web = new WebView(this);
		setContentView(web);
		
	}

}
