package wang.interfacedemo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import wang.tools.MyDatabaseHelper;
import wang.tools.Test_DataManage;
import wang.tools.Test_User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends Activity{
	Button xidianbutton;
	Button kedabutton;
	Button jiaodabutton;
	Button gongdabutton;
	Spinner spinner;
	Spinner spinner1;
	ImageView imageView;
	// 西电
	ImageView xidianXuegongImage;
	ImageView xidianClassImage;
	EditText xuehaoEdit;
	EditText nameEdit;
	EditText accountEdit;
	EditText classEdit;
	EditText passwordEdit;
	EditText confirmEdit;
	EditText shenfenzhengEdit;
	// 科大
	ImageView kedaXuegongImage;
	ImageView kedaClassImage;
	ImageView kedaMonitorImage;
	EditText kedamonitorEdit;
	EditText kedaxuehaoEdit;
	EditText kedaclassEdit;
	EditText kedapasswordEdit;
	EditText kedaconfirmEdit;
	EditText kedashenfenzhengEdit;
	EditText kedanameEdit;
	EditText kedaaccountEdit;
	// 交大
	ImageView jiaodaXuegongImage;
	ImageView jiaodaClassImage;
	EditText jiaodaxuehaoEdit;
	EditText jiaodaclassEdit;
	EditText jiaodapasswordEdit;
	EditText jiaodaconfirmEdit;
	EditText jiaodashenfenzhengEdit;
	EditText jiaodanameEdit;
	EditText jiaodaaccountEdit;

	String identity;
	RelativeLayout xidianLayout;
	RelativeLayout kedaLayout;
	RelativeLayout jiaodaLayout;
	LinearLayout linearLayout;
	LinearLayout xidianClassLayoutlinearLayout;
	LinearLayout kedaMonitorLayoutlinearLayout;
	LinearLayout kedaClassLayoutlinearLayout;
	LinearLayout jiaodaClassLayoutlinearLayout;
	MyDatabaseHelper dbHelper;
	String accountRegex = "[a-zA-Z]\\w{5,15}";
	String nameRegex = "[\u4E00-\u9FA5]{2,6}";
	String xuehaoRegex = "[0-9|a-zA-Z]{4,12}";
	String classRegex = "[a-zA-Z|0-9]{4,12}";
	String shenfengzhengRegex = "[1-9][0-9]{16}[0-9|X]";
	String mimaRegex = "\\w{6,12}";
	Test_DataManage dataManage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register_layout);
        
        init();
        //学校选择Spinner设置监听器
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					imageView.setImageResource(R.drawable.main);
					linearLayout.setVisibility(View.GONE);
					displayControl(xidianLayout, View.GONE, kedaLayout, View.GONE, jiaodaLayout, View.GONE, null, "", null, 0, "");
					break;
				//学校Spinner选择西电
				case 1:
					imageView.setImageResource(R.drawable.login_image);
					displayControl(linearLayout, View.VISIBLE, kedaLayout, View.GONE, jiaodaLayout, View.GONE, null, "", null, 0, "");
					identitySpinnerLoad(listLoad("西电"));
					spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub
							switch (arg2) {
							case 0:
								xidianLayout.setVisibility(View.GONE);
								break;
							case 1:
								displayControl(xidianLayout, View.VISIBLE, xidianClassLayoutlinearLayout,View.VISIBLE,xidianClassLayoutlinearLayout,View.VISIBLE,xuehaoEdit,"您的学号(数字)",xidianXuegongImage,R.drawable.xuehao,"学生");
								break;
							case 2:
								displayControl(xidianLayout, View.VISIBLE, xidianClassLayoutlinearLayout, View.GONE, xidianClassLayoutlinearLayout, View.GONE, xuehaoEdit,"请输入教师工号(数字)", xidianXuegongImage, R.drawable.teacherh, "老师");
								break;
							case 3:
								displayControl(xidianLayout, View.VISIBLE, xidianClassLayoutlinearLayout, View.GONE, xidianClassLayoutlinearLayout, View.GONE, xuehaoEdit, "请输入管理员认证码(数字)", xidianXuegongImage, R.drawable.guanlih, "管理员");
								break;
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
						}
					});
					break;
					//学校Spinner选择科大
				case 2:
					imageView.setImageResource(R.drawable.xikeda);
					displayControl(xidianLayout, View.GONE, linearLayout, View.VISIBLE, jiaodaLayout, View.GONE, null, "", null, 0, "");
					identitySpinnerLoad(listLoad("科大"));
					spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub
							switch (arg2) {
							case 0:
								kedaLayout.setVisibility(View.GONE);
								break;
							case 1:
								displayControl(kedaLayout, View.VISIBLE, kedaClassLayoutlinearLayout,View.VISIBLE,kedaMonitorLayoutlinearLayout,View.GONE,kedaxuehaoEdit,"您的学号(数字)",kedaXuegongImage,R.drawable.xuehao,"学生");
								break;
							case 2:
								displayControl(kedaLayout, View.VISIBLE, kedaClassLayoutlinearLayout,View.VISIBLE,kedaMonitorLayoutlinearLayout,View.VISIBLE,kedaxuehaoEdit,"您的学号(数字)",kedaXuegongImage,R.drawable.xuehao,"班长");
								break;
							case 3:
								displayControl(kedaLayout, View.VISIBLE, kedaClassLayoutlinearLayout,View.GONE,kedaMonitorLayoutlinearLayout,View.GONE,kedaxuehaoEdit,"请输入教师工号(数字)",kedaXuegongImage,R.drawable.teacherh,"老师");
								break;
							case 4:
								displayControl(kedaLayout, View.VISIBLE, kedaClassLayoutlinearLayout,View.GONE,kedaMonitorLayoutlinearLayout,View.GONE,kedaxuehaoEdit,"请输入管理员认证码(纯数字)",kedaXuegongImage,R.drawable.guanlih,"管理员");
								break;
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							
						}
					});
					break;
					//学校Spinner选择交大
				case 3:
					displayControl(xidianLayout, View.GONE, kedaLayout, View.GONE, linearLayout, View.VISIBLE, null, "", null, 0, "");
					identitySpinnerLoad(listLoad("交大"));
					spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub
							switch (arg2) {
							case 0:
								jiaodaLayout.setVisibility(View.GONE);
								break;
							case 1:
								displayControl(jiaodaLayout, View.VISIBLE, jiaodaClassLayoutlinearLayout,View.VISIBLE,jiaodaClassLayoutlinearLayout,View.VISIBLE,jiaodaxuehaoEdit,"您的学号(数字)",jiaodaXuegongImage,R.drawable.xuehao,"学生");
								break;
							case 2:
								displayControl(jiaodaLayout, View.VISIBLE, jiaodaClassLayoutlinearLayout, View.GONE, jiaodaClassLayoutlinearLayout, View.GONE, jiaodaxuehaoEdit,"请输入教师工号(数字)", jiaodaXuegongImage, R.drawable.teacherh, "老师");
								break;
							case 3:
								displayControl(jiaodaLayout, View.VISIBLE, jiaodaClassLayoutlinearLayout, View.GONE, jiaodaClassLayoutlinearLayout, View.GONE, jiaodaxuehaoEdit, "请输入管理员认证码(数字)", jiaodaXuegongImage, R.drawable.guanlih, "管理员");
								break;
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							
						}
					});
					break;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	//初始化
	private void init() {
		dataManage=new Test_DataManage(this);
        spinner=(Spinner) findViewById(R.id.spinnerId);       
		spinner1=(Spinner) findViewById(R.id.spinner1Id);
        imageView=(ImageView) findViewById(R.id.imageId);
        //给按钮设置监听器
        kedabutton=(Button) findViewById(R.id.kedazhucebabuttonId);
        kedabutton.setOnClickListener(new MyButtonListener());
        xidianbutton=(Button) findViewById(R.id.zhucebabuttonId);
        xidianbutton.setOnClickListener(new MyButtonListener());
        jiaodabutton=(Button) findViewById(R.id.jiaodazhucebabuttonId);
        jiaodabutton.setOnClickListener(new MyButtonListener());
        //西电
        xidianXuegongImage=(ImageView) findViewById(R.id.xidianXuegongImageId);
        xidianClassImage=(ImageView) findViewById(R.id.xidianClassImageId);
        xuehaoEdit=(EditText) findViewById(R.id.xuehaoId);
        classEdit=(EditText) findViewById(R.id.banjiId);
    	nameEdit=(EditText) findViewById(R.id.xingmingzhuceId);
    	accountEdit=(EditText) findViewById(R.id.zhanghaoId);
    	classEdit=(EditText) findViewById(R.id.banjiId);
    	passwordEdit=(EditText) findViewById(R.id.mimaId);
    	confirmEdit=(EditText) findViewById(R.id.querenmimaId);
    	shenfenzhengEdit=(EditText) findViewById(R.id.shenfenId);
    	//交大
        jiaodaXuegongImage=(ImageView) findViewById(R.id.jiaodaXuegongImageId);
        jiaodaClassImage=(ImageView) findViewById(R.id.jiaodaClassImageId);
        jiaodaxuehaoEdit=(EditText) findViewById(R.id.jiaodaxuehaoId);
        jiaodaclassEdit=(EditText) findViewById(R.id.jiaodabanjiId);
        jiaodanameEdit=(EditText) findViewById(R.id.jiaodaxingmingzhuceId);
    	jiaodaaccountEdit=(EditText) findViewById(R.id.jiaodazhanghaoId);
    	jiaodaclassEdit=(EditText) findViewById(R.id.jiaodabanjiId);
    	jiaodapasswordEdit=(EditText) findViewById(R.id.jiaodamimaId);
    	jiaodaconfirmEdit=(EditText) findViewById(R.id.jiaodaquerenmimaId);
    	jiaodashenfenzhengEdit=(EditText) findViewById(R.id.jiaodashenfenId);
        //科大
        kedaXuegongImage=(ImageView) findViewById(R.id.kedaXuegongImageId);
        kedaClassImage=(ImageView) findViewById(R.id.kedaClassImageId);
        kedaMonitorImage=(ImageView) findViewById(R.id.kedaMonitorImageId);
        kedaxuehaoEdit=(EditText) findViewById(R.id.kedaxuehaoId);
        kedaclassEdit=(EditText) findViewById(R.id.kedabanjiId);
        kedamonitorEdit=(EditText) findViewById(R.id.kedaMonitorId);
        kedanameEdit=(EditText) findViewById(R.id.kedaxingmingzhuceId);
        kedaaccountEdit=(EditText) findViewById(R.id.kedazhanghaoId);
        kedaclassEdit=(EditText) findViewById(R.id.kedabanjiId);
        kedapasswordEdit=(EditText) findViewById(R.id.kedamimaId);
        kedaconfirmEdit=(EditText) findViewById(R.id.kedaquerenmimaId);
        kedashenfenzhengEdit=(EditText) findViewById(R.id.kedashenfenId);
        
        linearLayout=(LinearLayout) findViewById(R.id.quanxianId);
        xidianClassLayoutlinearLayout=(LinearLayout) findViewById(R.id.xidianClassLayoutId);
        kedaClassLayoutlinearLayout=(LinearLayout) findViewById(R.id.kedaClassLayoutId);
        kedaMonitorLayoutlinearLayout=(LinearLayout) findViewById(R.id.kedaMonitorLayoutId);
        jiaodaClassLayoutlinearLayout=(LinearLayout) findViewById(R.id.jiaodaClassLayoutId);
        xidianLayout=(RelativeLayout) findViewById(R.id.xidianlayoutId);
        kedaLayout=(RelativeLayout) findViewById(R.id.kedalayoutId);
        jiaodaLayout=(RelativeLayout) findViewById(R.id.jiaodalayoutId);
        schoolSpinnerLoad();//载入学校spinner
	}
	//学校选择Spinner适配器 
	private void schoolSpinnerLoad() {
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listLoad("学校"));
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}
	//身份选择Spinner适配器
	private void identitySpinnerLoad(List<String> list) {
		// TODO Auto-generated method stub
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter);
	}
	//返回每个身份的list
	private List<String> listLoad(String str){
		List<String> list=new ArrayList<String>();
		if(str.equals("西电")||str.equals("交大")){
			list.add("请选择身份");
			list.add("学生");
			list.add("老师");
			list.add("管理员");
		}
		if(str.equals("科大")){
			list.add("请选择身份");
			list.add("学生");
			list.add("班长");
			list.add("老师");
			list.add("管理员");
		}
		if(str.equals("学校")){
	        list.add("请选择您的学校");
	        list.add("西安电子科技大学");
	        list.add("西安科技大学");
	        list.add("西安交通大学");
		}
		return list;
	}
	//控制退出
	long time;
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
    	if((System.currentTimeMillis()-time)>2000){
    		Toast.makeText(this,"再按一次退出注册", Toast.LENGTH_SHORT).show();
    		time=System.currentTimeMillis();
    	}
    	else{
    		Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
    		startActivity(intent); 
    	}
	}
	//结束Activity
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}
	//注册按钮 监听器
	class MyButtonListener implements OnClickListener{
		//注册键监听器代码
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(spinner.getSelectedItemPosition()==1)//西电
				register("西电",accountEdit,nameEdit,xuehaoEdit,kedamonitorEdit,classEdit,shenfenzhengEdit,passwordEdit,confirmEdit);
			else if(spinner.getSelectedItemPosition()==2)//科大
				register("科大",kedaaccountEdit,kedanameEdit,kedaxuehaoEdit,kedamonitorEdit,kedaclassEdit,kedashenfenzhengEdit,kedapasswordEdit,kedaconfirmEdit);
			else if(spinner.getSelectedItemPosition()==3)//交大
				register("交大",jiaodaaccountEdit,jiaodanameEdit,jiaodaxuehaoEdit,kedamonitorEdit,jiaodaclassEdit,jiaodashenfenzhengEdit,jiaodapasswordEdit,jiaodaconfirmEdit);
			}
	}
	//界面显示控制
	private void displayControl(View view1,int v1,View view2,int v2,View view3,int v3,EditText editText,String str,ImageView imageView,int resource,String identity){ 
		view1.setVisibility(v1);
		view2.setVisibility(v2);
		view3.setVisibility(v3);
		if(editText!=null&&imageView!=null){
			editText.setHint(str);
			imageView.setImageResource(resource);
		}
		this.identity=identity;
	}
	//注册逻辑
	private void register(String school,EditText accountEdit,EditText nameEdit,EditText xuehaoEdit,EditText moniterEdit,EditText classEdit,EditText shenfenzhengEdit,EditText passwordEdit,EditText confirmEdit){
		if(regex(accountEdit, accountRegex))
			if(regex(nameEdit, nameRegex))
	    	   if(regex(xuehaoEdit, xuehaoRegex))
	    		   if(!identity.equals("班长")||regex(moniterEdit, xuehaoRegex))
		    		   if(identity.equals("管理员")||identity.equals("老师")||regex(classEdit, classRegex))
				    	   if(regex(shenfenzhengEdit, shenfengzhengRegex))
				    		   if(regex(passwordEdit, mimaRegex))
				    		   		if(passwordEdit.getText().toString().equals(confirmEdit.getText().toString())){
				    		   			if(dataManage.getUser(accountEdit.getText().toString())==null){
				    		   			//添加到school对应的表
					    		   			dataManage.registerAdd(school,identity,accountEdit.getText().toString(), passwordEdit.getText().toString(), nameEdit.getText().toString(),
					    		   					shenfenzhengEdit.getText().toString(), xuehaoEdit.getText().toString(),xuehaoEdit.getText().toString(),
					    		   					xuehaoEdit.getText().toString(), moniterEdit.getText().toString(),classEdit.getText().toString());
											Intent intent1=new Intent(RegisterActivity.this,MainActivity.class);
								    		Toast.makeText(RegisterActivity.this, "提交成功，等待管理员审核", Toast.LENGTH_SHORT).show();
								    		startActivity(intent1);
				    		   			}
				    		   			else{
				    		   				focusAndToast(accountEdit, "账号已被注册");
				    		   			}
				    		   		}
				    		   		else{
							    		   focusAndToast(confirmEdit, "两次密码不一致");
						    		   }
				    		   else{
					    		   focusAndToast(passwordEdit, "密码不符合规则");
				    		   }
				    	   else{
				    		   focusAndToast(shenfenzhengEdit, "身份证号不符合规则");
				    	   }
		    		   else{
			    		   focusAndToast(classEdit, "班级不符合规则");
		    		   }
	    		   else{
		    		   focusAndToast(moniterEdit, "班长认证号不符合规则");
	    		   }
	    	   else{
	    		   if(identity.equals("学生")||identity.equals("班长"))
	    			   Toast.makeText(RegisterActivity.this,"学号不符合规则",Toast.LENGTH_SHORT).show();
	    		   else if(identity.equals("老师"))
	    			   Toast.makeText(RegisterActivity.this,"工号不符合规则",Toast.LENGTH_SHORT).show();
	    		   else if(identity.equals("管理员"))
	    			   Toast.makeText(RegisterActivity.this,"管理员认证号不符合规则",Toast.LENGTH_SHORT).show();
	    	   }
	       else{
			   focusAndToast(nameEdit, "姓名不符合规则");
	       }
	   else{
		   focusAndToast(accountEdit, "账号不符合规则");
	   }
	}
	//正则表达式判断
	private boolean regex(EditText editText,String str){
		return editText.getText().toString().matches(str);
	}
	//设置焦点并Toast
	private void focusAndToast(EditText editText,String str){
		editText.requestFocus();
		Toast.makeText(RegisterActivity.this,str,Toast.LENGTH_SHORT).show();
	}
} 

	