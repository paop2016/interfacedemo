package wang.interfacedemo;

import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import wang.tools.Test_DataManage;
import wang.tools.Test_User;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class __TeacherSeeAllStudentActivity extends Activity{
	Spinner spinner;
	ListView listView;
	Test_DataManage dataManage;
	TextView button;
	String account;
	String school;
	ArrayList<String> banjiList;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.teacherseeall_layout);
		
		spinner=(Spinner) findViewById(R.id.seeSpinnerId);
		listView=(ListView) findViewById(R.id.seeListViewId);
		button=(TextView) findViewById(R.id.backButtonId);
        TextView titleView=(TextView) findViewById(R.id.titleTextId);
        titleView.setText("可爱的学生们");
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		    	Intent intent=new Intent(__TeacherSeeAllStudentActivity.this,TeacherViewActivity.class);
		    	intent.putExtra("jay", account);
		    	startActivity(intent);
			}
		});
        dataManage=new Test_DataManage(this);
        account=getIntent().getStringExtra("account");
        school=getIntent().getStringExtra("teacherSchool");
        if(school==null)
        	school=dataManage.getUser(account).getSchool();
		final List<String> list1=new ArrayList<String>();
		list1.add("全部学生");
		banjiList=dataManage.getBanjiList(school);
		list1.addAll(banjiList);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list1);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if(arg2==0)
					listViewLoad(dataManage.findStudentBySchool(school));
				else
					listViewLoad(dataManage.findStudentBySchoolAndBanji(school, list1.get(arg2)));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	protected void listViewLoad(final ArrayList<Test_User> dataList) {
		// TODO Auto-generated method stub
		ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Iterator<Test_User> it=dataList.iterator();
		while(it.hasNext()){
			Test_User u=it.next();
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("Name", u.getName());
			map.put("Xuehao", u.getXuehao());
			map.put("Banji", u.getBanji());
			map.put("Done", u.getDoneSubject().size());
			map.put("Fail", u.getFailSubject().size());
			map.put("Certificate", u.getCertificate().size());
			list.add(map);
		}
		SimpleAdapter adapter=new SimpleAdapter(this, list, R.layout.teacherseeall_item_layout, new String[]{"Name","Xuehao","Banji","Done","Fail","Certificate"}, 
				new int[]{R.id.nameId,R.id.xuehaoId,R.id.banjiId,R.id.donesubject1Id,R.id.failsubject1Id,R.id.certificate1Id});
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Test_User u=dataList.get(position);
				StudentViewActivity studentViewActivity=new StudentViewActivity();
				studentViewActivity.start(__TeacherSeeAllStudentActivity.this, null, school, u.getXuehao(),3);
			}
		});
	}
//    @Override
//    protected void onStop() {
//    	// TODO Auto-generated method stub
//    	super.onStop();
//    	finish();
//    }
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	Intent intent=new Intent(__TeacherSeeAllStudentActivity.this,TeacherViewActivity.class);
    	intent.putExtra("jay", account);
    	startActivity(intent);
    	finish();
    }
}
