package sc2002Proj;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import reservation.Reservation.ReservStatus;

public class GuestFileIO {
	public static final String SEPARATOR = "|";
	
	public static ArrayList readGuests(String filename) throws IOException {
		//read string from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList alr = new ArrayList(); //store guests data
		
		for(int i=0;i < stringArray.size();i++) {
			String st = (String)stringArray.get(i);
			//get individual attributes of the string separated by SEPARATOR
			StringTokenizer star = new StringTokenizer(st, SEPARATOR);
			Guest guest = new Guest();
			CreditCardDetails ccdetail = new CreditCardDetails();
			guest.setFName(star.nextToken().trim());
			guest.setLName(star.nextToken().trim());
			guest.setId(star.nextToken().trim());
			guest.setContact(star.nextToken().trim());
			guest.setCountry(star.nextToken().trim());
			guest.setGender(star.nextToken().trim());
			guest.setNatlity(star.nextToken().trim());
			ccdetail.setHolderFName(star.nextToken().trim());
			ccdetail.setHolderLName(star.nextToken().trim());
			ccdetail.setCcNum(star.nextToken().trim());
			ccdetail.setExpDate(star.nextToken().trim());
			ccdetail.setBillAddr(star.nextToken().trim());
			guest.setCC(ccdetail);
			//guest.setReservStatus(ReservStatus.valueOf(star.nextToken().trim()));
			//add to guest list
			alr.add(guest);
		}
		return alr;
	}
	
	public static void saveGuest(String filename, List al) throws IOException {
		List alw = new ArrayList();
		
		for(int i=0;i<al.size();i++) {
			Guest guest = (Guest)al.get(i);
			StringBuilder st =  new StringBuilder();
			st.append(guest.getFName());
			st.append(SEPARATOR);
			st.append(guest.getLName());
			st.append(SEPARATOR);
			st.append(guest.getId());
			st.append(SEPARATOR);
			st.append(guest.getContact());
			st.append(SEPARATOR);
			st.append(guest.getCountry());
			st.append(SEPARATOR);
			st.append(guest.getGender());
			st.append(SEPARATOR);
			st.append(guest.getNatlity());
			st.append(SEPARATOR);
			st.append(guest.getCC());
			st.append(SEPARATOR);
			st.append(guest.getReservStatus());
			st.append(SEPARATOR);
			alw.add(st.toString()) ;
		}
		write(filename,alw);
	}
	
	public ArrayList removeGuest(ArrayList al) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Remove guest record...");
		System.out.println("Enter guest ID: ");
		String guestId = sc.next();
		
		for(int i=0;i<al.size();i++) {
			Guest guest = (Guest)al.get(i);
			if(guest.getId().equals(guestId)) {
				al.remove(i);
				System.out.printf("The guest %s is successfully removed.",guestId);
				return al;
			}
		}
		System.out.println("Guest is not found.");
		return al;
	}
	
	public static void write(String filename, List data) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		
		try {
			for(int i=0;i<data.size();i++) {
				out.println((String)data.get(i));
			}
		}
		finally {
			out.close();
		}
	}
	
	public static List read(String filename) throws IOException {
		List data = new ArrayList();
		Scanner scanner = new Scanner(new FileInputStream(filename));
		try {
			while(scanner.hasNextLine()) {
				data.add(scanner.nextLine());
			}
		}
		finally {
			scanner.close();
		}
		return data;
	}

}