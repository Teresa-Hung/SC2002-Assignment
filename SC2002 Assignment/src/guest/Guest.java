package guest;

import java.time.LocalDate;


/**
 * Represents a guest staying in the hotel.
 * @author EVANGELINE NG XUAN HUI
 * @version 1.0
 * @since 2022-04-16
 */
public class Guest {
	/**
	 * The first name of this guest.
	 */
	private String firstName;
	/**
	 * The last name of this guest.
	 */
	private String lastName;
	/**
	 * The country where this guest is from.
	 */
	private String country;
	/**
	 * The gender of this guest.
	 */
	private String gender;
	/**
	 * The nationality of this guest.
	 */
	private String nationality;
	/**
	 * The identification number of this guest.
	 */
	private String id;
	/**
	 * The email of this guest.
	 */
	private String email;
	/**
	 * The contact number of this guest.
	 */
	private String contactNum;
	/**
	 * The holder's first name of the credit card provided by this guest.
	 */
	private String holderFName;
	/**
	 * The holder's last name of the credit card provided by this guest.
	 */
	private String holderLName;
	/**
	 * The number of the credit card provided by this guest.
	 */
	private String ccNum;
	/**
	 * The expiry date of the credit card provided by this guest.
	 */
	private LocalDate expiryDate;
	/**
	 * The billing address of this guest.
	 */
	private String billAddr;
	/**
	 * The variable indicating whether payment has been made for this guest.
	 */
	private int paid;
	/**
	 * The number of the room this guest is staying.
	 */
	private String roomNum;
	/**
	 * The corresponding reservation code of this guest.
	 */
	private String reservCode;

	//constructor
	/**
	 * Creates a new guest object without any details.
	 */
	public Guest(){
		firstName = "-";
		lastName = "-";
		contactNum = "-";
		country = "-";
		gender = "-";
		nationality = "-";
		id = "-";
		email = "-";
		holderFName = "-";
		holderLName = "-";
		ccNum = "-";
		expiryDate = null;
		billAddr = "-";
		roomNum = "0";
		paid = 0;
		reservCode = "0";
		
	}
	
	//get & set attributes
	/**
	 * Sets the first name of this guest.
	 * @param f The first name of this guest.
	 */
	public void setFName(String f) {
		this.firstName = f;
	}
	/**
	 * Gets the first name of this guest.
	 * @return the guest's first name.
	 */
	public String getFName() {
		return firstName;
	}
	/**
	 * Sets the last name of this guest.
	 * @param l The last name of this guest.
	 */
	public void setLName(String l) {
		this.lastName = l;
	}
	/**
	 * Gets the first name of this guest.
	 * @return the guest's last name.
	 */
	public String getLName() {
		return lastName;
	}
	/**
	 * Sets the country where the guest is from.
	 * @param c The country.
	 */
	public void setCountry(String c) {
		this.country = c;
	}
	/**
	 * Gets the country where the guest is from.
	 * @return the country.
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * Sets the gender of this guest.
	 * @param g The guest's gender.
	 */
	public void setGender(String g) {
		this.gender = g;
	}
	/**
	 * Gets the gender of this guest.
	 * @return the guest's gender.
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * Sets the nationality of this guest.
	 * @param n The guest's nationality.
	 */
	public void setNatlity(String n) {
		this.nationality = n;
	}
	/**
	 * Gets the nationality of this guest.
	 * @return the guest's nationality.
	 */
	public String getNatlity() {
		return nationality;
	}
	/**
	 * Sets the identification number of this guest.
	 * @param i The guest's identification number.
	 */
	public void setId(String i) {
		this.id = i;
	}
	/**
	 * Gets the identification number of this guest.
	 * @return the guest's identification number.
	 */
	public String getId() {
		return id;
	}
	/**
	 * Sets the email of this guest.
	 * @param e The guest's email.
	 */
	public void setEmail(String e) {
		this.email = e;
	}
	/**
	 * Gets the email of this guest.
	 * @return the guest's email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Sets the contact number of this guest.
	 * @param num The guest's contact number.
	 */
	public void setContact(String num) {
		this.contactNum = num;
	}
	/**
	 * Gets the contact number of this guest.
	 * @return the guest's contact number.
	 */
	public String getContact() {
		return contactNum;
	}
	/**
	 * Sets the holder's first name of the credit card this guest provided.
	 * @param f The holder's first name.
	 */
	public void setHolderFName(String f) {
		this.holderFName = f;
	}
	/**
	 * Gets the holder's first name of the credit card this guest provided.
	 * @return the holder's first name.
	 */
	public String getHolderFName() {
		return holderFName;
	}
	/**
	 * Sets the holder's last name of the credit card this guest provided.
	 * @param l The holder's last name.
	 */
	public void setHolderLName(String l) {
		this.holderLName = l;
	}
	/**
	 * Gets the holder's last name of the credit card this guest provided.
	 * @return the holder's last name.
	 */
	public String getHolderLName() {
		return holderLName;
	}
	
	/*public void setCC(String cc) {
		ccNum = cc;
	}
	
	public String getCC() {
		return ccNum;
	}*/
	
	/**
	 * Sets the number of the credit card this guest provided.
	 * @param a The credit card number.
	 */
	public void setCcNum(String a) {
		this.ccNum = a;
	}
	/**
	 * Gets the number of the credit card this guest provided.
	 * @return the credit card number.
	 */
	public String getCcNum() {
		return ccNum;
	}
	/**
	 * Sets the expiry date of the credit card this guest provided.
	 * @param e The card's expiry date.
	 */
	public void setExpDate(LocalDate e) {
		this.expiryDate = e;
	}
	/**
	 * Gets the expiry date of the credit card this guest provided.
	 * @return the card's expiry date.
	 */
	public LocalDate getExpDate() {
		return expiryDate;
	}
	/**
	 * Sets the billing address of this guest.
	 * @param bilAdr The guest's billing address.
	 */
	public void setBillAddr(String bilAdr) {
		this.billAddr = bilAdr;
	}
	/**
	 * Gets the billing address of this guest.
	 * @return the guest's billing address.
	 */
	public String getBillAddr() {
		return billAddr;
	}
	/**
	 * Sets the number of the room the guest is staying.
	 * @param r The room's number.
	 */
	public void setRoomNum(String r) {
		this.roomNum = r;
	}
	/**
	 * Gets the number of the room the guest is staying.
	 * @return the room's number.
	 */
	public String getRoomNum(){
		return roomNum;
	}
	/**
	 * Sets the corresponding reservation code of this guest.
	 * @param h The reservation code.
	 */
	public void setReservCode(String h) {
		this.reservCode = h;
	}
	/**
	 * Gets the corresponding reservation code of this guest.
	 * @return the reservation code.
	 */
	public String getReservCode(){
		return reservCode;
	}
	/**
	 * Sets the payment status.
	 * 0 indicates unpaid bill, and 1 indicates paid one.
	 * @param p The payment status.
	 */
	public void setPaid(int p) {
		this.paid = p;
	}
	/**
	 * Gets the payment status.
	 * @return the payment status.
	 */
	public int getPaid(){
		return paid;
	}
}