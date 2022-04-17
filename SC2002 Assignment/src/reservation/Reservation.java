package reservation;

import java.time.*;

import room.*;
import room.Room.*;
import guest.*;

/**
 * Represents a reservation record in a Hotel Reservation and Payment System
 * @author Teresa Hung Kuo Chen
 * @version 1.0
 * @since 2022-04-16
 */
public class Reservation {


	/**
	 * Different Types of reservation status.
	 *
	 */
	public enum ReservStatus {
		CONFIRMED, IN_WAITLIST, CHECKED_IN, EXPIRED
	}

	/**
	 * The status of this reservation.
	 */
	private ReservStatus reservStatus;
	/**
	 * The corresponding reservation code for reference.
	 */
	private String reservCode;
	/**
	 * The room object stores the assigned room for this reservation.
	 * If the reservation is in waitlist, room will be null.
	 */
	private Room room;
	/**
	 * The requested room type of this reservation.
	 */
	private RoomType rtype;
	/**
	 * The requested bed type of this reservation.
	 */
	private BedType btype;
	/**
	 * The guest object stores the corresponding guest details of this reservation.
	 */
	private Guest guest;
	/**
	 * The scheduled check-in date of this reservation.
	 */
	private LocalDate dateCheckIn;
	/**
	 * The scheduled check-out date of this reservation.
	 */
	private LocalDate dateCheckOut;
	/**
	 * The number of adult guests of this reservation. 
	 */
	private int numAdult;
	/**
	 * The number of underage guests of this reservation. 
	 */
	private int numChild;
	
	// set methods
	/**
	 * Sets the reservation status.
	 * @param s The reservation status.
	 */
	public void setReservStatus(ReservStatus s) {reservStatus = s;}
	/**
	 * Sets the reservation code.
	 * The reservation code consists of an uppercase letter and a random number from 1 to 999
	 * @param s The reservation code.
	 */
	public void setReservCode(String s) {reservCode = s;}
	/**
	 * Sets the room object.
	 * @param r The room object.
	 */
	public void setRoom(Room r) {room = r;}
	/**
	 * Sets the requested room type.
	 * @param t The room type.
	 */
	public void setRType(RoomType t) {rtype = t;}
	/**
	 * Sets the requested bed type.
	 * @param t The bed type.
	 */
	public void setBType(BedType t) {btype = t;}
	/**
	 * Sets the associated guest object.
	 * @param g The guest object.
	 */
	public void setGuest(Guest g) {guest = g;}
	/**
	 * Sets the scheduled check-in date.
	 * @param d The date.
	 */
	public void setCheckInDate(LocalDate d) {dateCheckIn = d;}
	/**
	 * Sets the scheduled check-in date.
	 * @param d The date.
	 */
	public void setCheckOutDate(LocalDate d) {dateCheckOut = d;}
	/**
	 * Sets the number of adults.
	 * @param n The number.
	 */
	public void setNumAdult(int n) {numAdult = n;}
	/**
	 * Sets the number of children.
	 * @param n The number.
	 */
	public void setNumChild(int n) {numChild = n;}
	
	// get methods
	/**
	 * Gets the reservation status of this reservation.
	 * @return reservation status.
	 */
	public ReservStatus getReservStatus() {return reservStatus;}
	/**
	 * Gets the corresponding reservation code of this reservation.
	 * @return reservation code.
	 */
	public String getReservCode() {return reservCode;}
	/**
	 * Gets the associated guest object of this reservation.
	 * @return guest.
	 */
	public Guest getGuest() {return guest;}
	/**
	 * Gets the associated room object of this reservation.
	 * @return room.
	 */
	public Room getRoom() {return room;}
	/**
	 * Gets the requested room type of this reservation.
	 * @return room type.
	 */
	public RoomType getRType() {return rtype;}
	/**
	 * Gets the requested bed type of this reservation.
	 * @return bed type.
	 */
	public BedType getBType() {return btype;}
	/**
	 * Gets the scheduled check-in date of this reservation.
	 * @return date of check-in.
	 */
	public LocalDate getCheckInDate() {return dateCheckIn;}
	/**
	 * Gets the day of the scheduled check-in date.
	 * @return day of check-in.
	 */
	public DayOfWeek getCheckInDay() {return dateCheckIn.getDayOfWeek();}
	/**
	 * Gets the scheduled check-out date of this reservation.
	 * @return date of check-out.
	 */
	public LocalDate getCheckOutDate() {return dateCheckOut;}
	/**
	 * Gets the day of the scheduled check-out date.
	 * @return day of check-out.
	 */
	public DayOfWeek getCheckOutDay() {return dateCheckOut.getDayOfWeek();}
	/**
	 * Gets the number of adult guests of this reservation.
	 * @return number of adults.
	 */
	public int getNumAdult() {return numAdult;}
	/**
	 * Gets the number of underage guests of this reservation.
	 * @return number of children.
	 */
	public int getNumChild() {return numChild;}
	
	/**
	 * Prints the acknowledgement receipt of this reservation.
	 * Information includes the guest and room details, date of check in and check out, number of adults and children and the status of this reservation.
	 */
	// other methods
	public void printReceipt() {
		System.out.println("\n-----the reservation acknowledgement receipt is shown as below-----");
		
		// guest details & billing info
		System.out.println("\n---Guest Details---");
		System.out.println("First Name: " + guest.getFName());
		System.out.println("Last Name: " + guest.getLName());
		System.out.println("Gender: " + guest.getGender());
		System.out.println("ID: " + guest.getId());
		System.out.println("Contact Number: " + guest.getContact());
		System.out.println("Email: " + guest.getEmail());
		System.out.println("Country: " + guest.getCountry());
		System.out.println("Nationality: " + guest.getNatlity());
		
		String strReplacement = "************";
       	String lastFourNum = guest.getCcNum().substring(guest.getCcNum().length() - 4);
        String newString = strReplacement + lastFourNum;
		System.out.println("\n---Credit Card Details---");
		System.out.println("Holder Name: " + guest.getHolderFName() + " " + guest.getHolderLName());
		System.out.println("Credit Card Number: " + newString);
		System.out.println("Expiry Date: " + guest.getExpDate());
		System.out.println("Billing Address: " + guest.getBillAddr());
		
		// reservation details
		System.out.println("\n---Reservation Details---");
		System.out.println("Reservation Code: " + guest.getReservCode());
		System.out.println("Room Number: " + guest.getRoomNum());
		System.out.printf("Room type: %s\n", room.getRoomType());
		System.out.printf("Bed type: %s\n", room.getBedType());
		System.out.printf("Check-in Date: %s %s\n", dateCheckIn, getCheckInDay());
		System.out.printf("Check-out Date: %s %s\n", dateCheckOut, getCheckOutDay());
		System.out.printf("No. of Adults: %d\n", numAdult);
		System.out.printf("No. of Children: %d\n", numChild);
		System.out.println("Reservation Status: " + reservStatus + "\n");
	}
}
