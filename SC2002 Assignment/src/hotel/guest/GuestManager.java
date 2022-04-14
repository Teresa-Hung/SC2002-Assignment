package guest;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class GuestManager {
	
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
			guest.setEmail(star.nextToken().trim());
			guest.setCountry(star.nextToken().trim());
			guest.setGender(star.nextToken().trim());
			guest.setNatlity(star.nextToken().trim());
			guest.setHolderFName(star.nextToken().trim());
			guest.setHolderLName(star.nextToken().trim());
			guest.setCcNum(star.nextToken().trim());
			guest.setExpDate(star.nextToken().trim());
			guest.setBillAddr(star.nextToken().trim());
			guest.setPaid(Integer.parseInt(star.nextToken().trim()));
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
			st.append(guest.getEmail());
			st.append(SEPARATOR);
			st.append(guest.getCountry());
			st.append(SEPARATOR);
			st.append(guest.getGender());
			st.append(SEPARATOR);
			st.append(guest.getNatlity());
			st.append(SEPARATOR);
			st.append(guest.getHolderFName());
			st.append(SEPARATOR);
			st.append(guest.getHolderLName());
			st.append(SEPARATOR);
			st.append(guest.getCcNum());
			st.append(SEPARATOR);
			st.append(guest.getExpDate());
			st.append(SEPARATOR);
			st.append(guest.getBillAddr());
			st.append(SEPARATOR);
			st.append(guest.getPaid());
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
	
	public ArrayList<Guest> getGuestList() {
		return guestList;
	}
	
	
	public Guest findById(String id) {
		for(Guest g: guestList){
			if(g.getId().toLowerCase().equals(id.toLowerCase())
			   return g;
		}
		return null;
	}
	
	
	public Guest findByName(String firstName, String lastName) {
		for(Guest g: guestList) {
			if(g.getFName().toLowerCase().equals(firstName.toLowerCase()) && g.getLName().toLowerCase().equals(lastName.toLowerCase()))
				return g;
		}
		return null;
	}		   
		
	public Guest createGuest() {
		try {
			Guest guest = new Guest();
			System.out.println("Create Guest Details. ");
			System.out.println("Please enter details accordingly");
			System.out.println("First Name: ");
			guest.setFName(sc.next());
			System.out.println("Last Name: ");
			guest.setLName(sc.next());
			System.out.println("ID (Passport/Driving License): ");
			guest.setId(sc.next());
			System.out.println("Contact Number: ");
			guest.setContact(sc.next());
			System.out.println("Email: ");
			guest.setEmail(sc.next());
			System.out.println("Country: ");
			guest.setCountry(sc.next());
			System.out.println("Gender: ");
			guest.setGender(sc.next());
			System.out.println("Nationality: ");
			guest.setNatlity(sc.next());
			System.out.println("Holder name (first and last name): ");
			guest.setHolderFName(sc.next());
			guest.setHolderLName(sc.next());
			System.out.println("Credit Card Number: ");
			guest.setCcNum(sc.next());
			System.out.println("Expiry Date(dd/mm/yyyy): ");
			String expdate = LocalDate.parse(sc.next(), formatter);
			guest.setExpDate(expdate);
			sc.nextLine();
			System.out.println("Billing Address: ");
			String s = sc.nextLine();
			guest.setBillAddr(s);
			System.out.println("Details created!");
			guestList.add(guest);
			return guest;
		}
		
		catch(DateTimeParseException e)
		{
			System.out.println("The date is invalid.");
			return null;
		}
	}
	
	
	public Guest searchGuest() { //search by 1. id, 2. name
		System.out.println("Search Details.");
		if(guestList.size() == 0) {
			System.out.println("There is no guest registered in the hotel. ");
			return null;
		}
		System.out.println("(1) Search by ID.");
		System.out.println("(2) Search by name.");
		int a = sc.nextInt();
		
		if(a>2) {
			System.out.println("Please enter valid option.");
			System.out.println("(1) Search by ID.");
			System.out.println("(2) Search by name.");
			a = sc.nextInt();
		}
		Guest tempGuest = null;
		switch(a) {
			case 1:
				System.out.println("Enter ID: ");
				String tempId = sc.next();
				tempGuest = findById(tempId, guestList);
				break;
			case 2:
				System.out.println("Enter first name and last name: ");
				String fname = sc.next();
				String lname = sc.next();
				tempGuest = findByName(fname, lname, guestList);
				break;
			
		}
		
		if(tempGuest != null) {
			return tempGuest; //then print guest details in main func
		}
		else {
			return null;
		}
	}
	public Guest updateDetails() {
		public Guest updateDetails() {
		System.out.println("Update Details.");
		if(guestList.size() == 0) {
			System.out.println("There is no guest registered in the hotel. ");
			return null;
		}
		System.out.println("Enter Guest ID:");
		String tempid = sc.next();
		Guest curGuest = findById(tempid, guestList); 
		if(curGuest == null) {
			System.out.println("Guest is not found.");
			return null;
		}
		return updateGuest(curGuest);
	}
		
	public Guest updateGuest(Guest g)
	{
		try{
		System.out.println("(1) Update first and last name.");
		System.out.println("(2) Update ID.");
		System.out.println("(3) Update contact number.");
		System.out.println("(4) Update email.");
		System.out.println("(5) Update country.");
		System.out.println("(6) Update gender.");
		System.out.println("(7) Update nationality.");
		System.out.println("(8) Update credit card details.");
		System.out.println("Enter your option: ");
		int choice = sc.nextInt();
		switch(choice) {
			case 1:
				System.out.println("Enter new first name: ");
				g.setFName(sc.next());
				System.out.println("Enter new last name: ");
				g.setLName(sc.next());
				break;
			case 2:
				System.out.println("Enter new ID (Passport/Driving License): ");
				g.setId(sc.next());
				break;
			case 3:
				System.out.println("Enter new contact number: ");
				g.setContact(sc.next());
				break;
			case 4:
				System.out.println("Enter new email: ");
				g.setEmail(sc.next());
				break;
			case 5:
				System.out.println("Enter new country: ");
				g.setCountry(sc.next());
				break;
			case 6:
				System.out.println("Update gender: ");
				g.setGender(sc.next());
				break;
			case 7:
				System.out.println("Enter new nationality: ");
				g.setNatlity(sc.next());
				break;
			case 8:
				try {
					System.out.println("Enter new holder name: ");
					g.setHolderFName(sc.next());
					g.setHolderLName(sc.next());
					System.out.println("Enter new credit card number: ");
					g.setCcNum(sc.next());
					System.out.println("Enter new expiry date(dd/mm/yyyy): ");
					String d = LocalDate.parse(sc.next(), formatter);
					g.setExpDate(d);
					sc.nextLine();
					System.out.println("Enter new billing address: ");
					String s = sc.nextLine();
					g.setBillAddr(s);
					break;
				}
				catch(DateTimeParseException e)
				{
					System.out.println("The date is invalid.");
					return null;
				}
		}
		System.out.println("Guest details updated!");
		return g;
		}
		
	}
	
		
		
	public void displayGuestDetails(Guest g) {
		String fname, lname, ctry, gender, natlity, email, id, contactNum,rmNum,reservCode;
		String holderFname, holderLname, ccNum, expDate, billAddr;
		int paid;
		fname = g.getFName();
		lname = g.getLName();
		ctry = g.getCountry();
		gender = g.getGender();
		natlity = g.getNatlity();
		email = g.getEmail();
		contactNum = g.getContact();
		id = g.getId();
		rmNum = g.getRoomNum();
		reservCode = g.getReservCode();
		holderFname = g.getHolderFName();
		holderLname = g.getHolderLName();
		ccNum = g.getCcNum();
		expDate = g.getExpDate().toString();
		billAddr = g.getBillAddr();
		
		String strReplacement = "************";
        	String lastFourNum = ccNum.substring(ccNum.length() - 4);
        	String newString = strReplacement + lastFourNum;
		System.out.println("First Name: " + fname);
		System.out.println("Last Name: " + lname);
		System.out.println("ID: " + id);
		System.out.println("Contact Number: " + contactNum);
		System.out.println("Email: " + email);
		System.out.println("Country: " + ctry);
		System.out.println("Gender: " + gender);
		System.out.println("Nationality: " + natlity);
		System.out.println("Credit Card Details: ");
		System.out.println("Holder Name: " + holderFname + " " + holderLname);
		System.out.println("Credit Card Number: " + newString);
		System.out.println("Expiry Date: " + expDate);
		System.out.println("Billing Address: " + billAddr);
		System.out.println("Room Number: " + rmNum);
		System.out.println("Reservation Code: " + reservCode);

	}


}
