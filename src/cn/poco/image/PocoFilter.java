package cn.poco.image;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class PocoFilter 
{
	static 
	{
        System.loadLibrary("PocoImage");
    }
	
	public static native int test(Bitmap dest,Bitmap src);
	
	public static native int compositeImageChannel(Bitmap dest,Bitmap src,
			int channel,int comOp,int opacity);		
	
	public static native int compositeImageRectChannel(Bitmap dest,Bitmap src,			
			int dx,int dy,int dw,int dh,
			int sx,int sy,int sw,int sh,			
			int channel,int comOp,int opacity);
	
	
	public static  int compositeImageRectChannel(Bitmap dest,Bitmap src,			
			Rect dr,Rect sr,					
			int channel,int comOp,int opacity)
	{
		return compositeImageRectChannel(dest,src,
				dr.left,dr.top,dr.width(),dr.height(),
				sr.left,sr.top,sr.width(),sr.height(),
				channel,comOp,opacity);		
	}
	
	
	public static native int LevelImageChannel(Bitmap dest,int channel,
			 double black_point, double white_point,
			   double gamma);
	
	public static native int LevelOutImageChannel(Bitmap dest,int channel,
			 double black_point, double white_point,
			   double gamma);
	
	
	public static native int gaussianBlurImageChannel(Bitmap dest,int channel,double sigma,int radius);
	public static native int gaussMaskBlur(Bitmap dest,Bitmap mask,int radius);
	
	
	public static native int sharpenImageChannel(Bitmap dest,int channel,int radius,double sigma);
	public static native int sharpenImageFast(Bitmap dest,Bitmap src,int percent);
	public static native int sharpenImageFast2(Bitmap dest,int percent);
	
	public static native int noiseChannel(Bitmap dest,int channel,int degree);
	
	public static native int sketch(Bitmap dest,int threshold);
	
	public static native int gray(Bitmap dest,int flag);
	
	public static native int composite(Bitmap dest,Bitmap src,int flage);
	
	public static native int changeBrightness(Bitmap dest,int value);
	
	public static native int changeContrast(Bitmap dest,int value);
	
	public static native int changeSaturation(Bitmap src,int value);
	
	public static native int changeBrightness2(Bitmap src,int brightness,int breakPoint);
	public static native int changeBrightness3(Bitmap src);
	
	public static native int colorBalance(Bitmap dest,
			double cyan_red_l, double cyan_red_m,double cyan_red_h,
			double magenta_green_l,double magenta_green_m,double magenta_green_h,
			double yellow_blue_l,double yellow_blue_m,double yellow_blue_h,
			int preserve_luminosity);
	
	
	public static native int optionColor(Bitmap dest,int rc,int rm,int ry,int rk,
			int gc,int gm,int gy,int gk,
			int bc,int bm,int by,int bk,
			int cc,int cm,int cy,int ck,
			int mc,int mm,int my,int mk,
			int yc,int ym,int yy,int yk);
	
	public static native int optionWhileBlack(Bitmap dest,int wc,int wm,int wy,int wk,
			 int bc,int bm,int by,int bk);

	public static native int optionExceptWhileBlack(Bitmap dest,int c,int m,int y,int k);
	
	public static native int shaderComposite(Bitmap dest,Bitmap alpha,Bitmap src);
	
	public static native int lightShadow(Bitmap dest);
	
	public static native int mixChannel(Bitmap dest,int redPercent,int greenPercent,int bluePercent);
	
	public static native int changeHSL(Bitmap dest, int h, int s, int l);
	
	public static native int moreBeaute(Bitmap dest,int light,int blur,int sharpen,int hue);
	public static native int moreBeaute2(Bitmap dest,Bitmap src,int light,int blur,int sharpen,int hue);
	
	public static native int changeSaturationAndContrast(Bitmap dest,float s,float c);
	
	public static native int zoomMotionBlur(Bitmap dest,int x1,int y1,int width,int height,
			int cx,int cy,int length,int outward,int quality);
	
	public static native int oldFilm(Bitmap dest);
	
	public static native int grandBleu(Bitmap dest);
	
	public static native int pocotoaster(Bitmap dest);
	
	public static class ChannelType	{
		public static final int UndefinedChannel = 0x0000;
		public  static final int RedChannel = 0x0001;
		public  static final int GrayChannel = 0x0001;
		public  static final int CyanChannel = 0x0001;
		public  static final int GreenChannel = 0x0002;
		public  static final int MagentaChannel = 0x0002;
		public static final int BlueChannel = 0x0004;
		public static final int YellowChannel = 0x0004;
		public static final int  AlphaChannel = 0x0008;
		public static final int  OpacityChannel = 0x0008;
		public static final int  MatteChannel = 0x0008;  /* deprecated */
		public static final int  BlackChannel = 0x0020;
		public static final int  IndexChannel = 0x0020;
		public static final int  AllChannels = 0xff;
		public static final int  DefaultChannels = (AllChannels &~ OpacityChannel);
	}
	
	public static class PocoCompositeOperator {
		
		public static int UndefinedCompositeOp = 0;
		public static int  NoCompositeOp = 1;
		public static int  ModulusAddCompositeOp =2;
		public static int  AtopCompositeOp =3;
		public static int  BlendCompositeOp =4;
		public static int  BumpmapCompositeOp = 5;
		public static int  ChangeMaskCompositeOp =6;
		public static int  ClearCompositeOp =7;
		public static int  ColorBurnCompositeOp=8;
		public static int  ColorDodgeCompositeOp=9;
		public static int  ColorizeCompositeOp=10;
		public static int  CopyBlackCompositeOp=11;
		public static int  CopyBlueCompositeOp=12;
		public static int  CopyCompositeOp=13;
		public static int  CopyCyanCompositeOp=14;
		public static int  CopyGreenCompositeOp=15;
		public static int  CopyMagentaCompositeOp=16;
		public static int  CopyOpacityCompositeOp=17;
		public static int  CopyRedCompositeOp=18;
		public static int  CopyYellowCompositeOp=19;
		public static int  DarkenCompositeOp=20;
		public static int  DstAtopCompositeOp=21;
		public static int  DstCompositeOp=22;
		public static int  DstInCompositeOp=23;
		public static int  DstOutCompositeOp=24;
		public static int  DstOverCompositeOp=25;
		public static int  DifferenceCompositeOp=26;
		public static int  DisplaceCompositeOp=27;
		public static int  DissolveCompositeOp=28;
		public static int  ExclusionCompositeOp=29;
		public static int  HardLightCompositeOp=30;
		public static int  HueCompositeOp=31;
		public static int  InCompositeOp=32;
		public static int  LightenCompositeOp=33;
		public static int  LinearLightCompositeOp=34;
		public static int  LuminizeCompositeOp=35;
		public static int  MinusCompositeOp=36;
		public static int  ModulateCompositeOp=37;
		public static int  MultiplyCompositeOp=38;
		public static int  OutCompositeOp=39;
		public static int  OverCompositeOp=40;
		public static int  OverlayCompositeOp=41;
		public static int  PlusCompositeOp=42;
		public static int  ReplaceCompositeOp=43;
		public static int  SaturateCompositeOp=44;
		public static int  ScreenCompositeOp=45;
		public static int  SoftLightCompositeOp=46;
		public static int  SrcAtopCompositeOp=47;
		public static int  SrcCompositeOp=48;
		public static int  SrcInCompositeOp=49;
		public static int  SrcOutCompositeOp=50;
		public static int  SrcOverCompositeOp=51;
		public static int  ModulusSubtractCompositeOp=52;
		public static int  ThresholdCompositeOp=53;
		public static int  XorCompositeOp=54;
		public static int  DivideCompositeOp=55;
		public static int  DistortCompositeOp=56;
		public static int  BlurCompositeOp=57;
		public static int  PegtopLightCompositeOp=58;
		public static int  VividLightCompositeOp=59;
		public static int  PinLightCompositeOp=60;
		public static int  LinearDodgeCompositeOp=61;
		public static int  LinearBurnCompositeOp=62;
		public static int  MathematicsCompositeOp=63;

	}

}
