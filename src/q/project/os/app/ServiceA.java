package q.project.os.app;

import q.project.QProjectItem;
import q.util.a.QLog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

/**
 * http://www.eoeandroid.com/case/2012/0507/1568.html
 *
 */
public class ServiceA extends QProjectItem {
	
	private ServiceC.BinderC binder = null;
	MyServiceConnection conn;
	
	@Override
	protected void onInit() {
		conn = new MyServiceConnection();
		//
		btn = getNextButton();
		btn.setText("bind service");
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bindService(new Intent(ctx, ServiceC.class), conn, Service.BIND_AUTO_CREATE);
			}
		});
		//
		btn = getNextButton();
		btn.setText("handle service");
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (binder != null) {
					Toast.makeText(getApplicationContext(), binder.doSomething()+"", Toast.LENGTH_SHORT).show();
				} 
			}
		});
		//
		btn = getNextButton();
		btn.setText("unbind service");
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				unbindService(conn);//bind了之后一定要unbind
			}
		});
		//
		btn = getNextButton();
		btn.setText("start service");
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startService(new Intent(ctx, ServiceC.class));
			}
		});
		//
		btn = getNextButton();
		btn.setText("stop service");
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				stopService(new Intent(ctx, ServiceC.class));
			}
		});
		
	}
	
	/**
	 * 实现ServiceConnection接口
	 * 
	 */
	class MyServiceConnection implements ServiceConnection {
		/**
		 * 和MyService绑定时系统回调这个方法
		 */
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// MyService中的onBinder()方法的返回值实际上是一个MyBinder对象, 因此可以使用强制转换.
			binder = (ServiceC.BinderC) service;
		}

		/**
		 * 解除和MyService的绑定时系统回调这个方法
		 */
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// 解除和MyService的绑定后, 将binder设置为null.
			binder = null;
		}
	}
}




