package q.project.os.viewc;

import q.project.QProjectBSimple;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Bitmap.Config;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ViewPaintA extends QProjectBSimple {
	
	protected void onInit() {
		final ViewPaint pv = new ViewPaint(this);
		pv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 300));
		layout.addView(pv, 0);
		//
		btn = getNextButton();
		btn.setText("生成图片");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ImageView iv = new ImageView(ctx);
				iv.setImageBitmap(pv.getBitmap());
				new AlertDialog.Builder(ctx).setView(iv).show();
			}
		});
		//
		btn = getNextButton();
		btn.setText("清除");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pv.clear();
			}
		});
	};
	
}

/**
 * This view implements the drawing canvas.
 * 
 * It handles all of the input events and drawing functions.
 */
class ViewPaint extends View {
	private Paint paint;
	private Canvas canvas;
	private Bitmap bitmap;
	private Path path;

	public ViewPaint(Context context) {
		super(context);					
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStrokeWidth(3);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.BLACK);					
		path = new Path();	
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	public void clear() {
		if (canvas != null) {
			paint.setColor(Color.WHITE);
			canvas.drawPaint(paint);
			paint.setColor(Color.BLACK);
			canvas.drawColor(Color.WHITE);
			invalidate();			
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);			
		canvas = new Canvas(bitmap);
		canvas.drawColor(Color.WHITE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// canvas.drawColor(BRUSH_COLOR);
		canvas.drawBitmap(bitmap, 0, 0, null);
		canvas.drawPath(path, paint);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		
		int curW = bitmap != null ? bitmap.getWidth() : 0;
		int curH = bitmap != null ? bitmap.getHeight() : 0;
		if (curW >= w && curH >= h) {
			return;
		}

		if (curW < w)
			curW = w;
		if (curH < h)
			curH = h;

		Bitmap newBitmap = Bitmap.createBitmap(curW, curH, Bitmap.Config.ARGB_8888);
		Canvas newCanvas = new Canvas();
		newCanvas.setBitmap(newBitmap);
		if (bitmap != null) {
			newCanvas.drawBitmap(bitmap, 0, 0, null);
		}
		bitmap = newBitmap;
		canvas = newCanvas;
	}

	private float cur_x, cur_y;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			cur_x = x;
			cur_y = y;
			path.moveTo(cur_x, cur_y);
			break;
		}

		case MotionEvent.ACTION_MOVE: {
			path.quadTo(cur_x, cur_y, x, y);
			cur_x = x;
			cur_y = y;
			break;
		}

		case MotionEvent.ACTION_UP: {
			canvas.drawPath(path, paint);
			path.reset();
			break;
		}
		}

		invalidate();

		return true;
	}
}