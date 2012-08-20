package q.project.other;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import q.util.QUtil4A;
import q.util.a.QLog;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class QCamera extends Activity {
	
	QCameraUtil.Preview preview;
	ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout);
		//
		layout.addView(new Preview(this), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
		//preview = QCameraUtil.preview(this);
		//layout.addView(preview);
		//
		iv = new ImageView(this);
		layout.addView(iv);
		//
		Button btn = new Button(this);
		btn.setText("拍照");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				iv.setImageBitmap(QCameraUtil.takePicture(preview));
			}
		});
		layout.addView(btn);
		
	}
	
	/**
	 * A simple wrapper around a Camera and a SurfaceView that renders a centered preview of the Camera
	 * to the surface. We need to center the SurfaceView because not all devices have cameras that
	 * support preview sizes at the same aspect ratio as the device's display.
	 */
	class Preview extends SurfaceView implements SurfaceHolder.Callback {

	    SurfaceHolder mHolder;
	    Camera mCamera;

	    Preview(Context context) {
	        super(context);

	        // Install a SurfaceHolder.Callback so we get notified when the
	        // underlying surface is created and destroyed.
	        mHolder = this.getHolder();
	        mHolder.addCallback(this);
	        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	    }

	    public void surfaceCreated(SurfaceHolder holder) {
	    	QLog.log("surfaceCreated");
	        // The Surface has been created, acquire the camera and tell it where
	        // to draw.
	    	mCamera = Camera.open();
	        try {
	            if (mCamera != null) {
	                mCamera.setPreviewDisplay(holder);
	            }
	        } catch (IOException exception) {
	        	if (mCamera != null) {
		            mCamera.stopPreview();
		            mCamera.release();
		        }
	        }
	    }

	    public void surfaceDestroyed(SurfaceHolder holder) {
	    	QLog.log("surfaceDestroyed");
	        // Surface will be destroyed when we return, so stop the preview.
	        if (mCamera != null) {
	            mCamera.stopPreview();
	            mCamera.release();
	        }
	    }

	    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
	    	QLog.log("surfaceChanged");
	        // Now that the size is known, set up the camera parameters and begin
	        // the preview.
	    	Camera.Parameters parameters = mCamera.getParameters();
	        /*
	        List<Size> sizes = parameters.getSupportedPreviewSizes();
	        Size optimalSize = sizes.get(0);
	        int width = optimalSize.width;
	        int height = optimalSize.height;
	        if(width > height){
	        	int temp = width;
	        	width = height;
	        	height = temp;
	        }
	        parameters.setPreviewSize(width, height);
	        
	        
	        QLog.b(this, "size", "width=" + optimalSize.width + " height:" + optimalSize.height);*/
	        	        
	        
	        //  
	        //QLog.b(this, "getPictureSize", "width="+parameters.getPictureSize().width+" height="+parameters.getPictureSize().height);
	        
	        //parameters.setPictureSize(width,  height);
	    	//
	        setPictureAndPreviewSize(parameters);
	        QLog.log("getJpegQuality" + parameters.getJpegQuality());
	        
	        //
	        //parameters.set("orientation", "landscape");
	        //parameters.setRotation(90);
	        
	        requestLayout();
	        parameters.setPictureFormat(PixelFormat.JPEG);
	        mCamera.setParameters(parameters);
	        mCamera.startPreview();
	    }
	    
	  //设置图片大小和预览大小,预览大小参考图片大小的比例
    	public Size setPictureAndPreviewSize(Camera.Parameters p)
    	{
    		Size size = p.getSupportedPictureSizes().get(0);
    		Size sz = setPictureSize(p, size.width, size.height);
    		if(sz != null)
    		{
    			setPreviewSize(p, sz.width, sz.height);
    		}
    		return sz;
    	}
    	
    	//设置图片大小
    	public  Size setPictureSize(Camera.Parameters p, int w, int h)
    	{
    		if(w < h)
    		{
    			int temp = w;
    			w = h;
    			h = temp;
    		}
			List<Size> list = p.getSupportedPictureSizes();
			if(list == null)
			{
				return p.getPictureSize();
			}
			int len = list.size();
			for(int i = 0; i < len; i++)
			{
				System.out.println("支持的图片大小:"+list.get(i).width+"/"+list.get(i).height);
			}
			Size size = getSizeBest(list, w, h);
			if(size != null)
			{
				p.setPictureSize(size.width, size.height);
				p.setJpegQuality(90);
				//mCamera.setParameters(p);
				System.out.println("设置图片大小:"+size.width+"/"+size.height);
			}
			return p.getPictureSize();
    	}
    	
    	private Size getSizeBest(List<Size> list, int w, int h)
 		{
 			int len = 0;
 			if(list != null)
 			{
 				int offset = 0x00ffffff;
 				len = list.size();
 				Size best = null;
 				for (int i = 0; i < len; i++) {
 					Size size = list.get(i);
 					int xoffset = Math.abs(w-size.width);
 					int yoffset = Math.abs(h-size.height);
 					int os = yoffset+xoffset;
 					if(os < offset){
 						offset = os;
 						best = size;
 					}
 				}
 				if(best != null)
 				{
 					return best;
 				}
 				else if(len > 0)
 				{
 					return list.get(0);
 				}
 			}
 			return null;
 		}
    	
    	//设置预览大小
    	public  Size setPreviewSize(Camera.Parameters p, int w, int h)
    	{
    		if(w < h)
    		{
    			int temp = w;
    			w = h;
    			h = temp;
    		}		
    			List<Size> list = p.getSupportedPreviewSizes();
    			
    			if(list == null)
    			{
    				return p.getPreviewSize();
    			}
    			int len = list.size();
    			for(int i = 0; i < len; i++)
    			{
    				System.out.println("支持的预览大小:"+list.get(i).width+"/"+list.get(i).height);
    			}
    			
    			ArrayList<Size> filterSize = new ArrayList<Size>();
    			for(int i = 0; i < len; i++)
    			{
    				Size size = list.get(i);
    				int bigOne =  size.width>size.height?size.width:size.height;
    				if(bigOne > 400)
    				{
    					filterSize.add(size);
    				}
    			}
    			list = filterSize;
    			
    			Size size = getRatioBest(list, w, h);
    			if(size != null)
    			{				
    				p.setPreviewSize(size.width, size.height);
    				System.out.println("设置预览大小:"+size.width+"/"+size.height);
    				try{
    					//mCamera.setParameters(p);
    				}catch(Exception e) 
    				{ 
    					System.out.println("error in set size");
    					//parameters = mCamera.getParameters();					
    				}
    			
    			}
    			size = p.getPreviewSize();
    			return size;
    	}
    	
    	private Size getRatioBest(List<Size> list, int w, int h)
    	{
    		int len = 0;
    		if(list != null)
    		{
    			len = list.size();
    			if(len > 0)
    			{
    				float f1 = (float)w/(float)h;
    				Size size = list.get(0);
    				float f = f1-(float)size.width/(float)size.height;
    				if(f < 0) f=-f;
    				for (int i = 1; i < len; i++) {
    					Size temp = list.get(i);
    					float f2 = f1-(float)temp.width/(float)temp.height;
    					if(f2 < 0) f2=-f2;
    					if(f2 < f)
    					{
    							f = f2;
    							size = temp;
    					}
    				}
    				
    				return size;
    			}
    		}
    		return null;
    	}
	    
	    private void printSupportedPreviewSizes(){
	    	QLog.log("printSupportedPreviewSizes");
	    	List<Size> list = mCamera.getParameters().getSupportedPreviewSizes();
	    	for (int i = 0; i < list.size(); i++) {
				QLog.log("size" + "width="+list.get(i).width+" height="+list.get(i).height);
			}
	    }
	    
	    private void printSupportedPictureSizes(){
	    	QLog.log("printSupportedPictureSizes");
	    	List<Size> list = mCamera.getParameters().getSupportedPictureSizes();
	    	for (int i = 0; i < list.size(); i++) {
				QLog.log("size" + "width="+list.get(i).width+" height="+list.get(i).height);
			}
	    }

	    private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
	        final double ASPECT_TOLERANCE = 0.1;
	        double targetRatio = (double) w / h;
	        if (sizes == null) return null;

	        Size optimalSize = null;
	        double minDiff = Double.MAX_VALUE;

	        int targetHeight = h;

	        // Try to find an size match aspect ratio and size
	        for (Size size : sizes) {
	            double ratio = (double) size.width / size.height;
	            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
	            if (Math.abs(size.height - targetHeight) < minDiff) {
	                optimalSize = size;
	                minDiff = Math.abs(size.height - targetHeight);
	            }
	        }

	        // Cannot find the one match the aspect ratio, ignore the requirement
	        if (optimalSize == null) {
	            minDiff = Double.MAX_VALUE;
	            for (Size size : sizes) {
	                if (Math.abs(size.height - targetHeight) < minDiff) {
	                    optimalSize = size;
	                    minDiff = Math.abs(size.height - targetHeight);
	                }
	            }
	        }
	        return optimalSize;
	    }

	    

	}

}
