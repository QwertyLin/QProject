package q.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.markupartist.android.widget.PullToRefreshActivity;

import q.util.QUtil4A;
import q.util.QUtil;
import q.util.QUtil4SystemService;
import q.util.a.QDrawable;
import q.util.view.imageView.AnimationImageView;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class QProjectActivity1 extends Activity {
	
	float degree = 0;
	ImageView imageView;
	TextView textView;
	//FrameLayout f;
	AnimationImageView fv;
	
	
	VideoView videoView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
    	
        
        setContentView(R.layout.main);
        
        startActivity(new Intent(this, QProjectActivity2.class));
        //finish();
    	if(true){
    		return;
    	}
    	
        Resources res = getResources();
        
        videoView = (VideoView) findViewById(R.id.VideoView);
        
        MediaController mediaController = new MediaController(this); 
        mediaController.setAnchorView(videoView); 
        
        //mediaController.
        
        int BUFF_LENGTH = 1024 * 1024;
        String file = "/sdcard/Video/test.mp4";
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(file, "rw");
			byte[] buff = new byte[BUFF_LENGTH];

			int pos = 0;
			int lineNum = 1;
			int startPos = 0;
			int endPos = 0;
			int read = -1;
			
			raf.seek(raf.length() - BUFF_LENGTH);
			raf.read(buff);
			raf.seek(0);
			raf.write(buff);
			raf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Uri video = Uri.parse("/sdcard/Video/test.mp4");
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(video); 
        videoView.start();
        //
        
		
       
        //videoView.start();
        
        //videoView.
        //textView = (TextView)findViewById(R.id.text);
        //f = (FrameLayout)findViewById(R.id.layout);
        
        //textView.setOnTouchListener(movingEventListener);
        
        
        /*new Thread(){
        	public void run() {
        		while(true){
        			SystemClock.sleep(10000);
        			handler.sendEmptyMessage(1);
        		}
        	};
        }.start();*/
        
        //Bitmap bitmap = QBitmap.decode("qq.jpg");
        
        /*try {
        	String[] str = QJson.decodeArray("[\"a\",\"b\",\"c\"]");
			for(String s:str){
				System.out.println(s);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
       /* String[] str = {"a", "b", "c"};
        System.out.println(QJson.encodeArray(str));
        try {
			System.out.println(QJson.encodeObject());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        //getViews
       // imageView = (ImageView) findViewById(R.id.image);
       // textView = (TextView) findViewById(R.id.text);
        
        //setViews
        //imageView.setImageBitmap(bitmap);
        
         //QOs.networkIsAvailable(this);     
        
       // startActivity(new Intent(this, DrawableActivity.class));
        //this.finish();
       // BitmapFactory.
        //new Thread(runnable).start();
    }
    
    Handler handler = new Handler(){
    	@Override
    	public void handleMessage(Message msg) {
    		switch(msg.what){
    		case 1:
    			 videoView.seekTo(200000);
    			 videoView.start();
    			 break;
    		}
    		super.handleMessage(msg);
    	}
    };
    
    private OnTouchListener movingEventListener = new OnTouchListener() { 
        int lastX, lastY; 
 
        @Override 
        public boolean onTouch(View v, MotionEvent event) { 
            switch (event.getAction()) { 
            case MotionEvent.ACTION_DOWN: 
                lastX = (int) event.getRawX(); 
                lastY = (int) event.getRawY(); 
                break; 
            case MotionEvent.ACTION_MOVE: 
                int dx = (int) event.getRawX() - lastX; 
                int dy = (int) event.getRawY() - lastY; 
 
                //System.out.println("dx:"+dx+" dy:"+dy);
                int left = v.getLeft() + dx; 
                int top = v.getTop() + dy; 
                int right = v.getRight() + dx; 
                int bottom = v.getBottom() + dy; 
                // 设置不能出界 
                if (left < 0) { 
                    left = 0; 
                    right = left + v.getWidth(); 
                } 
                if (right > 320) { 
                    right = 320; 
                    left = right - v.getWidth(); 
                } 
                if (bottom > 480) { 
                    bottom = 480; 
                    top = bottom - v.getHeight(); 
                }
                /*
 
                
 
                if (top < 0) { 
                    top = 0; 
                    bottom = top + v.getHeight(); 
                } 
 
                 */
                //System.out.println("move "+left+" "+top+" "+left+" "+bottom);
                v.layout(left, top, right, bottom); 
 
                //lastX = (int) event.getRawX(); 
                //lastY = (int) event.getRawY(); 
 
                break; 
            case MotionEvent.ACTION_UP: 
            	while(v.getTop()>0){
            		//System.out.println("update "+v.getLeft()+" "+v.getTop()+" "+v.getRight()+" "+v.getBottom());
            		v.layout(v.getLeft(), v.getTop()-1, v.getRight(), v.getBottom()-1);
            		
            	}
            	lastX = 0; lastY = 0;
                break; 
            } 
            return true; 
        } 
    }; 
    /*Handler handler = new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		textView.setText(degree+"");
    		imageView.setBackgroundDrawable(gradientLinearRoundRect(degree));
    	};
    };
    
    Runnable runnable = new Runnable() {
		@Override
		public void run() {
			while(!Thread.currentThread().isInterrupted()){
				if(degree < 1){
					degree += 0.01;
					SystemClock.sleep(500);
					handler.sendEmptyMessage(0);
				}
			}
		}
	};*/
    
    /*public static final Drawable gradientLinearRoundRect(float degree){
		final float xStart = 0, yStart = 0, //渐变开始坐标
				xEnd = 300, yEnd = 0; //渐变结束坐标
		final int[] colors = new int[] { 0xFFFF0000, 0xFFFF0000, 0xFFFFFFFF, 0xFFFFFFFF }; //渐变颜色集
		final float[] positions = new float[] {0, degree, degree, 1}; //渐变段落集，与颜色集一一对应
		final float radius = 45; //圆角度
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
		dr.getPaint().setShader(
				new LinearGradient(
						xStart, yStart, xEnd, yEnd,
						colors, positions, Shader.TileMode.MIRROR)
				);
      	return dr;
	}*/
}