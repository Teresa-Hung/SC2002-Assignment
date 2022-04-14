package reservation;

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.time.*;

import reservation.Reservation.ReservStatus;
import room.*;
import guest.*;

public class ReservationManager {

	Scanner sc = new Scanner(System.in);
	RoomManager Rm = new RoomManager("48_Hotel_Rooms.txt");
	ArrayList<Room> roomlist = Rm.getRoomList();
	String[] waitlist = new String[100]; // store the reservation in waitlist
	int waitlistNum = 0;

	public boolean createReserv(Reservation reserv) {
		
		System.out.println("Create reservation...");
		// generate reservation code
		reserv.setReservCode(createReservCode());
		
		// set guest details
		GuestManager gm = new GuestManager();
		reserv.setGuest(gm.createGuest());
		
		// set room details
		reserv.inputRoom(roomlist); // get room
		if (reserv.getReservStatus() == ReservStatus.IN_WAITLIST) // put reservation in waitlist
			waitlist[waitlistNum++] = reserv.getReservCode();
		
		// get check-in and check-out date
		if (!reserv.inputDates(true, true))
			return false;

		// get number of adults/children
		reserv.inputNumAdult();
		reserv.inputNumChild();
		
		return true;
	}

	public String createReservCode() {
		Random rand = new Random();
		char c = (char) (rand.nextInt(26) + 'A');
		Integer i = rand.nextInt(1000);
		return c + Integer.toString(i);
	}
	
	public ArrayList<Reservation> updateReserv(ArrayList<Reservation> al) {
		
		roomlist = Rm.getRoomList();
		String code;
		System.out.println("Update reservation...");
		System.out.println("Please enter the reservation code: ");
		code = sc.next();

		// find the target reservation
		Reservation target = searchReserv(al,code);
		if (target == null) return al; // target does not exist

		// update
		int choice;

		System.out.println("Please choose the detail you would like to update.");
		System.out.println("1: Check-in date");
		System.out.println("2: Check-out date");
		System.out.println("3: Room");
		System.out.println("4: Guest details or Billing information");
		System.out.println("5: Number of Adults");
		System.out.println("6: Number of Children");
		choice = sc.nextInt();

		boolean success = true;
		switch (choice) {
		case 1:
			success = target.inputDates(true, false);
			break;

		case 2:
			success = target.inputDates(false, true);
			break;

		case 3:
			target.inputRoom(roomlist);
			break;

		case 4:
			GuestManager gm = new GuestManager();
			target.setGuest(gm.updateGuest(target.getGuest()));
			break;

		case 5:
			target.inputNumAdult();
			break;

		case 6:
			target.inputNumChild();
			break;

		default:
			System.out.println("The choice is invalid.");

		}
		if (success)
			System.out.println("The detail has been successfully updated.");
		target.printReceipt();
			
		return al;
	}
	
	public Reservation searchReserv(ArrayList<Reservation> reservlist, String code)
	{
		for(int i=0; i<reservlist.size(); i++)
		{
			Reservation cur = (Reservation) reservlist.get(i);
			if(cur.getReservCode().equals(code))
				return cur;
		}
		System.out.printf("The reservation code %s does not exist.\n",code);
		return null;
	}
	// method to manage waitlist
}
