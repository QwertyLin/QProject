package q.project.os.app;

import q.util.a.QLog;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class ServiceC extends Service {
		
	/**
	 * MyBinder是Binder的子类, 而Binder实现了IBinder接口.
	 */
	@Override
	public IBinder onBind(Intent intent) {
		QLog.log("onBind");
		return new BinderC();
	}
	
	@Override
	public boolean onUnbind(Intent arg0) {
		QLog.log("onUnbind");
		return super.onUnbind(arg0);
	}
	
	@Override
	public void onCreate() {
		QLog.log("onCreate");
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		QLog.log("onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		QLog.log("onDestroy");
		super.onDestroy();
	}
	
	public class BinderC extends Binder {
		/**
		 * 获取Service的运行时间
		 * @return
		 */
		public long doSomething() {
			return System.currentTimeMillis();
		}
	}
}
