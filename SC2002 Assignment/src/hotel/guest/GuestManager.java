package guest;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class GuestManager {
	
	ArrayList<Guest> guestList = new ArrayList<Guest>();
	Scanner sc = new Scanner(System.in);
	
	public GuestManager() {
		guestList = new ArrayList<Guest>();
	}
	public ArrayList<Guest> getList(){
		return guestList;
	}
	
	public Guest findById(String id, ArrayList<Guest> list ) {
		for(Guest guest: list) {
			if(guest.getId().toLowerCase().equals(id))
				return guest;
		}
		return null;
	}
	
	public Guest findByName(String fName, String lName, ArrayList<Guest> list) {
		for(Guest guest: list) {
			if(guest.getFName().toLowerCase().equals(fName) && guest.getLName().toLowerCase().equals(lName))
				return guest;
		}
		return null;
	}
	
	public Guest createGuest() {
		Guest guest = new Guest();
		CreditCardDetails ccDetail = new CreditCardDetails();
		try {
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
			System.out.println("Email Address: ");
			guest.setEmail(sc.next());
			System.out.println("Country: ");
			guest.setCountry(sc.next());
			System.out.println("Gender: ");
			guest.setGender(sc.next());
			System.out.println("Nationality: ");
			guest.setNatlity(sc.next());
			System.out.println("Credit Card Details: ");
			System.out.println("Holder name (first and last name): ");
			ccDetail.setHolderFName(sc.next());
			ccDetail.setHolderLName(sc.next());
			System.out.println("Credit Card Number: ");
			ccDetail.setCcNum(sc.next());
			System.out.println("Expiry Date(dd/mm/yyyy): ");
			ccDetail.setExpDate(sc.next());
			sc.nextLine();
			System.out.println("Billing Address: ");
			String s = sc.nextLine();
			String [] bilAdr = s.split("\\s");
			ccDetail.setBillAddr(bilAdr);
			guest.setCC(ccDetail);
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
				tempGuest = findById(tempId.toLowerCase(), guestList);
				break;
			case 2:
				System.out.println("Enter first name and last name: ");
				String fname = sc.next();
				String lname = sc.next();
				tempGuest = findByName(fname.toLowerCase(), lname.toLowerCase(), guestList);
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
				CreditCardDetails ccDetail = new CreditCardDetails();
				try {
					System.out.println("Enter new holder name: ");
					ccDetail.setHolderFName(sc.next());
					ccDetail.setHolderLName(sc.next());
					System.out.println("Enter new credit card number: ");
					ccDetail.setCcNum(sc.next());
					System.out.println("Enter new expiry date(dd/mm/yyyy): ");
					ccDetail.setExpDate(sc.next());
					sc.nextLine();
					System.out.println("Enter new billing address: ");
					String s = sc.nextLine();
					String [] bilAdr = s.split("\\s");
					ccDetail.setBillAddr(bilAdr);
					g.setCC(ccDetail);
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
	
	
	public void displayGuestDetails(Guest g) {
		CreditCardDetails ccDetail = g.getCC();
		System.out.println("Display Guest Details.");
		String fname, lname, email, ctry, gender, natlity, id, contactNum;
		String holderFname, holderLname, ccNum;
		String expDate;
		String[] billAddr;
		fname = g.getFName();
		lname = g.getLName();
		email = g.getEmail();
		ctry = g.getCountry();
		gender = g.getGender();
		natlity = g.getNatlity();
		contactNum = g.getContact();
		id = g.getId();
		holderFname = ccDetail.getHolderFName();
		holderLname = ccDetail.getHolderLName();
		ccNum = ccDetail.getCcNum();
		expDate = ccDetail.getExpDate().toString();
		billAddr = ccDetail.getBillAddr();
		String strReplacement = "************";
        String lastFourNum = ccNum.substring(ccNum.length() - 4);
        String newString = strReplacement + lastFourNum;
		System.out.println("First Name: " + fname);
		System.out.println("Last Name: " + lname);
		System.out.println("Email: " + email);
		System.out.println("Country: " + ctry);
		System.out.println("Gender: " + gender);
		System.out.println("Nationality: " + natlity);
		System.out.println("ID: " + id);
		System.out.println("Contact Number: " + contactNum);
		System.out.println("Credit Card Details: ");
		System.out.println("Holder Name: " + holderFname + " " + holderLname);
		System.out.println("Credit Card Number: " + newString);
		System.out.println("Expiry Date: " + expDate);
		System.out.print("Billing Address: ");
		for(String w:billAddr){  
			System.out.print(w + " ");
			}
		System.out.println("\n");
	}


}
