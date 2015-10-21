package wang.interfacedemo;

import java.util.ArrayList;
import java.util.HashMap;

import wang.fragment.Teacher_Fragment1;
import wang.fragment.Teacher_Fragment2;
import wang.fragment.Teacher_Fragment3;
import wang.tools.Test_DataManage;
import wang.tools.Test_User;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class __TeacherSearchResultActivity extends Activity{
	Button reSearch;
	String account="";
	String input;
	Test_DataManage dataManage=new Test_DataManage(this);;
	Test_User u;
	EditText editText;
	FragmentManager fragmentManager;
	Teacher_Fragment1 fragment1;
	public Teacher_Fragment2 fragment2;
	Teacher_Fragment3 fragment3;
	Fragment[] fm_pages=new Fragment[3];
	ArrayList<Test_User> list;
	TextView button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.teachersearchresult_layout);
        
        reSearch=(Button) findViewById(R.id.sousuoId); 
        editText=(EditText) findViewById(R.id.inputId);
        button=(TextView) findViewById(R.id.backButtonId);
        list=new ArrayList<Test_User>();
        TextView titleView=(TextView) findViewById(R.id.titleTextId);
        titleView.setText("搜索结果");
        input=getIntent().getStringExtra("input");
        account=getIntent().getStringExtra("account");
        list=dataManage.getStudentByXuehaoOrNameOrShenfenzheng(dataManage.getUser(account).getSchool(), input);
//        u=dataManage.getStudentByXuehao(dataManage.getUser(account).getSchool(), input);
//        u=dataManage.getStudentByXuehao("西电", input);
        fragment1=new Teacher_Fragment1();
        fragment2=new Teacher_Fragment2();
        fragment3=new Teacher_Fragment3();
        fm_pages[0]=fragment1;
        fm_pages[1]=fragment2;
        fm_pages[2]=fragment3;
        fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        for (int i = 0; i < fm_pages.length; i++) {
        	fragmentTransaction.add(R.id.FragmentHolderId, fm_pages[i],""+i);
        }
        fragmentTransaction.commit();
        if(list.size()==0)
        	showFragment(0);
        else if(list.size()==1)
        	showFragment(1);
        else
        	showFragment(2);
        reSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String input=editText.getText().toString();
				Intent intent=new Intent(__TeacherSearchResultActivity.this,__TeacherSearchResultActivity.class);
				intent.putExtra("account", account);
				intent.putExtra("input", input);
        		startActivity(intent);
        		finish();
			}
		});
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		    	Intent intent=new Intent(__TeacherSearchResultActivity.this,TeacherViewActivity.class);
		    	intent.putExtra("jay", account);
		    	startActivity(intent);
			}
		});
	}
	public void showFragment(int position) {
		// TODO Auto-generated method stub
		FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        for (int i = 0; i < fm_pages.length; i++) {
			if(i==position)
				fragmentTransaction.show(fm_pages[i]);
			else
				fragmentTransaction.hide(fm_pages[i]);
		}
        fragmentTransaction.commit();
	}
	public ArrayList<Test_User> getList(){
		return list;
	}
	public String getSchool(){
		return dataManage.getUser(account).getSchool();
	}
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	Intent intent=new Intent(__TeacherSearchResultActivity.this,TeacherViewActivity.class);
    	intent.putExtra("jay", account);
    	startActivity(intent);
    	finish();
    }
}
