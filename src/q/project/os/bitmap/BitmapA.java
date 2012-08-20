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
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class BitmapA extends Activity {
	
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
		btn.setText("从byte[](拍照),File(SD卡),Resources(res), InputStream(网络)解码Bitmap");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeStream(bitmapStream);
				show(bm);
			}
		});
		//
		btn = (Button)findViewById(R.id.btn2);
		btn.setText("由int[]创建bitmap");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final int width = 200, height = 200;
				int[] colors = new int[width * height];
		        for (int y = 0; y < height; y++) {
		            for (int x = 0; x < width; x++) {
		                int r = x * 255 / (width - 1);
		                int g = y * 255 / (height - 1);
		                int b = 255 - Math.min(r, g);
		                int a = Math.max(r, g);
		                colors[y * width + x] = (a << 24) | (r << 16) | (g << 8) | b;
		            }
		        }
		        Bitmap bm = Bitmap.createBitmap(colors, width, height, Config.ARGB_8888);//4*8=32位位图
				show(bm);
			}
		});
		//
		btn = (Button)findViewById(R.id.btn3);
		btn.setText("按指定宽度解码大图片Bitmap，只能缩小不能放大，旨在避免内存溢出。PS：因为是按整数比例缩小，所以只能接近并不能精确得到预期的宽度");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final int width = 20;
				BitmapFactory.Options opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeStream(bitmapStream, null, opts);
				int s = opts.outWidth / width;//原宽度与期望宽度的比例
				if (s == 0) {
					s = 1;
				}
				opts = new BitmapFactory.Options();
				opts.inSampleSize = s;
				Bitmap bm = BitmapFactory.decodeStream(bitmapStream, null, opts);
				show(bm);
			}
		});
		//
		btn = (Button)findViewById(R.id.btn4);
		btn.setText("将bitmap不透明的部分填充成指定颜色");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeStream(bitmapStream);
				final int red = 255, green = 0, blue = 0;//要填充的颜色，0~255
				int width = bm.getWidth(), height = bm.getHeight();
				int[] colors = new int[width * height];
		        for (int y = 0; y < height; y++) {
		            for (int x = 0; x < width; x++) {
		            	int color = bm.getPixel(x, y); 
		            	if(color != 0) {
		            		colors[y * width + x] = Color.argb(Color.alpha(color), red, green, blue);
		            	}
		            }
		        }
		        bm = Bitmap.createBitmap(colors, width, height, Config.ARGB_8888);
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
