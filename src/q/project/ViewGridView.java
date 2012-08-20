package q.project;

import java.io.File;

import q.util.view.gridView.AsynImageGridView;
import q.util.view.imageView.BorderImageView;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewGridView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_grid_view);
		
		//getViews
		LinearLayout layout = (LinearLayout)findViewById(R.id.layout);
		
		String[] files = new File("/sdcard/DCIM/Camera").list();
		for(int i = 0; i < files.length; i++){
			files[i] = "/sdcard/DCIM/Camera/"+files[i];
			//System.out.println(files[i]);
		}
		new AsynImageGridView(this, files, 4, 6, (FrameLayout) findViewById(R.id.f));
	}
	
	
}
