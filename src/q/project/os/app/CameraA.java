package q.project.os.app;

import q.project.R;
import q.util.bitmap.QBitmapUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class CameraA extends Activity {
	
	RelativeLayout layout;
	LinearLayout layoutBtn;
	Context ctx;
	QCameraView cv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.ctx = this;
	    //
	    setContentView(R.layout.q_project_relative);
	    layout = (RelativeLayout)findViewById(R.id.layout);
	    layoutBtn = (LinearLayout)findViewById(R.id.layout_btn);
	    //
	    cv = new QCameraView(this);
		layout.addView(cv, 0);
		//
		Button btn;
		btn = new Button(this);
		layoutBtn.addView(btn);		
		btn.setText("设置镜头方向");
		btn.setOnClickListener(new OnClickListener() {
			int i = 0;
			@Override
			public void onClick(View v) {
				cv.setOrientation(i);
				i += 90;
				i %= 360;
			}
		});
		//
		btn = new Button(this);
		layoutBtn.addView(btn);		
		btn.setText("自动对焦");
		btn.setOnClickListener(new OnClickListener() {
			int i = 0;
			@Override
			public void onClick(View v) {
				cv.autoFocus();
			}
		});
		//
		btn = new Button(this);
		layoutBtn.addView(btn);		
		btn.setText("拍照");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cv.takePicture(new QCameraView.TackPictureCallback() {
					@Override
					public void onCompleted(Bitmap bm) {
						ImageView iv = new ImageView(ctx);
						iv.setImageBitmap(bm);
					    new AlertDialog.Builder(ctx).setView(iv).setMessage(QBitmapUtil.toString(bm)).show();
					}
				});
				
			}
		});
	}

}