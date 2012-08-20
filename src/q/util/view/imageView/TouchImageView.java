package q.util.view.imageView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.os.SystemClock;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * 可拖动缩放并且不会露出底色的ImageView
 *
 */
public class TouchImageView extends ImageView {

    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    Bitmap bitmap; 
    
    static final int NONE = 0;// 初始状态
    static final int DRAG = 1;// 拖动
    static final int ZOOM = 2;// 缩放
    int mode = NONE;//判断是拖动还是缩放

    PointF startPoint = new PointF();//用于拖动时确定开始点
    
    PointF midPoint = new PointF();//用于缩放时确定中心点
    float dist = 1f;//缩放比例
    float minScale;// 最小缩放比例
    static final float MAX_SCALE = 4f;// 最大缩放比例

	public TouchImageView(Context context, Bitmap bitmap) {
		super(context);
		this.bitmap = bitmap;
		//初始化自身
		this.setScaleType(ScaleType.MATRIX);
		this.setOnTouchListener(onTouchListener);
		//异步获得ImageView高度并进行剩下的初始化
		new Thread(){
			public void run() {
				SystemClock.sleep(100);
				handler.sendEmptyMessage(0);
			};
		}.start();
	}
	
	private void init(){
		minZoom();
        full();
        this.setImageMatrix(matrix);
        this.setImageBitmap(bitmap);
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			init();
		};
	};
	
	private OnTouchListener onTouchListener = new OnTouchListener() {
		
		/**
	     * 触屏监听
	     */
	    public boolean onTouch(View v, MotionEvent event) {

	        switch (event.getAction() & MotionEvent.ACTION_MASK) {
	        // 主点按下
	        case MotionEvent.ACTION_DOWN:
	        	System.out.println("==Image_setOnMultiPointTouchListener: ACTION_DOWN: "+" x="+event.getX()+" y="+event.getY());
	            savedMatrix.set(matrix);
	            startPoint.set(event.getX(), event.getY());
	            mode = DRAG;
	            break;
	        // 副点按下
	        case MotionEvent.ACTION_POINTER_DOWN://多点触控    
	        	System.out.println("==Image_setOnMultiPointTouchListener: ACTION_POINTER_DOWN");
	            dist = spacing(event);
	            // 如果连续两点距离大于10，则判定为多点模式
	            if (spacing(event) > 10f) {
	                savedMatrix.set(matrix);
	                midPoint(midPoint, event);
	                mode = ZOOM;
	            }
	            break;
	        case MotionEvent.ACTION_UP:
	        	System.out.println("==Image_setOnMultiPointTouchListener: ACTION_UP");
	        case MotionEvent.ACTION_POINTER_UP:
	        	System.out.println("==Image_setOnMultiPointTouchListener: ACTION_POINTER_UP");
	            mode = NONE;
	            break;
	        case MotionEvent.ACTION_MOVE:
	        	System.out.println("==Image_setOnMultiPointTouchListener: ACTION_MOVE: "+" x="+event.getX()+" y="+event.getY());
	            if (mode == DRAG) {
	                matrix.set(savedMatrix);
	                matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
	            } else if (mode == ZOOM) {
	                float newDist = spacing(event);
	                if (newDist > 10f) {
	                    matrix.set(savedMatrix);
	                    float scale = newDist / dist;
	                    matrix.postScale(scale, scale, midPoint.x, midPoint.y);
	                }
	            }
	            break;
	        }
	        TouchImageView.this.setImageMatrix(matrix);
	        CheckView();
	        return true;
	    }
	};
	
	/**
     * 限制最大最小缩放比例，自动居中
     */
    private void CheckView() {
        float p[] = new float[9];
        matrix.getValues(p);
        if (mode == ZOOM) {
            if (p[0] < minScale) {
                matrix.setScale(minScale, minScale);
            }
            if (p[0] > MAX_SCALE) {
                matrix.set(savedMatrix);
            }
        }
        full();
    }

    /**
     * 最小缩放比例，最大为100%
     */
    private void minZoom() {
        minScale = Math.max(
                (float) this.getWidth() / (float) bitmap.getWidth(),
                (float) this.getHeight() / (float) bitmap.getHeight());
        if (minScale < 1.0) {
            matrix.postScale(minScale, minScale);
        }
    }

    /**
     * 横向、纵向居中铺满，使不露出底色
     */
    protected void full() {

        Matrix m = new Matrix();
        m.set(matrix);
        RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        m.mapRect(rect);

        float height = rect.height();
        float width = rect.width();

        float deltaX = 0, deltaY = 0;

        //垂直方向居中铺满
        int screenHeight = this.getHeight();
        if (height < screenHeight) {
            deltaY = (screenHeight - height) / 2 - rect.top;
        } else if (rect.top > 0) {
            deltaY = -rect.top;
        } else if (rect.bottom < screenHeight) {
            deltaY = screenHeight - rect.bottom;
        }
        //水平方向居中铺满
        int screenWidth = this.getWidth();
        if (width < screenWidth) {
            deltaX = (screenWidth - width) / 2 - rect.left;
        } else if (rect.left > 0) {
            deltaX = -rect.left;
        } else if (rect.right < screenWidth) {
            deltaX = screenWidth - rect.right;
        }
        
        matrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 计算2点斜边长度，用于衡量缩放尺度
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    /**
     * 两点的中点
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
	
}
