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
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class CanvasA extends Activity {
	
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
		btn.setText("倒影");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeStream(bitmapStream);
				final int gap = 4;//倒影与原图之间的空隙
				final int reflectHeight = bm.getHeight()/2;//倒影高度
				int width = bm.getWidth();
				int height = bm.getHeight();
				Bitmap newBm = Bitmap.createBitmap(width, height + reflectHeight, Config.ARGB_8888);
				//创建倒影图像
				Matrix matrix = new Matrix();
				matrix.setScale(1, -1);//垂直反转
				Bitmap reflection = Bitmap.createBitmap(bm, 0, height - reflectHeight, width, reflectHeight, matrix, false);
				//先画原图，再画倒影
				Canvas canvas = new Canvas(newBm);
				canvas.drawBitmap(bm, 0, 0, null);
				canvas.drawBitmap(reflection, 0, height + gap, null);
				//倒影添加渐变
				Paint paint = new Paint();
				LinearGradient shader = new LinearGradient(0, height, 0, newBm.getHeight() + gap, //渐变
						0x70ffffff, 0x00ffffff, TileMode.CLAMP);
				paint.setShader(shader);
				paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
				canvas.drawRect(0, height, width, newBm.getHeight() + gap, paint);
				show(newBm);
			}
		});
		//
		btn = (Button)findViewById(R.id.btn2);
		btn.setText("圆角边框");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
				//final int color = 0xFFFF0000;
				final int round = 1000;//圆角弧度
				int width = bm.getWidth();
				int height = bm.getHeight();
				Bitmap newBm = Bitmap.createBitmap(width, height, Config.ARGB_8888);
				//画圆角
				Paint paint = new Paint();
				paint.setAntiAlias(true);//抗锯齿
				//paint.setColor(color);
				Rect rect = new Rect(0, 0, width, height);
				Canvas canvas = new Canvas(newBm);
				canvas.drawARGB(0, 0, 0, 0);
				canvas.drawRoundRect(new RectF(rect), round, round, paint);
				//合并圆角与图片
				paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));//使用图像合成的16条Porter-Duff规则的任意一条来控制Paint如何与已有的Canvas图像进行交互。
				canvas.drawBitmap(bm, rect, rect, paint);
				show(newBm);
			}
		});
		//
		btn = (Button)findViewById(R.id.btn3);
		btn.setText("矩形边框");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeStream(bitmapStream);
				final int color = 0xFFFF0000;
				int width = bm.getWidth(), height = bm.getHeight();
				final int size = (width > height ? width : height) * 20 / 750;//边框大小
				Bitmap newBm = bm.copy(Bitmap.Config.ARGB_8888, true);
				Canvas canvas = new Canvas(newBm);         
		        Paint paint = new Paint();  
		        paint.setAntiAlias(true);
		        paint.setColor(color);
		        //上边
		        canvas.drawRect(0, 0, width, size, paint);
		        //下边
		        canvas.drawRect(0, height - size, width, height, paint);
		        //左边
		        canvas.drawRect(0, 0, size, height, paint);
		        //右边
		        canvas.drawRect(width - size, 0, width, height, paint);    
				show(newBm);
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
