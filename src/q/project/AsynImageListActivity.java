package q.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * 异步图片列表
 *
 */
public class AsynImageListActivity extends Activity {
	
	private ExecutorService threadPool = Executors.newCachedThreadPool();
	private Map<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();
	List<String> data;
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asyn_image_list);
		
		data = new ArrayList<String>();
		data.add("http://tp2.sinaimg.cn/1784470365/50/1280483518/0");
		data.add("http://tp4.sinaimg.cn/1733309631/50/5600879827/1");
		data.add("http://tp4.sinaimg.cn/1563486443/50/5619612007/1");
		data.add("http://tp3.sinaimg.cn/1969399594/50/5617496823/1");
		data.add("http://tp3.sinaimg.cn/1884735442/50/5601571924/1");
		data.add("http://tp2.sinaimg.cn/2216755173/50/5604910335/1");
		data.add("http://tp4.sinaimg.cn/1762696131/50/5601957015/1");
		data.add("http://tp2.sinaimg.cn/2140227493/50/5620826774/0");
		data.add("http://tp4.sinaimg.cn/1787215031/50/5620478324/1");
		data.add("http://tp2.sinaimg.cn/1925076605/50/0/1");
		data.add("http://tp1.sinaimg.cn/1863212792/50/5611331325/1");
		data.add("http://tp1.sinaimg.cn/1857665552/50/5620367846/0");
		data.add("http://tp3.sinaimg.cn/1683532574/50/5616462887/1");
		data.add("http://tp4.sinaimg.cn/1943274631/50/5619851630/0");
		data.add("http://tp3.sinaimg.cn/1197161814/50/1290146312/1");
		data.add("http://tp2.sinaimg.cn/1787907481/50/5619708286/1");
		data.add("http://tp1.sinaimg.cn/1760743492/50/5603382611/1");
		data.add("http://tp4.sinaimg.cn/1859834551/50/5609064093/1");
		data.add("http://tp4.sinaimg.cn/2178697367/50/5603010991/0");
		data.add("http://tp2.sinaimg.cn/1697055000/50/5623250781/1");
		
		//getViews
		listView = (ListView)findViewById(R.id.list);
		
		//setViews
		listView.setAdapter(new DataAdapter());
	}
	
	class DataAdapter extends BaseAdapter {
		
		private LayoutInflater inflater = getLayoutInflater();

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if(convertView == null){
				convertView = inflater.inflate(R.layout.asyn_image_item, null);
				holder = new Holder();
				holder.image = (ImageView) convertView.findViewById(R.id.image);
				convertView.setTag(holder);
			}else {
				holder = (Holder)convertView.getTag();
			}
			String url = data.get(position);
			String imageTag = url + position;
			holder.image.setTag(imageTag);//用来更新图片
			loadImage(url, holder.image, imageTag);
			return convertView;
		}
		
		void loadImage(final String url, ImageView imageView, final String imageTag) {
			// 如果缓存中有图片，则加载
			if (imageCache.containsKey(url)) {
				Log.i("Q", "==loadImage: cache "+imageTag );
				SoftReference<Bitmap> softReference = imageCache.get(url);
				if (softReference != null) {
					Bitmap bitmap = softReference.get();
					if (bitmap != null && !bitmap.isRecycled()) {
						imageView.setImageBitmap(bitmap);
					}
				}
			} else { //否则开新线程从本地或者网络加载
				Log.i("Q", "==loadImage: network");
				threadPool.submit(new Runnable() {
					Bitmap bitmap = null;
					Handler handler = new Handler() {
						public void handleMessage(Message msg) {
							switch(msg.what){
							case 0:
								ImageView imageView = (ImageView) listView.findViewWithTag(imageTag);
								if(imageView != null) {
									if(bitmap != null && !bitmap.isRecycled()){
										imageView.setImageBitmap(bitmap);
									}
								}
								break;
							}
						};
					};
					@Override
					public void run() {
						try {
							//TODO
							//bitmap = BitmapFactory.decodeStream(QUtil.Net_getHttpInputStream(url));
							imageCache.put(url, new SoftReference<Bitmap>(bitmap)); // 加入软引用
							//handler在主线程加载图像
							handler.sendEmptyMessage(0);
						} catch (Exception e) {
							e.printStackTrace();
							handler.sendEmptyMessage(1);
						}
						
					}
				});	
			}
		}
		
	}
	
	static class Holder {
		ImageView image;
	}

}
