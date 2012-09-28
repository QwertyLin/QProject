package q.project.openapi;

import q.project.QProjectItem;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BaiduLocationA extends QProjectItem {

	BaiduLocationUtil location;
	
	@Override
	protected void onInit() {
		//
		btn = getNextButton();
		btn.setText("启动定位");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				location = new BaiduLocationUtil(mCtx, new BaiduLocationUtil.Callback() {
					@Override
					public void onFinish(double latitude, double longitude, String address) {
						Toast.makeText(mCtx, 
								"latitude=" + latitude + " longitude=" + longitude + " address=" + address, Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}
	
	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = this;
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout);
		
		//
		btn = new Button(this);
		btn.setText("刷新定位");
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(location != null){
					location.refresh();
				}
			}
		});
		layout.addView(btn);
	}*/
	
}

/**
 * 百度定位API
 * http://dev.baidu.com/wiki/geolocation/
 * 
 * 资源:
/libs/armeabi/liblocSDK.so
/libs/locSDK.jar
 * 
 * 权限：
<permission android:name="android.permission.BAIDU_LOCATION_SERVICE"></permission>
<uses-permission android:name="android.permission.BAIDU_LOCATION_SERVICE"></uses-permission>
 *
 * Service:
<service android:name="com.baidu.location.f" android:enabled="true"
	android:process=":remote" android:permission="android.permission.BAIDU_LOCATION_SERVICE">
	<intent-filter>
		<action android:name="com.baidu.location.service_v2.3"></action>
	</intent-filter>
</service>
 */
class BaiduLocationUtil {
	
	private LocationClient mLocationClient; //静态的
	private Callback callback;
	
	public interface Callback{
		void onFinish(double latitude, double longitude, String address);
	}
	
	public BaiduLocationUtil(Context ctx, Callback callback){
		this.callback = callback;
		//初始化
		if(mLocationClient == null){
			mLocationClient = new LocationClient(ctx);
			mLocationClient.registerLocationListener(new MyLocationListenner());
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);				//打开gps
			option.setCoorType("gcj02"); //设置坐标类型，gcj02表国测局经纬度坐标系，bd09ll表百度经纬度坐标系
			option.setPriority(LocationClientOption.NetWorkFirst); ////设置网络优先
			//option.setScanSpan(2000);	//单位毫秒，设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
			mLocationClient.setLocOption(option);
		}
		mLocationClient.start();//启动service
	}
	
	public void refresh(){
		if(!mLocationClient.isStarted()){
			mLocationClient.start();
		}else{
			mLocationClient.requestLocation();
		}
	}
	
	public void stop(){
		if (mLocationClient != null && mLocationClient.isStarted()){
    		mLocationClient.stop();
    		mLocationClient = null;
    	}
	}
	
	/**
	 * 监听函数，有新位置的时候
	 */
	private class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return ;
			/*if (location.getLocType() == BDLocation.TypeGpsLocation){
				System.out.println(location.getSpeed());
				System.out.println(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				System.out.println(location.getAddrStr());
			}*/
			//
			//callback.onFinish(location.getLatitude(), location.getLongitude(), location.getAddrStr());
			int code = location.getLocType();
			if(code == BDLocation.TypeGpsLocation || code == BDLocation.TypeNetWorkLocation){//成功
				callback.onFinish(location.getLatitude(), location.getLongitude(), location.getAddrStr());
			}else if(code == 65){//缓存
				mLocationClient.requestLocation(); //开启完立刻获取位置
			}
		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
			// TODO Auto-generated method stub
			
		}
	}
}
