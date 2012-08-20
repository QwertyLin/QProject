package q.project.os.app;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.View.OnClickListener;
import q.project.QProjectItem;

public class ConnectivityManagerA extends QProjectItem {

	@Override
	protected void onInit() {
		btn = getNextButton();
		btn.setText("检测网络是否可用");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isAvailable = false;
				ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		        NetworkInfo info = cm.getActiveNetworkInfo();
		        if (info != null) {
		            isAvailable = info.isAvailable();
		        }
		        new AlertDialog.Builder(ctx).setMessage(String.valueOf(isAvailable)).show();
			}
		});
		//
		btn = getNextButton();
		btn.setText("检测wifi是否可用");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isAvailable = false;
				ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo info = cm.getActiveNetworkInfo();//获取网络的连接情况
				if(info != null && info.getType() == ConnectivityManager.TYPE_WIFI){
					isAvailable = true;
		        }
		        new AlertDialog.Builder(ctx).setMessage(String.valueOf(isAvailable)).show();
			}
		});
	}
	
}
