package wang.interfacedemo;

import java.util.ArrayList;

import wang.tools.Test_DataManage;
import wang.tools.Test_User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StudentViewActivity extends Activity{
	Button button;
	Button buttonDone;
	Button buttonFail;
	Button buttonCertificate;
	TextView backButton;
	TextView titleView;
	TextView nameText;
	TextView doneText;
	TextView failText;
	TextView certificateText;
	String account;
	String school;
	String xuehao;
	ArrayList<Test_User> list;
	Test_DataManage dataManage;
	Test_User u;
	int flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.student_layout);
        
        dataManage=new Test_DataManage(this);
        buttonDone=(Button) findViewById(R.id.button1Id);
        buttonFail=(Button) findViewById(R.id.button2Id);
        buttonCertificate=(Button) findViewById(R.id.button3Id);
        backButton=(TextView) findViewById(R.id.backButtonId);
        titleView=(TextView) findViewById(R.id.titleTextId);
        nameText=(TextView) findViewById(R.id.xingmingId);
        doneText=(TextView) findViewById(R.id.doneNumId);
        failText=(TextView) findViewById(R.id.failNumId);
        certificateText=(TextView) findViewById(R.id.certificateId);
        Intent intent=getIntent();
        flag=intent.getIntExtra("flag", 4);
        titleView.setText("学生信息");
        if(flag==1)
        	backButton.setVisibility(View.GONE);
        if(flag==3)
        	backButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(StudentViewActivity.this,__TeacherSeeAllStudentActivity.class);
					intent.putExtra("teacherSchool", school);
					startActivity(intent);
				}
			});
        account=intent.getStringExtra("account");
        school=intent.getStringExtra("school");
        xuehao=intent.getStringExtra("xuehao");
        if(account!=null)
        	u=dataManage.getStudentByXuehao(dataManage.getUser(account).getSchool(), dataManage.getXuehao(account));
        else if(school!=null&&xuehao!=null)
        	u=dataManage.getStudentByXuehao(school, xuehao);
        if(u==null){
        	Toast.makeText(this, "您的信息未录入,请联系管理员", 5000).show();
        	buttonCertificate.setVisibility(View.GONE);
        	buttonDone.setVisibility(View.GONE);
        	buttonFail.setVisibility(View.GONE);}
        else{
        	nameText.setText(u.getName());
	        doneText.setText(u.getDoneSubject().size()+"");
	        failText.setText(u.getFailSubject().size()+"");
	        certificateText.setText(u.getCertificate().size()+"");}
        buttonCertificate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(StudentViewActivity.this,__CertificateActivity.class);
				if(account!=null)
					intent.putExtra("jay",account);
				else {
					intent.putExtra("school", school);
					intent.putExtra("xuehao", xuehao);
				}
				intent.putExtra("flag", flag);
				startActivity(intent);
			}
		});
        buttonDone.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		Intent intent=new Intent(StudentViewActivity.this,__DoneDetailsActivity.class);
				if(account!=null)
					intent.putExtra("jay",account);
				else {
					intent.putExtra("school", school);
					intent.putExtra("xuehao", xuehao);
				}
				intent.putExtra("flag", flag);
        		startActivity(intent);
        	}
        });
        buttonFail.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		Intent intent=new Intent(StudentViewActivity.this,__FailDetailsActivity.class);
				if(account!=null)
					intent.putExtra("jay",account);
				else {
					intent.putExtra("school", school);
					intent.putExtra("xuehao", xuehao);
				}
				intent.putExtra("flag", flag);
        		startActivity(intent);
        	}
        });
	}
	public void start(Context context,String account,String school,String xuehao,int flag){
		Intent intent=new Intent(context,StudentViewActivity.class);
		if(context instanceof MainActivity){
			intent.putExtra("account", account);
		}
		if(context instanceof __TeacherSeeAllStudentActivity){
			intent.putExtra("school", school);
			intent.putExtra("xuehao", xuehao);
		}
//		if(context instanceof MainActivity){
//			intent.putExtra("account", account);
//		}
		intent.putExtra("flag", flag);
		context.startActivity(intent);
	}
	long time;
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(flag==1)
	    	if((System.currentTimeMillis()-time)>2000){
	    		Toast.makeText(this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
	    		time=System.currentTimeMillis();
	    	}
	    	else{
	    		finish();
	    	}
//		if(flag==3){
//			Intent intent=new Intent(StudentViewActivity.this,__TeacherSeeAllStudentActivity.class);
//			intent.putExtra("teacherSchool", school);
//			startActivity(intent);
//			finish();
//		}
		else {
			finish();
		}
	}
}
