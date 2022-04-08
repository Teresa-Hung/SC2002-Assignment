package guest;
import java.util.Scanner;

public class Guest {
	private String firstName;
	private String lastName;
	private String email;
	private String country;
	private String gender;
	private String nationality;
	private String id;
	private CreditCardDetails creditCardNum;
	private String contactNum;
	private boolean paid;
	private boolean reservStatus;
	
	Scanner sc = new Scanner(System.in);
	//constructor
	public Guest(){
		firstName = null;
		lastName = null;
		email = null;
		country = null;
		gender = null;
		nationality = null;
		id = null;
		creditCardNum = null;
		contactNum = null;
		paid = false;
		reservStatus = false;
	}
	
	//get & set attributes
	public boolean getPaid() {
		return paid;
	}
	public void setPaid(boolean pay) {
		this.paid = pay;
	}
	public boolean getReservStatus() {
		return reservStatus;
	}
	public void setReservStatus(boolean status) {
		this.reservStatus = status;
	}
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
	public void setEmail(String a) {
		this.email = a;
	}
	public String getEmail() {
		return email;
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
	public void setCC(CreditCardDetails cc) {
		this.creditCardNum = cc;
	}
	public CreditCardDetails getCC() {
		return creditCardNum;
	}
	public void setContact(String num) {
		this.contactNum = num;
	}
	public String getContact() {
		return contactNum;
	}
	

}
