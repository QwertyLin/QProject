package q.project.os.bitmap;

import java.io.IOException;
import java.io.InputStream;

import q.project.QProjectItem;
import q.project.R;
import q.util.a.QToStr;
import q.util.bitmap.QBitmapDecoder;
import q.util.bitmap.QBitmapFilter;
import q.util.bitmap.QBitmapUtil;
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
		btn.setText("按指定宽度解码（宽松）");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				show(QBitmapDecoder.deWidthLoose("/sdcard/DCIM/Camera/1.jpg", 200));
			}
		});
		//
		btn = getNextButton();
		btn.setText("按指定宽度解码（严格）");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				show(QBitmapDecoder.deWidthStrict("/sdcard/DCIM/Camera/1.jpg", 200));
			}
		});
		//
		btn = getNextButton();
		btn.setText("将bitmap不透明的部分填充成指定颜色");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
				show(QBitmapFilter.fillColor(bm, 0xFF00FF00));
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
