package reservation;

import java.util.Scanner;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import reservation.Reservation.ReservStatus;
import room.*;
import room.Room.RoomStatus;
import room.Room.RoomType;
import room.Room.BedType;
import guest.*;
import main.FileIO.ReadWrite;

public class ReservationManager implements ReadWrite {

	public static final String SEPARATOR = "|";
	private ArrayList<Reservation> reservlist;
	
	Scanner sc = new Scanner(System.in);
	GuestManager gm = new GuestManager();
	
	public ReservationManager()
	{
		reservlist = readReservation();
	}
	
	public ArrayList<Reservation> getReservList() {return reservlist;}
	
	public ArrayList<Reservation> readReservation() {
		ArrayList<String> stringArray = read("reservation.txt"); // read String from text file
		ArrayList<Reservation> alr = new ArrayList<>() ; // to store Reservation data
		for (int i = 0 ; i < stringArray.size() ; i++) {
			String st = (String)stringArray.get(i);
			// get individual 'fields' of the string separated by SEPARATOR
			StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","
			Reservation reserv = new Reservation();
			GuestManager gm = new GuestManager();
			RoomManager Rm = new RoomManager();
			
			reserv.setReservStatus(ReservStatus.valueOf(star.nextToken().trim()));
			reserv.setReservCode(star.nextToken().trim());
			// retrieve room details
			String roomnum = star.nextToken().trim();
			if(roomnum != "0000")
				reserv.setRoom(Rm.findRoom(roomnum, false));
			reserv.setRType(RoomType.valueOf(star.nextToken().trim()));
			reserv.setBType(BedType.valueOf(star.nextToken().trim()));
			// retrieve guest details
			String id = star.nextToken().trim();
			reserv.setGuest(gm.findById(id));
			reserv.setCheckInDate(LocalDate.parse(star.nextToken().trim()));
			reserv.setCheckOutDate(LocalDate.parse(star.nextToken().trim()));
			reserv.setNumAdult(Integer.parseInt(star.nextToken().trim()));
			reserv.setNumChild(Integer.parseInt(star.nextToken().trim()));
			
			alr.add(reserv);
		}
		return alr;
	}
	
	public void writeReservation() {
		ArrayList<String> alw = new ArrayList<String>() ;// to store Reservation data
		for (int i = 0 ; i < reservlist.size() ; i++) {
			Reservation reserv = reservlist.get(i);
			StringBuilder st =  new StringBuilder();
			st.append(reserv.getReservStatus());
			st.append(SEPARATOR);
			st.append(reserv.getReservCode());
			st.append(SEPARATOR);
			if (reserv.getRoom() != null)
				st.append(reserv.getRoom().getRoomNumber()); // room store by room number
			else
				st.append("0000");
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
		write("reservation.txt", alw);
	}
	
	public void write(String fileName, List<String> data) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(fileName));
			for (String s: data)
				out.println(s);
			out.close();
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
	
	public ArrayList<String> read(String fileName) {
		ArrayList<String> data = new ArrayList<>();
	    try {
	    	Scanner scanner = new Scanner(new FileInputStream(fileName));
	    	while (scanner.hasNextLine())
	    		data.add(scanner.nextLine());
	    	scanner.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    return data;
	}
	
	public void removeReservRecord(String reservCode)
	{
		for(Reservation reserv: reservlist)
		{
			if(reserv.getReservCode().equals(reservCode))
			{
				reservlist.remove(reserv);
				System.out.printf("The reservation record %s is successfully removed.\n",reservCode);
				writeReservation();
				return;
			}
		}
		System.out.printf("The reservation record %s is not found.\n",reservCode);
	}

	public String createReservCode() {
		Random rand = new Random();
		char c = (char) (rand.nextInt(26) + 'A');
		Integer i = rand.nextInt(1000);
		return c + Integer.toString(i);
	}
	
	public void addReserv(Reservation reserv) {
		reservlist.add(reserv);
	}
	
	public void updateCheckInDate(String reservCode, String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Reservation reserv = searchReserv(reservCode);
		try {
			LocalDate checkOutDate =  reserv.getCheckOutDate();
			LocalDate checkInDate = LocalDate.parse(date, formatter);
			if (reserv.checkDates(checkInDate, checkOutDate))
				reserv.setCheckInDate(checkInDate);
		} catch (DateTimeParseException e) {
			System.out.println("The date is invalid.");
			return;
		}
		System.out.println("Check-out Date updated to " + date);
	}
	
	public void updateCheckOutDate(String reservCode, String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Reservation reserv = searchReserv(reservCode);
		try {
			LocalDate checkInDate =  reserv.getCheckInDate();
			LocalDate checkOutDate = LocalDate.parse(date, formatter);
			if (reserv.checkDates(checkInDate, checkOutDate))
				reserv.setCheckOutDate(checkOutDate);
		} catch (DateTimeParseException e) {
			System.out.println("The date is invalid.");
			return;
		}
		System.out.println("Check-out Date updated to " + date);
	}
	
	public Reservation searchReserv(String code) {
		for(Reservation reserv: reservlist)
		{
			if(reserv.getReservCode().equals(code))
				return reserv;
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
	
	public void reservationUI(ReservationManager resm, RoomManager rm, GuestManager gm, Scanner sc) {
		String choice;
		do {
			System.out.println("-----------------------------\n"
							 + "Reservation Menu:\n"
							 + "(1) Create Reservation.\n"
							 + "(2) Update Reservation.\n"
							 + "(3) Remove Reservation.\n"
							 + "(4) Print Reservation Status.\n"
							 + "(5) Exit\n"
							 + "-----------------------------");
			choice = sc.nextLine();
			switch(choice) {
			case "1":
				System.out.println("Creating reservation...");
				Reservation reserv = new Reservation();
				
				// generate reservation code
				String reservCode = resm.createReservCode();
				reserv.setReservCode(reservCode);
				
				// create guest
				Guest g = gm.createGuestUI(gm, sc);
				if (g == null) return;
				reserv.setGuest(g);
				g.setReservCode(reservCode);
				
				// assign room for guest
				while (true) {
					try {
						System.out.print("Options: SINGLE, DOUBLE, SUITE, VIP_SUITE\n"
								   	   + "Enter the room type: ");
						RoomType roomType = RoomType.valueOf(sc.nextLine().toUpperCase());
						System.out.print("Options: TWIN, QUEEN, KING\n"
								   	   + "Enter the bed type: ");
						BedType bedType = BedType.valueOf(sc.nextLine().toUpperCase());
						Room r = rm.getAvailableRoom(roomType, bedType);
						if (r != null) {
							reserv.setRoom(r);
							reserv.setRType(roomType);
							reserv.setBType(bedType);
							r.setRoomStatus(RoomStatus.RESERVED);
							g.setRoomNum(r.getRoomNumber());
							break;
						}
					} catch (IllegalArgumentException e) {
						System.out.println("Invalid type entered.");
						continue;
					}
				}
				
				try {
					// get check-in and check-out date
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					System.out.println("Enter Check-in Date (dd/mm/yyyy): ");
					reserv.setCheckInDate(LocalDate.parse(sc.nextLine(), formatter));
					System.out.println("Enter Check-out Date (dd/mm/yyyy): ");
					reserv.setCheckOutDate(LocalDate.parse(sc.nextLine(), formatter));
					
					// get number of adults/children
					System.out.println("Enter the Number of Adults: ");
					reserv.setNumAdult(Integer.parseInt(sc.nextLine()));
					System.out.println("Enter the Number of Children: ");
					reserv.setNumChild(Integer.parseInt(sc.nextLine()));
				} catch (NumberFormatException e) {
					System.out.println("Invalid number entered.");
					return;
				} catch (DateTimeParseException e) {
					System.out.println("The date is invalid.");
					return;
				}
				
				// success
				reserv.setReservStatus(ReservStatus.CONFIRMED);
				resm.addReserv(reserv);
				System.out.println("Reservation successfully created with code " + reservCode);
				break;
			case "2":
				System.out.println("Updating reservation...");
				updateReservationUI(resm, rm, gm, sc);
				break;
			case "3":
				System.out.println("Enter reservation code to remove:");
				resm.removeReservRecord(sc.nextLine());
				break;
			case "4":
				System.out.println("Enter reservation code to search:");
				String reservCode1 = sc.nextLine();
				Reservation reserv1 = resm.searchReserv(reservCode1);
				if (reserv1 != null)
					reserv1.printReceipt();
				break;
			case "5":
				resm.writeReservation();
				break;
			default:
				System.out.println("Invalid option.");
			}
		} while (!choice.equals("5"));
	}
	
	public void updateReservationUI(ReservationManager resm, RoomManager rm, GuestManager gm, Scanner sc) {
		System.out.println("Update reservation...");
		System.out.println("Please enter the reservation code: ");
		String code = sc.nextLine();

		// find the target reservation
		Reservation target = resm.searchReserv(code);
		if (target == null) return; // target does not exist

		// update
		System.out.println("--------------------------------------------------\n"
						 + "Please choose the detail you would like to update."
						 + "(1): Check-in date\n"
						 + "(2): Check-out date\n"
						 + "(3): Room\n"
						 + "(4): Number of Adults\n"
						 + "(5): Number of Children\n"
						 + "--------------------------------------------------");
		switch (sc.nextLine()) {
		case "1":
			System.out.println("Enter Check-in Date (dd/mm/yyyy): ");
			resm.updateCheckInDate(code, sc.nextLine());
			break;
		case "2":
			System.out.println("Enter Check-out Date (dd/mm/yyyy): ");
			resm.updateCheckOutDate(code, sc.nextLine());
			break;
		case "3":
			RoomType roomType;
			BedType bedType;
			while (true) {
				try {
					System.out.println("Enter the room type: ");
					roomType = RoomType.valueOf(sc.nextLine().toUpperCase());
					System.out.println("Enter the bed type: ");
					bedType = BedType.valueOf(sc.nextLine().toUpperCase());
				} catch (IllegalArgumentException e) {
					System.out.println("Invalid type entered.");
					return;
				}
				Room r = rm.getAvailableRoom(roomType, bedType);
				if (r != null) {
					target.setRoom(r);
					break;
				}
			}
			System.out.println("The room has been successfully updated.");
			break;
		case "4":
			try {
				System.out.println("Enter the Number of Adults: ");
				target.setNumAdult(Integer.parseInt(sc.nextLine()));
			} catch (NumberFormatException e) {
				System.out.println("Invalid number entered.");
				return;
			}
			System.out.println("Number of Adults updated to " + target.getNumAdult());
			break;
		case "5":
			try {
				System.out.println("Enter the Number of Children: ");
				target.setNumChild(Integer.parseInt(sc.nextLine()));
			} catch (NumberFormatException e) {
				System.out.println("Invalid number entered.");
				return;
			}
			System.out.println("Number of Children updated to " + target.getNumChild());
			break;
		default:
			System.out.println("Invalid option.");
		}
	}
}