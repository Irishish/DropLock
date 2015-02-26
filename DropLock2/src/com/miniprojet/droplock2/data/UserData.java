package com.miniprojet.droplock2.data;

public class UserData {
	
	private String idUsers;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNum;
	private String password;
	private String apiKey;
	
	public UserData() {
		super();
	}
	
	public UserData (String idUsers, String firstName, String lastName, String email, String phoneNum,
			String password, String apiKey) {
		super();
		this.idUsers = idUsers;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNum = phoneNum;
		this.password = password;
		this.apiKey = apiKey;
	}
	
	public UserData (String idUsers, String firstName, String lastName, String email, 
			String phoneNum, String apiKey) {
		super();
		this.idUsers = idUsers;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNum = phoneNum;
		this.apiKey = apiKey;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getIdUsers() {
		return idUsers;
	}

	public void setIdUsers(String idUsers) {
		this.idUsers = idUsers;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	

}
