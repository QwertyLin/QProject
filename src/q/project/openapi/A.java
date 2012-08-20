package q.project.openapi;

import q.project.QProjectList;

public class A extends QProjectList {
	
	@Override
	protected void onInit() {
		init("百度定位", BaiduLocationA.class);
		/*init("OAuth2.0 QQ微博", Oauth2A.class, QOauth2.EXTRA_TYPE, QOauth2.TYPE_QQ_WEIBO);
		init("OAuth2.0 QZone", Oauth2A.class, QOauth2.EXTRA_TYPE, QOauth2.TYPE_QZONE);
		init("OAuth2.0 人人网", Oauth2A.class, QOauth2.EXTRA_TYPE, QOauth2.TYPE_RENREN);
		init("OAuth2.0 新浪微博", Oauth2A.class, QOauth2.EXTRA_TYPE, QOauth2.TYPE_SINA_WEIBO);*/
	}
	
}