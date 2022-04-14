package room;

import room.Room.BedType;
import room.Room.RoomStatus;
import room.Room.RoomType;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.StringTokenizer;

public class RoomManager {
	public static final String SEPARATOR = "|";
	private ArrayList<Room> roomList = new ArrayList<>();
	
	public RoomManager(String fileName) {
		try {
			this.readRoomList(fileName);
		} catch (Exception e) {
			System.out.println("readRoomList() failed.");
		}
	}
	
	private void readRoomList(String fileName) {
	    try {
	    	Scanner sc = new Scanner(new FileInputStream(fileName));
	    	while (sc.hasNextLine()) {
				StringTokenizer star = new StringTokenizer(sc.nextLine() , SEPARATOR);
				String roomNumber = star.nextToken().trim();						// roomNumber
				RoomType roomType = RoomType.valueOf(star.nextToken().trim());		// roomType
				BedType bedType = BedType.valueOf(star.nextToken().trim());			// bedType
				RoomStatus roomStatus = RoomStatus.valueOf(star.nextToken().trim());// roomStatus
				boolean wifiEnabled = Boolean.parseBoolean(star.nextToken().trim());// wifiEnabled
				boolean smoking = Boolean.parseBoolean(star.nextToken().trim());	// smoking
				boolean balcony = Boolean.parseBoolean(star.nextToken().trim());	// balcony
				int maxSize = Integer.parseInt(star.nextToken().trim());			// maxSize
				
				// creating room object with details
				Room r = new Room(roomType, bedType, maxSize);
				r.setRoomNumber(roomNumber);
				r.setRoomStatus(roomStatus);
				r.setWifi(wifiEnabled);
				r.setSmoking(smoking);
				r.setBalcony(balcony);
				roomList.add(r);
	    	}
	    	sc.close();
	    } catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeRoomList(String fileName) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(fileName));
	        for (Room r: roomList) {
				StringBuilder st =  new StringBuilder();
				st.append(r.getRoomNumber().trim()); st.append(SEPARATOR);					// roomNumber
				st.append(r.getRoomType().name().trim()); st.append(SEPARATOR);				// roomType
				st.append(r.getBedType().name().trim()); st.append(SEPARATOR);				// bedType
				st.append(r.getRoomStatus().name().trim()); st.append(SEPARATOR);			// roomStatus
				st.append(Boolean.toString(r.isWifiEnabled()).trim()); st.append(SEPARATOR);// wifiEnabled
				st.append(Boolean.toString(r.isSmoking()).trim()); st.append(SEPARATOR);	// smoking
				st.append(Boolean.toString(r.hasBalcony()).trim()); st.append(SEPARATOR);	// balcony
				st.append(Integer.toString(r.getMaxSize())); st.append(SEPARATOR);			// maxSize
				out.println(st.toString());
			}
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Room> getRoomList() {
		return roomList;
	}
	
	public Room findRoom(String roomNumber) {
		return findRoom(roomNumber, true);
	}
	
	public Room findRoom(String roomNumber, boolean print) {
		for (Room r: roomList)
			if (roomNumber.equals(r.getRoomNumber()))
				return r;
		if (print) System.out.println("Room number does not exist.");
		return null;
	}
	
	public void printOccupancyReport() {
		ArrayList<Room> single = new ArrayList<>();
		ArrayList<Room> doublee = new ArrayList<>();
		ArrayList<Room> suite = new ArrayList<>();
		ArrayList<Room> vip_suite = new ArrayList<>();

		for (Room r: roomList) {
			switch(r.getRoomType()) {
			case SINGLE:
				single.add(r);
				break;
			case DOUBLE:
				doublee.add(r);
				break;
			case SUITE:
				suite.add(r);
				break;
			case VIP_SUITE:
				vip_suite.add(r);
				break;
			default:
				break;
			}
		}
		
		System.out.println("-------------------------------------------------------\n"
						 + "Print Room Occupancy Report");
		// print single room vacancy
		System.out.print("Single: ");
		printOccupancyUtil(single);
		// print double room vacancy
		System.out.print("Double: ");
		printOccupancyUtil(doublee);
		// print suite room vacancy
		System.out.print("Suite: ");
		printOccupancyUtil(suite);
		// print vip_suite room vacancy
		System.out.print("VIP Suite: ");
		printOccupancyUtil(vip_suite);
		System.out.println("-------------------------------------------------------\n");
	}
	
	private void printOccupancyUtil(ArrayList<Room> list) {
		int i = 0;
		ArrayList<Room> listv = new ArrayList<>();
		for (Room r: list)
			if (r.getRoomStatus() == RoomStatus.VACANT) {
				listv.add(r);
				i++;
			}
		System.out.println(i + " out of " + list.size() + " rooms are vacant.");
		if (!listv.isEmpty()) {
			listv.sort(new RoomComparator());
			for (i = 0; i < listv.size(); i++) {
				if (i == 0) System.out.print("\tRooms: ");
				if (i == listv.size() - 1) System.out.println(listv.get(i).getRoomNumber() + ".");
				else System.out.print(listv.get(i).getRoomNumber() + ",");
				if (i%8 == 7 && i != list.size()-1) System.out.print("\n\t       ");
			}
		}
	}
	
	public void printStatusReport() {
		ArrayList<Room> vacant = new ArrayList<>();
		ArrayList<Room> occupied = new ArrayList<>();
		ArrayList<Room> reserved = new ArrayList<>();
		ArrayList<Room> under_maintenance = new ArrayList<>();

		for (Room r: roomList) {
			switch(r.getRoomStatus()) {
			case VACANT:
				vacant.add(r);
				break;
			case OCCUPIED:
				occupied.add(r);
				break;
			case RESERVED:
				reserved.add(r);
				break;
			case UNDER_MAINTENANCE:
				under_maintenance.add(r);
				break;
			default:
				break;
			}
		}
		
		System.out.println("-------------------------------------------------------\n"
						 + "Print Room Status Report");
		// print vacant rooms
		System.out.println("Vacant:");
		if (vacant.isEmpty()) System.out.println("\tNo rooms are vacant.");
		else printRoomUtil(vacant);
		// print occupied rooms
		System.out.println("Occupied:");
		if (occupied.isEmpty()) System.out.println("\tNo rooms are occupied.");
		else printRoomUtil(occupied);
		// print reserved rooms
		System.out.println("Reserved:");
		if (reserved.isEmpty()) System.out.println("\tNo rooms are reserved.");
		else printRoomUtil(reserved);
		// print under maintenance rooms
		System.out.println("Under Maintenance:");
		if (under_maintenance.isEmpty()) System.out.println("\tNo rooms are under maintenance.");
		else printRoomUtil(under_maintenance);
		System.out.println("-------------------------------------------------------");
	}

	public void printRoomReport() {
		ArrayList<Room> wifiEnabled = new ArrayList<>();
		ArrayList<Room> wifiDisabled = new ArrayList<>();
		ArrayList<Room> smoking = new ArrayList<>();
		ArrayList<Room> nonSmoking = new ArrayList<>();
		ArrayList<Room> hasBalcony = new ArrayList<>();
		ArrayList<Room> noBalcony = new ArrayList<>();
		
		for (Room r: roomList) {
			if (r.isWifiEnabled()) wifiEnabled.add(r);
			else wifiDisabled.add(r);
			if (r.isSmoking()) smoking.add(r);
			else nonSmoking.add(r);
			if (r.hasBalcony()) hasBalcony.add(r);
			else noBalcony.add(r);
		}
		
		System.out.println("-------------------------------------------------------\n"
						 + "Print Room Report:");
		System.out.println("WiFi Enabled:");
		if (wifiEnabled.isEmpty()) System.out.println("\tNo rooms are WiFi Enabled.");
		else printRoomUtil(wifiEnabled);
		System.out.println("WiFi Disabled:");
		if (wifiDisabled.isEmpty()) System.out.println("\tNo rooms are WiFi Disabled.");
		else printRoomUtil(wifiDisabled);
		System.out.println("Smoking:");
		if (smoking.isEmpty()) System.out.println("\tNo rooms are smoking.");
		else printRoomUtil(smoking);
		System.out.println("Non-Smoking:");
		if (nonSmoking.isEmpty()) System.out.println("\tNo rooms are non-smoking.");
		else printRoomUtil(nonSmoking);
		System.out.println("Has Balcony:");
		if (hasBalcony.isEmpty()) System.out.println("\tNo rooms have balcony.");
		else printRoomUtil(hasBalcony);
		System.out.println("No Balcony:");
		if (noBalcony.isEmpty()) System.out.println("\tNo rooms have no balcony.");
		else printRoomUtil(noBalcony);
		System.out.println("-------------------------------------------------------");
	}
	
	private void printRoomUtil(ArrayList<Room> list) {
		list.sort(new RoomComparator());
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) System.out.print("\tRooms: ");
			if (i == list.size()-1) System.out.println(list.get(i).getRoomNumber() + ".");
			else System.out.print(list.get(i).getRoomNumber() + ",");
			if (i%8 == 7 && i != list.size()-1) System.out.print("\n\t       ");
		}
	}
	
	public void printRoomDetails(String roomNumber) {
		Room r = findRoom(roomNumber);
		if (r != null) {
			System.out.println("-------------------------------\n"
							 + "Print Room Details\n"
							 + "Room Number  : " + r.getRoomNumber() + "\n"
							 + "Room Type    : " + r.getRoomType() + "\n"
							 + "Bed Type     : " + r.getBedType() + "\n"
							 + "Room Status  : " + r.getRoomStatus() + "\n"
							 + "WiFi Enabled : " + r.isWifiEnabled() + "\n"
							 + "Smoking      : " + r.isSmoking() + "\n"
							 + "Has Balcony  : " + r.hasBalcony() + "\n"
							 + "Max Size     : " + r.getMaxSize() + " guests\n"
							 + "-------------------------------");
		}
	}
	
	public RoomStatus getRoomStatus(String roomNumber) {
		Room r = findRoom(roomNumber);
		if (r != null)
			return r.getRoomStatus();
		else
			return null;
	}
	
	public void updateRoomNumber(String roomNumber, String newRoomNumber) {
		Room r = findRoom(newRoomNumber, false);
		if (r != null) {
			System.out.println("Room number already exists.");
			return;
		}
		findRoom(roomNumber).setRoomNumber(newRoomNumber);
		System.out.println("Room number updated successfully.");
	}
	
	public void updateRoomType(String roomNumber, RoomType roomType) {
		Room r = findRoom(roomNumber);
		if (r != null) {
			r.setRoomType(roomType);
			System.out.println("Room type updated successfully.");
		}
	}
	
	public void updateBedType(String roomNumber, BedType bedType) {
		Room r = findRoom(roomNumber);
		if (r != null) {
			r.setBedType(bedType);
			System.out.println("Bed type updated successfully.");
		}
	}
	
	public void updateRoomStatus(String roomNumber, RoomStatus status) {
		Room r = findRoom(roomNumber);
		if (r != null) {
			r.setRoomStatus(status);
			System.out.println("Room status updated successfully.");
		}
	}
	
	public void updateRoomWifi(String roomNumber, boolean wifi) {
		Room r = findRoom(roomNumber);
		if (r != null) {
			r.setWifi(wifi);
			System.out.println("Room wifi updated successfully.");
		}
	}
	
	public void updateRoomBalcony(String roomNumber, boolean balc) {
		Room r = findRoom(roomNumber);
		if (r != null) {
			r.setBalcony(balc);
			System.out.println("Room balcony updated successfully.");
		}
	}
	
	public void updateRoomSmoking(String roomNumber, boolean sm) {
		Room r = findRoom(roomNumber);
		if (r != null) {
			r.setSmoking(sm);
			System.out.println("Room smoking updated successfully.");
		}
	}
	
	private void updateDetailsUI(Scanner sc) {
		System.out.print("Enter room number: ");
		Room r = findRoom(sc.nextLine());
		if (r == null) return;
		System.out.print("--------------------\n"
					   + "Update Details Menu:\n"
					   + "(1) Room Number\n"
					   + "(2) Room Type\n"
					   + "(3) Bed Type\n"
					   + "(4) Room Status\n"
					   + "(5) Toggle WiFi\n"
					   + "(6) Toggle Smoking\n"
					   + "(7) Toggle Balcony\n"
					   + "(8) Max Size\n"
					   + "--------------------\n"
					   + "Enter option: ");
		switch (sc.nextLine()) {
		case "1":
			System.out.print("Enter new room number: ");
			String newRoomNumber = sc.nextLine();
			r.setRoomNumber(newRoomNumber);
			break;
		case "2":
			System.out.print("Options: SINGLE, DOUBLE, SUITE, VIP_SUITE\n"
						   + "Enter new room type: ");
			try {
				RoomType newRoomType = RoomType.valueOf(sc.nextLine().toUpperCase());
				r.setRoomType(newRoomType);
				System.out.println("Room type set to " + newRoomType);
			} catch (Exception e) {
				System.out.println("Invalid room type.");
			}
			break;
		case "3":
			System.out.print("Options: TWIN, QUEEN, KING\n"
						   + "Enter new bed type: ");
			try {
				BedType newBedType = BedType.valueOf(sc.nextLine().toUpperCase());
				r.setBedType(newBedType);
				System.out.println("Bed type set to " + newBedType);
			} catch (Exception e) {
				System.out.println("Invalid bed type.");
			}
			break;
		case "4":
			System.out.print("Options: VACANT, OCCUPIED, RESERVED, UNDER_MAINTENANCE\n"
						   + "Enter new room status: ");
			try {
				RoomStatus newRoomStatus = RoomStatus.valueOf(sc.nextLine().toUpperCase());
				r.setRoomStatus(newRoomStatus);
				System.out.println("Room status set to " + newRoomStatus);
			} catch (Exception e) {
				System.out.println("Invalid room status.");
			}
			break;
		case "5":
			r.setWifi(!r.isWifiEnabled());
			System.out.println("WiFi Enabled set to " + r.isWifiEnabled() + ".");
			break;
		case "6":
			r.setSmoking(!r.isSmoking());
			System.out.println("Smoking set to " + r.isSmoking() + ".");
			break;
		case "7":
			r.setBalcony(!r.hasBalcony());
			System.out.println("Balcony set to " + r.hasBalcony() + ".");
			break;
		case "8":
			System.out.print("Enter new max size: ");
			try {
				int i = Integer.parseInt(sc.nextLine());
				r.setMaxSize(i);
				System.out.println("Max size set to " + i);
			} catch (NumberFormatException e) {
				System.out.println("Invalid size.");
			}
			break;
		default:
			System.out.println("Invalid option.");
			break;
		}
	}
	
	public void roomUI() {
		String choice;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("-------------------------------\n"
							 + "Room Menu:\n"
			        		 + "(1) Print Room Occupancy Report\n"
			        		 + "(2) Print Room Status Report\n"
			        		 + "(3) Print Room Report\n"
			        		 + "(4) Print Room Details\n"
			        		 + "(5) Update Room Details\n"
			        		 + "(6) Exit\n"
			        		 + "-------------------------------");
			choice = sc.nextLine();
			switch (choice) {
			case "1":
				printOccupancyReport();
				break;
			case "2":
				printStatusReport();
				break;
			case "3":
				printRoomReport();
				break;
			case "4":
				System.out.print("Enter room number: ");
				printRoomDetails(sc.nextLine());
				break;
			case "5":
				updateDetailsUI(sc);
				break;
			case "6":
				writeRoomList("48_Hotel_Rooms.txt");
				break;
			default:
				System.out.println("Invalid option.");
			}
		} while (!choice.equals("6"));
		sc.close();
	}
}

class RoomComparator implements Comparator<Room> {
	@Override
	public int compare(Room room1, Room room2) {
		return room1.getRoomNumber().compareTo(room2.getRoomNumber());
	}
}
