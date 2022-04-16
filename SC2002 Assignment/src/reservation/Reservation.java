package reservation;

import java.time.*;

import room.*;
import room.Room.*;
import guest.*;
import guest.GuestManager;

public class Reservation {

	public enum ReservStatus {
		CONFIRMED, IN_WAITLIST, CHECKED_IN, EXPIRED
	}

	private ReservStatus reservStatus;
	private String reservCode;
	private Room room;
	private RoomType rtype;
	private BedType btype;
	private Guest guest;
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
	// check check-in/out dates are valid
	public boolean checkDates(LocalDate dCI, LocalDate dCO) {
		if (dCI.compareTo(dCO) > 0) {
			System.out.println("The scheduled check-in and check-out dates are invalid.");
			return false;
		}
		return true;
	}
	
	public void printReceipt() {
		System.out.println("\n-----the reservation acknowledgement receipt is shown as below-----");
		//System.out.printf("Reservation Code: %s\n", reservCode);
		// guest details & billing info
		GuestManager gm = new GuestManager();
		gm.displayGuestDetails(guest);
		//System.out.printf("Room number: %s\n", room.getRoomNumber());
		System.out.printf("Room type: %s\n", room.getRoomType());
		System.out.printf("Bed type: %s\n", room.getBedType());
		System.out.printf("Check-in Date: %s %s\n", dateCheckIn, getCheckInDay());
		System.out.printf("Check-out Date: %s %s\n", dateCheckOut, getCheckOutDay());
		System.out.printf("No. of Adults: %d\n", numAdult);
		System.out.printf("No. of Children: %d\n", numChild);
		System.out.printf("Reservation Status: ");
		System.out.println(reservStatus);
		System.out.printf("\n\n");
	}
}
