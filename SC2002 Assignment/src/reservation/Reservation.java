package reservation;

import java.time.*;

import room.*;
import room.Room.*;
import guest.*;

public class Reservation {

	public enum ReservStatus {
		CONFIRMED, IN_WAITLIST, CHECKED_IN, EXPIRED
	}

	private ReservStatus reservStatus;
	private String reservCode;
	private Room room;
	private RoomType rtype;
	private BedType btype;
	private Guest guest = new Guest();
	private LocalDate dateCheckIn;
	private LocalDate dateCheckOut;
	private int numAdult;
	private int numChild;
	
	// set methods
	public void setReservStatus(ReservStatus s) {reservStatus = s;}
	public void setReservCode(String s) {reservCode = s;}
	public void setRoom(Room r) {room = r;}
	public void setRType(RoomType t) {rtype = t;}
	public void setBType(BedType t) {btype = t;}
	public void setGuest(Guest g) {guest = g;}
	public void setCheckInDate(LocalDate d) {dateCheckIn = d;}
	public void setCheckOutDate(LocalDate d) {dateCheckOut = d;}
	public void setNumAdult(int n) {numAdult = n;}
	public void setNumChild(int n) {numChild = n;}
	
	// get methods
	public ReservStatus getReservStatus() {return reservStatus;}
	public String getReservCode() {return reservCode;}
	public Guest getGuest() {return guest;}
	public Room getRoom() {return room;}
	public RoomType getRType() {return rtype;}
	public BedType getBType() {return btype;}
	public LocalDate getCheckInDate() {return dateCheckIn;}
	public DayOfWeek getCheckInDay() {return dateCheckIn.getDayOfWeek();}
	public LocalDate getCheckOutDate() {return dateCheckOut;}
	public DayOfWeek getCheckOutDay() {return dateCheckOut.getDayOfWeek();}
	public int getNumAdult() {return numAdult;}
	public int getNumChild() {return numChild;}

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
