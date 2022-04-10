package reservation;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import room.*;
import room.Room.RoomType;
import room.Room.Status;
import guest.*;

public class Reservation {

	public enum ReservStatus {
		CONFIRMED, IN_WAITLIST, CHECKED_IN, EXPIRED
	}

	private ReservStatus reservStatus;
	private String reservCode;
	private Room room;
	private Guest guest;
	private LocalDate dateCheckIn;
	private LocalDate dateCheckOut;
	private int numAdult;
	private int numChild;

	Scanner sc = new Scanner(System.in);
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	// set methods
	public void setReservStatus(ReservStatus s) {reservStatus = s;}
	public void setReservCode(String s) {reservCode = s;}
	public void setRoom(Room r) {room = r;}
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
	public LocalDate getCheckInDate() {return dateCheckIn;}
	public DayOfWeek getCheckInDay() {return dateCheckIn.getDayOfWeek();}
	public LocalDate getCheckOutDate() {return dateCheckOut;}
	public DayOfWeek getCheckOutDay() {return dateCheckOut.getDayOfWeek();}
	public int getNumAdult() {return numAdult;}
	public int getNumChild() {return numChild;}

	// other methods
	public void inputRoom(Room[] roomlist) {
		System.out.println("Enter the room type: ");
		RoomType type = RoomType.valueOf(sc.next());
		// check roomlist, if vacant then assign room to customer
		boolean vacant = false;
		for (int i = 0; i < roomlist.length; i++) {
			if (roomlist[i].getRoomType() == type) {
				if (roomlist[i].getStatus() == Status.VACANT) {
					vacant = true;
					setRoom(roomlist[i]);
					setReservStatus(ReservStatus.CONFIRMED);
					break;
				}
			}
		}
		if (vacant == false)
			setReservStatus(ReservStatus.IN_WAITLIST);
	}
	
	public boolean inputDates(boolean checkin, boolean checkout) {
		try {
			String date;
			LocalDate dCI = dateCheckIn, dCO = dateCheckOut;
			if (checkin == true) {
				System.out.println("Enter Check-in Date (dd/mm/yyyy): ");
				date = sc.next();
				dCI = LocalDate.parse(date, formatter);
			}
			if (checkout == true) {
				System.out.println("Enter Check-out Date (dd/mm/yyyy): ");
				date = sc.next();
				dCO = LocalDate.parse(date, formatter);
			}
			// if dates are valid, update and return true
			if (checkDates(dCI, dCO)) {
				setCheckInDate(dCI);
				setCheckOutDate(dCO);
				return true;
			}
			return false;
		}

		catch (DateTimeParseException e) {
			System.out.println("The date is invalid.");
			return false;
		}
	}

	// check check-in/out dates are valid
	public boolean checkDates(LocalDate dCI, LocalDate dCO) {
		if (dCI.compareTo(dCO) > 0) {
			System.out.println("The scheduled check-in and check-out dates are invalid.");
			return false;
		}
		return true;
	}

	public void inputNumAdult() {
		System.out.println("Enter the Number of Adults: ");
		setNumAdult(sc.nextInt());
	}

	public void inputNumChild() {
		System.out.println("Enter the Number of Children: ");
		setNumChild(sc.nextInt());
	}

	public void printReceipt() {
		System.out.println("\n-----the reservation acknowledgement receipt is shown as below-----");
		System.out.printf("Reservation Code: %s\n", reservCode);
		// guest details
		System.out.println("Guest Details");
		System.out.printf("First Name: %s\n", guest.getFName());
		System.out.printf("Lirst Name: %s\n", guest.getLName());
		System.out.printf("ID: %s\n", guest.getId());
		System.out.printf("Contact: %s\n", guest.getContact());
		System.out.printf("Email: %s\n", guest.getEmail());
		// System.out.printf("Room number: %d\n", room.getRoomNumber());
		// System.out.printf("Room type: %d\n", room.getRoomType());
		// billing info
		System.out.println("Billing Information");
		System.out.printf("Credit Card Number: %s\n", guest.getCC().getCcNum());
		System.out.printf("Card Holder's Name: %s %s\n", guest.getCC().getHolderFName(), guest.getCC().getHolderLName());
		System.out.printf("Expiry Date: %s\n", guest.getCC().getExpDate());
		String[] billAdr = guest.getCC().getBillAddr();
		System.out.printf("Billing Address: ");
		for(int i=0;i<billAdr.length;i++)
			System.out.printf("%s ", billAdr[i]);
		System.out.println("");
		System.out.printf("Check-in Date: %s %s\n", dateCheckIn, getCheckInDay());
		System.out.printf("Check-out Date: %s %s\n", dateCheckOut, getCheckOutDay());
		System.out.printf("No. of Adults: %d\n", numAdult);
		System.out.printf("No. of Children: %d\n", numChild);
		// System.out.printf("Reservation Status: ", reservStatus,"\n");
		System.out.println("");
	}

}
