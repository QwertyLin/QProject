package q.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QProjectActivity2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		try {
			dos();
		} catch (PatternSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	void dos() throws PatternSyntaxException, NumberFormatException, IOException{
		//File file = new File("E:\\test.srt");
    	//System.out.println(file.exists());
    	
    	
    	
    	//BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));
    	BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("srt.srt"), "gbk"));
    	
    	String str = null;
    	StringBuffer text = null;
    	Caption c = null;
    	final List<Caption> list = new ArrayList<Caption>();
    	while( (str = br.readLine()) != null){
    		//System.out.println(str);
    		if(c == null){ //先获得时间轴
    			Pattern p = Pattern.compile("([0-9]{2}):([0-9]{2}):([0-9]{2}),([0-9])00 --> ([0-9]{2}):([0-9]{2}):([0-9]{2}),([0-9])00");
        		Matcher m = p.matcher(str);
        		if(m.find()){
        			//System.out.println(m.group(1));
        			c = new Caption();
        			c.timeStart = Integer.parseInt(m.group(1)) * 60 * 60 * 10 
        							+ Integer.parseInt(m.group(2)) * 60 * 10
        							+ Integer.parseInt(m.group(3)) * 10
        							+ Integer.parseInt(m.group(4));
        			c.timeEnd = Integer.parseInt(m.group(5)) * 60 * 60 * 10 
									+ Integer.parseInt(m.group(6)) * 60 * 10
									+ Integer.parseInt(m.group(7)) * 10
									+ Integer.parseInt(m.group(8));
        			text = new StringBuffer();
        			continue;
        		}
    		}else{ //再获得字幕
    			if(str.equals("")){ //结束
    				c.text = text.toString();
    				list.add(c);
        			c = null;
        		}else{ //继续
        			text.append(str);
        		}
    		}
    	}
    	new Thread(){
    		public void run() {
    			while(true){
    				try {
    					sleep(100);
						time++;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    		};
    	}.start();
    	new Thread(){
    		public void run() {
    			int state = 0;
    			for(int i = 0, size = list.size(); i < size; i++ ){
    				final Caption cp = list.get(i);
    				if(time > cp.timeEnd){
    					state = 1;
    					continue;
    				}else if(time < cp.timeStart){
    					state = 2;
    					i -= 2;
    					continue;
    				}else if(time >= cp.timeStart && time <= cp.timeEnd){
    					if(state != 3){ 
    						state = 3;
    						runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									System.out.println(cp.timeStart + " " + cp.timeEnd + " " + cp.text);
									Toast.makeText(getApplicationContext(), cp.timeStart + " " + cp.timeEnd + " " + cp.text, 1000).show();
								}
							});
    						//System.out.println(cp.timeStart + " " + cp.timeEnd + " " + cp.text);
    					}
    					i--;
    					try {
							sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    				}
    			}    			
    			/*while(true){
    				try {
						t.sleep(1000);
						//System.out.println(new Date().getTime());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}*/
    		};
    	}.start();
	}
	
	static long time = 1000;
    
    static class Caption {
    	String text;
    	long timeStart; //单位 100ms
    	long timeEnd;  //单位 100ms
    }
}

