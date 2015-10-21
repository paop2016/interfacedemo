package wang.interfacedemo;

import java.util.ArrayList;
import java.util.Iterator;

import wang.tools.Test_DataManage;
import wang.tools.Test_User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class __DoneDetailsActivity extends Activity{
	Test_DataManage dataManage;
	Test_User u;
	String account;
	String xuehao;
	String school;
	TextView certificateText;
	StringBuffer sb;
	ArrayList<String> arr;
	int flag;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.done_details_layout);
        
        TextView titleView=(TextView) findViewById(R.id.titleTextId);
        TextView button=(TextView) findViewById(R.id.backButtonId);
        titleView.setText("已完成学科");
        
        dataManage=new Test_DataManage(this);
        account=getIntent().getStringExtra("jay");
        xuehao=getIntent().getStringExtra("xuehao");
        school=getIntent().getStringExtra("school");
        flag=getIntent().getIntExtra("flag", 4);
        if(account!=null)
        	u=dataManage.getStudentByXuehao(dataManage.getUser(account).getSchool(), dataManage.getXuehao(account));
        else if(xuehao!=null&&school!=null)
        	u=dataManage.getStudentByXuehao(school, xuehao);
        sb=new StringBuffer();
        arr=u.getDoneSubject();
        certificateText=(TextView) findViewById(R.id.done_details_2Id);
        Iterator<String> it=arr.iterator();
        while(it.hasNext()){
        	sb.append(it.next());
        	sb.append(" ");
        }
        certificateText.setText(sb.toString());
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
					
			}
		});
        
	}
}
