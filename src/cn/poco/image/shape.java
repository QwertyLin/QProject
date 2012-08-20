package cn.poco.image;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class shape 
{
	public static Bitmap resize(Bitmap src,int width,int height)
	{
		Bitmap dest = Bitmap.createBitmap(width, height,src.getConfig());
		Canvas canvas = new Canvas(dest);
		
		Matrix matrix = new Matrix();		
		matrix.postScale((float)dest.getWidth() / src.getWidth(), (float)(dest.getHeight()) / src.getHeight());
		canvas.drawBitmap(src, matrix, new Paint());		
		
		return dest;		
	}

}
