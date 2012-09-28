package q.project.os.app;

import q.project.QProjectItem;
import q.util.os.QNotificationUtil;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class NotificationA extends QProjectItem {
		
	@Override
	protected void onInit() {
		btn = getNextButton();
		btn.setText("show通知");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				QNotificationUtil.show(mCtx, 1, android.R.drawable.stat_sys_warning, "标题", "内容", PendingIntent.getActivity(mCtx, 0, new Intent(), 0));
			}
		});
		//
		btn = getNextButton();
		btn.setText("cancel通知");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				QNotificationUtil.cancel(mCtx, 1);
			}
		});
	}

}
