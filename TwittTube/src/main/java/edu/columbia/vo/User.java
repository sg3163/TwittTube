package edu.columbia.vo;

public class User {
	
	public User() {
		
	}
	
	public User(String userId, String firstName, String lastName) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String groupNumber;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}
}
