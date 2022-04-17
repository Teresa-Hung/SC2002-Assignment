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

/**
 * Contains control methods to manage the reservation records.
 * Implements ReadWrite interface to do file input and output.
 * @author TeresaHung
 * @version 1.0
 * @since 2022-04-16
 */
public class ReservationManager implements ReadWrite {

	/**
	 * The separator specifies the delimiter used in file input and output.
	 */
	public static final String SEPARATOR = "|";
	/**
	 * The reservation list contains all the records of reservation.
	 */
	private ArrayList<Reservation> reservlist;
	
	Scanner sc = new Scanner(System.in);
	GuestManager gm = new GuestManager();
	
	/**
	 * Sets the reservation list by reading from file.
	 */
	public ReservationManager()
	{
		reservlist = readReservation();
	}
	
	/**
	 * Gets the records of reservation list.
	 * @return reservation list.
	 */
	public ArrayList<Reservation> getReservList() {return reservlist;}
	
	/**
	 * Reads in the reservation record from file "reservation.txt" and creates the reservation list.
	 * @return reservation list.
	 */
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
	
	/**
	 * Output the records in reservation list to the file "reservation.txt".
	 */
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
	
	/**
	 * The method to output data to a file.
	 * @param fileName The name of the file to be written in.
	 * @param data The data to be written.
	 */
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
	
	/**
	 * The method to input data from a file.
	 * @param fileName The name of the file to be read from.
	 */
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
	
	/**
	 * Removes a reservation record from the file.
	 * @param reservCode The corresponding code of the reservation to be removed.
	 */
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

	/**
	 * Generates an unique reservation code randomly.
	 * The reservation code consists of an uppercase letter and a random number from 1 to 999.
	 * @return reservation code.
	 */
	public String createReservCode() {
		Random rand = new Random();
		char c = (char) (rand.nextInt(26) + 'A');
		Integer i = rand.nextInt(1000);
		return c + Integer.toString(i);
	}
	
	/**
	 * Adds a reservation record to the reservation list.
	 * @param reserv The reservation object to be added.
	 */
	public void addReserv(Reservation reserv) {
		reservlist.add(reserv);
	}
	
	// check check-in/out dates are valid
	/**
	 * Checks whether the scheduled length of stay is valid.
	 * Invalid length of stay refers that date of departure from the hotel is earlier than the date of arrival.
	 * @param dCI The check-in date.
	 * @param dCO The check-out date.
	 * @return true if the length of stay is valid; Otherwise, return false.
	 */
	public boolean checkDates(LocalDate dCI, LocalDate dCO) {
		if (dCI.compareTo(dCO) > 0) {
			System.out.println("The scheduled check-in and check-out dates are invalid.");
			return false;
		}
		return true;
	}
	
	/**
	 * Updates the date of check in of a reservation.
	 * @param reservCode The code of the reservation to be updated.
	 * @param date The new check-in date.
	 */
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
		System.out.println("Check-in Date updated to " + date);
	}
	
	/**
	 * Updates the date of check out of a reservation.
	 * @param reservCode The code of the reservation to be updated.
	 * @param date The new check-out date.
	 */
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
	
	/**
	 * Searches a reservation record by the reservation code.
	 * @param code The code of the target reservation.
	 * @return the corresponding reservation object or null if the reservation record is not found.
	 */
	public Reservation searchReserv(String code) {
		for(Reservation reserv: reservlist)
		{
			if(reserv.getReservCode().equals(code))
				return reserv;
		}
		System.out.printf("The reservation code %s does not exist.\n",code);
		return null;
	}
	
	/**
	 * Prints all Reservation in the reservation list.
	 * Reservation with status CHECKED_IN or EXPIRED will not be printed.
	 */
	public void printAllReserv()
	{
		System.out.println("-------------------------------------------------------\n"
				 + "Print All Reservation Records");
		for(Reservation reserv: reservlist)
		{
			if(reserv.getReservStatus() != ReservStatus.EXPIRED && reserv.getReservStatus()!=ReservStatus.CHECKED_IN)
				reserv.printReceipt();
		}
		System.out.println("-------------------------------------------------------\n");
	}

	/**
	 * Updates the reservation in waitlist.
	 * If there is any vacant room which meets the requested type, then assign the room to this reservation.
	 * Changes the reservation status to CONFIRMED when successfully assigned a room. 
	 * @param roomlist The list of hotel rooms.
	 */
	public void updateWaitlist(ArrayList<Room> roomlist)
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
	
	/**
	 * Checks in to the hotel.
	 * Guests are allowed to check in from 2 p.m. on the scheduled date of check in with a confirmed reservation. 
	 * @param reserv The associated reservation of check-in.
	 * @return true if check in successfully; Otherwise, return false.
	 */
	public boolean checkIn(Reservation reserv) {
		Timestamp now = Timestamp.from(Instant.now());
		Timestamp scheduledCheckIn = Timestamp.valueOf(reserv.getCheckInDate() + " 14:00:00"); // 2pm on check-in date
		if(reserv.getReservStatus() != ReservStatus.CONFIRMED)
		{
			System.out.println("The reservation is not confirmed.");
			return false;
		}
		if (now.before(scheduledCheckIn) == true) {
			System.out.println("It is not the scheduled check-in hours.");
			return false;
		}

		reserv.setReservStatus(ReservStatus.CHECKED_IN);
		reserv.getRoom().setRoomStatus(RoomStatus.OCCUPIED);
		System.out.println("Check in successfully.");
		return true;
	}

	/**
	 * Sets the status of the reservation to EXPIRED and free the room.
	 * The reservation will be expired if no one checks in by 2 a.m. on the next day of scheduled date of check in.
	 * @param reserv The reservation that is expired.
	 */
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
