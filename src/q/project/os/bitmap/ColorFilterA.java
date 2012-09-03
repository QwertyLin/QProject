package q.project.os.bitmap;

import java.io.IOException;
import java.io.InputStream;

import q.project.R;
import q.util.a.QToStr;
import q.util.bitmap.QBitmapUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class ColorFilterA extends Activity {
	
	Context ctx;
	Button btn;
	InputStream bitmapStream;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.q_project);
		ctx = this;
		bitmapStream = null;
		try {
			bitmapStream = getAssets().open("bitmap.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//
		btn = (Button)findViewById(R.id.btn1);
		btn.setText("调节亮度");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeStream(bitmapStream);
				show(ColorFilterUtil.brightness(bm, -100));
			}
		});
		//
		btn = (Button)findViewById(R.id.btn2);
		btn.setText("调节对比度");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeStream(bitmapStream);
				show(ColorFilterUtil.contrast(bm, 100));
			}
		});
		//
		btn = (Button)findViewById(R.id.btn3);
		btn.setText("调节饱和度");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeStream(bitmapStream);
				show(ColorFilterUtil.saturation(bm, -50));
			}
		});
		//
		btn = (Button)findViewById(R.id.btn4);
		btn.setText("底片效果");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeStream(bitmapStream);
				show(ColorFilterUtil.negative(bm));
			}
		});
		//
		btn = (Button)findViewById(R.id.btn5);
		btn.setText("灰白效果");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeStream(bitmapStream);
				show(ColorFilterUtil.gray(bm));
			}
		});
	}
	
	private final void show(Bitmap bm){
		ImageView iv = new ImageView(this);
		iv.setImageBitmap(bm);
		iv.setBackgroundColor(0xFFFFFFFF);
		new AlertDialog.Builder(this)
		.setView(iv)
		.setMessage(QBitmapUtil.toString(bm))
		.show();
	}
}
