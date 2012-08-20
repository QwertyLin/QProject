package q.util.view.imageView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

/**
 * 异步加载图片的ImageView
 *
 */
public class AsynImageView extends ImageView {
	
	/**
	 * @param context
	 * @param imageFile 图片路径
	 * @param width 宽度
	 */
	public AsynImageView(Context context, String imageFile, int width) {
		super(context);
		loadImageBitmap(imageFile, width);
	}
	
	public void loadImageBitmap(final String path, final int width) {
		new Thread(){
			public void run() {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(path, options);
				int mWidth = options.outWidth;
				int s = mWidth / width;
				if (s == 0) {
					s = 1;
				}
				options = new BitmapFactory.Options();
				options.inSampleSize = s;
				Bitmap bitmap = BitmapFactory.decodeFile(path, options);
				Message msg = Message.obtain();
				msg.obj = bitmap;
				handler.sendMessage(msg);
			}
		}.start();
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			final Bitmap bm = (Bitmap)msg.obj;
			setImageBitmap(bm);
		};
	};
}