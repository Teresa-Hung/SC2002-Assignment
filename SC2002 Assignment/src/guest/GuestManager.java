package guest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
}