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
}
