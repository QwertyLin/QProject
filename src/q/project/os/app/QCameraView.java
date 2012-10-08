package q.project.os.app;

import java.io.IOException;
import q.QLog;
import q.QToStr;
import q.os.QWindowUtil;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 尽量在onResume()中初始化，加快体验速度。
 * 2大功能：取景：preview；拍摄：take picture。
 * 进程间的通讯机制：Binder
 * 架构：分成Client和Server端
 */
public class QCameraView extends SurfaceView implements SurfaceHolder.Callback {
	
	public Camera camera;
	private int orientation = 90; //镜头方向，默认是横向的，这里旋转90度成竖向。
	private Context ctx;
	
	public QCameraView(Context ctx) {
		super(ctx);
		this.ctx = ctx;
		QWindowUtil.setScreenKeepOn((Activity)ctx);
		// Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	//Sureface创建的时候，此方法被调用
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, acquire the camera and tell it where
        // to draw.
		//打开摄像头，获得Camera对象
		camera = Camera.open();
		QLog.log(this, "surfaceCreated" + QToStr.toStr(camera));
		try {
			//设置显示
			camera.setPreviewDisplay(holder);
		} catch (IOException exception) {
			camera.release();
			camera = null;
		}
		//预览图选择90度
		camera.setDisplayOrientation(orientation);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		QLog.log(this, "onLayout");
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		QLog.log(this, "surfaceChanged" + " format=" + format + " width=" + w + " height=" + h);
		Camera.Parameters para = camera.getParameters();
		//设置属性
		para.setPictureFormat(PixelFormat.JPEG);// 设置照片的输出格式
		onInitSize(para);
		//para.setPreviewSize(size.width, size.height);
		camera.setParameters(para);
		//this.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, (int)(1.0f * size.width / size.height * w)));

		//开始预览
		camera.stopPreview();
		camera.startPreview();
		//已经获得Surface的width和height，设置Camera的参数
		//Camera.Parameters parameters = camera.getParameters();
		//parameters.setPreviewSize(w, h);
		//camera.setParameters(parameters);
		QLog.log(this, QToStr.toStr(camera));
	}	
	
	private void onInitSize(Camera.Parameters para) {
		float ratioStd = 0.05f;
		float ratio;
		for(Size picSize : para.getSupportedPictureSizes()){
			QLog.log(this, "supportedPictureSizes " + picSize.width + "*" + picSize.height);
			for(Size preSize : para.getSupportedPreviewSizes()){
				if(picSize.width < preSize.width || picSize.height < preSize.height){
					continue;
				}
				QLog.log(this, "supportedPreviewSizes " + preSize.width + "*" + preSize.height);
				ratio = Math.abs(1.0f * picSize.width / picSize.height - 1.0f * preSize.width / preSize.height);
				QLog.log(this, "ratio=" + ratio);
				if(ratio <= ratioStd){
					//保存配置
					para.setPictureSize(picSize.width, picSize.height);
					para.setPreviewSize(preSize.width, preSize.height);
					return;
				}
			}
		}
	}

	//Surface销毁的时候，此方法被调用
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		QLog.log(this, "surfaceDestroyed");
		if(camera != null){
			camera.stopPreview();
			camera.release();
			camera = null;
		}
	}
	
	protected void Q(){};
	
	/**
	 * 设置镜头方向
	 */
	public void setOrientation(int degree){
		camera.stopPreview();
		camera.setDisplayOrientation(degree);
		camera.startPreview();
	}
	
	/**
	 * 自动对焦
	 */
	public void autoFocus(){
		camera.autoFocus(null);
	}
	
	/**
	 * 拍照
	 */
	public void takePicture(TackPictureCallback callback){
		if(shutterCallback == null || jpegCallback == null){
			onInitTakePictureCallback(callback);
		}
		//拍照前自动对焦
		camera.autoFocus(new AutoFocusCallback() {
			
			@Override
			public void onAutoFocus(boolean success, Camera camera) {
				camera.takePicture(shutterCallback, null, jpegCallback);
			}
		});
	}
	public interface TackPictureCallback {
		void onCompleted(Bitmap bm);
	}
	private ShutterCallback shutterCallback; //按下快门时回调
	private PictureCallback jpegCallback;//返回照片的JPEG格式的数据
	//拍照初始化
	private void onInitTakePictureCallback(final TackPictureCallback callback){
		shutterCallback = new ShutterCallback(){
			public void onShutter() {
				//发出提示用户的声音
				new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME)
					.startTone(ToneGenerator.TONE_PROP_BEEP2);
			}
		};
		jpegCallback = new PictureCallback(){
			public void onPictureTaken(byte[] data, Camera camera) {
				Parameters ps = camera.getParameters();
				if(ps.getPictureFormat() == PixelFormat.JPEG){
					Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
					//
					if(orientation != 0){
						Matrix matrix = new Matrix();
						matrix.setRotate(orientation);
						bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
					}
					//
					if(callback != null){
						callback.onCompleted(bm);
					}
				}
			}
		};
	}
}
