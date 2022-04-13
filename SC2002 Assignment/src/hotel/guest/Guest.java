package guest;
import java.util.Scanner;
import reservation.Reservation.ReservStatus;
import payment.Payment.PaidType;

public class Guest {
	private String firstName;
	private String lastName;
	private String country;
	private String gender;
	private String nationality;
	private String id;
	private String contactNum;
	private CreditCardDetails creditCardNum;
	private ReservStatus reservStatus;
	private int paid;
	
	Scanner sc = new Scanner(System.in);
	//constructor
	public Guest(){
		firstName = null;
		lastName = null;
		country = null;
		gender = null;
		nationality = null;
		id = null;
		creditCardNum = null;
		contactNum = null;
		paid = 0;
		reservStatus = null;
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
	public void setReservStatus(ReservStatus s){
		this.reservStatus = s;
	}
	public ReservStatus getReservStatus() {
		return reservStatus;
	}
	public void setPaid(int p) {
		this.paid = p;
	}
	public int getPaid(){
		return p;
	}
	

}
