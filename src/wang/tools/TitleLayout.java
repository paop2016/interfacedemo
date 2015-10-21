package wang.tools;

import wang.interfacedemo.MainActivity;
import wang.interfacedemo.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleLayout extends RelativeLayout{

	public TitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.title_layout,this);
//		TextView backText=(TextView) findViewById(R.id.backButtonId);
		TextView switchText=(TextView) findViewById(R.id.switchButtonId);
		
//		backText.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				((Activity)getContext()).finish();
//			}
//		});
		switchText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder aBuilder=new Builder(getContext());
				aBuilder.setMessage("确定注销登入吗");
				aBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent=new Intent(getContext(),MainActivity.class);
						getContext().startActivity(intent);
						((Activity)getContext()).finish();
					}
				});
				aBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
					
				});
				aBuilder.show();
			}
		});
		
	}
	
}
