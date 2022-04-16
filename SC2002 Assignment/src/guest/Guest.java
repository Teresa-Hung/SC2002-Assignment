package guest;

import java.time.LocalDate;

public class Guest {
	private String firstName;
	private String lastName;
	private String country;
	private String gender;
	private String nationality;
	private String id;
	private String email;
	private String contactNum;
	private String holderFName;
	private String holderLName;
	private String ccNum;
	private LocalDate expiryDate;
	private String billAddr;
	private int paid;
	private String roomNum;
	private String reservCode;

	//constructor
	public Guest(){
		firstName = null;
		lastName = null;
		contactNum = null;
		country = null;
		gender = null;
		nationality = null;
		id = null;
		email = null;
		holderFName = null;
		holderLName = null;
		ccNum = null;
		expiryDate = null;
		billAddr = null;
		roomNum = "0";
		paid = 0;
		reservCode = "0";
		
	}
	
	//get & set attributes
	public void setFName(String f) {
		this.firstName = f;
	}
	public String getFName() {
		return firstName;
	}
	public void setLName(String l) {
		this.lastName = l;
	}
	public String getLName() {
		return lastName;
	}
	public void setCountry(String c) {
		this.country = c;
	}
	public String getCountry() {
		return country;
	}
	public void setGender(String g) {
		this.gender = g;
	}
	public String getGender() {
		return gender;
	}
	public void setNatlity(String n) {
		this.nationality = n;
	}
	public String getNatlity() {
		return nationality;
	}
	public void setId(String i) {
		this.id = i;
	}
	public String getId() {
		return id;
	}
	public void setEmail(String e) {
		this.email = e;
	}
	public String getEmail() {
		return email;
	}
	public void setCC(String cc) {
		ccNum = cc;
	}
	public String getCC() {
		return ccNum;
	}
	public void setContact(String num) {
		this.contactNum = num;
	}
	public String getContact() {
		return contactNum;
	}
	public void setHolderFName(String f) {
		this.holderFName = f;
	}
	public String getHolderFName() {
		return holderFName;
	}
	public void setHolderLName(String l) {
		this.holderLName = l;
	}
	public String getHolderLName() {
		return holderLName;
	}
	public void setCcNum(String a) {
		this.ccNum = a;
	}
	public String getCcNum() {
		return ccNum;
	}
	public void setExpDate(LocalDate e) {
		this.expiryDate = e;
	}
	public LocalDate getExpDate() {
		return expiryDate;
	}
	public void setBillAddr(String bilAdr) {
		this.billAddr = bilAdr;
	}
	public String getBillAddr() {
		return billAddr;
	}
	public void setRoomNum(String r) {
		this.roomNum = r;
	}
	public String getRoomNum(){
		return roomNum;
	}
	public void setReservCode(String h) {
		this.reservCode = h;
	}
	public String getReservCode(){
		return reservCode;
	}
	public void setPaid(int p) {
		this.paid = p;
	}
	public int getPaid(){
		return paid;
	}
	
	public void displayGuestDetails() {
		String strReplacement = "************";
        String lastFourNum = ccNum.substring(ccNum.length() - 4);
        String newString = strReplacement + lastFourNum;
		System.out.println("First Name: " + firstName);
		System.out.println("Last Name: " + lastName);
		System.out.println("ID: " + id);
		System.out.println("Contact Number: " + contactNum);
		System.out.println("Email: " + email);
		System.out.println("Country: " + country);
		System.out.println("Gender: " + gender);
		System.out.println("Nationality: " + nationality);
		System.out.println("Credit Card Details: ");
		System.out.println("Holder Name: " + holderFName + " " + holderLName);
		System.out.println("Credit Card Number: " + newString);
		System.out.println("Expiry Date: " + expiryDate);
		System.out.println("Billing Address: " + billAddr);
		System.out.println("Reservation Code: " + reservCode);
		System.out.println("Room Number: " + roomNum);
	}
}
