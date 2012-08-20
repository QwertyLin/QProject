package q.project.other;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class QCameraUtil {
	
	/**
	 * 获得摄像头View，可通过addView添加到布局中
	 */
	public static final Preview preview(Context ctx) {
		return new Preview(ctx);
	}
	public static class Preview extends SurfaceView implements SurfaceHolder.Callback {
	    SurfaceHolder holder;
	    Camera camera;

	    Preview(Context context) {
	        super(context);

	        // Install a SurfaceHolder.Callback so we get notified when the
	        // underlying surface is created and destroyed.
	        holder = getHolder();
	        holder.addCallback(this);
	        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	    }

	    public void surfaceCreated(SurfaceHolder holder) {
	    	System.out.println("--surfaceCreated");
	        // The Surface has been created, acquire the camera and tell it where
	        // to draw.
	        camera = Camera.open();
	        try {
	           camera.setPreviewDisplay(holder);
	        } catch (IOException exception) {
	            camera.release();
	            camera = null;
	            // TODO: add more exception handling logic here
	        }
	    }

	    public void surfaceDestroyed(SurfaceHolder holder) {
	    	System.out.println("--surfaceDestroyed");
	        // Surface will be destroyed when we return, so stop the preview.
	        // Because the CameraDevice object is not a shared resource, it's very
	        // important to release it when the activity is paused.
	        camera.stopPreview();
	        camera.release();
	        camera = null;
	    }


	    private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
	        final double ASPECT_TOLERANCE = 0.05;
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

	    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
	    	System.out.println("--surfaceChanged");
	        // Now that the size is known, set up the camera parameters and begin
	        // the preview.
	        Camera.Parameters parameters = camera.getParameters();

	        List<Size> sizes = parameters.getSupportedPreviewSizes();
	        //Size optimalSize = getOptimalPreviewSize(sizes, w, h);
	        Size optimalSize = sizes.get(0);
	        System.out.println(optimalSize.width+":"+optimalSize.height);
	        parameters.setPreviewSize(optimalSize.width, optimalSize.height);

	        camera.setParameters(parameters);
	        camera.startPreview();
	    }
	}
	
	public static final Bitmap takePicture(QCameraUtil.Preview preview) {
		TakePictureCallback callback = new TakePictureCallback();
		System.out.println(preview.camera == null);
		preview.camera.takePicture(null, null, callback); 
		return callback.bitmap;
	}
	private static class TakePictureCallback implements PictureCallback {
		Bitmap bitmap;
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			System.out.println("==takePicture");
			BitmapFactory.Options options = new BitmapFactory.Options();
       	 	options.inSampleSize = 2;//缩放比例
       	 	bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
       	 	System.out.println(bitmap.getWidth()+":"+bitmap.getHeight());
		}
	}

}
