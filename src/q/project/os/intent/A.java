package q.project.os.intent;

import q.project.QProjectList;

public class A extends QProjectList {

	@Override
	protected void onInit() {
		init("跳转到外部程序", IntentToOutA.class);
		init("从外部程序跳转进来", IntentFromOutA.class);
	}
	
	
}