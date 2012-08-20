package q.project;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public abstract class QProjectBSimple extends Activity {
	
	protected LinearLayout layout;
	protected Button btn;
	private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10;
	protected Context ctx;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.q_project_simple);
		ctx = this;
		//
		layout = (LinearLayout)findViewById(R.id.layout);
		btn1 = (Button)findViewById(R.id.btn1);
		btn2 = (Button)findViewById(R.id.btn2);
		btn3 = (Button)findViewById(R.id.btn3);
		btn4 = (Button)findViewById(R.id.btn4);
		btn5 = (Button)findViewById(R.id.btn5);
		btn6 = (Button)findViewById(R.id.btn6);
		btn7 = (Button)findViewById(R.id.btn7);
		btn8 = (Button)findViewById(R.id.btn8);
		btn9 = (Button)findViewById(R.id.btn9);
		btn10 = (Button)findViewById(R.id.btn10);
		//
		onInit();
	}
	
	protected abstract void onInit();
	
	private int i = 0;

	protected Button getNextButton(){
		i++;
		switch(i){
		case 1: return btn1;
		case 2: return btn2;
		case 3: return btn3;
		case 4: return btn4;
		case 5: return btn5;
		case 6: return btn6;
		case 7: return btn7;
		case 8: return btn8;
		case 9: return btn9;
		case 10: return btn10;
		}
		return null;
	}
}
