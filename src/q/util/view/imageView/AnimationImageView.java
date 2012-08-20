package q.util.view.imageView;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.ImageView;

/**
 * 动画帧的ImageView
 *
 */
public class AnimationImageView extends ImageView{
	
	//定义AnimationDrawable动画
	private AnimationDrawable	frameAnimation = null;
	
	//定义一个Drawable对象
	Drawable					mBitAnimation  = null;
	
	public AnimationImageView(Context context, Drawable[] frames, int[] durations) {
		super(context);
		//实例化AnimationDrawable对象
		frameAnimation = new AnimationDrawable();
		
		/*装载资源*/
		//这里用一个循环装载所有名字类似的资源
		//如"a1...........15.png"的图片
		int size = frames.length;
		for(int i = 0; i < size; i++){
			//int id = getResources().getIdentifier("a" + i, "drawable", context.getPackageName());
			//此方法返回一个可绘制的对象与特定的资源ID相关联 
			//mBitAnimation = getResources().getDrawable(id);
			/*为动画添加一帧*/
			//参数mBitAnimation是该帧的图片
			//参数500是该帧显示的时间，按毫秒计算
			frameAnimation.addFrame(frames[i], durations[i]);
		}
		/*上边用到了Resources的getIdentifier方法 方法返回一个资源的唯一标识符，如果没有这个资源就返回0
		 * 0不是有效的标识符，在说说这个方法几个参数的含义
		 * 第一个 就是我们的资源名称了。
		 * 第二个 就是我们要去哪里找我们的资源 我们的图片在drawable 下 所以为drawable
		 * 第三个 我们用了Context的getPackageName返回应用程序的包名
		 * */
		
		//设置播放模式是否循环播放，false表示循环，true表示不循环
		frameAnimation.setOneShot(false);
		
		//设置本类将要显示的这个动画
		this.setBackgroundDrawable( frameAnimation );
	}
	
    public void startSp(){
    	new Thread(){
    		public void run() {
    			SystemClock.sleep(100);
    			handler.sendEmptyMessage(0);
    		}
    	}.start();
    }
    
    Handler handler = new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		start();
    	};
    };
	
	public void start(){
		frameAnimation.start();
	}
	
	public void stop(){
		frameAnimation.stop();
	}
}
