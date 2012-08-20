package q.util.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * @author Q
 * 
 */
public final class QPrefs {
	
	private static final String FILE_NAME = "config";
	
	/**
	 * 获得应用版本号
	 * 
	 * @param context
	 * @return
	 */
	public static final int getVersionCode(Context ctx){
		try {
			return ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 获得应用版本名
	 * 
	 * @param context
	 * @return
	 */
	public static final String getVersionName(Context ctx){
		try {
			return ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static final SharedPreferences getSharedPreferences(Context ctx){
		return ctx.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
	}
	
	private static final SharedPreferences.Editor getSharedPreferencesEditor(Context ctx){
		return getSharedPreferences(ctx).edit();
	}
	
	public static final boolean getBoolean(Context ctx, String key){
		return getSharedPreferences(ctx).getBoolean(key, false);
	}
	
	public static final void setBoolean(Context ctx, String key, boolean value) {
		getSharedPreferencesEditor(ctx).putBoolean(key, value).commit();
	}
	
	public static final float getFloat(Context ctx, String key){
		return getSharedPreferences(ctx).getFloat(key, 0f);
	}
	
	public static final void setFloat(Context ctx, String key, float value) {
		getSharedPreferencesEditor(ctx).putFloat(key, value).commit();
	}
	
	public static final int getInt(Context ctx, String key){
		return getSharedPreferences(ctx).getInt(key, 0);
	}
	
	public static final void setInt(Context ctx, String key, int value) {
		getSharedPreferencesEditor(ctx).putInt(key, value).commit();
	}
	
	public static final long getLong(Context ctx, String key){
		return getSharedPreferences(ctx).getLong(key, 0L);
	}
	
	public static final void setLong(Context ctx, String key, long value) {
		getSharedPreferencesEditor(ctx).putLong(key, value).commit();
	}
	
	public static final String getString(Context ctx, String key){
		return getSharedPreferences(ctx).getString(key, null);
	}
	
	public static final void setString(Context ctx, String key, String value) {
		getSharedPreferencesEditor(ctx).putString(key, value).commit();
	}
		
}
