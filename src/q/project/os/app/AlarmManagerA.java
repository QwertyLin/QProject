package q.project.os.app;

import java.util.Date;

import q.project.QProjectActivity;
import q.project.QProjectItem;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class AlarmManagerA extends QProjectItem {

	@Override
	protected void onInit() {
		btn = getNextButton();
		btn.setText("定时打开Intent");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PendingIntent pi = PendingIntent.getActivity(mCtx, 0, new Intent(mCtx, QProjectActivity.class), 0);
				AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
				am.set(AlarmManager.RTC_WAKEUP, new Date().getTime() + 3000, pi);
			}
		});
	}
	
	
}
