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
	//增
	//注册 增加待审用户 
	public void registerAdd(String school,String identity,String account, String password, String name,
			 String shenfenzheng, String xuehao,String gonghao, String managerId, String moniterId, String banji){
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put("status","wait");cv.put("identity",identity);cv.put("account",account);cv.put("password", password);
		cv.put("name",name);cv.put("shenfenzheng",shenfenzheng);cv.put("class",banji);
		if(identity.equals("学生"))
			cv.put("xuehao",xuehao);
		else if(identity.equals("班长")){
			cv.put("xuehao",xuehao);
			cv.put("moniterId", moniterId);
		}
		else if(identity.equals("老师"))
			cv.put("gonghao",gonghao);
		else if(identity.equals("管理员"))
			cv.put("managerId",managerId);
		if(school.equals("西电")){
			db.insert("XidianData", null,cv);
		}
		else if(school.equals("科大")){
			db.insert("KedaData", null,cv);
		}
		else if(school.equals("交大")){
			db.insert("JiaodaData", null,cv);
		}
	}
	//总管理 既不用管理员认证  一般是第一个管理员
	public void vip(String school,String account, String password) {
		// TODO Auto-generated method stub
		if(getUser(account)==null){
			SQLiteDatabase db=dbHelper.getWritableDatabase();
			ContentValues cv=new ContentValues();
			cv.put("name", "vip");
			cv.put("account", account);
			cv.put("password", password);
			cv.put("status", "pass");
			cv.put("identity", "管理员");
			if(school.equals("西电")){
				db.insert("XidianData", null,cv);
			}
			else if(school.equals("科大")){
				db.insert("KedaData", null,cv);
			}
			else if(school.equals("交大")){
				db.insert("JiaodaData", null,cv);
			}
		}
	}
	//删 
	//管理员 删除被拒绝用户
	public void delate(Test_User u){
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		if(u.getSchool().equals("西电"))
			db.delete("XidianData","account=?",new String[]{u.getAccount()});
		if(u.getSchool().equals("科大"))
			db.delete("KedaData","account=?",new String[]{u.getAccount()});
		if(u.getSchool().equals("交大"))
			db.delete("JiaodaData","account=?",new String[]{u.getAccount()});
	}
	//管理员 删除全部记录
	public void delateAll(String school){
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		if(school.equals("西电"))
			db.delete("XidianData","status=?",new String[]{"refuse"});
		if(school.equals("科大"))
			db.delete("KedaData","status=?",new String[]{"refuse"});
		if(school.equals("交大"))
			db.delete("JiaodaData","status=?",new String[]{"refuse"});
	}
	
    //改
	//管理员审核或删除用户后，将审核状态status改为pass,或refuse
	public void managerOperate(Test_User u,String newStatus){
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put("status",newStatus);
		if(u.getSchool().equals("西电"))
			db.update("XidianData",cv,"account=?",new String[]{u.getAccount()});
		if(u.getSchool().equals("科大"))
			db.update("KedaData",cv, "account=?",new String[]{u.getAccount()});
		if(u.getSchool().equals("交大"))
			db.update("JiaodaData",cv,"account=?",new String[]{u.getAccount()});
	}
	//用户修改密码
	public boolean passwordChange(String account,String oldPassword,String newPassword){
		ArrayList<Test_User> list=findBySchoolIdentityAndStatus("全部", "全部", "全部");
		Iterator<Test_User> it=list.iterator();
		while(it.hasNext()){
			Test_User u=it.next();
			if(u.getAccount().equals(account)&&u.getPassword().equals(oldPassword)){
				SQLiteDatabase db=dbHelper.getWritableDatabase();
				ContentValues cv=new ContentValues();
				cv.put("password",newPassword);
				if(u.getSchool().equals("西电")){
					db.update("XidianData",cv, "account=?",new String[]{u.getAccount()});}
				else if(u.getSchool().equals("科大"))
					db.update("KedaData",cv,"account=?",new String[]{u.getAccount()});
				else if(u.getSchool().equals("交大"))
					db.update("JiaodaData",cv,"account=?",new String[]{u.getAccount()});
				Toast.makeText(context,"修改成功",2000).show();
				return true;
			}
		}
		Toast.makeText(context,"账号或密码错误",2000).show();
		return false;
	}
	
	//查
	//万用工具 返回任意学校 身份  状态的用户
	public ArrayList<Test_User> findBySchoolIdentityAndStatus(String school,String identity,String status){
		ArrayList<Test_User> list=new ArrayList<Test_User>();
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		Cursor cursor = null;
		if(identity.equals("全部")){
			list.addAll(findBySchoolIdentityAndStatus(school,"学生",status));
			list.addAll(findBySchoolIdentityAndStatus(school,"班长",status));
			list.addAll(findBySchoolIdentityAndStatus(school,"老师",status));
			list.addAll(findBySchoolIdentityAndStatus(school,"管理员",status));
			return list;
		}
		if(status.equals("全部")){
			list.addAll(findBySchoolIdentityAndStatus(school,identity,"wait"));
			list.addAll(findBySchoolIdentityAndStatus(school,identity,"refuse"));
			list.addAll(findBySchoolIdentityAndStatus(school,identity,"pass"));
			return list;
		}
		if(school.equals("全部")){
			list.addAll(findBySchoolIdentityAndStatus("西电",identity,status));
			list.addAll(findBySchoolIdentityAndStatus("科大",identity,status));
			list.addAll(findBySchoolIdentityAndStatus("交大",identity,status));
			return list;
		}
			
		if(school.equals("西电"))
			cursor=db.query("XidianData",null,"identity=? and status=?",new String[]{identity,status},null,null,null);
		else if(school.equals("科大"))
			cursor=db.query("KedaData",null,"identity=? and status=?",new String[]{identity,status},null,null,null);
		else if(school.equals("交大"))
			cursor=db.query("JiaodaData",null,"identity=? and status=?",new String[]{identity,status},null,null,null);
		if(cursor.moveToFirst()){
			do{
				Test_User u=new Test_User(school,cursor.getString(cursor.getColumnIndex("identity")),cursor.getString(cursor.getColumnIndex("account")),
						cursor.getString(cursor.getColumnIndex("password")),cursor.getString(cursor.getColumnIndex("name")),cursor.getString(cursor.getColumnIndex("shenfenzheng")), 
						cursor.getString(cursor.getColumnIndex("xuehao")),cursor.getString(cursor.getColumnIndex("gonghao")),cursor.getString(cursor.getColumnIndex("managerId")),
						school.equals("科大")?cursor.getString(cursor.getColumnIndex("moniterId")):"",
						cursor.getString(cursor.getColumnIndex("class")),cursor.getString(cursor.getColumnIndex("status")));
				list.add(u);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return list;
	}
	//获得该学校所有学生信息
	public ArrayList<Test_User> findStudentBySchool(String school){
		ArrayList<Test_User> list=new ArrayList<Test_User>();
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		Cursor cursor = null;
		if(school.equals("全部")){
			list.addAll(findStudentBySchool("西电"));
			list.addAll(findStudentBySchool("科大"));
			list.addAll(findStudentBySchool("交大"));
			return list;
		}
		if(school.equals("西电"))
			cursor=db.query("XidianStudentData", null, null, null, null, null, null);
		else if(school.equals("科大"))
			cursor=db.query("KedaStudentData", null, null, null, null, null, null);
		else if(school.equals("交大"))
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
	//将获取的学科变为list形式返回
	private ArrayList<String> toArray(String string) {
		// TODO Auto-generated method stub
		ArrayList<String> arr=new ArrayList<String>();
		String[] str=string.split(" ");
		for (int i = 0; i < str.length; i++) {
			arr.add(str[i]);
		}
		//修正 多余空格
		if(TextUtils.isEmpty(str[0]))
			arr.remove(0);
		return arr;
	}
	
	//提供账号 校验 返回用户对象
	public Test_User getUser(String account){
		ArrayList<Test_User> list=findBySchoolIdentityAndStatus("全部", "全部", "全部");
		Iterator<Test_User> it=list.iterator();
		while(it.hasNext()){
			Test_User u=it.next();
			if(u.getAccount().equals(account)){
				return u;
			}
		}
		return null;
	}
	//提供账号 返回学号
	public String getXuehao(String account){
		ArrayList<Test_User> list=findBySchoolIdentityAndStatus("全部", "学生", "pass");
		Iterator<Test_User> it=list.iterator();
		while(it.hasNext()){
			Test_User u=it.next();
			if(u.getAccount().equals(account))
				return u.getXuehao();
		}
		return "";
	}
	//提供学校 and学号或身份证 返回学生成绩信息 考虑到姓名搜索可能有重复 所以返回List集合
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
	//提供学校 返回所有班级
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
	//提供学校和班级 返回学生列表
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
	//提供账号密码  校验  返回是否有该用户 用于登入界面
	public Test_User accountPasswordCheck(String account,String password){
		ArrayList<Test_User> list=findBySchoolIdentityAndStatus("全部", "全部", "pass");
		Iterator<Test_User> it=list.iterator();
		while(it.hasNext()){
			Test_User u=it.next();
			if(u.getAccount().equals(account)&&u.getPassword().equals(password)){
				return u;
			}
		}
		return null;
	}
	//提供学校和学科  返回补考时间
	public String getTime(String school,String subject){
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		Cursor cursor = null;
		String time = null;
		if(school.equals("西电"))
			cursor=db.query("XidianClassData", null, "subject=?", new String[]{subject}, null, null, null);
		else if(school.equals("科大"))
			cursor=db.query("KedaClassData", null, "subject=?", new String[]{subject}, null, null, null);
		else if(school.equals("交大"))
			cursor=db.query("JiaodaClassData", null, "subject=?", new String[]{subject}, null, null, null);
		if(cursor.moveToFirst())
			time=cursor.getString(cursor.getColumnIndex("time"));
		return time;
	}
	//提供学校和学科  返回学分
	public String getCredit(String school,String subject){
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		Cursor cursor = null;
		String credit = null;
		if(school.equals("西电"))
			cursor=db.query("XidianClassData", null, "subject=?", new String[]{subject}, null, null, null);
		else if(school.equals("科大"))
			cursor=db.query("KedaClassData", null, "subject=?", new String[]{subject}, null, null, null);
		else if(school.equals("交大"))
			cursor=db.query("JiaodaClassData", null, "subject=?", new String[]{subject}, null, null, null);
		if(cursor.moveToFirst())
			credit=cursor.getString(cursor.getColumnIndex("credit"));
		return credit;
	}

}
