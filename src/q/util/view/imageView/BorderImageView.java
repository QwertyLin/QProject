package q.util.view.imageView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ImageView;

/**
 * 带边框的ImageView
 */
public class BorderImageView extends ImageView {
	
	private int borderColor, borderWidth;

	/**
	 * @param context
	 * @param borderColor 边框颜色
	 * @param borderWidth 边框大小
	 */
	public BorderImageView(Context context, int borderColor, int borderWidth) {
		super(context);
		this.borderColor = borderColor;
		this.borderWidth = borderWidth;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Rect rec=canvas.getClipBounds();
		rec.bottom--;//不减的话看不到底边框
		rec.right--;//不减的话看不到右边框
		Paint paint=new Paint();
		paint.setColor(borderColor);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(borderWidth);
		canvas.drawRect(rec, paint);
	}
	
	/**
	 * 改变边框颜色
	 * @param borderColor 边框颜色
	 */
	public void setBorderColor(int borderColor){
		this.borderColor = borderColor;
		invalidate();
	}
}
