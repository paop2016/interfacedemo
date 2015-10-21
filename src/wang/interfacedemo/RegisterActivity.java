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
	// ����
	ImageView xidianXuegongImage;
	ImageView xidianClassImage;
	EditText xuehaoEdit;
	EditText nameEdit;
	EditText accountEdit;
	EditText classEdit;
	EditText passwordEdit;
	EditText confirmEdit;
	EditText shenfenzhengEdit;
	// �ƴ�
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
	// ����
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
        //ѧУѡ��Spinner���ü�����
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
				//ѧУSpinnerѡ������
				case 1:
					imageView.setImageResource(R.drawable.login_image);
					displayControl(linearLayout, View.VISIBLE, kedaLayout, View.GONE, jiaodaLayout, View.GONE, null, "", null, 0, "");
					identitySpinnerLoad(listLoad("����"));
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
								displayControl(xidianLayout, View.VISIBLE, xidianClassLayoutlinearLayout,View.VISIBLE,xidianClassLayoutlinearLayout,View.VISIBLE,xuehaoEdit,"����ѧ��(����)",xidianXuegongImage,R.drawable.xuehao,"ѧ��");
								break;
							case 2:
								displayControl(xidianLayout, View.VISIBLE, xidianClassLayoutlinearLayout, View.GONE, xidianClassLayoutlinearLayout, View.GONE, xuehaoEdit,"�������ʦ����(����)", xidianXuegongImage, R.drawable.teacherh, "��ʦ");
								break;
							case 3:
								displayControl(xidianLayout, View.VISIBLE, xidianClassLayoutlinearLayout, View.GONE, xidianClassLayoutlinearLayout, View.GONE, xuehaoEdit, "���������Ա��֤��(����)", xidianXuegongImage, R.drawable.guanlih, "����Ա");
								break;
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
						}
					});
					break;
					//ѧУSpinnerѡ��ƴ�
				case 2:
					imageView.setImageResource(R.drawable.xikeda);
					displayControl(xidianLayout, View.GONE, linearLayout, View.VISIBLE, jiaodaLayout, View.GONE, null, "", null, 0, "");
					identitySpinnerLoad(listLoad("�ƴ�"));
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
								displayControl(kedaLayout, View.VISIBLE, kedaClassLayoutlinearLayout,View.VISIBLE,kedaMonitorLayoutlinearLayout,View.GONE,kedaxuehaoEdit,"����ѧ��(����)",kedaXuegongImage,R.drawable.xuehao,"ѧ��");
								break;
							case 2:
								displayControl(kedaLayout, View.VISIBLE, kedaClassLayoutlinearLayout,View.VISIBLE,kedaMonitorLayoutlinearLayout,View.VISIBLE,kedaxuehaoEdit,"����ѧ��(����)",kedaXuegongImage,R.drawable.xuehao,"�೤");
								break;
							case 3:
								displayControl(kedaLayout, View.VISIBLE, kedaClassLayoutlinearLayout,View.GONE,kedaMonitorLayoutlinearLayout,View.GONE,kedaxuehaoEdit,"�������ʦ����(����)",kedaXuegongImage,R.drawable.teacherh,"��ʦ");
								break;
							case 4:
								displayControl(kedaLayout, View.VISIBLE, kedaClassLayoutlinearLayout,View.GONE,kedaMonitorLayoutlinearLayout,View.GONE,kedaxuehaoEdit,"���������Ա��֤��(������)",kedaXuegongImage,R.drawable.guanlih,"����Ա");
								break;
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							
						}
					});
					break;
					//ѧУSpinnerѡ�񽻴�
				case 3:
					displayControl(xidianLayout, View.GONE, kedaLayout, View.GONE, linearLayout, View.VISIBLE, null, "", null, 0, "");
					identitySpinnerLoad(listLoad("����"));
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
								displayControl(jiaodaLayout, View.VISIBLE, jiaodaClassLayoutlinearLayout,View.VISIBLE,jiaodaClassLayoutlinearLayout,View.VISIBLE,jiaodaxuehaoEdit,"����ѧ��(����)",jiaodaXuegongImage,R.drawable.xuehao,"ѧ��");
								break;
							case 2:
								displayControl(jiaodaLayout, View.VISIBLE, jiaodaClassLayoutlinearLayout, View.GONE, jiaodaClassLayoutlinearLayout, View.GONE, jiaodaxuehaoEdit,"�������ʦ����(����)", jiaodaXuegongImage, R.drawable.teacherh, "��ʦ");
								break;
							case 3:
								displayControl(jiaodaLayout, View.VISIBLE, jiaodaClassLayoutlinearLayout, View.GONE, jiaodaClassLayoutlinearLayout, View.GONE, jiaodaxuehaoEdit, "���������Ա��֤��(����)", jiaodaXuegongImage, R.drawable.guanlih, "����Ա");
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
	//��ʼ��
	private void init() {
		dataManage=new Test_DataManage(this);
        spinner=(Spinner) findViewById(R.id.spinnerId);       
		spinner1=(Spinner) findViewById(R.id.spinner1Id);
        imageView=(ImageView) findViewById(R.id.imageId);
        //����ť���ü�����
        kedabutton=(Button) findViewById(R.id.kedazhucebabuttonId);
        kedabutton.setOnClickListener(new MyButtonListener());
        xidianbutton=(Button) findViewById(R.id.zhucebabuttonId);
        xidianbutton.setOnClickListener(new MyButtonListener());
        jiaodabutton=(Button) findViewById(R.id.jiaodazhucebabuttonId);
        jiaodabutton.setOnClickListener(new MyButtonListener());
        //����
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
    	//����
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
        //�ƴ�
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
        schoolSpinnerLoad();//����ѧУspinner
	}
	//ѧУѡ��Spinner������ 
	private void schoolSpinnerLoad() {
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listLoad("ѧУ"));
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}
	//���ѡ��Spinner������
	private void identitySpinnerLoad(List<String> list) {
		// TODO Auto-generated method stub
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter);
	}
	//����ÿ����ݵ�list
	private List<String> listLoad(String str){
		List<String> list=new ArrayList<String>();
		if(str.equals("����")||str.equals("����")){
			list.add("��ѡ�����");
			list.add("ѧ��");
			list.add("��ʦ");
			list.add("����Ա");
		}
		if(str.equals("�ƴ�")){
			list.add("��ѡ�����");
			list.add("ѧ��");
			list.add("�೤");
			list.add("��ʦ");
			list.add("����Ա");
		}
		if(str.equals("ѧУ")){
	        list.add("��ѡ������ѧУ");
	        list.add("�������ӿƼ���ѧ");
	        list.add("�����Ƽ���ѧ");
	        list.add("������ͨ��ѧ");
		}
		return list;
	}
	//�����˳�
	long time;
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
    	if((System.currentTimeMillis()-time)>2000){
    		Toast.makeText(this,"�ٰ�һ���˳�ע��", Toast.LENGTH_SHORT).show();
    		time=System.currentTimeMillis();
    	}
    	else{
    		Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
    		startActivity(intent); 
    	}
	}
	//����Activity
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}
	//ע�ᰴť ������
	class MyButtonListener implements OnClickListener{
		//ע�������������
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(spinner.getSelectedItemPosition()==1)//����
				register("����",accountEdit,nameEdit,xuehaoEdit,kedamonitorEdit,classEdit,shenfenzhengEdit,passwordEdit,confirmEdit);
			else if(spinner.getSelectedItemPosition()==2)//�ƴ�
				register("�ƴ�",kedaaccountEdit,kedanameEdit,kedaxuehaoEdit,kedamonitorEdit,kedaclassEdit,kedashenfenzhengEdit,kedapasswordEdit,kedaconfirmEdit);
			else if(spinner.getSelectedItemPosition()==3)//����
				register("����",jiaodaaccountEdit,jiaodanameEdit,jiaodaxuehaoEdit,kedamonitorEdit,jiaodaclassEdit,jiaodashenfenzhengEdit,jiaodapasswordEdit,jiaodaconfirmEdit);
			}
	}
	//������ʾ����
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
	//ע���߼�
	private void register(String school,EditText accountEdit,EditText nameEdit,EditText xuehaoEdit,EditText moniterEdit,EditText classEdit,EditText shenfenzhengEdit,EditText passwordEdit,EditText confirmEdit){
		if(regex(accountEdit, accountRegex))
			if(regex(nameEdit, nameRegex))
	    	   if(regex(xuehaoEdit, xuehaoRegex))
	    		   if(!identity.equals("�೤")||regex(moniterEdit, xuehaoRegex))
		    		   if(identity.equals("����Ա")||identity.equals("��ʦ")||regex(classEdit, classRegex))
				    	   if(regex(shenfenzhengEdit, shenfengzhengRegex))
				    		   if(regex(passwordEdit, mimaRegex))
				    		   		if(passwordEdit.getText().toString().equals(confirmEdit.getText().toString())){
				    		   			if(dataManage.getUser(accountEdit.getText().toString())==null){
				    		   			//��ӵ�school��Ӧ�ı�
					    		   			dataManage.registerAdd(school,identity,accountEdit.getText().toString(), passwordEdit.getText().toString(), nameEdit.getText().toString(),
					    		   					shenfenzhengEdit.getText().toString(), xuehaoEdit.getText().toString(),xuehaoEdit.getText().toString(),
					    		   					xuehaoEdit.getText().toString(), moniterEdit.getText().toString(),classEdit.getText().toString());
											Intent intent1=new Intent(RegisterActivity.this,MainActivity.class);
								    		Toast.makeText(RegisterActivity.this, "�ύ�ɹ����ȴ�����Ա���", Toast.LENGTH_SHORT).show();
								    		startActivity(intent1);
				    		   			}
				    		   			else{
				    		   				focusAndToast(accountEdit, "�˺��ѱ�ע��");
				    		   			}
				    		   		}
				    		   		else{
							    		   focusAndToast(confirmEdit, "�������벻һ��");
						    		   }
				    		   else{
					    		   focusAndToast(passwordEdit, "���벻���Ϲ���");
				    		   }
				    	   else{
				    		   focusAndToast(shenfenzhengEdit, "���֤�Ų����Ϲ���");
				    	   }
		    		   else{
			    		   focusAndToast(classEdit, "�༶�����Ϲ���");
		    		   }
	    		   else{
		    		   focusAndToast(moniterEdit, "�೤��֤�Ų����Ϲ���");
	    		   }
	    	   else{
	    		   if(identity.equals("ѧ��")||identity.equals("�೤"))
	    			   Toast.makeText(RegisterActivity.this,"ѧ�Ų����Ϲ���",Toast.LENGTH_SHORT).show();
	    		   else if(identity.equals("��ʦ"))
	    			   Toast.makeText(RegisterActivity.this,"���Ų����Ϲ���",Toast.LENGTH_SHORT).show();
	    		   else if(identity.equals("����Ա"))
	    			   Toast.makeText(RegisterActivity.this,"����Ա��֤�Ų����Ϲ���",Toast.LENGTH_SHORT).show();
	    	   }
	       else{
			   focusAndToast(nameEdit, "���������Ϲ���");
	       }
	   else{
		   focusAndToast(accountEdit, "�˺Ų����Ϲ���");
	   }
	}
	//������ʽ�ж�
	private boolean regex(EditText editText,String str){
		return editText.getText().toString().matches(str);
	}
	//���ý��㲢Toast
	private void focusAndToast(EditText editText,String str){
		editText.requestFocus();
		Toast.makeText(RegisterActivity.this,str,Toast.LENGTH_SHORT).show();
	}
} 

	