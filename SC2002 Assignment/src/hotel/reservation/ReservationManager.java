package reservation;

import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import reservation.Reservation.ReservStatus;
import room.*;
import room.Room.BedType;
import room.Room.RoomStatus;
import room.Room.RoomType;
import guest.*;

public class ReservationManager {

	public static final String SEPARATOR = "|";
	private ArrayList<Reservation> reservlist = new ArrayList();
	private String filename = "reservation.txt";
	
	Scanner sc = new Scanner(System.in);
	RoomManager Rm = new RoomManager("48_Hotel_Rooms.txt");
	ArrayList<Room> roomlist = Rm.getRoomList();
	
	public ArrayList<Reservation> getReservList() {return reservlist;}
	
	public void writeReservation() {
		List<String> alw = new ArrayList<String>() ;// to store Reservation data
		try 
		{
			for (int i = 0 ; i < reservlist.size() ; i++) {
				Reservation reserv = (Reservation) reservlist.get(i);
				StringBuilder st =  new StringBuilder() ;
				st.append(reserv.getReservStatus());
				st.append(SEPARATOR);
				st.append(reserv.getReservCode());
				st.append(SEPARATOR);
				if(reserv.getRoom() != null)
					st.append(reserv.getRoom().getRoomNumber()); // room store by room number
				else st.append("0000");
				st.append(SEPARATOR);
				st.append(reserv.getRType());
				st.append(SEPARATOR);
				st.append(reserv.getBType());
				st.append(SEPARATOR);
				st.append(reserv.getGuest().getId()); // guest store by guest id
				st.append(SEPARATOR);
				st.append(reserv.getCheckInDate());
				st.append(SEPARATOR);
				st.append(reserv.getCheckOutDate());
				st.append(SEPARATOR);
				st.append(reserv.getNumAdult());
				st.append(SEPARATOR);
				st.append(reserv.getNumChild());
				alw.add(st.toString()) ;
			}
			write(alw);
		}
		catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}
        
	}
	
	public void removeReservRecord()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Remove reservation record...");
		System.out.println("Enter the reservation code: ");
		String reservCode = sc.next();
		
		for(int i=0; i<reservlist.size();i++)
		{
			Reservation reserv = (Reservation) reservlist.get(i);
			if(reserv.getReservCode().equals(reservCode))
			{
				reservlist.remove(i);
				System.out.printf("The reservation record %s is successfully removed.\n",reservCode);
				writeReservation();
				return;
			}
		}
		System.out.printf("The reservation record %s is not found.\n",reservCode);
	}
	
	public void write(List data) throws IOException  {
	    PrintWriter out = new PrintWriter(new FileWriter(filename));

	    try {
			for (int i =0; i < data.size() ; i++) {
	      		out.println((String)data.get(i));
			}
	    }
	    finally {
	      out.close();
	    }
	  }

	public boolean createReserv(Reservation reserv) {
		
		System.out.println("Create reservation...");
		// generate reservation code
		reserv.setReservCode(createReservCode());
		
		// set guest details
		GuestManager gm = new GuestManager();
		reserv.setGuest(gm.createGuest());
		
		// set room details
		reserv.inputRoom(roomlist); // get room
		
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
	public void updateWaitlist(ArrayList<Reservation> reservlist, ArrayList<Room> roomlist)
	{
		System.out.println("Updating waitlist...");
		// for testing
		for(int i=0; i<reservlist.size(); i++)
		{
			Reservation target = reservlist.get(i);
			if(target.getReservStatus().equals(ReservStatus.IN_WAITLIST))
			{
				for(int j=0; j<roomlist.size();j++)
				{
					Room cur = (Room) roomlist.get(j);
					if(target.getRType().compareTo(cur.getRoomType()) == 0 && target.getBType().compareTo(cur.getBedType()) == 0 && cur.getRoomStatus().equals(RoomStatus.VACANT))
					{
						target.setRoom(cur);
						target.setReservStatus(ReservStatus.CONFIRMED);
						cur.setRoomStatus(RoomStatus.OCCUPIED);
						System.out.printf("Reservation %s has been assigned room %s.\n",target.getReservCode(),cur.getRoomNumber());
						break;
					}
				}
			}
		}
	}
	
	public boolean checkIn(Reservation reserv) {
		Timestamp now = Timestamp.from(Instant.now());
		Timestamp scheduledCheckIn = Timestamp.valueOf(reserv.getCheckInDate() + " 14:00:00"); // 2pm on check-in date
		if (now.before(scheduledCheckIn) == true) {
			System.out.println("Please check-in at your scheduled check-in time.");
			return false;
		}

		reserv.setReservStatus(ReservStatus.CHECKED_IN);
		return true;
	}

	public void expire(Reservation reserv) {
		Timestamp now = Timestamp.from(Instant.now());
		Timestamp deadlineCheckIn = Timestamp.valueOf(reserv.getCheckInDate().plusDays(1) + " 02:00:00"); // 2am on the next day
		// of check-in date
		if (now.after(deadlineCheckIn) == true)
			reserv.setReservStatus(ReservStatus.EXPIRED);

		// empty the room
		reserv.getRoom().setRoomStatus(RoomStatus.VACANT);
	}
}
