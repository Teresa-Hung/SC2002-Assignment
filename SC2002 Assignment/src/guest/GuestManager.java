package guest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.StringTokenizer;
import main.FileIO.ReadWrite;

public class GuestManager implements ReadWrite {
	
	public static final String SEPARATOR = "|";	
	private ArrayList<Guest> guestList;
	Scanner sc = new Scanner(System.in);
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public GuestManager()
	{
		guestList = readGuests();
	}
	
	public ArrayList<Guest> readGuests(){
		ArrayList<Guest> alr = new ArrayList<>(); //store guests data
		
		//read string from text file
		ArrayList<String> stringArray = read("guestListDetails.txt");
	
		for(int i=0;i < stringArray.size();i++) {
			String st = (String)stringArray.get(i);
			//get individual attributes of the string separated by SEPARATOR
			StringTokenizer star = new StringTokenizer(st, SEPARATOR);
			Guest guest = new Guest();
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
			guest.setExpDate(LocalDate.parse(star.nextToken().trim()));
			guest.setBillAddr(star.nextToken().trim());
			guest.setRoomNum(star.nextToken().trim());
			guest.setReservCode(star.nextToken().trim());
			guest.setPaid(Integer.parseInt(star.nextToken().trim()));
			alr.add(guest);
		}
		return alr;
	}
	
	public void saveGuest() {
		ArrayList<String> alw = new ArrayList<>();
		
		for(int i=0;i<guestList.size();i++) {
			Guest guest = guestList.get(i);
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
			st.append(guest.getRoomNum());
			st.append(SEPARATOR);
			st.append(guest.getReservCode());
			st.append(SEPARATOR);
			st.append(guest.getPaid());
			st.append(SEPARATOR);
			alw.add(st.toString());
		}
		write("guestListDetails.txt", alw);
	}

	public void write(String fileName, List<String> data) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(fileName));
			for(int i=0;i<data.size();i++) {
				out.println(data.get(i));
			out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> read(String fileName) {
		ArrayList<String> data = new ArrayList<>();
		try {
			Scanner scanner = new Scanner(new FileInputStream(fileName));
			while(scanner.hasNextLine()) {
				data.add(scanner.nextLine());
			scanner.close();
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		return data;
	}
	
	public ArrayList<Guest> getGuestList() {
		return guestList;
	}
	
	public Guest findById(String id) {
		for(Guest g: guestList)
			if(g.getId().toLowerCase().equals(id.toLowerCase()))
			   return g;
		return null;
	}
	
	public Guest findByName(String firstName, String lastName) {
		for(Guest g: guestList) {
			if(g.getFName().toLowerCase().equals(firstName.toLowerCase()) && g.getLName().toLowerCase().equals(lastName.toLowerCase()))
				return g;
		}
		return null;
	}
	
	public void addGuest(Guest g) {
		guestList.add(g);
	}

	public void removeGuest(String id) {
		for(Guest g: guestList)
			if(g.getId().toLowerCase().equals(id.toLowerCase())) {
			   guestList.remove(g);
			   System.out.println("Guest removed.");
			   return;
			}
		System.out.println("Guest does not exist.");
	}
	
	public void guestUI(GuestManager gm, Scanner sc) {
		String choice;
		do {
			System.out.print("-------------------------\n"
						   + "Guest Menu:\n"
						   + "(1) Update Guest Details.\n"
						   + "(2) Search Guest.\n"
						   + "(3) Remove Guest.\n"
						   + "(4) Exit.\n"
						   + "-------------------------\n"
						   + "Enter option: ");
			choice = sc.nextLine();
			switch(choice) {
				case "1":
					updateGuest(gm, sc);
					break;
				case "2":
					searchGuest(gm, sc);
					break;
				case "3":
					System.out.print("Enter guest ID to remove: ");
					gm.removeGuest(sc.nextLine());
					break;
				case "4":
					gm.saveGuest();
					break;
				default:
					System.out.println("Invalid option.");
			}
		} while (!choice.equals("4"));
	}
	
	public void searchGuest(GuestManager gm, Scanner sc) { 
		if(gm.getGuestList().size() == 0) {
			System.out.println("There are no guests registered in the hotel.");
			return;
		}
		System.out.println("(1) Search by ID.");
		System.out.println("(2) Search by name.");
		Guest g = null;
		switch(sc.nextLine()) {
			case "1":
				System.out.print("Enter guest ID: ");
				String tempId = sc.nextLine();
				g = gm.findById(tempId);
				break;
			case "2":
				System.out.println("Enter first name and last name: ");
				String fname = sc.next();
				String lname = sc.next();
				if (sc.hasNextLine()) 
					sc.nextLine();
				g = gm.findByName(fname, lname);
				break;
			default:
				System.out.println("Invalid option.");
		}
		if(g != null) {
			System.out.println("Guest found.");
			g.displayGuestDetails();
			return;
		}
		else{
			System.out.println("Guest does not exist.");
			return;
		}
	}
	
	public void updateGuest(GuestManager gm, Scanner sc) {
		System.out.print("Enter guest ID: ");
		String id = sc.nextLine();
		Guest g = gm.findById(id);
		if (g == null) {
			System.out.println("Guest does not exist.");
			return;
		}
		g.displayGuestDetails();
		System.out.println("-------------------------------\n"
						 + "Update Guest Menu:\n"
						 + "(1) Update first and last name.\n"
						 + "(2) Update ID.\n"
						 + "(3) Update contact number.\n"
						 + "(4) Update email.\n"
						 + "(5) Update country.\n"
					 	 + "(6) Update gender.\n"
					 	 + "(7) Update nationality.\n"
			 			 + "(8) Update credit card details.\n"
			 			 + "(9) Exit\n"
			 			 + "-------------------------------\n"
		 				 + "Enter option:");
		switch(sc.nextLine()) {
			case "1":
				System.out.println("Enter new first name: ");
				g.setFName(sc.nextLine());
				System.out.println("Enter new last name: ");
				g.setLName(sc.nextLine());
				System.out.println("Guest name set to " + g.getFName() + " " + g.getLName());
				break;
			case "2":
				System.out.println("Enter new ID (Passport/Driving License): ");
				g.setId(sc.nextLine());
				System.out.println("New ID set to " + g.getId());
				break;
			case "3":
				System.out.println("Enter new contact number: ");
				g.setContact(sc.nextLine());
				System.out.println("New contact number set to " + g.getContact());
				break;
			case "4":
				System.out.println("Enter new email: ");
				g.setEmail(sc.nextLine());
				System.out.println("New email set to " + g.getEmail());
				break;
			case "5":
				System.out.println("Enter new country: ");
				g.setCountry(sc.nextLine());
				System.out.println("New country set to " + g.getCountry());
				break;
			case "6":
				System.out.println("Update gender: ");
				g.setGender(sc.nextLine());
				System.out.println("New gender set to " + g.getGender());
				break;
			case "7":
				System.out.println("Enter new nationality: ");
				g.setNatlity(sc.nextLine());
				System.out.println("New nationality set to " + g.getNatlity());
				break;
			case "8":
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				try {
					System.out.println("Enter new holder name: ");
					g.setHolderFName(sc.next());
					g.setHolderLName(sc.next());
					if (sc.hasNextLine()) sc.nextLine();
					System.out.println("Enter new credit card number: ");
					g.setCcNum(sc.nextLine());
					System.out.println("Enter new expiry date(dd/mm/yyyy): ");
					g.setExpDate(LocalDate.parse(sc.nextLine(), formatter));
					sc.nextLine();
					System.out.println("Enter new billing address: ");
					String s = sc.nextLine();
					g.setBillAddr(s);
					break;
				} catch (DateTimeParseException e) {
					System.out.println("The date is invalid.");
				}
			case "9":
				break;
			default:
				System.out.println("Invalid option.");
		}
		gm.saveGuest();
	}
	
	public Guest createGuest(GuestManager gm, Scanner sc) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			Guest guest = new Guest();
			System.out.println("Create Guest Details.");
			System.out.println("Please enter details accordingly");
			System.out.println("First Name: ");
			guest.setFName(sc.next());
			System.out.println("Last Name: ");
			guest.setLName(sc.next());
			if (sc.hasNextLine()) sc.nextLine();
			System.out.println("ID (Passport/Driving License): ");
			guest.setId(sc.nextLine());
			System.out.println("Contact Number: ");
			guest.setContact(sc.nextLine());
			System.out.println("Email: ");
			guest.setEmail(sc.nextLine());
			System.out.println("Country: ");
			guest.setCountry(sc.nextLine());
			System.out.println("Gender: ");
			guest.setGender(sc.nextLine());
			System.out.println("Nationality: ");
			guest.setNatlity(sc.nextLine());
			System.out.println("Credit Card Holder Name (first and last name): ");
			guest.setHolderFName(sc.next());
			guest.setHolderLName(sc.next());
			if (sc.hasNextLine()) sc.nextLine();
			System.out.println("Credit Card Number: ");
			guest.setCcNum(sc.next());
			System.out.println("Expiry Date(dd/mm/yyyy): ");
			guest.setExpDate(LocalDate.parse(sc.next(), formatter));
			sc.nextLine();
			System.out.println("Billing Address: ");
			String s = sc.nextLine();
			guest.setBillAddr(s);
			System.out.println("Details created!");
			gm.addGuest(guest);
			return guest;
		} catch (DateTimeParseException e) {
			System.out.println("The date is invalid.");
		}
		return null;
	}
	
	public void displayGuestDetails(Guest g) {
		String strReplacement = "************";
       		String lastFourNum = g.getCcNum().substring(g.getCcNum().length() - 4);
        	String newString = strReplacement + lastFourNum;
		System.out.println("First Name: " + g.getFName());
		System.out.println("Last Name: " + g.getLName());
		System.out.println("ID: " + g.getId());
		System.out.println("Contact Number: " + g.getContact());
		System.out.println("Email: " + g.getEmail());
		System.out.println("Country: " + g.getCountry());
		System.out.println("Gender: " + g.getGender());
		System.out.println("Nationality: " + g.getNatlity());
		System.out.println("Credit Card Details: ");
		System.out.println("Holder Name: " + g.getHolderFName() + " " + g.getHolderLName());
		System.out.println("Credit Card Number: " + newString);
		System.out.println("Expiry Date: " + g.getExpDate());
		System.out.println("Billing Address: " + g.getBillAddr());
		System.out.println("Reservation Code: " + g.getReservCode());
		System.out.println("Room Number: " + g.getRoomNum());
	}
	
	public void displayBillingDetails(Guest g) {
		String strReplacement = "************";
		String lastFourNum = g.getCcNum().substring(g.getCcNum().length() - 4);
        	String newString = strReplacement + lastFourNum;
		System.out.println("Billing Details: ");
		System.out.println("Holder Name: " + g.getHolderFName() + " " + g.getHolderLName());
		System.out.println("Credit Card Number: " + newString);
		System.out.println("Billing Address: " + g.getBillAddr());
	}
}
