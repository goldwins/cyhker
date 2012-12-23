package com.cycrypt.module.account;

public class Account {
	

	private String cyId;
	private String email;
	private String pass;
	
	public String getCyId() {
		return cyId;
	}
	public void setCyId(String cyId) {
		this.cyId = cyId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	@Override
	public String toString() {
		return "Account [cyId=" + cyId + ", email=" + email + ", pass=" + pass
				+ "]";
	}
}
