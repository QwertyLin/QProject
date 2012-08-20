package q.util.a;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.graphics.Shader;

public class QDrawable {
	
	/**
     * 描边
     * 
<shape xmlns:android="http://schemas.android.com/apk/res/android" 
    android:shape="rectangle">
    <solid android:color="#FFFF0000"/>
    <stroke 
        android:width="10dp" 
        android:color="#FFFFFFFF"
        android:dashWidth="1dp" 
        android:dashGap="2dp" />
</shape>
     */
    public static final Drawable stroke(){    	
    	final Shape shape = new RectShape();//图形
    	final int color = 0xFFFF0000; //图形填充颜色
    	final int strokeColor = 0xFFFFFFFF; //边框填充颜色
    	final float strokeWidth = 10; //边框宽度
    	ShapeDrawable dr = new ShapeDrawable(shape) {//通过重写onDraw方法实现边框
            @Override protected void onDraw(Shape s, Canvas c, Paint p) {
            	Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                strokePaint.setStyle(Paint.Style.STROKE);
                strokePaint.setColor(strokeColor);
                strokePaint.setStrokeWidth(strokeWidth);
                s.draw(c, p);//先画在画布上
                s.draw(c, strokePaint);//再画边框
            }
        };
       	dr.getPaint().setColor(color);
    	return dr;
    }
    
    /**
     * 矩形
     * 
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="#FFFF0000"/>
</shape>
     */
    public static final ShapeDrawable rect(){
    	final int color = 0xFFFF0000;//填充颜色
    	ShapeDrawable dr = new ShapeDrawable(new RectShape());
    	dr.getPaint().setColor(color);
    	return dr;
    }
	
	/**
     * 外圆角矩形
     * 
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="#FF0000FF"/>
    <corners 
        android:topLeftRadius="45dip"
        android:topRightRadius="45dip"
        android:bottomLeftRadius="45dip"
        android:bottomRightRadius="45dip"
        />
</shape>
     */
    public static final Drawable roundRect(){
    	final float radius = 45; //圆角度
    	final int bgColor = 0xFF0000FF; //填充颜色
    	ShapeDrawable dr = new ShapeDrawable(
    			new RoundRectShape(
    					new float[] { 
    							radius, radius, //左上角
    							radius, radius, //右上角
    							radius, radius, //右下角
    							radius, radius //左下角 
    							}, 
    					null, null)
    			);
    	dr.getPaint().setColor(bgColor);
    	return dr;
    }
    
    /**
     * 内圆角空心矩形
     */
    public static final Drawable roundRectInner(){
    	final float radius = 45; //圆角度
    	final float padding = 6;//内圆角矩形与外边框的边距
    	final int bgColor = 0xFF0000FF; //填充颜色
    	ShapeDrawable dr = new ShapeDrawable(
    			new RoundRectShape(
    					new float[8], 
    					new RectF(padding, padding, padding, padding), 
    					new float[] { 
							radius, radius, //左上角
							radius, radius, //右上角
							radius, radius, //右下角
							radius, radius //左下角 
							}
    					)
    			);
    	dr.getPaint().setColor(bgColor);
    	return dr;
    }
    
    /**
     * 波浪矩形
     */
    public static final ShapeDrawable roundRectWave(){
    	final int color = 0xFF0000FF;//填充颜色
    	ShapeDrawable dr = new ShapeDrawable(
    			new RoundRectShape(
    					new float[] { 12, 12, 12, 12, 0, 0, 0, 0 }, 
    					new RectF(6, 6, 6, 6),
    					null)
    			);
    	dr.getPaint().setColor(color);
    	PathEffect pe = new DiscretePathEffect(10, 4);
        PathEffect pe2 = new CornerPathEffect(4);
        dr.getPaint().setPathEffect(new ComposePathEffect(pe2, pe));
        return dr;
    }
    
    /**
     * 椭圆
     * 
<shape xmlns:android="http://schemas.android.com/apk/res/android" 
    android:shape="oval">
    <solid android:color="#FFFF0000"/>
</shape>
     */
    public static final ShapeDrawable oval(){
    	final int color = 0xFF00FF00; //填充颜色
    	ShapeDrawable dr = new ShapeDrawable(new OvalShape());
    	dr.getPaint().setColor(color);
    	return dr;
    }
    
    /**
     * 扇形
     */
    public static final ShapeDrawable arc(){
    	final float startAngle = 45; //开始角度
    	final float sweepAngle = -270; //旋转角度，正数顺时针，负数逆时针
    	final int color = 0x88FF8844; //填充颜色
    	ShapeDrawable dr = new ShapeDrawable(new ArcShape(startAngle, sweepAngle));
    	dr.getPaint().setColor(color);
    	return dr;
    }
    
    /**
     * 路径图形
     */
    public static final ShapeDrawable path(){
    	final int color = 0xFF0000FF; //填充颜色
    	Path path = new Path();
        path.moveTo(50, 0);//路径开始
        path.lineTo(30, 0);
        path.lineTo(0, 50);
        path.lineTo(50, 100);
        path.lineTo(100, 50);
        path.close();//路径结束
    	ShapeDrawable dr = new ShapeDrawable(
    			new PathShape(path, 100, 100)
    			);
    	dr.getPaint().setColor(color);
    	return dr;
    }
	
	/**
     * 线性渐变
     * 
<shape xmlns:android="http://schemas.android.com/apk/res/android" 
    android:shape="rectangle">
    <gradient 
        android:type="linear"
        android:startColor="#FFFF0000" 
        android:centerColor="#FF00FF00"
        android:endColor="#FF0000FF"
        android:angle="270"
        />
</shape>
     */
	public static final Drawable gradientLinear(){
		final float xStart = 0, yStart = 0, //渐变开始坐标
				xEnd = 0, yEnd = 200; //渐变结束坐标
		final int[] colors = new int[] { 0xFFFF0000, 0xFF00FF00, 0xFF0000FF }; //渐变颜色集
		final float[] positions = new float[] {0, 0.5f, 1}; //渐变段落集，与颜色集一一对应
		final Shape shape = new RectShape();//图形
		ShapeDrawable dr = new ShapeDrawable(shape);
		dr.getPaint().setShader(
				new LinearGradient(
						xStart, yStart, xEnd, yEnd,
						colors, positions, Shader.TileMode.MIRROR)
				);
      	return dr;
	}
	
	/**
     * 扇形渐变
     * 
<shape xmlns:android="http://schemas.android.com/apk/res/android" 
    android:shape="rectangle">
    <gradient 
        android:type="sweep"
        android:startColor="#FFFF0000" 
        android:centerColor="#FF00FF00"
        android:endColor="#FF0000FF"
        />
</shape>
     */
	public static final ShapeDrawable gradientSweep(){
		final float cx = 160, cy = 100;//放射中心
		final int[] colors = new int[] { 0xFFFF0000, 0xFF00FF00, 0xFF0000FF }; //渐变颜色集
		final Shape shape = new RectShape();//图形
		ShapeDrawable dr = new ShapeDrawable(shape);
		dr.getPaint().setShader(
				new SweepGradient(
						cx, cy , 
						colors,	null)
				);
      	return dr;
	}
	
	/**
	 * 放射渐变
	 * 
<shape xmlns:android="http://schemas.android.com/apk/res/android" 
    android:shape="rectangle">
    <gradient 
        android:type="radial"
        android:gradientRadius="100"
        android:startColor="#FFFF0000" 
        android:centerColor="#FF00FF00"
        android:endColor="#FF0000FF"
        />
</shape>
	 */
	public static final ShapeDrawable gradientRadial(){
		final float xCenter = 160, yCenter = 100;//放射中心
		final int[] colors = new int[] { 0xFFFF0000, 0xFF00FF00, 0xFF0000FF }; //渐变颜色集
		final Shape shape = new RectShape();//图形
		ShapeDrawable dr = new ShapeDrawable(shape);
		dr.getPaint().setShader(
				new RadialGradient(
						xCenter, yCenter, 100, 
						colors, null, TileMode.CLAMP)
				);
      	return dr;
	}
	
	/**
     * 图像纹理
     */
	public static final ShapeDrawable shaderBitmap(Bitmap bm){
		ShapeDrawable dr = new ShapeDrawable(new RectShape());
      	dr.getPaint().setShader(
      			new BitmapShader(bm, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
      			);
      	return dr;
	}
	
	/**
     * 图形纹理
     */
	public static final ShapeDrawable shaderBitmap(){
		ShapeDrawable dr = new ShapeDrawable(new RectShape());
		int[] pixels = new int[] { 0xFFFF0000, 0xFF00FF00, 0xFF0000FF, 0};
        Bitmap bm = Bitmap.createBitmap(pixels, 2, 2,
                                        Bitmap.Config.ARGB_8888);
      	dr.getPaint().setShader(
      			new BitmapShader(bm, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
      			);
      	return dr;
	}

}
