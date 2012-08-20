package q.project;

import q.project.other.QCameraUtil;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CameraActivity extends Activity implements OnClickListener {
	
	ImageView imageView;
	QCameraUtil.Preview preview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera);
		
		//getViews
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
		Button button = (Button) findViewById(R.id.button);
		imageView = (ImageView) findViewById(R.id.imageView);
		
		//setViews
		preview = QCameraUtil.preview(this);
		layout.addView(preview);
		
		
		
		
		//setListeners
		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button:
			//System.out.println(preview);
			imageView.setImageBitmap(QCameraUtil.takePicture(preview));
			break;
		}
	}

}
