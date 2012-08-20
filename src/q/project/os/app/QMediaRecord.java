package q.project.os.app;

import java.io.IOException;

import android.content.Context;
import android.media.MediaRecorder;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class QMediaRecord extends QCameraView {

	public QMediaRecord(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	MediaRecorder mMediaRecorder;
	/**
	 * 录像
	 */
	public void takeVideoStart(String fileSave){
		/*if(camera != null){
			camera.stopPreview();
			camera.release();
			camera = null;
		}
		mMediaRecorder = new MediaRecorder();
		mMediaRecorder.setPreviewDisplay(holder.getSurface());
		mMediaRecorder
				.setVideoSource(MediaRecorder.VideoSource.CAMERA);
		mMediaRecorder
				.setAudioSource(MediaRecorder.AudioSource.MIC);
		mMediaRecorder
				.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mMediaRecorder
				.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
		mMediaRecorder
				.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mMediaRecorder.setVideoSize(320, 240);
		mMediaRecorder.setVideoFrameRate(15);
		mMediaRecorder.setOutputFile(fileSave);
		try {
			mMediaRecorder.prepare();
			mMediaRecorder.start();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void takeVideoStop(){
		mMediaRecorder.stop();
		mMediaRecorder.release();
		mMediaRecorder = null;
	}
}