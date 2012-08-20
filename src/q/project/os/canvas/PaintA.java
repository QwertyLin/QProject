package q.project.os.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Paint.Align;
import android.graphics.PorterDuff.Mode;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import q.project.QProjectItem;
import q.util.a.QToStr;

public class PaintA extends QProjectItem {
	
	Paint paint = new Paint();

	@Override
	protected void onInit() {
		final ViewTest view = new ViewTest(this);
		layout.addView(view, 0);
		//
		btn = getNextButton();
		btn.setText("AntiAlias 消除锯齿");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				paint.setAntiAlias(true);
				view.invalidate();
			}
		});
		//
		btn = getNextButton();
		btn.setText("Color 颜色");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				paint.setColor(0xFFFF0000);
				view.invalidate();
			}
		});
		//
		btn = getNextButton();
		btn.setText("Alpha 透明度");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				paint.setAlpha(0x88);
				view.invalidate();
			}
		});
		//
		btn = getNextButton();
		btn.setText("ShadowLayer 阴影层");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				paint.setShadowLayer(5f, 5.0f, 5.0f, Color.BLACK);
				view.invalidate();
			}
		});
		//
		btn = getNextButton();
		btn.setText("Xfermode 重叠处理:合并，取交集或并集");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
				view.invalidate();
			}
		});
		//
		btn = getNextButton();
		btn.setText("Style 画笔风格");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				paint.setStyle(Paint.Style.STROKE);
				paint.setStrokeWidth(5);
				view.invalidate();
			}
		});
		//
		btn = getNextButton();
		btn.setText("TextScaleX 字体X轴缩放");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				paint.setTextScaleX(2f);
				view.invalidate();
			}
		});
		//
		btn = getNextButton();
		btn.setText("TextSize 字体大小");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				paint.setTextSize(50);
				view.invalidate();
			}
		});
		//
		btn = getNextButton();
		btn.setText("TextAlign 字体方向");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				paint.setTextAlign(Align.CENTER);
				view.invalidate();
			}
		});
		//
		btn = getNextButton();
		btn.setText("TextSkewX 倾斜");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				paint.setTextSkewX(-0.25f);
				view.invalidate();
			}
		});
		//
		btn = getNextButton();
		btn.setText("Typeface 字体风格");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				paint.setTypeface(Typeface.DEFAULT_BOLD);
				view.invalidate();
			}
		});
		//
		btn = getNextButton();
		btn.setText("underline 下划线");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				paint.setUnderlineText(true);
				view.invalidate();
			}
		});
		//
		btn = getNextButton();
		btn.setText("strikeThru 删除线");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				paint.setStrikeThruText(true);
				view.invalidate();
			}
		});
	}
	
	class ViewTest extends View {

		public ViewTest(Context context) {
			super(context);
			setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 100));
			paint.setTextSize(20);
		}
		
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			canvas.drawColor(0xFFFFFFFF);
			canvas.drawText("test", 50, 50, paint);
			canvas.drawRoundRect(new RectF(0, 0, 30, 30), 10, 10, paint);
			System.out.println(QToStr.toStr(paint));
		}
	}
}
