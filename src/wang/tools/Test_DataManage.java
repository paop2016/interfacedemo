package wang.tools;

import java.util.ArrayList;
import java.util.Iterator;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.widget.Toast;

public class Test_DataManage{
	MyDatabaseHelper dbHelper;
	Context context;
	public Test_DataManage(Context context){
		this.context=context;
		dbHelper=new MyDatabaseHelper(context, "Data",null,33);
	}
	public void create()
	{
		SQLiteDatabase db=dbHelper.getWritableDatabase();
	}
	//��
	//ע�� ���Ӵ����û� 
	public void registerAdd(String school,String identity,String account, String password, String name,
			 String shenfenzheng, String xuehao,String gonghao, String managerId, String moniterId, String banji){
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put("status","wait");cv.put("identity",identity);cv.put("account",account);cv.put("password", password);
		cv.put("name",name);cv.put("shenfenzheng",shenfenzheng);cv.put("class",banji);
		if(identity.equals("ѧ��"))
			cv.put("xuehao",xuehao);
		else if(identity.equals("�೤")){
			cv.put("xuehao",xuehao);
			cv.put("moniterId", moniterId);
		}
		else if(identity.equals("��ʦ"))
			cv.put("gonghao",gonghao);
		else if(identity.equals("����Ա"))
			cv.put("managerId",managerId);
		if(school.equals("����")){
			db.insert("XidianData", null,cv);
		}
		else if(school.equals("�ƴ�")){
			db.insert("KedaData", null,cv);
		}
		else if(school.equals("����")){
			db.insert("JiaodaData", null,cv);
		}
	}
	//�ܹ��� �Ȳ��ù���Ա��֤  һ���ǵ�һ������Ա
	public void vip(String school,String account, String password) {
		// TODO Auto-generated method stub
		if(getUser(account)==null){
			SQLiteDatabase db=dbHelper.getWritableDatabase();
			ContentValues cv=new ContentValues();
			cv.put("name", "vip");
			cv.put("account", account);
			cv.put("password", password);
			cv.put("status", "pass");
			cv.put("identity", "����Ա");
			if(school.equals("����")){
				db.insert("XidianData", null,cv);
			}
			else if(school.equals("�ƴ�")){
				db.insert("KedaData", null,cv);
			}
			else if(school.equals("����")){
				db.insert("JiaodaData", null,cv);
			}
		}
	}
	//ɾ 
	//����Ա ɾ�����ܾ��û�
	public void delate(Test_User u){
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		if(u.getSchool().equals("����"))
			db.delete("XidianData","account=?",new String[]{u.getAccount()});
		if(u.getSchool().equals("�ƴ�"))
			db.delete("KedaData","account=?",new String[]{u.getAccount()});
		if(u.getSchool().equals("����"))
			db.delete("JiaodaData","account=?",new String[]{u.getAccount()});
	}
	//����Ա ɾ��ȫ����¼
	public void delateAll(String school){
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		if(school.equals("����"))
			db.delete("XidianData","status=?",new String[]{"refuse"});
		if(school.equals("�ƴ�"))
			db.delete("KedaData","status=?",new String[]{"refuse"});
		if(school.equals("����"))
			db.delete("JiaodaData","status=?",new String[]{"refuse"});
	}
	
    //��
	//����Ա��˻�ɾ���û��󣬽����״̬status��Ϊpass,��refuse
	public void managerOperate(Test_User u,String newStatus){
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put("status",newStatus);
		if(u.getSchool().equals("����"))
			db.update("XidianData",cv,"account=?",new String[]{u.getAccount()});
		if(u.getSchool().equals("�ƴ�"))
			db.update("KedaData",cv, "account=?",new String[]{u.getAccount()});
		if(u.getSchool().equals("����"))
			db.update("JiaodaData",cv,"account=?",new String[]{u.getAccount()});
	}
	//�û��޸�����
	public boolean passwordChange(String account,String oldPassword,String newPassword){
		ArrayList<Test_User> list=findBySchoolIdentityAndStatus("ȫ��", "ȫ��", "ȫ��");
		Iterator<Test_User> it=list.iterator();
		while(it.hasNext()){
			Test_User u=it.next();
			if(u.getAccount().equals(account)&&u.getPassword().equals(oldPassword)){
				SQLiteDatabase db=dbHelper.getWritableDatabase();
				ContentValues cv=new ContentValues();
				cv.put("password",newPassword);
				if(u.getSchool().equals("����")){
					db.update("XidianData",cv, "account=?",new String[]{u.getAccount()});}
				else if(u.getSchool().equals("�ƴ�"))
					db.update("KedaData",cv,"account=?",new String[]{u.getAccount()});
				else if(u.getSchool().equals("����"))
					db.update("JiaodaData",cv,"account=?",new String[]{u.getAccount()});
				Toast.makeText(context,"�޸ĳɹ�",2000).show();
				return true;
			}
		}
		Toast.makeText(context,"�˺Ż��������",2000).show();
		return false;
	}
	
	//��
	//���ù��� ��������ѧУ ���  ״̬���û�
	public ArrayList<Test_User> findBySchoolIdentityAndStatus(String school,String identity,String status){
		ArrayList<Test_User> list=new ArrayList<Test_User>();
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		Cursor cursor = null;
		if(identity.equals("ȫ��")){
			list.addAll(findBySchoolIdentityAndStatus(school,"ѧ��",status));
			list.addAll(findBySchoolIdentityAndStatus(school,"�೤",status));
			list.addAll(findBySchoolIdentityAndStatus(school,"��ʦ",status));
			list.addAll(findBySchoolIdentityAndStatus(school,"����Ա",status));
			return list;
		}
		if(status.equals("ȫ��")){
			list.addAll(findBySchoolIdentityAndStatus(school,identity,"wait"));
			list.addAll(findBySchoolIdentityAndStatus(school,identity,"refuse"));
			list.addAll(findBySchoolIdentityAndStatus(school,identity,"pass"));
			return list;
		}
		if(school.equals("ȫ��")){
			list.addAll(findBySchoolIdentityAndStatus("����",identity,status));
			list.addAll(findBySchoolIdentityAndStatus("�ƴ�",identity,status));
			list.addAll(findBySchoolIdentityAndStatus("����",identity,status));
			return list;
		}
			
		if(school.equals("����"))
			cursor=db.query("XidianData",null,"identity=? and status=?",new String[]{identity,status},null,null,null);
		else if(school.equals("�ƴ�"))
			cursor=db.query("KedaData",null,"identity=? and status=?",new String[]{identity,status},null,null,null);
		else if(school.equals("����"))
			cursor=db.query("JiaodaData",null,"identity=? and status=?",new String[]{identity,status},null,null,null);
		if(cursor.moveToFirst()){
			do{
				Test_User u=new Test_User(school,cursor.getString(cursor.getColumnIndex("identity")),cursor.getString(cursor.getColumnIndex("account")),
						cursor.getString(cursor.getColumnIndex("password")),cursor.getString(cursor.getColumnIndex("name")),cursor.getString(cursor.getColumnIndex("shenfenzheng")), 
						cursor.getString(cursor.getColumnIndex("xuehao")),cursor.getString(cursor.getColumnIndex("gonghao")),cursor.getString(cursor.getColumnIndex("managerId")),
						school.equals("�ƴ�")?cursor.getString(cursor.getColumnIndex("moniterId")):"",
						cursor.getString(cursor.getColumnIndex("class")),cursor.getString(cursor.getColumnIndex("status")));
				list.add(u);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return list;
	}
	//��ø�ѧУ����ѧ����Ϣ
	public ArrayList<Test_User> findStudentBySchool(String school){
		ArrayList<Test_User> list=new ArrayList<Test_User>();
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		Cursor cursor = null;
		if(school.equals("ȫ��")){
			list.addAll(findStudentBySchool("����"));
			list.addAll(findStudentBySchool("�ƴ�"));
			list.addAll(findStudentBySchool("����"));
			return list;
		}
		if(school.equals("����"))
			cursor=db.query("XidianStudentData", null, null, null, null, null, null);
		else if(school.equals("�ƴ�"))
			cursor=db.query("KedaStudentData", null, null, null, null, null, null);
		else if(school.equals("����"))
			cursor=db.query("JiaodaStudentData", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Test_User u=new Test_User(cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("xuehao")),
						cursor.getString(cursor.getColumnIndex("class")), cursor.getString(cursor.getColumnIndex("shenfenzheng")),toArray(cursor.getString(cursor.getColumnIndex("done"))), toArray(cursor.getString(cursor.getColumnIndex("fail"))),
						toArray(cursor.getString(cursor.getColumnIndex("certificate"))));
				list.add(u);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return list;
	}
	//����ȡ��ѧ�Ʊ�Ϊlist��ʽ����
	private ArrayList<String> toArray(String string) {
		// TODO Auto-generated method stub
		ArrayList<String> arr=new ArrayList<String>();
		String[] str=string.split(" ");
		for (int i = 0; i < str.length; i++) {
			arr.add(str[i]);
		}
		//���� ����ո�
		if(TextUtils.isEmpty(str[0]))
			arr.remove(0);
		return arr;
	}
	
	//�ṩ�˺� У�� �����û�����
	public Test_User getUser(String account){
		ArrayList<Test_User> list=findBySchoolIdentityAndStatus("ȫ��", "ȫ��", "ȫ��");
		Iterator<Test_User> it=list.iterator();
		while(it.hasNext()){
			Test_User u=it.next();
			if(u.getAccount().equals(account)){
				return u;
			}
		}
		return null;
	}
	//�ṩ�˺� ����ѧ��
	public String getXuehao(String account){
		ArrayList<Test_User> list=findBySchoolIdentityAndStatus("ȫ��", "ѧ��", "pass");
		Iterator<Test_User> it=list.iterator();
		while(it.hasNext()){
			Test_User u=it.next();
			if(u.getAccount().equals(account))
				return u.getXuehao();
		}
		return "";
	}
	//�ṩѧУ andѧ�Ż����֤ ����ѧ���ɼ���Ϣ ���ǵ����������������ظ� ���Է���List����
	public ArrayList<Test_User> getStudentByXuehaoOrNameOrShenfenzheng(String school,String input){
		ArrayList<Test_User> studentList=new ArrayList<Test_User>();
		ArrayList<Test_User> list=findStudentBySchool(school);
		Iterator<Test_User> it=list.iterator();
		while(it.hasNext()){
			Test_User u=it.next();
			if(u.getXuehao().equals(input))
				studentList.add(u);
			else if(u.getName().equals(input))
				studentList.add(u);
			else if(u.getShenfenzheng().equals(input))
				studentList.add(u);
		}
		return studentList;
	}
	public Test_User getStudentByXuehao(String school,String xuehao){
		ArrayList<Test_User> list=findStudentBySchool(school);
		Iterator<Test_User> it=list.iterator();
		while(it.hasNext()){
			Test_User u=it.next();
			if(u.getXuehao().equals(xuehao))
				return u;
		}
		return null;
	}
	//�ṩѧУ �������а༶
	public ArrayList<String> getBanjiList(String school){
		ArrayList<Test_User> studentList=findStudentBySchool(school);
		ArrayList<String> list=new ArrayList<String>();
		Iterator<Test_User> it=studentList.iterator();
		while(it.hasNext()){
			Test_User u=it.next();
			String banji=u.getBanji();
			if(!list.contains(banji))
				list.add(banji);
		}
		return list;
	}
	//�ṩѧУ�Ͱ༶ ����ѧ���б�
	public ArrayList<Test_User> findStudentBySchoolAndBanji(String school,String banji){
		ArrayList<Test_User> list=findStudentBySchool(school);
		ArrayList<Test_User> studentList=new ArrayList<Test_User>();
		Iterator<Test_User> it=list.iterator();
		while(it.hasNext()){
			Test_User u=it.next();
			if(u.getBanji().equals(banji))
				studentList.add(u);
		}
		return studentList;
	}
	//�ṩ�˺�����  У��  �����Ƿ��и��û� ���ڵ������
	public Test_User accountPasswordCheck(String account,String password){
		ArrayList<Test_User> list=findBySchoolIdentityAndStatus("ȫ��", "ȫ��", "pass");
		Iterator<Test_User> it=list.iterator();
		while(it.hasNext()){
			Test_User u=it.next();
			if(u.getAccount().equals(account)&&u.getPassword().equals(password)){
				return u;
			}
		}
		return null;
	}
	//�ṩѧУ��ѧ��  ���ز���ʱ��
	public String getTime(String school,String subject){
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		Cursor cursor = null;
		String time = null;
		if(school.equals("����"))
			cursor=db.query("XidianClassData", null, "subject=?", new String[]{subject}, null, null, null);
		else if(school.equals("�ƴ�"))
			cursor=db.query("KedaClassData", null, "subject=?", new String[]{subject}, null, null, null);
		else if(school.equals("����"))
			cursor=db.query("JiaodaClassData", null, "subject=?", new String[]{subject}, null, null, null);
		if(cursor.moveToFirst())
			time=cursor.getString(cursor.getColumnIndex("time"));
		return time;
	}
	//�ṩѧУ��ѧ��  ����ѧ��
	public String getCredit(String school,String subject){
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		Cursor cursor = null;
		String credit = null;
		if(school.equals("����"))
			cursor=db.query("XidianClassData", null, "subject=?", new String[]{subject}, null, null, null);
		else if(school.equals("�ƴ�"))
			cursor=db.query("KedaClassData", null, "subject=?", new String[]{subject}, null, null, null);
		else if(school.equals("����"))
			cursor=db.query("JiaodaClassData", null, "subject=?", new String[]{subject}, null, null, null);
		if(cursor.moveToFirst())
			credit=cursor.getString(cursor.getColumnIndex("credit"));
		return credit;
	}

}
