package cn.poco.image;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;


public class filter {		
	
	/**********************************************************/
	//颜色
	public static Bitmap xProIIFilter(Bitmap srcBmp)
	{		
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;
		
 		PocoFilter.LevelImageChannel(srcBmp, PocoImageInfo.ChannelType.RedChannel | PocoImageInfo.ChannelType.GreenChannel, 50, 225, 1.0); 
 		PocoFilter.LevelOutImageChannel(srcBmp, PocoImageInfo.ChannelType.BlueChannel , 0, 225, 1.0); 
 		
 		
 		
 		return srcBmp;
	}
	
		
	public static Bitmap LomoFi(Bitmap srcBmp)
	{		
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;
		
		Rect rect = new Rect(0,0,srcBmp.getWidth(),srcBmp.getHeight());
		
		PocoFilter.LevelImageChannel(srcBmp, PocoImageInfo.ChannelType.AllChannels, 45, 225, 1.0);
		
		
		
		Bitmap maskBmp = mask.createMask(srcBmp.getWidth(), srcBmp.getHeight(), 255,255,255);
		PocoFilter.compositeImageRectChannel(srcBmp, maskBmp, rect, rect,
				PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.OverlayCompositeOp,204);
		
		maskBmp = mask.createDarkCornerMask( srcBmp.getWidth(), srcBmp.getHeight(),
 				0xffffffff,0xffdcdcdc,0xffc8c8c8,0xff646464,0.0f,0.5f,0.6f,1.0f);
		PocoFilter.compositeImageRectChannel(srcBmp, maskBmp, rect, rect,
				PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.MultiplyCompositeOp,128);
		
		
		return srcBmp;
	}
	
	
	public static Bitmap f1977(Bitmap srcBmp,Bitmap noiseMask)
	{
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;
		
		Rect rect = new Rect(0,0,srcBmp.getWidth(),srcBmp.getHeight());
		
		PocoFilter.LevelImageChannel(srcBmp, PocoImageInfo.ChannelType.RedChannel, 0, 215, 1.0); 		
		
		Bitmap maskBmp = mask.createMask(srcBmp.getWidth(), srcBmp.getHeight(), 228,254,215);
		PocoFilter.compositeImageRectChannel(srcBmp, maskBmp, rect, rect,
				PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.DarkenCompositeOp,255);
		
		maskBmp = Bitmap.createBitmap(srcBmp.getWidth(), srcBmp.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(maskBmp);
		Matrix  matrix = new Matrix();
		matrix.postScale((float)srcBmp.getWidth() / noiseMask.getWidth(),
				(float)srcBmp.getHeight() / noiseMask.getHeight());
		Paint paint = new Paint();
		paint.setAntiAlias(true);		
		canvas.drawBitmap(noiseMask, matrix,paint);		
		PocoFilter.compositeImageRectChannel(srcBmp, maskBmp, rect, rect,
				PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.LightenCompositeOp,230);			
				
		
		return srcBmp;
	}	
	
	public static Bitmap studio(Bitmap srcBmp)
	{
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;
		Rect rect = new Rect(0,0,srcBmp.getWidth(),srcBmp.getHeight());
		
		//200
		PocoFilter.LevelImageChannel(srcBmp,PocoImageInfo.ChannelType.RedChannel | PocoImageInfo.ChannelType.BlueChannel, 0, 210, 1.0);
	
		Bitmap maskBmp = mask.createMask(srcBmp.getWidth(), srcBmp.getHeight(), 208,208,246);
		PocoFilter.compositeImageRectChannel(srcBmp, maskBmp, rect, rect,
				PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.OverlayCompositeOp,153);	
		
		//74,139,238
		maskBmp = mask.createMask(srcBmp.getWidth(), srcBmp.getHeight(), 74,139,215);
		PocoFilter.compositeImageRectChannel(srcBmp, maskBmp, rect, rect,
				PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.HardLightCompositeOp,102);
		
		return srcBmp;

	}	
	
	
	public static Bitmap cerbbeanNoon(Bitmap srcBmp,Bitmap maskRes)
	{
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;
		
		//65, 255, 1.0
		PocoFilter.LevelImageChannel(srcBmp, PocoImageInfo.ChannelType.AllChannels, 45, 255, 1.0);
		
		Bitmap maskBmp = Bitmap.createBitmap(srcBmp.getWidth(), srcBmp.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(maskBmp);
		Matrix  matrix = new Matrix();
		matrix.postScale((float)srcBmp.getWidth() / maskRes.getWidth(),
				(float)srcBmp.getHeight() / maskRes.getHeight());
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		
		canvas.drawBitmap(maskRes, matrix,paint);		
		
		//PocoFilter.compositeImageChannel(maskBmp,srcBmp,
			//	PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.DarkenCompositeOp,235);
			

		Rect rect = new Rect(0,0,srcBmp.getWidth(),srcBmp.getHeight());
		PocoFilter.compositeImageRectChannel(srcBmp,maskBmp,rect,rect,
				PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.DarkenCompositeOp,235);

		return srcBmp;
	}
	
		
	public static Bitmap colorFeverRed(Bitmap srcBmp,Bitmap shader)
	{
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;	
		
		Bitmap shaderBmp = Bitmap.createBitmap(srcBmp.getWidth(), srcBmp.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(shaderBmp);
		Matrix  matrix = new Matrix();
		matrix.postScale((float)srcBmp.getWidth() / shader.getWidth(),
				(float)srcBmp.getHeight() / shader.getHeight());
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		
		canvas.drawBitmap(shader, matrix,paint);
		
		effect.addColorFeverRed(srcBmp,shaderBmp);
		
		return  srcBmp;
	
	}
	
	public static Bitmap colorFeverYellow(Bitmap srcBmp,Bitmap shader)
	{
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;	
		
		Bitmap shaderBmp = Bitmap.createBitmap(srcBmp.getWidth(), srcBmp.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(shaderBmp);
		Matrix  matrix = new Matrix();
		matrix.postScale((float)srcBmp.getWidth() / shader.getWidth(),
				(float)srcBmp.getHeight() / shader.getHeight());
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		
		canvas.drawBitmap(shader, matrix,paint);
		
		effect.addColorFeverYellow(srcBmp,shaderBmp);
		
		return  srcBmp;
	
	}
	
	//colorFeverGray 老旧黑白
	public static Bitmap colorFeverGray(Bitmap srcBmp,Bitmap shader)
	{
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;	
		
		Bitmap shaderBmp = Bitmap.createBitmap(srcBmp.getWidth(), srcBmp.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(shaderBmp);
		Matrix  matrix = new Matrix();
		matrix.postScale((float)srcBmp.getWidth() / shader.getWidth(),
				(float)srcBmp.getHeight() / shader.getHeight());
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		
		canvas.drawBitmap(shader, matrix,paint);
		
		PocoFilter.gray(srcBmp, 0);		
		effect.addColorFeverGray(srcBmp,shaderBmp);
		
		return  srcBmp;
	
	}
	
	//colorFeverGreen 往时回忆
	public static Bitmap colorFeverGreen(Bitmap srcBmp,Bitmap shader)
	{
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;	
		
		Bitmap shaderBmp = Bitmap.createBitmap(srcBmp.getWidth(), srcBmp.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(shaderBmp);
		Matrix  matrix = new Matrix();
		matrix.postScale((float)srcBmp.getWidth() / shader.getWidth(),
				(float)srcBmp.getHeight() / shader.getHeight());
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		
		canvas.drawBitmap(shader, matrix,paint);		
				
		effect.addColorFeverGreen(srcBmp,shaderBmp);
		
		return  srcBmp;
	
	}
	
	//colorFeverYellow2 胶片过爆
	public static Bitmap colorFeverYellow2(Bitmap srcBmp,Bitmap shader)
	{
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;	
		
		Bitmap shaderBmp = Bitmap.createBitmap(srcBmp.getWidth(), srcBmp.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(shaderBmp);
		Matrix  matrix = new Matrix();
		matrix.postScale((float)srcBmp.getWidth() / shader.getWidth(),
				(float)srcBmp.getHeight() / shader.getHeight());
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		
		canvas.drawBitmap(shader, matrix,paint);	
		
		//canvas.setBitmap(srcBmp);
		//paint.setAlpha(178);
		//canvas.drawBitmap(shaderBmp, 0, 0, paint);
				
		effect.addColorFeverYellow2(srcBmp,shaderBmp);
		
		return  srcBmp;
	
	}
	
	//colorFeverRed2 渲染溢光
	public static Bitmap colorFeverRed2(Bitmap srcBmp,Bitmap shader)
	{
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;	
		
		Bitmap shaderBmp = Bitmap.createBitmap(srcBmp.getWidth(), srcBmp.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(shaderBmp);
		Matrix  matrix = new Matrix();
		matrix.postScale((float)srcBmp.getWidth() / shader.getWidth(),
				(float)srcBmp.getHeight() / shader.getHeight());
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		
		canvas.drawBitmap(shader, matrix,paint);	
		
		canvas.setBitmap(srcBmp);
		paint.setAlpha(255);
		canvas.drawBitmap(shaderBmp, 0, 0, paint);
				
		effect.addColorFeverRed2(srcBmp,shaderBmp);
		
		return  srcBmp;
	
	}
	
	//colorFeverRed3 艳丽反转
	public static Bitmap colorFeverRed3(Bitmap srcBmp,Bitmap shader)
	{
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;	
		
		Bitmap shaderBmp = Bitmap.createBitmap(srcBmp.getWidth(), srcBmp.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(shaderBmp);
		Matrix  matrix = new Matrix();
		matrix.postScale((float)srcBmp.getWidth() / shader.getWidth(),
				(float)srcBmp.getHeight() / shader.getHeight());
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		
		canvas.drawBitmap(shader, matrix,paint);	
		
		canvas.setBitmap(srcBmp);
		paint.setAlpha(255);
		canvas.drawBitmap(shaderBmp, 0, 0, paint);
				
		effect.addColorFeverRed3(srcBmp,shaderBmp);
		
		return  srcBmp;
	
	}
	
	//colorFeverGreen2 青色映画
	public static Bitmap colorFeverGreen2(Bitmap srcBmp,Bitmap shader)
	{
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;	
		
		Bitmap shaderBmp = Bitmap.createBitmap(srcBmp.getWidth(), srcBmp.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(shaderBmp);
		Matrix  matrix = new Matrix();
		matrix.postScale((float)srcBmp.getWidth() / shader.getWidth(),
				(float)srcBmp.getHeight() / shader.getHeight());
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		
		canvas.drawBitmap(shader, matrix,paint);	
		
		//canvas.setBitmap(srcBmp);
		//paint.setAlpha(178);
		//canvas.drawBitmap(shaderBmp, 0, 0, paint);
				
		effect.addColorFeverGreen2(srcBmp,shaderBmp);
		
		return  srcBmp;
	
	}
	
	
	public static Bitmap polaroid_g(Bitmap srcBmp)
	{		
		//Bitmap srcBmp = src.copy( Bitmap.Config.ARGB_8888,true);	
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;
		Rect rect = new Rect(0,0,srcBmp.getWidth(),srcBmp.getHeight());
		
		int[] colors = 		{0xffced6c6,	0xff7aa594};
		float[] positions = {0.0f, 			1.0f};
		
		Bitmap maskBmp = mask.createDarkCornerMask(srcBmp.getWidth(),srcBmp.getHeight(),
				colors,positions);	
		//178
		PocoFilter.compositeImageRectChannel(srcBmp, maskBmp, rect, rect,
				PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.OverlayCompositeOp,178);
		
		maskBmp = mask.createDarkCornerMask(srcBmp.getWidth(),srcBmp.getHeight(),
				colors,positions);
		//204
		PocoFilter.compositeImageRectChannel(srcBmp, maskBmp, rect, rect,
				PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.DarkenCompositeOp,178);	
		
		return srcBmp;
		
	}
	
	public static Bitmap polaroid_y(Bitmap srcBmp)
	{		
		//Bitmap srcBmp = src.copy( Bitmap.Config.ARGB_8888,true);	
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;
		
		Rect rect = new Rect(0,0,srcBmp.getWidth(),srcBmp.getHeight());
		
		int[] colors = 		{0xffe2e3b7,	0xffadad8b};
		float[] positions = {0.0f, 			1.0f};
		
		Bitmap maskBmp = mask.createDarkCornerMask(srcBmp.getWidth(),srcBmp.getHeight(),
				colors,positions);	
		PocoFilter.compositeImageRectChannel(srcBmp, maskBmp, rect, rect,
				PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.OverlayCompositeOp,178);
		
		maskBmp = mask.createDarkCornerMask(srcBmp.getWidth(),srcBmp.getHeight(),
				colors,positions);
		PocoFilter.compositeImageRectChannel(srcBmp, maskBmp, rect, rect,
				PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.DarkenCompositeOp,178);
				
		
		
		return srcBmp;
		
	}
	
	
	public static Bitmap sketch(Bitmap srcBmp)
	{		
			
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;		
		
		
		PocoFilter.sketch(srcBmp, 2);
		
		return srcBmp;
		
	}
	
	//sunset 夕阳
	public static Bitmap sunset(Bitmap srcBmp)
	{
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;		
		
		effect.addSunset(srcBmp);
		
		
		return srcBmp;
		
	}
	
	//foodColor 食物鲜艳
	public static Bitmap foodColor(Bitmap srcBmp)
	{
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;
		
		Bitmap destBmp = srcBmp.copy(Bitmap.Config.ARGB_8888, true);
		
		
		PocoFilter.colorBalance(destBmp, 
				0, 20, 0,
				0, 0, 0,
				0, 0, 0,
				0);	
		
		
		PocoFilter.changeSaturation(destBmp, 13);
		PocoFilter.LevelImageChannel(destBmp, PocoImageInfo.ChannelType.AllChannels, 0, 250, 1);
		PocoFilter.changeBrightness(destBmp, 9);
		PocoFilter.changeContrast(destBmp, -9);
		PocoFilter.sharpenImageFast2(destBmp, 30);		
		
		return destBmp;
		
	}
	
	//暗角魅蓝
	public static Bitmap HDRDarkenBlue(Bitmap srcBmp)
	{
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;
		
		Bitmap destBmp = srcBmp.copy(Bitmap.Config.ARGB_8888, true);		
		
		
		Bitmap tempBmp = mask.createDarkCornerMask(640, 640,
				0xffffffff,0xffffffff,0xffdcdcdc,0xff161616,0.0f,0.65f,0.75f,1.0f);
		Bitmap maskBmp = Bitmap.createBitmap(srcBmp.getWidth(), srcBmp.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(maskBmp);
		Matrix matrix = new Matrix();
		matrix.postScale((float)maskBmp.getWidth() / tempBmp.getWidth(), (float)maskBmp.getHeight() / tempBmp.getHeight());
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		canvas.drawBitmap(tempBmp, matrix, paint);	
		
		
		PocoFilter.compositeImageRectChannel(destBmp, maskBmp, 
											 new Rect(0,0,destBmp.getWidth(),destBmp.getHeight()),
											 new Rect(0,0,srcBmp.getWidth(),srcBmp.getHeight()), 
											 PocoImageInfo.ChannelType.AllChannels, 
											 PocoCompositeOperator.MultiplyCompositeOp, 255);
		
		
		
		PocoFilter.mixChannel(destBmp, 100, 100, 91);
		
		PocoFilter.colorBalance(destBmp, 0, -100, 30,
			0, -27, 0,
			0, 0, 0, 
			1);
		PocoFilter.optionWhileBlack(destBmp, 0, 0, 65, 0,5, 5,-10, 0);
		
		
		
		
	
		
		return destBmp;
		
	}
	
	
	//HDR偏红
	public static Bitmap HDRRed(Bitmap srcBmp)
	{
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;
		
		Bitmap destBmp = srcBmp.copy(Bitmap.Config.ARGB_8888, true);
		
		
		
		PocoFilter.colorBalance(destBmp, 0, 70, 35,
								0, 0, 0,
								0, 0, 0, 
								1);		
		
		
		PocoFilter.optionColor(destBmp, 0, 0, 0, 0,
				0, 0, 0, 0,
				0, 0, 0, 0,
				0, 0, 0, 0,
				 0, 0, 0, 0,
				 0, 0, 100, 0);
		
		
		PocoFilter.optionWhileBlack(destBmp, 0, 0, 30, 0,70, 0, 0, 0);			
		
		
		
		PocoFilter.LevelImageChannel(destBmp, PocoImageInfo.ChannelType.AllChannels, 5, 245, 1);
		
		return destBmp;
		
	}
	
	//HDR-重
	public static Bitmap HDR(Bitmap srcBmp)
	{
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;
		
		Bitmap destBmp = srcBmp.copy(Bitmap.Config.ARGB_8888, true);
		Bitmap maskBmp = srcBmp.copy(Bitmap.Config.ARGB_8888, true);			
		
		
		PocoFilter.lightShadow(maskBmp);		
		PocoFilter.gaussianBlurImageChannel(maskBmp, PocoImageInfo.ChannelType.AllChannels, 1, 1);
		
		PocoFilter.compositeImageRectChannel(destBmp, maskBmp, 
				 new Rect(0,0,destBmp.getWidth(),destBmp.getHeight()),
				 new Rect(0,0,srcBmp.getWidth(),srcBmp.getHeight()), 
				 PocoImageInfo.ChannelType.AllChannels, 
				 PocoCompositeOperator.OverlayCompositeOp, 255);		
		
		
		
		Canvas canvas = new Canvas(maskBmp);
		canvas.drawColor(0xffffffff);
		
		PocoFilter.compositeImageRectChannel(destBmp, maskBmp, 
				 new Rect(0,0,destBmp.getWidth(),destBmp.getHeight()),
				 new Rect(0,0,srcBmp.getWidth(),srcBmp.getHeight()), 
				 PocoImageInfo.ChannelType.AllChannels, 
				 PocoCompositeOperator.OverlayCompositeOp, 128);	
		
		
		
		
		
		return destBmp;
		
	}
	
	
	//魔幻紫色  magickPurple
	public static Bitmap magickPurple(Bitmap srcBmp)
	{
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;
		
		Bitmap destBmp = srcBmp.copy(Bitmap.Config.ARGB_8888, true);
		Bitmap maskBmp = mask.createMagicPurpleMask(srcBmp.getWidth(), srcBmp.getHeight());
		PocoFilter.gaussMaskBlur(destBmp, maskBmp, 20);
		
		PocoFilter.mixChannel(destBmp, 100, 100, 71);		
		
		
		Canvas canvas = new Canvas(maskBmp);
		canvas.drawColor(0xff9f279f);
		
		PocoFilter.compositeImageRectChannel(destBmp, maskBmp, 
				 new Rect(0,0,destBmp.getWidth(),destBmp.getHeight()),
				 new Rect(0,0,srcBmp.getWidth(),srcBmp.getHeight()), 
				 PocoImageInfo.ChannelType.AllChannels, 
				 PocoCompositeOperator.ScreenCompositeOp, 110);	
		
		
		
		PocoFilter.optionWhileBlack(destBmp, 0, 0, 0, 0,20, 30, 0, 0);	
		
		PocoFilter.optionColor(destBmp, 0, 0, 0, 0,
				0, 0, 0, 0,
				0, 0, 0, 0,
				0, 0, 0, 0,
				 0, 0, 0, 0,
				 0, 0, 20, 0);
		
	
		
		return destBmp;
		
	}
	
	public static  Bitmap moreBeaute(Bitmap destBmp,int light,int blur,int sharpen,int hue)
	{
		PocoFilter.moreBeaute(destBmp, light, blur, sharpen, hue);
		return destBmp;
		
	}
	
	public static  Bitmap moreBeaute2(Bitmap destBmp,Bitmap srcBmp,int light,int blur,int sharpen,int hue)
	{
		PocoFilter.moreBeaute2(destBmp,srcBmp, light, blur, sharpen, hue);
		return destBmp;		
	}
	
	
	/**********************************************************/
	//特效
	public static Bitmap lomo(Bitmap srcBmp)
	{
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;		
		
		
		
		int[] colors = 	{0xffffffff,		0xffdcdcdc,		0xffbfbfbf,					 	
					 	 0xff000000};
		float[] positions = {0.0f,			0.4f,			0.5f,					 	
					 	 1.0f};
		Bitmap maskBmp = mask.createDarkCornerMask( srcBmp.getWidth(), srcBmp.getHeight(),
				colors,positions);

		//PocoFilter.compositeImageChannel(maskBmp,srcBmp,
			//	PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.MultiplyCompositeOp,255);
		
		//PocoFilter.LevelImageChannel(maskBmp, PocoImageInfo.ChannelType.AllChannels, 20, 255, 1.0);
		
		Rect rect = new Rect(0,0,srcBmp.getWidth(),srcBmp.getHeight());
		PocoFilter.compositeImageRectChannel(srcBmp,maskBmp,rect,rect,
			PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.MultiplyCompositeOp,255);
		
		PocoFilter.LevelImageChannel(srcBmp, PocoImageInfo.ChannelType.AllChannels, 20, 255, 1.0);
		
		return srcBmp;
		
	}
	
	
	public static Bitmap feelgoodSnow(Bitmap srcBmp)
	{		
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;
		
		
		Bitmap maskBmp = mask.createMask(srcBmp.getWidth(), srcBmp.getHeight(), 58,58,73);
		
		//PocoFilter.compositeImageChannel(maskBmp,srcBmp,
			//	PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.OverlayCompositeOp,204);
		
		
		//effect.addSnow(maskBmp);	
		
		Rect rect = new Rect(0,0,srcBmp.getWidth(),srcBmp.getHeight());
		PocoFilter.compositeImageRectChannel(srcBmp,maskBmp,rect,rect,
				PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.OverlayCompositeOp,204);
		
		
		effect.addSnow(srcBmp);
		
		return srcBmp;
		
	}
	

	public static Bitmap partyDazzle(Bitmap srcBmp)
	{		
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;
		
		int[] colors = 		{0xff7b0a1a,0xff7b0a1a};
		float[] positions = {0.0f,1.0f};
		
		Bitmap maskBmp = mask.createDarkCornerMask(srcBmp.getWidth(), srcBmp.getHeight(),colors,positions);
		
		//PocoFilter.compositeImageChannel(maskBmp,srcBmp,
		//		PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.OverlayCompositeOp,178);
				
		//effect.addDazzle(maskBmp);
		
		Rect rect = new Rect(0,0,srcBmp.getWidth(),srcBmp.getHeight());
		PocoFilter.compositeImageRectChannel(srcBmp,maskBmp,rect,rect,
				PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.OverlayCompositeOp,178);
				
		effect.addDazzle(srcBmp);
		
		
		return srcBmp;
		
	}
	
	
	public static Bitmap magicHour(Bitmap srcBmp)
	{		
		if(srcBmp.getConfig() != Bitmap.Config.ARGB_8888)
			return null;
		
		Bitmap maskBmp = mask.createMask(srcBmp.getWidth(), srcBmp.getHeight(), 255,249,217);
		
		//PocoFilter.compositeImageChannel(maskBmp,srcBmp,
			//	PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.DarkenCompositeOp,179);
		
		//PocoFilter.LevelImageChannel(maskBmp, PocoImageInfo.ChannelType.RedChannel | PocoImageInfo.ChannelType.GreenChannel, 40, 235, 1.0); 
 		
		//effect.addMagickHour(maskBmp);
		
		Rect rect = new Rect(0,0,srcBmp.getWidth(),srcBmp.getHeight());
		PocoFilter.compositeImageRectChannel(srcBmp,maskBmp,rect,rect,
				PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.DarkenCompositeOp,179);
		
		PocoFilter.LevelImageChannel(srcBmp, PocoImageInfo.ChannelType.RedChannel | PocoImageInfo.ChannelType.GreenChannel, 40, 235, 1.0); 
 		
		effect.addMagickHour(srcBmp);
		
 		
		return srcBmp;
	}
	
	
	
	/****************************************************/
	//其他
	
	public static Bitmap changeHSL(Bitmap src,int h,int s,int l)
	{
		if(src.getConfig() != Bitmap.Config.ARGB_8888)
			return null;
		PocoFilter.changeHSL(src,h,s,l);
		return src;
	}
	
	/**
	 *  调节锐度	
	 *  
	 *  @src 源Bitmap 
	 *
	 *  @param value 亮度值，范围在[0, 100]。
	 *
	 *  @returns 返回一个处理后的新Bitmap
	 */
	public static Bitmap sharpen(Bitmap src, int percent)
	{
		percent = percent < 0 ? 0 : (percent > 100 ? 100 : percent);
		percent = (int)(70.0 * percent / 100);
		
		Bitmap dest = src.copy(Bitmap.Config.ARGB_8888, true);
		PocoFilter.sharpenImageFast(dest, src, percent);
		
		
		return dest;
		
	}
	
	/**
	 * 4色Lomo滤镜
	 * @param flag
	 * 0 : Red
	 * 1 : green
	 * 2 : blue
	 * 3 : yellow
	 * @returns 返回一个处理后的新Bitmap
	 */
	public static Bitmap lomo4(Bitmap src,int flag)
	{
		Bitmap dest = src.copy(Bitmap.Config.ARGB_8888, true);
		
		double cyan_red_l = 0;
		double cyan_red_m = 0;
		double cyan_red_h = 0;
		double magenta_green_l = 0;
		double magenta_green_m = 0;
		double magenta_green_h = 0;
		double yellow_blue_l = 0;
		double yellow_blue_m = 0;
		double yellow_blue_h = 0;
		
		int preserve_luminosity = 0;
		
		switch(flag)
		{
		//red
		case 0:
			cyan_red_m = 86;
			magenta_green_m = -30;
			yellow_blue_m = 0;
			break;
		//green	
		case 1:
			cyan_red_m = -42;
			magenta_green_m = 81;
			yellow_blue_m = 0;
			break;
		//blue	
		case 2:
			cyan_red_m = -71;
			magenta_green_m = -1;
			yellow_blue_m = 73;
			break;
		//yellow	
		case 3:
			cyan_red_m = 52;
			magenta_green_m = 18;
			yellow_blue_m = -100;
			break;
		default:
			preserve_luminosity = 0;
				
		}
		
		PocoFilter.colorBalance(dest, 
				cyan_red_l, cyan_red_m, cyan_red_h,
				magenta_green_l, magenta_green_m, magenta_green_h,
				yellow_blue_l, yellow_blue_m, yellow_blue_h,
				preserve_luminosity);
		
		return dest;
		
	}
	
	
	/*
	 * 径向模糊
	 *x,y			:起始坐标
	 *width,heigh	:图片大小
	 * cx,cy 		:中心坐标
	 * length 		: 模糊半径
	 * outward		:0 向内模糊，1向外模糊
	 * quality 		: 0
	 */
	
	public static  Bitmap zoomMotionBlur(Bitmap destBmp,int x,int y,int width,int height,
			int cx,int cy,int length,int outward,int quality)
	{
		PocoFilter.zoomMotionBlur(destBmp, x, y, width, height, cx, cy, length,outward, quality);
		return destBmp;
	}
	
	
	/**
 *  旧效果
 *
 *  @param source 需要添加旧照片效果的 Bitmap 对象。
 *
 *  @returns 返回一个添加了旧照片效果的 Bitmap 对象。
 */

public static Bitmap oldPicture( Bitmap source)
{
	/**
	 *  灰度
	 *
	 *  <p>一个给 ColorMatrixFilter 对象作参数用的描述灰度的常数数组。</p>
	 */
	float[] GRAY_SCALE_MATRIX = new float[]{
									0.3086f, 0.6094f, 0.0820f, 0, 0,
									0.3086f, 0.6094f, 0.0820f, 0, 0,
									0.3086f, 0.6094f, 0.0820f, 0, 0,
									0,      0,      0,      1, 0
									};
	
	float[] oldPictureMatrix = new float[] {
									0.94f, 0,   0,   0,   0,
									0,    0.9f, 0,   0,   0,
									0,    0,   0.8f, 0,   0,
									0,    0,   0,   0.8f, 0};
	
	Bitmap returnBitmap  = Bitmap.createBitmap(source);		
	Canvas canvas = new Canvas(returnBitmap);		
	Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);		
	ColorMatrix cm = new ColorMatrix();
	
	cm.set(GRAY_SCALE_MATRIX);
	ColorMatrixColorFilter  grayFilter  = new ColorMatrixColorFilter ( cm );
	paint.setColorFilter(grayFilter);	
	canvas.drawBitmap(source, 0, 0, paint);
	
	cm.set(oldPictureMatrix);
	ColorMatrixColorFilter  oldPictureFilter  = new ColorMatrixColorFilter ( cm );
	paint.setColorFilter(oldPictureFilter);		
	canvas.drawBitmap(returnBitmap, 0, 0, paint);	
	
	
	
	return returnBitmap;
	
}


public static Bitmap oldFilm(Bitmap destBmp)
{
	if(destBmp.getConfig() != Bitmap.Config.ARGB_8888)
		return null;
	
	PocoFilter.oldFilm(destBmp);
	
	return destBmp;
}


public static Bitmap redsun(Bitmap destBmp,Bitmap redsunMaskBmpSreen,Bitmap redsunMaskBmpOverly)
{
	if(destBmp.getConfig() != Bitmap.Config.ARGB_8888)
		return null;
	
	int width = destBmp.getWidth();
	int height = destBmp.getHeight();
	
	Rect rect = new Rect(0,0,width,height);
	Bitmap maskBmp = null;
	maskBmp = destBmp.copy(Bitmap.Config.ARGB_8888, true);
	PocoFilter.compositeImageRectChannel(destBmp, maskBmp, rect, rect,
			PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.OverlayCompositeOp,163);
	
	Canvas canvas = new Canvas();	

	
	maskBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	canvas.setBitmap(maskBmp);
	canvas.drawARGB(255, 245, 48, 48);
	PocoFilter.compositeImageRectChannel(destBmp, maskBmp, rect, rect,
			PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.OverlayCompositeOp,153);
	
	
	
	Paint paint = new Paint();
	paint.setAntiAlias(true);
	Matrix matrix = new Matrix();
	matrix.postScale((float)width / redsunMaskBmpSreen.getWidth(), 
			(float)height / redsunMaskBmpSreen.getHeight());

	maskBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	canvas.setBitmap(maskBmp);
	canvas.drawBitmap(redsunMaskBmpSreen, matrix, paint);
	
	PocoFilter.compositeImageRectChannel(destBmp, maskBmp, rect, rect,
			PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.ScreenCompositeOp,255);
	
	matrix.reset();
	matrix.postScale((float)width / redsunMaskBmpOverly.getWidth(), 
			(float)height / redsunMaskBmpOverly.getHeight());

	maskBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	canvas.setBitmap(maskBmp);
	canvas.drawBitmap(redsunMaskBmpOverly, matrix, paint);
	
	PocoFilter.compositeImageRectChannel(destBmp, maskBmp, rect, rect,
			PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.OverlayCompositeOp,255);
	
	
	return destBmp;
	
}

public static Bitmap grandBleu(Bitmap destBmp,Bitmap grandBleuTB,Bitmap grandBleuM)
{
	if(destBmp.getConfig() != Bitmap.Config.ARGB_8888)
		return null;
	
	int width = destBmp.getWidth();
	int height = destBmp.getHeight();
	
	PocoFilter.grandBleu(destBmp);
	
	
	Bitmap grandBleuBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	Canvas canvas = new Canvas(grandBleuBmp);
	Paint paint = new Paint();
	paint.setAntiAlias(true);
	Matrix matrix = new Matrix();
	matrix.postScale((float)width / grandBleuTB.getWidth(), 
			(float)height / grandBleuTB.getHeight());	
	canvas.drawBitmap(grandBleuTB, matrix, paint);
	
	Rect rect = new Rect(0,0,width,height);
	PocoFilter.compositeImageRectChannel(destBmp, grandBleuBmp, rect, rect,
			PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.OverlayCompositeOp,235);
	
	grandBleuBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	canvas.setBitmap(grandBleuBmp);
	matrix.reset();
	matrix.postScale((float)width / grandBleuM.getWidth(), 
			(float)height / grandBleuM.getHeight());	
	canvas.drawBitmap(grandBleuM, matrix, paint);
	
	rect = new Rect(0,0,width,height);
	PocoFilter.compositeImageRectChannel(destBmp, grandBleuBmp, rect, rect,
			PocoImageInfo.ChannelType.AllChannels, PocoCompositeOperator.OverlayCompositeOp,235);
	
	return destBmp;
	
}
	
     
	
}




