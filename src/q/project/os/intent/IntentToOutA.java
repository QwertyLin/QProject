package q.project.os.intent;

import q.project.QProjectItem;
import q.util.QIntentUtil;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class IntentToOutA extends QProjectItem {
	
	@Override
	protected void onInit() {
		btn = getNextButton();
		btn.setText("HOME");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_MAIN).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addCategory(Intent.CATEGORY_HOME));  
			}
		});
		//
		btn = getNextButton();
		btn.setText("Action Send 发送");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_SEND)
				.setType("image/*")
				.putExtra(Intent.EXTRA_STREAM, Uri.parse("QProject:testUrl"));
				startActivity(i);
			}
		});
		//
		btn = getNextButton();
		btn.setText("Action View 浏览器");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW)
				.setData(Uri.parse("http://www.baidu.com"));
				startActivity(i);
			}
		});
		//
		btn = getNextButton();
		btn.setText("settings.WirelessSettings 网络设置");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.WirelessSettings"));
				startActivity(intent);
			}
		});
		//
		btn = getNextButton();
		btn.setText("相册");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				QIntentUtil.contentImage(IntentToOutA.this, REQUEST_IMAGE);
			}
		});
		//
		btn = getNextButton();
		btn.setText("相机");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				QIntentUtil.mediaCamera(IntentToOutA.this, REQUEST_IMAGE);
			}
		});
		//
		btn = getNextButton();
		btn.setText("谷歌地图");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				QIntentUtil.mapGoogle(ctx, "23.122359", "113.302746", null);
			}
		});
	}
	
	private static final int 
		REQUEST_IMAGE = 1;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
		case REQUEST_IMAGE:
			Bitmap bm = QIntentUtil.resultBitmap(this, data);
			if(bm != null){
				ImageView iv = new ImageView(ctx);
				iv.setImageBitmap(bm);
				new AlertDialog.Builder(ctx).setView(iv).show();
			}
			break;
		}
	}

}
