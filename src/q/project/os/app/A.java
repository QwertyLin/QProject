package q.project.os.app;

import q.project.QProjectList;

public class A extends QProjectList {

	@Override
	protected void onInit() {
		init("Activity", ActivityA.class);
		init("ActivityGroup", ActivityGroupA.class);
		init("AlarmManager 定时器", AlarmManagerA.class);
    	init("Animation 动画", AnimationA.class);
    	init("Camera 拍照", CameraA.class);
    	init("ConnectivityManager 网络管理", ConnectivityManagerA.class);
    	init("Handler 消息任务", HandlerA.class);
    	init("MediaRecordA", MediaRecordA.class);
    	init("Notification 通知", NotificationA.class);
    	init("PackageManager 包管理", ManagerA.class);
    	init("Service 后台服务", ServiceA.class);
	}
	
	
}