package q.util.a;

import cn.poco.image.PocoFilter;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;

public class PocoBitmapFilter {
	
	/**
	 * 胶片特效
	 * 
	 */
	public static final Bitmap xProIIFilter(Bitmap bitmap) {		
		if(bitmap.getConfig() != Bitmap.Config.ARGB_8888) {
			return null;
		}
		Bitmap newBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
		PocoFilter.LevelImageChannel(newBitmap, ChannelType.RedChannel | ChannelType.GreenChannel, 50, 225, 1.0); 
 		PocoFilter.LevelOutImageChannel(newBitmap, ChannelType.BlueChannel , 0, 225, 1.0); 
 		return newBitmap;
	}
	
	/**
	 * 魔幻紫色
	 * 
	 */
	public static final Bitmap magickPurple(Bitmap bitmap) {
		if (bitmap.getConfig() != Bitmap.Config.ARGB_8888) {
			return null;
		}
		Bitmap newBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
		Bitmap maskBmp = createMagicPurpleMask(bitmap.getWidth(),
				bitmap.getHeight());
		PocoFilter.gaussMaskBlur(newBitmap, maskBmp, 20);

		PocoFilter.mixChannel(newBitmap, 100, 100, 71);

		Canvas canvas = new Canvas(maskBmp);
		canvas.drawColor(0xff9f279f);

		PocoFilter.compositeImageRectChannel(newBitmap, maskBmp, new Rect(0, 0,
				newBitmap.getWidth(), newBitmap.getHeight()),
				new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
				ChannelType.AllChannels,
				PocoCompositeOperator.ScreenCompositeOp, 110);

		PocoFilter.optionWhileBlack(newBitmap, 0, 0, 0, 0, 20, 30, 0, 0);

		PocoFilter.optionColor(newBitmap, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0);

		return newBitmap;
	}
	private static final Bitmap createMagicPurpleMask(int width, int height) {
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawARGB(0, 0, 0, 0);

		int[] colors;
		float positions[];
		Paint paint = new Paint();

		colors = new int[] { 0xffffffff, 0x00000000, 0x00000000, 0xffffffff };
		positions = new float[] { 0.0f, 0.4f, 0.6f, 1.0f };

		LinearGradient lg = new LinearGradient(0, 0, width, height, colors,
				positions, TileMode.CLAMP);
		paint.setShader(lg);
		canvas.drawRect(0, 0, width, height, paint);

		return bitmap;
	}
	
	/**
	 * 美食特效
	 * 
	 */
	public static final Bitmap foodColor(Bitmap bitmap) {
		if (bitmap.getConfig() != Bitmap.Config.ARGB_8888) {
			return null;
		}
		Bitmap destBmp = bitmap.copy(Bitmap.Config.ARGB_8888, true);

		PocoFilter.colorBalance(destBmp, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0);

		PocoFilter.changeSaturation(destBmp, 13);
		PocoFilter.LevelImageChannel(destBmp, ChannelType.AllChannels, 0, 250,
				1);
		PocoFilter.changeBrightness(destBmp, 9);
		PocoFilter.changeContrast(destBmp, -9);
		PocoFilter.sharpenImageFast2(destBmp, 30);

		return destBmp;
	}
	
	/**
	 * 美白嫩肤
	 * 
	 */
	public static final Bitmap moreBeaute(Bitmap bitmap)	{
		if (bitmap.getConfig() != Bitmap.Config.ARGB_8888) {
			return null;
		}
		Bitmap destBmp = bitmap.copy(Bitmap.Config.ARGB_8888, true);
		PocoFilter.moreBeaute(destBmp, 70, 70, 30, 70);
		return destBmp;
	}
	
	/**
	 * 日系风格
	 * 
	 */
	public static Bitmap studio(Bitmap bitmap) {
		if (bitmap.getConfig() != Bitmap.Config.ARGB_8888) {
			return null;
		}
		Bitmap srcBmp = bitmap.copy(Config.ARGB_8888, true);
			
		Rect rect = new Rect(0, 0, srcBmp.getWidth(), srcBmp.getHeight());

		// 200
		PocoFilter.LevelImageChannel(srcBmp, ChannelType.RedChannel
				| ChannelType.BlueChannel, 0, 210, 1.0);

		Bitmap maskBmp = createMask(srcBmp.getWidth(), srcBmp.getHeight(), 208,
				208, 246);
		PocoFilter.compositeImageRectChannel(srcBmp, maskBmp, rect, rect,
				ChannelType.AllChannels,
				PocoCompositeOperator.OverlayCompositeOp, 153);

		// 74,139,238
		maskBmp = createMask(srcBmp.getWidth(), srcBmp.getHeight(), 74, 139,
				215);
		PocoFilter.compositeImageRectChannel(srcBmp, maskBmp, rect, rect,
				ChannelType.AllChannels,
				PocoCompositeOperator.HardLightCompositeOp, 102);

		return srcBmp;
	}
	private static final Bitmap createMask(int width, int height, int r, int g, int b) {
		Bitmap destBmp = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(destBmp);
		canvas.drawRGB(r, g, b);
		return destBmp;
	}
	
	/**
	 * LOMO风格
	 * 
	 */
	public static Bitmap lomoFi(Bitmap bitmap) {
		if (bitmap.getConfig() != Bitmap.Config.ARGB_8888) {
			return null;
		}
		Bitmap srcBmp = bitmap.copy(Config.ARGB_8888, true);

		Rect rect = new Rect(0, 0, srcBmp.getWidth(), srcBmp.getHeight());

		PocoFilter.LevelImageChannel(srcBmp, ChannelType.AllChannels, 45, 225,
				1.0);

		Bitmap maskBmp = createMask(srcBmp.getWidth(), srcBmp.getHeight(), 255,
				255, 255);
		PocoFilter.compositeImageRectChannel(srcBmp, maskBmp, rect, rect,
				ChannelType.AllChannels,
				PocoCompositeOperator.OverlayCompositeOp, 204);

		maskBmp = createDarkCornerMask(srcBmp.getWidth(), srcBmp.getHeight(),
				0xffffffff, 0xffdcdcdc, 0xffc8c8c8, 0xff646464, 0.0f, 0.5f,
				0.6f, 1.0f);
		PocoFilter.compositeImageRectChannel(srcBmp, maskBmp, rect, rect,
				ChannelType.AllChannels,
				PocoCompositeOperator.MultiplyCompositeOp, 128);

		return srcBmp;
	}
	private static Bitmap createDarkCornerMask(int width, int height,
			int color1, int color2, int color3, int color4, float position1,
			float position2, float position3, float position4) {
		int[] colors = { color1, color2, color3, color4 };
		float[] positions = { position1, position2, position3, position4 };

		return createDarkCornerMask(width, height, colors, positions);
	}
	private static Bitmap createDarkCornerMask(int width, int height,
			int[] colors, float positions[]) {
		Bitmap destBmp = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(destBmp);

		int cx = width / 2;
		int cy = height / 2;
		int radius = (int) Math.sqrt((width / 2) * (width / 2) + (height / 2)
				* (height / 2));

		RadialGradient radialGradientShader = new RadialGradient(cx, cy,
				radius, colors, positions, TileMode.CLAMP);
		Paint shaderPaint = new Paint();
		shaderPaint.setShader(radialGradientShader);
		shaderPaint.setAntiAlias(true);

		canvas.drawCircle(cx, cy, radius, shaderPaint);

		return destBmp;
	}
	
	/**
	 * 小资绿
	 * 
	 */
	public static Bitmap polaroid_g(Bitmap bitmap) {
		if (bitmap.getConfig() != Bitmap.Config.ARGB_8888) {
			return null;
		}
		Bitmap srcBmp = bitmap.copy(Config.ARGB_8888, true);
			
		Rect rect = new Rect(0, 0, srcBmp.getWidth(), srcBmp.getHeight());

		int[] colors = { 0xffced6c6, 0xff7aa594 };
		float[] positions = { 0.0f, 1.0f };

		Bitmap maskBmp = createDarkCornerMask(srcBmp.getWidth(),
				srcBmp.getHeight(), colors, positions);
		// 178
		PocoFilter.compositeImageRectChannel(srcBmp, maskBmp, rect, rect,
				ChannelType.AllChannels,
				PocoCompositeOperator.OverlayCompositeOp, 178);

		maskBmp = createDarkCornerMask(srcBmp.getWidth(), srcBmp.getHeight(),
				colors, positions);
		// 204
		PocoFilter.compositeImageRectChannel(srcBmp, maskBmp, rect, rect,
				ChannelType.AllChannels,
				PocoCompositeOperator.DarkenCompositeOp, 178);

		return srcBmp;
	}
	
	//////////////////////////////////////////////////////////////////
	
	private static class ChannelType	{
		static final int UndefinedChannel = 0x0000;
		static final int RedChannel = 0x0001;
		static final int GrayChannel = 0x0001;
		static final int CyanChannel = 0x0001;
		static final int GreenChannel = 0x0002;
		static final int MagentaChannel = 0x0002;
		static final int BlueChannel = 0x0004;
		static final int YellowChannel = 0x0004;
		static final int  AlphaChannel = 0x0008;
		static final int  OpacityChannel = 0x0008;
		static final int  MatteChannel = 0x0008;  /* deprecated */
		static final int  BlackChannel = 0x0020;
		static final int  IndexChannel = 0x0020;
		static final int  AllChannels = 0xff;
		static final int  DefaultChannels = (AllChannels &~ OpacityChannel);
	}
	
	private static class PocoCompositeOperator {
		static final int UndefinedCompositeOp = 0;
		static final int  NoCompositeOp = 1;
		static final int  ModulusAddCompositeOp =2;
		static final int  AtopCompositeOp =3;
		static final int  BlendCompositeOp =4;
		static final int  BumpmapCompositeOp = 5;
		static final int  ChangeMaskCompositeOp =6;
		static final int  ClearCompositeOp =7;
		static final int  ColorBurnCompositeOp=8;
		static final int  ColorDodgeCompositeOp=9;
		static final int  ColorizeCompositeOp=10;
		static final int  CopyBlackCompositeOp=11;
		static final int  CopyBlueCompositeOp=12;
		static final int  CopyCompositeOp=13;
		static final int  CopyCyanCompositeOp=14;
		static final int  CopyGreenCompositeOp=15;
		static final int  CopyMagentaCompositeOp=16;
		static final int  CopyOpacityCompositeOp=17;
		static final int  CopyRedCompositeOp=18;
		static final int  CopyYellowCompositeOp=19;
		static final int  DarkenCompositeOp=20;
		static final int  DstAtopCompositeOp=21;
		static final int  DstCompositeOp=22;
		static final int  DstInCompositeOp=23;
		static final int  DstOutCompositeOp=24;
		static final int  DstOverCompositeOp=25;
		static final int  DifferenceCompositeOp=26;
		static final int  DisplaceCompositeOp=27;
		static final int  DissolveCompositeOp=28;
		static final int  ExclusionCompositeOp=29;
		static final int  HardLightCompositeOp=30;
		static final int  HueCompositeOp=31;
		static final int  InCompositeOp=32;
		static final int  LightenCompositeOp=33;
		static final int  LinearLightCompositeOp=34;
		static final int  LuminizeCompositeOp=35;
		static final int  MinusCompositeOp=36;
		static final int  ModulateCompositeOp=37;
		static final int  MultiplyCompositeOp=38;
		static final int  OutCompositeOp=39;
		static final int  OverCompositeOp=40;
		static final int  OverlayCompositeOp=41;
		static final int  PlusCompositeOp=42;
		static final int  ReplaceCompositeOp=43;
		static final int  SaturateCompositeOp=44;
		static final int  ScreenCompositeOp=45;
		static final int  SoftLightCompositeOp=46;
		static final int  SrcAtopCompositeOp=47;
		static final int  SrcCompositeOp=48;
		static final int  SrcInCompositeOp=49;
		static final int  SrcOutCompositeOp=50;
		static final int  SrcOverCompositeOp=51;
		static final int  ModulusSubtractCompositeOp=52;
		static final int  ThresholdCompositeOp=53;
		static final int  XorCompositeOp=54;
		static final int  DivideCompositeOp=55;
		static final int  DistortCompositeOp=56;
		static final int  BlurCompositeOp=57;
		static final int  PegtopLightCompositeOp=58;
		static final int  VividLightCompositeOp=59;
		static final int  PinLightCompositeOp=60;
		static final int  LinearDodgeCompositeOp=61;
		static final int  LinearBurnCompositeOp=62;
		static final int  MathematicsCompositeOp=63;
	}
}
