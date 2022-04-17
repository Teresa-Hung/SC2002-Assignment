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

/**
 * Contains control methods to manage the guest records.
 * Implements ReadWrite interface to do file input and output.
 * @author EVANGELINE NG XUAN HUI
 * @version 1.0
 * @since 2022-04-17
 */
public class GuestManager implements ReadWrite {
	
	/**
	 * The separator specifies the delimiter used in file input and output.
	 */
	public static final String SEPARATOR = "|";	
	/**
	 * The guest list contains all the records of guest details.
	 */
	private ArrayList<Guest> guestList; 
	Scanner sc = new Scanner(System.in);
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	/**
	 * Sets the guest list by reading from file.
	 */
	public GuestManager()
	{
		guestList = readGuests();
	}
	
	/**
	 * Reads in the guest records from file "guestListDetails.txt" and creates the guest list.
	 * @return guest list.
	 */
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
	
	/**
	 * Output the records in guest list to the file "guestListDetails.txt".
	 */
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

	/**
	 * The method to output data to a file.
	 * @param fileName The name of the file to be written in.
	 * @param data The data to be written.
	 */
	public void write(String fileName, List<String> data) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(fileName));
			for(int i=0;i<data.size();i++)
				out.println(data.get(i));
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
			while(scanner.hasNextLine())
				data.add(scanner.nextLine());
		} catch (IOException e){
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * Gets the records of guest list.
	 * @return guest list.
	 */
	public ArrayList<Guest> getGuestList() {
		return guestList;
	}
	
	/**
	 * Searches a guest record in the guest list by identification number.
	 * @param id The identification number of the guest to search.
	 * @return the found guest object, or null if not found.
	 */
	public Guest findById(String id) {
		for(Guest g: guestList)
			if(g.getId().toLowerCase().equals(id.toLowerCase()))
			   return g;
		return null;
	}
	
	/**
	 * Searches a guest record in the guest list by the guest's full name.
	 * @param firstName The guest's first name.
	 * @param lastName The guest's last name.
	 * @return the found guest object, or null if not found.
	 */
	public Guest findByName(String firstName, String lastName) {
		for(Guest g: guestList) {
			if(g.getFName().toLowerCase().equals(firstName.toLowerCase()) && g.getLName().toLowerCase().equals(lastName.toLowerCase()))
				return g;
		}
		return null;
	}
	
	/**
	 * Adds a guest record to the guest list.
	 * @param g The guest object to be added.
	 */
	public void addGuest(Guest g) {
		guestList.add(g);
	}

	/**
	 * Removes a guest record from the guest list.
	 * @param reservCode The corresponding reservation code of the guest to be removed.
	 */
	public void removeGuest(String reservCode) {
		for(Guest g: guestList)
			if(g.getReservCode().toLowerCase().equals(reservCode.toLowerCase())) {
			   guestList.remove(g);
			   System.out.println("Guest removed.");
			   return;
			}
		System.out.println("Guest does not exist.");
	}
	
	/**
	 * Searches a guest record in the guest list.
	 * Searches can be done by either the guest's id or full name.
	 * @param sc The scanner object to scan user's input.
	 */
	public void searchGuest(Scanner sc) { 
		if(guestList.size() == 0) {
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
				g = findById(tempId);
				break;
			case "2":
				System.out.println("Enter first name and last name: ");
				String fname = sc.next();
				String lname = sc.next();
				if (sc.hasNextLine()) 
					sc.nextLine();
				g = findByName(fname, lname);
				break;
			default:
				System.out.println("Invalid option.");
		}
		if(g != null) {
			System.out.println("Guest found.");
			displayGuestDetails(g);
			return;
		}
		else{
			System.out.println("Guest does not exist.");
			return;
		}
	}
	
	/**
	 * Updates a guest's details in the guest list and output to the file.
	 * Reads in the guest's id to access the guest record.
	 * @param sc The scanner object to scan user's input.
	 */
	public void updateGuest(Scanner sc) {
		System.out.print("Enter guest ID: ");
		String id = sc.nextLine();
		Guest g = findById(id);
		if (g == null) {
			System.out.println("Guest does not exist.");
			return;
		}
		displayGuestDetails(g);
		System.out.print("==============================\n"
					   + "     Update Guest Menu:\n"
					   + "==============================\n"
					   + "(1) Update first and last name\n"
					   + "(2) Update ID\n"
					   + "(3) Update contact number\n"
					   + "(4) Update email\n"
					   + "(5) Update country\n"
					   + "(6) Update gender\n"
					   + "(7) Update nationality\n"
					   + "(8) Update credit card details\n"
					   + "(9) Return to Guest Menu\n"
					   + "===============================\n"
					   + "Enter option: ");
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
		saveGuest();
	}
	
	/**
	 * Creates a guest record in the guest list.
	 * @param sc The scanner object to scan user's input.
	 * @return the created guest record, or null if not successful.
	 */
	public Guest createGuest(Scanner sc) {
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
			if (sc.hasNextLine()) sc.nextLine();
			System.out.println("Billing Address: ");
			String s = sc.nextLine();
			guest.setBillAddr(s);
			System.out.println("Details created!");
			guestList.add(guest);
			return guest;
		} catch (DateTimeParseException e) {
			System.out.println("The date is invalid.");
		}
		return null;
	}
	
	/**
	 * Displays the details of a guest record.
	 * The information includes the guest's full name, id, country, gender, nationality, 
	 * the billing details, the corresponding reservation code, and the number of the room the guest is staying 
	 * @param g The guest to display details.
	 */
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
	
	/**
	 * Displays the billing details of a guest.
	 * The information include the credit card holder's name, card number and the billing address.
	 * @param g The guest to display billing details.
	 */
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
