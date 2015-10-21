package wang.tools;

import java.util.ArrayList;

public class Test_User {
	private String school=null;
	private String identity=null;
	private String account=null;
	private String password=null;
	private String name=null;
	private String shenfenzheng=null;
	private String xuehao=null;
	private String gonghao=null;
	private String managerId=null;
	private String moniterId=null;
	private String banji=null;
	private String status=null;
	private ArrayList<String> doneSubject;
	private ArrayList<String> failSubject;
	private ArrayList<String> certificate;
	private String subject;
	private String time;
	private String credit;
	public Test_User(String subject, String time, String credit) {
		this.subject = subject;
		this.time = time;
		this.credit = credit;
	}
	public Test_User(String school,String identity,String account, String password, String name,
			String shenfenzheng, String xuehao,String gonghao, String managerId, String moniterId, String banji,
			String status) {
		this.school=school;
		this.account = account;
		this.password = password;
		this.name = name;
		this.identity = identity;
		this.shenfenzheng = shenfenzheng;
		this.xuehao = xuehao;
		this.gonghao = gonghao;
		this.managerId = managerId;
		this.moniterId = moniterId;
		this.banji = banji;
		this.status = status;
	}
//	public Test_User(String account,String name, String xuehao, String banji,
//			ArrayList<String> doneSubject, ArrayList<String> failSubject,
//			ArrayList<String> certificate) {
//		this.account=account;
//		this.name = name;
//		this.xuehao = xuehao;
//		this.banji = banji;
//		this.doneSubject = doneSubject;
//		this.failSubject = failSubject;
//		this.certificate = certificate;
//	}
	public Test_User(String name, String xuehao, String banji,String shenfenzheng,
			ArrayList<String> doneSubject, ArrayList<String> failSubject,
			ArrayList<String> certificate) {
		this.name = name;
		this.xuehao = xuehao;
		this.banji = banji;
		this.shenfenzheng=shenfenzheng;
		this.doneSubject = doneSubject;
		this.failSubject = failSubject;
		this.certificate = certificate;
	}
	public ArrayList<String> getDoneSubject() {
		return doneSubject;
	}
	public ArrayList<String> getFailSubject() {
		return failSubject;
	}
	public ArrayList<String> getCertificate() {
		return certificate;
	}
	public String getSchool() {
		return school;
	}
	public String getName() {
		return name;
	}
	public String getIdentity() {
		return identity;
	}
	public String getShenfenzheng() {
		return shenfenzheng;
	}
	public String getXuehao() {
		return xuehao;
	}
	public String getGonghao() {
		return gonghao;
	}
	public String getRenzhenghao() {
		return managerId;
	}
	public String getAccount() {
		return account;
	}
	public String getPassword() {
		return password;
	}
	public String getManagerId() {
		return managerId;
	}
	public String getMoniterId() {
		return moniterId;
	}
	public String getStatus() {
		return status;
	}
	public String getBanji() {
		return banji;
	}
	public String getSubject() {
		return subject;
	}
	public String getTime() {
		return time;
	}
	public String getCredit() {
		return credit;
	}
}
