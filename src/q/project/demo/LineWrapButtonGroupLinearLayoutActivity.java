package q.project.demo;

import q.util.view.LinearLayout.LineWrapButtonGroupLinearLayout;
import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

public class LineWrapButtonGroupLinearLayoutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FrameLayout layout = new FrameLayout(this);
		setContentView(layout);
		
		final String[] texts = {"去", "去我", "去我额", "去我额人", "去我额人他", "去", "去我", "去我额", "去我额人", "去我额人他"};
		
		LineWrapButtonGroupLinearLayout v = new LineWrapButtonGroupLinearLayout(this, texts, new LineWrapButtonGroupLinearLayout.Callback() {
			
			@Override
			public void onItemClick(int index) {
				toast(texts[index]);
			}
		});
		
		layout.addView(v);
	}
	
	private void toast(String show){
		Toast.makeText(this, show, Toast.LENGTH_SHORT).show();
	}
}
