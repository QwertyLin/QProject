package q.project.os.bitmap;

import java.io.IOException;
import java.io.InputStream;

import q.project.R;
import q.util.a.QToStr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MatrixA extends Activity {
	
	Context ctx;
	Button btn;
	InputStream bitmapStream;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.q_project);
		bitmapStream = null;
		try {
			bitmapStream = getAssets().open("bitmap.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//
		btn = (Button)findViewById(R.id.btn1);
		btn.setText("旋转");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeStream(bitmapStream);
				final float degrees = 45;
				Matrix matrix = new Matrix();
				matrix.setRotate(degrees);
				bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, false);
				show(bm);
			}
		});
		//
		btn = (Button)findViewById(R.id.btn2);
		btn.setText("缩放");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeStream(bitmapStream);
				final float width = 300f, height = 300f;
				Matrix matrix = new Matrix();
				matrix.setScale(width / bm.getWidth(), height / bm.getHeight());
				bm =  Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, false);
				show(bm);
			}
		});
		//
		btn = (Button)findViewById(R.id.btn3);
		btn.setText("垂直反转");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeStream(bitmapStream);
				Matrix matrix = new Matrix();
				matrix.setScale(1, -1);//水平反转为matrix.setScale(-1, 1);
				bm =  Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, false);
				show(bm);
			}
		});
		
	}
	
	private final void show(Bitmap bm){
		ImageView iv = new ImageView(this);
		iv.setImageBitmap(bm);
		iv.setBackgroundColor(0xFFFFFFFF);
		new AlertDialog.Builder(this)
		.setView(iv)
		.setMessage(QToStr.toStr(bm))
		.show();
	}
}
