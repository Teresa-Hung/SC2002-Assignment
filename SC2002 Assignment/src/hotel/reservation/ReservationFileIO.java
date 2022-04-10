package reservation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.time.*;

import reservation.Reservation.ReservStatus;
import guest.GuestManager;

enum UpdateType {RESERVSTATUS,ROOM,CHECKINDATE,CHECKOUTDATE,NUMADULT,NUMCHILD,NULL}

public class ReservationFileIO {
	public static final String SEPARATOR = "|";
	
	public static ArrayList readReservation(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList alr = new ArrayList() ;// to store Professors data

        for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","
				Reservation reserv = new Reservation();
				GuestManager gm = new GuestManager();
				//reserv.setReservStatus(ReservStatus.valueOf(star.nextToken().trim()));
				reserv.setReservCode(star.nextToken().trim());
				//reserv.setGuest(gm.findById(star.nextToken().trim(), guestlist));
				reserv.setCheckInDate(LocalDate.parse(star.nextToken().trim()));
				reserv.setCheckOutDate(LocalDate.parse(star.nextToken().trim()));
				reserv.setNumAdult(Integer.parseInt(star.nextToken().trim()));
				reserv.setNumChild(Integer.parseInt(star.nextToken().trim()));
				
				// add to Professors list
				alr.add(reserv) ;
			}
			return alr ;
	}
	
	public static void saveReservation(String filename, List al) throws IOException {
		List alw = new ArrayList() ;// to store Reservation data

        for (int i = 0 ; i < al.size() ; i++) {
				Reservation reserv = (Reservation)al.get(i);
				StringBuilder st =  new StringBuilder() ;
				//st.append(reserv.getReservStatus());
				//st.append(SEPARATOR);
				st.append(reserv.getReservCode());
				st.append(SEPARATOR);
				/*st.append(reserv.getRoom().getRoomNumber()); // room store by room number
				//st.append(SEPARATOR);
				st.append(reserv.getGuest().getId()); // guest store by guest id
				st.append(SEPARATOR);*/
				st.append(reserv.getCheckInDate());
				st.append(SEPARATOR);
				st.append(reserv.getCheckOutDate());
				st.append(SEPARATOR);
				st.append(reserv.getNumAdult());
				st.append(SEPARATOR);
				st.append(reserv.getNumChild());
				alw.add(st.toString()) ;
			}
			write(filename,alw);
	}
	
	public ArrayList removeReservRecord(ArrayList al)
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Remove reservation record...");
		System.out.println("Enter the reservation code: ");
		String reservCode = sc.next();
		
		for(int i=0; i<al.size();i++)
		{
			Reservation reserv = (Reservation) al.get(i);
			if(reserv.getReservCode().equals(reservCode))
			{
				al.remove(i);
				System.out.printf("The reservation record %s is successfully removed.",reservCode);
				return al;
			}
		}
		System.out.printf("The reservation record %s is not found.",reservCode);
		return al;
	}
	
	public static void write(String fileName, List data) throws IOException  {
	    PrintWriter out = new PrintWriter(new FileWriter(fileName));

	    try {
			for (int i =0; i < data.size() ; i++) {
	      		out.println((String)data.get(i));
			}
	    }
	    finally {
	      out.close();
	    }
	  }
	
	public static List read(String fileName) throws IOException {
		List data = new ArrayList() ;
	    Scanner scanner = new Scanner(new FileInputStream(fileName));
	    try {
	      while (scanner.hasNextLine()){
	        data.add(scanner.nextLine());
	      }
	    }
	    finally{
	      scanner.close();
	    }
	    return data;
	  }
}
