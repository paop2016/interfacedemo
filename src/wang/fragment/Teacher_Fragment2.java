package wang.fragment;

import java.util.ArrayList;

import wang.interfacedemo.R;
 import wang.interfacedemo.__CertificateActivity;
import wang.interfacedemo.__DoneDetailsActivity;
import wang.interfacedemo.__FailDetailsActivity;
import wang.interfacedemo.__TeacherSearchResultActivity;
import wang.tools.Test_DataManage;
import wang.tools.Test_User;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Teacher_Fragment2 extends Fragment{
	Button button;
	Button buttonDone;
	Button buttonFail;
	Button buttonCertificate;
	TextView nameText;
	TextView doneText;
	TextView failText;
	TextView certificateText;
	ArrayList<Test_User> list;
	Test_DataManage dataManage;
	Test_User u;
	Intent intent1;
	Intent intent2;
	Intent intent3;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
        View view=inflater.inflate(R.layout.techearsearchresultfragment, null);
        dataManage=new Test_DataManage(getActivity());
        buttonDone=(Button) view.findViewById(R.id.button1Id);
        buttonFail=(Button) view.findViewById(R.id.button2Id);
        buttonCertificate=(Button) view.findViewById(R.id.button3Id);
        nameText=(TextView) view.findViewById(R.id.xingmingId);
        doneText=(TextView) view.findViewById(R.id.doneNumId);
        failText=(TextView) view.findViewById(R.id.failNumId);
        certificateText=(TextView) view.findViewById(R.id.certificateId);
        intent1=new Intent(getActivity(),__CertificateActivity.class);
        intent2=new Intent(getActivity(),__DoneDetailsActivity.class);
        intent3=new Intent(getActivity(),__FailDetailsActivity.class);
        list=((__TeacherSearchResultActivity)getActivity()).getList();
        if(list.size()==1){
        	u=list.get(0);
	    	nameText.setText(u.getName());
	        doneText.setText(u.getDoneSubject().size()+"");
	        failText.setText(u.getFailSubject().size()+"");
	        certificateText.setText(u.getCertificate().size()+"");
	    }
        buttonCertificate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent1.putExtra("school", ((__TeacherSearchResultActivity)getActivity()).getSchool());
				intent1.putExtra("xuehao",u.getXuehao());
				startActivity(intent1);
			}
		});
        buttonDone.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
				intent2.putExtra("school", ((__TeacherSearchResultActivity)getActivity()).getSchool());
				intent2.putExtra("xuehao",u.getXuehao());
        		startActivity(intent2);
        	}
        });
        buttonFail.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
				intent3.putExtra("school", ((__TeacherSearchResultActivity)getActivity()).getSchool());
				intent3.putExtra("xuehao",u.getXuehao());
        		startActivity(intent3);
        	}
        });
        return view;
	}
	public void load(Test_User u){
		this.u=u;
    	nameText.setText(u.getName());
        doneText.setText(u.getDoneSubject().size()+"");
        failText.setText(u.getFailSubject().size()+"");
        certificateText.setText(u.getCertificate().size()+"");
        intent1.putExtra("xuehao",u.getXuehao());
        intent2.putExtra("xuehao",u.getXuehao());
        intent3.putExtra("xuehao",u.getXuehao());
	}
}
