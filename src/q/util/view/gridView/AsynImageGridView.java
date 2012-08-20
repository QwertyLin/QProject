package q.util.view.gridView;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * 异步加载图片的GridView
 *
 */
public class AsynImageGridView extends GridView {
	
	private ExecutorService threadPool = Executors.newCachedThreadPool();
	private Map<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();
	
	private Context ctx;
	private String[] imageFiles;
	private int itemWidth;
	private int itemSpacing;
	private int column;

	public AsynImageGridView(Context ctx, String[] imageFiles, int column, int itemSpacing, FrameLayout parentLayout) {
		super(ctx);
		this.ctx = ctx;
		this.imageFiles = imageFiles;
		this.itemSpacing = itemSpacing;
		this.column = column;
		//初始化自身
		this.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));
		this.setGravity(Gravity.CENTER);
		this.setNumColumns(column);
		//设置夫布局
		parentLayout.addView(this);
		//线程
		new Thread(){
			public void run() {
				SystemClock.sleep(100);
				handler.sendEmptyMessage(0);
			};
		}.start();
	}
	
	private void init(){
		itemWidth = (this.getWidth() - column * itemSpacing) / column;
		this.setVerticalSpacing(itemSpacing);
		this.setHorizontalSpacing(itemSpacing);
		this.setAdapter(new DataAdapter());
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			init();
		};
	};
	
	/**
	 * 回收bitmap所占内存
	 */
	public void recycleBitmap(){
		SoftReference<Bitmap> sr;
		Bitmap b;
		for(String s:imageCache.keySet()){
			sr = imageCache.get(s);
			if(null != sr){
				b = sr.get();
				if(null != b && !b.isRecycled()){
					b.recycle();
					b = null;
				}
			}
		}
		System.gc();
	}
	
	class DataAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return imageFiles.length;
		}

		@Override
		public Object getItem(int position) {
			return imageFiles[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View v, ViewGroup parent) {
			AsynListImageView iv;
			if(null == v) {
				iv = new AsynListImageView(ctx);
				iv.setLayoutParams(new GridView.LayoutParams(itemWidth, itemWidth));
				iv.setScaleType(ScaleType.CENTER_CROP);
				v = iv;
			}else{
				iv = (AsynListImageView)v;
			}
			iv.loadImageBitmap(imageFiles[position], position);
			return iv;
		}
		
	}
	
	class AsynListImageView extends ImageView {
		
		private int position;

		public AsynListImageView(Context context) {
			super(context);
		}
		
		public void loadImageBitmap(final String path, final int position) {
			this.position = position;
			if (imageCache.containsKey(path)) {
				SoftReference<Bitmap> softReference = imageCache.get(path);
				if (softReference != null) {
					final Bitmap bm = softReference.get();
					if (bm != null && !bm.isRecycled()) {
						Log.i("Q", "==AsynImageView: cache "+position);
						setImageBitmap(bm);
						setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								//TODO
							}
						});
						return;
					}
				}
			}
			setImageBitmap(null);
			setOnClickListener(null);
			Log.i("Q", "==AsynImageView: load "+position);
			threadPool.submit(new Runnable() {				
				@Override
				public void run() {
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = true;
					BitmapFactory.decodeFile(path, options);
					int mWidth = options.outWidth;
					int s = mWidth / itemWidth;
					if (s == 0) {
						s = 1;
					}
					options = new BitmapFactory.Options();
					options.inSampleSize = s;
					Bitmap bitmap = BitmapFactory.decodeFile(path, options);
					imageCache.put(path, new SoftReference<Bitmap>(bitmap)); // 加入软引用
					Message msg = Message.obtain();
					msg.arg1 = position;
					msg.obj = bitmap;
					handler.sendMessage(msg);
				}
			});	
		}
		
		private Handler handler = new Handler(){
			public void handleMessage(android.os.Message msg) {
				if(msg.arg1 == position) {
					final Bitmap bm = (Bitmap)msg.obj;
					setImageBitmap(bm);
					setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							//TODO
						}
					});
				}
			};
		};
	}
}