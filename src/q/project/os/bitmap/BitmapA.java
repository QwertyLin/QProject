package q.project.os.bitmap;

import q.project.QProjectItem;
import q.project.R;
import q.bitmap.QBitmapDecoder;
import q.bitmap.QBitmapFilter;
import q.bitmap.QBitmapFilterColor;
import q.bitmap.QBitmapFilterMatrix;
import q.bitmap.QBitmapUtil;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class BitmapA extends QProjectItem {
	
	String file = "/sdcard/DCIM/Camera/1.jpg";
	
	@Override
	protected void onInit() {
		//
		btn = getNextButton();
		btn.setText("从byte[](拍照),File(SD卡),Resources(res), InputStream(网络)解码Bitmap");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeFile(file);
				show(bm);
			}
		});
		//
		btn = getNextButton();
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
		btn = getNextButton();
		btn.setText("按指定尺度解码Bitmap（宽松）");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				show(QBitmapDecoder.deLoose(file, 200, 200));
			}
		});
		//
		btn = getNextButton();
		btn.setText("在指定尺度内(不超出)解码Bitmap");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				show(QBitmapDecoder.deInSize(file, 200, 200));
			}
		});
		//
		btn = getNextButton();
		btn.setText("MatrixFilter 旋转");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeFile(file);
				show(QBitmapFilterMatrix.rotate(bm, 45));
			}
		});
		//
		btn = getNextButton();
		btn.setText("MatrixFilter 缩放");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeFile(file);
				show(QBitmapFilterMatrix.scale(bm, 300, 0));
			}
		});
		//
		btn = getNextButton();
		btn.setText("MatrixFilter 水平或垂直反转");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeFile(file);
				show(QBitmapFilterMatrix.reverse(bm, true));
			}
		});
		//
		btn = getNextButton();
		btn.setText("ColorFilter 调节亮度");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeFile(file);
				show(QBitmapFilterColor.brightness(bm, -100));
			}
		});
		//
		btn = getNextButton();
		btn.setText("ColorFilter 调节对比度");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeFile(file);
				show(QBitmapFilterColor.contrast(bm, 100));
			}
		});
		//
		btn = getNextButton();
		btn.setText("ColorFilter 调节饱和度");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeFile(file);
				show(QBitmapFilterColor.saturation(bm, -50));
			}
		});
		//
		btn = getNextButton();
		btn.setText("ColorFilter 底片效果");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeFile(file);
				show(QBitmapFilterColor.negative(bm));
			}
		});
		//
		btn = getNextButton();
		btn.setText("ColorFilter 灰白效果");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeFile(file);
				show(QBitmapFilterColor.gray(bm));
			}
		});
		//
		btn = getNextButton();
		btn.setText("Filter 颜色填充");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
				show(QBitmapFilter.fillColor(bm, 0xFF00FF00));
			}
		});
		//
		btn = getNextButton();
		btn.setText("Filter 倒影");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
				show(QBitmapFilter.reflect(bm, 2, 10));
			}
		});
		//
		btn = getNextButton();
		btn.setText("Filter 圆角");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
				show(QBitmapFilter.roundCorner(bm, 50));
			}
		});
		//
		btn = getNextButton();
		btn.setText("Filter 边框");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
				show(QBitmapFilter.border(bm, 2));
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
