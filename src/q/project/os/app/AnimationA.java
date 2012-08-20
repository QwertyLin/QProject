package q.project.os.app;

import q.project.QProjectItem;
import q.project.R;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

//http://www.eoeandroid.com/case/2012/0504/1554.html
//Animation主要有两种动画模式：
//一种是tweened animation（渐变动画）:AlphaAnimation、ScaleAnimation
//一种是frame by frame（画面转换动画）：TranslateAnimation、RotateAnimation

public class AnimationA extends QProjectItem {
	
	ImageView iv;

	@Override
	protected void onInit() {
		iv = new ImageView(this);
		layout.addView(iv, 0);
		//
		btn = getNextButton();
		btn.setText("AlphaAnimation 渐变动画");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlphaAnimation ani = new AlphaAnimation(0, 1);
				show(ani);
			}
		});
		//
		btn = getNextButton();
		btn.setText("ScaleAnimation 缩放动画");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ScaleAnimation ani = new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f);
				show(ani);
			}
		});
		//
		btn = getNextButton();
		btn.setText("TranslateAnimation 移动动画");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TranslateAnimation ani = new TranslateAnimation(0, 100, 0, 0);
				show(ani);
			}
		});
		//
		btn = getNextButton();
		btn.setText("RotateAnimation 旋转动画");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//比较常用Animation.RELATIVE_TO_SELF，后一个参数为以1为基数的比例值。
				RotateAnimation ani = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				show(ani);
			}
		});
		//
		btn = getNextButton();
		btn.setText("AnimationDrawable 帧动画");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AnimationDrawable ani = new AnimationDrawable();
				Resources res = getResources();
				ani.addFrame(res.getDrawable(R.drawable.ic_launcher), 300);
				ani.addFrame(res.getDrawable(R.drawable.drawable), 300);
				ani.setOneShot(false);//循环
				iv.setBackgroundDrawable(ani);
				ani.start();
			}
		});
	}
	
	private void show(Animation ani){
		ani.setDuration(3000);
		ani.setRepeatCount(Animation.INFINITE);
		iv.setImageResource(R.drawable.ic_launcher);
		iv.startAnimation(ani);
	}

}
