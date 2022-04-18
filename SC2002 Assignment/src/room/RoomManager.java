package room;

import room.Room.BedType;
import room.Room.RoomStatus;
import room.Room.RoomType;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import main.FileIO.ReadWrite;

/**
 * Contains control methods to manage the guest records.
 * Implements ReadWrite interface to do file input and output.
 * @author BRYAN WU JIAHE
 * @version 1.0
 * @since 2022-04-17
 */
public class RoomManager implements ReadWrite {
	/**
	 * The separator specifies the delimiter used in file input and output.
	 */
	public static final String SEPARATOR = "|";
	/**
	 * The roomList contains all the records of Room.
	 */
	private ArrayList<Room> roomList = new ArrayList<>();
	/**
	 * Private static instance of RoomManager to ensure only 1 instance is created during runtime.
	 */
	private static RoomManager instance = null;
	
	/**
	 * Private constructor that loads the roomList by reading from file "roomlist.txt".
	 * Prints error message if reading is failed.
	 */
	private RoomManager() {
		try {
			this.readRoomList("roomlist.txt");
		} catch (Exception e) {
			System.out.println("readRoomList() failed.");
		}
	}
	/**
	 * Returns the instance of RoomManager.
	 * @return RoomManager instance.
	 */
	public static RoomManager getInstance() {
        if (instance == null)
            instance = new RoomManager();
        return instance;
	}
	/**
	 * Reads in the room records from file and creates the roomList.
	 * @param filename The file to be read from.
	 */
	private void readRoomList(String filename) {
		ArrayList<String> stringArray = read("roomlist.txt");
    	for (String s: stringArray) {
			StringTokenizer star = new StringTokenizer(s , SEPARATOR);
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
	}
	/**
	 * Output the records in roomList to the file "roomlist.txt".
	 */
	public void saveRoomList() {
		ArrayList<String> stringArray = new ArrayList<>();
        for (Room r: roomList) {
			StringBuilder st =  new StringBuilder();
			st.append(r.getRoomNumber() + SEPARATOR);	// roomNumber
			st.append(r.getRoomType() + SEPARATOR);		// roomType
			st.append(r.getBedType() + SEPARATOR);		// bedType
			st.append(r.getRoomStatus() + SEPARATOR);	// roomStatus
			st.append(r.isWifiEnabled() + SEPARATOR);	// wifiEnabled
			st.append(r.isSmoking() + SEPARATOR);		// smoking
			st.append(r.hasBalcony() + SEPARATOR);		// balcony
			st.append(r.getMaxSize() + SEPARATOR);		// maxSize
			stringArray.add(st.toString());
		}
        write("roomlist.txt", stringArray);
	}
	
	/**
	 * The method to input data from a file.
	 * @param filename The name of the file to be read from.
	 */
	@Override
	public ArrayList<String> read(String filename) {
		ArrayList<String> data = new ArrayList<>();
		try {
			Scanner sc = new Scanner(new FileInputStream(filename));
			while(sc.hasNextLine())
				data.add(sc.nextLine());
			sc.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		return data;
	}
	/**
	 * The method to output data to a file.
	 * @param filename The name of the file to be written in.
	 * @param data The data to be written.
	 */
	@Override
	public void write(String filename, List<String> data) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter("roomlist.txt"));
			for (String s: data)
				out.println(s);
			out.close();
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
	
	/**
	 * Gets the records of guest list.
	 * @return the room list.
	 */
	public ArrayList<Room> getRoomList() {
		return roomList;
	}
	
	/**
	 * Searches a Room record in the roomList by room number and prints this Room's details.
	 * @param roomNumber The room number to search.
	 * @return the found Room object, or null if not found.
	 */
	public Room findRoom(String roomNumber) {
		return findRoom(roomNumber, true);
	}
	
	/**
	 * Searches a Room record in the roomList by room number.
	 * @param roomNumber The room number to search.
	 * @param print The variable indicating whether to print this Room's details.
	 * @return the found Room object, or null if not found.
	 */
	public Room findRoom(String roomNumber, boolean print) {
		for (Room r: roomList)
			if (roomNumber.equals(r.getRoomNumber()))
				return r;
		if (print) System.out.println("Room number does not exist.");
		return null;
	}
	
	/**
	 * Gets an available Room with the requested room type and bed type.
	 * @param roomType The requested room type.
	 * @param bedType The requested room type.
	 * @return the available Room if any, otherwise return null.
	 */
	public Room getAvailableRoom(RoomType roomType, BedType bedType) {
		for (Room r: roomList) {
			if (r.getRoomType() == roomType && r.getBedType() == bedType && r.getRoomStatus() == RoomStatus.VACANT)
				return r;
		}
		System.out.println("No available rooms found.");
		return null;
	}
	
	/**
	 * Checks whether a room number exists in the hotel.
	 * @param roomNumber The room number to check.
	 * @return true if exists, otherwise false.
	 */
	public boolean roomNumberExists(String roomNumber) {
		if (findRoom(roomNumber) != null)
			return true;
		else
			return false;
	}
	
	/**
	 * Prints the occupancy report of each room type in the hotel.
	 * Lists out in order of Single, Double, Suite and VIP Suite. 
	 */
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
	
	/**
	 * Prints the details of the occupancy report of one room type.
	 * @param list The room list of a specific room type.
	 */
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
			for (i = 0; i < listv.size(); i++) {
				if (i == 0) System.out.print("\tRooms: ");
				if (i == listv.size() - 1) System.out.println(listv.get(i).getRoomNumber() + ".");
				else System.out.print(listv.get(i).getRoomNumber() + ",");
				if (i%8 == 7 && i != list.size()-1) System.out.print("\n\t       ");
			}
		}
	}
	
	/**
	 * Prints the status report of the hotel rooms.
	 * Lists out in order of Vacant, Occupied, Reserved and Under Maintenance. 
	 */
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

	/**
	 * Prints the room report of the hotel rooms.
	 * The details include the room's WIFI availability, smoking policies and balcony information.
	 */
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
	
	/**
	 * Prints all the room numbers of a list of specific type of rooms.
	 * @param list The list of specific type of rooms. 
	 */
	private void printRoomUtil(ArrayList<Room> list) {
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) System.out.print("\tRooms: ");
			if (i == list.size()-1) System.out.println(list.get(i).getRoomNumber() + ".");
			else System.out.print(list.get(i).getRoomNumber() + ",");
			if (i%8 == 7 && i != list.size()-1) System.out.print("\n\t       ");
		}
	}
	
	/**
	 * Prints the room details of a Room.
	 * Reads in the room number to access the Room's record.
	 * @param roomNumber The number of the Room to print. 
	 */
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
	
	/**
	 * Gets the room type of the given room number.
	 * @param roomNumber The room number of the Room.
	 * @return the Room's room type.
	 */
	public RoomType getRoomType(String roomNumber) {
		return findRoom(roomNumber).getRoomType();
	}
	
	/**
	 * Gets the bed type of the given room number.
	 * @param roomNumber The room number of the Room.
	 * @return the Room's bed type.
	 */
	public BedType getBedType(String roomNumber) {
		return findRoom(roomNumber).getBedType();
	}
	
	/**
	 * Gets the status of the given room number.
	 * @param roomNumber The room number of the Room.
	 * @return the Room's status.
	 */
	public RoomStatus getRoomStatus(String roomNumber) {
		return findRoom(roomNumber).getRoomStatus();
	}
	
	/**
	 * Gets the WIFI availability of the given room number.
	 * @param roomNumber The room number of the Room.
	 * @return the Room's WIFI availability.
	 */
	public boolean isRoomWifiEnabled(String roomNumber) {
		return findRoom(roomNumber).isWifiEnabled();
	}
	
	/**
	 * Gets the smoking policies of the given room number.
	 * @param roomNumber The room number of the Room.
	 * @return the Room's smoking policies.
	 */
	public boolean isRoomSmoking(String roomNumber) {
		return findRoom(roomNumber).isSmoking();
	}
	
	/**
	 * Gets the balcony information of the given room number.
	 * @param roomNumber The room number of the Room.
	 * @return the Room's balcony information.
	 */
	public boolean roomHasBalcony(String roomNumber) {
		return findRoom(roomNumber).hasBalcony();
	}
	
	/**
	 * Updates the room number of a Room.
	 * The new room number should be four digits and not overlap an existing room number.
	 * Prints error message if the room number is invalid.
	 * @param roomNumber The number of the Room to update.
	 * @param newRoomNumber The updated room number.
	 */
	public void updateRoomNumber(String roomNumber, String newRoomNumber) {
		Room r = findRoom(roomNumber);
		if (r == null)
			return;
		if (newRoomNumber.length() != 4) {
			System.out.println("Invalid room number format.");
			return;
		}
		try {
			// if new room number is not numeric, parseInt throws NumberFormatException
			Integer.parseInt(newRoomNumber);
			boolean valid = roomNumber.charAt(0) == newRoomNumber.charAt(0) && roomNumber.charAt(1) == newRoomNumber.charAt(1);
			if (!valid) {
				System.out.println("Invalid room number format.");
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid room number format.");
			return;
		}
		if (roomNumberExists(newRoomNumber)) {
			System.out.println("Room number already exists.");
			return;
		}
		r.setRoomNumber(newRoomNumber);
		System.out.println("Room number set to " + newRoomNumber);
	}
	
	/**
	 * Updates a room type of a Room with the enum RoomType.
	 * @param roomNumber The number of the Room to update.
	 * @param roomType The updated room type.
	 */
	public void updateRoomType(String roomNumber, RoomType roomType) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setRoomType(roomType);
	}
	
	/**
	 * Updates the room type of a Room with String.
	 * Prints error message if the new room type is invalid.
	 * @param roomNumber The number of the Room to update.
	 * @param roomType The updated room type.
	 */
	public void updateRoomType(String roomNumber, String roomType) {
		Room r = findRoom(roomNumber);
		if (r == null)
			return;
		try {
			RoomType newRoomType = RoomType.valueOf(roomType);
			updateRoomType(roomNumber, newRoomType);
			System.out.println("Room type set to " + newRoomType);
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid room type.");
		}
	}
	
	/**
	 * Updates the bed type of a Room with the enum BedType.
	 * @param roomNumber The number of the Room to update.
	 * @param bedType The updated bed type.
	 */
	public void updateBedType(String roomNumber, BedType bedType) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setBedType(bedType);
	}
	
	/**
	 * Updates the bed type of a Room with String.
	 * Prints error message if the new bed type is invalid.
	 * @param roomNumber The number of the Room to update.
	 * @param bedType
	 */
	public void updateBedType(String roomNumber, String bedType) {
		Room r = findRoom(roomNumber);
		if (r == null)
			return;
		try {
			BedType newBedType = BedType.valueOf(bedType);
			updateBedType(roomNumber, newBedType);
			System.out.println("Bed type set to " + newBedType);
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid bed type.");
		}
	}
	
	/**
	 * Updates the room status of a Room with the enum RoomStatus.
	 * @param roomNumber The number of the Room to update.
	 * @param roomStatus The updated room status.
	 */
	public void updateRoomStatus(String roomNumber, RoomStatus roomStatus) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setRoomStatus(roomStatus);
	}
	
	/**
	 * Updates the room status of a Room with String.
	 * Prints error message if the new room status is invalid.
	 * @param roomNumber The number of the Room to update.
	 * @param roomStatus The updated room status.
	 */
	public void updateRoomStatus(String roomNumber, String roomStatus) {
		Room r = findRoom(roomNumber);
		if (r == null)
			return;
		try {
			RoomStatus newRoomStatus = RoomStatus.valueOf(roomStatus.toUpperCase());
			updateRoomStatus(roomNumber, newRoomStatus);
			System.out.println("Room status set to " + newRoomStatus);
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid room status.");
		}
	}
	
	/**
	 * Updates the WIFI availability of a Room.
	 * @param roomNumber The number of the Room to update.
	 * @param wifiEnabled The updated WIFI availability.
	 */
	public void updateRoomWifi(String roomNumber, boolean wifiEnabled) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setWifi(wifiEnabled);
	}
	
	/**
	 * Toggles the WIFI availability of a Room.
	 * Sets to the opposite of the original status.
	 * @param roomNumber The number of the Room to toggle.
	 */
	public void toggleRoomWifi(String roomNumber) {
		Room r = findRoom(roomNumber);
		if (r != null) {
			r.setWifi(!r.isWifiEnabled());
			System.out.println("WiFi Enabled set to " + r.isWifiEnabled() + ".");
		}
	}
	
	/**
	 * Updates the balcony information of a Room.
	 * @param roomNumber The number of the Room to update.
	 * @param balcony The updated balcony information.
	 */
	public void updateRoomBalcony(String roomNumber, boolean balcony) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setBalcony(balcony);
	}
	
	/**
	 * Toggles the balcony information of a Room.
	 * Sets to the opposite of the original status.
	 * @param roomNumber The number of the Room to toggle.
	 */
	public void toggleRoomBalcony(String roomNumber) {
		Room r = findRoom(roomNumber);
		if (r != null) {
			r.setWifi(!r.hasBalcony());
			System.out.println("Balcony set to " + r.hasBalcony() + ".");
		}
	}
	
	/**
	 * Updates the smoking policies of a Room.
	 * @param roomNumber The number of the Room to update.
	 * @param smoking The updated smoking policies.
	 */
	public void updateRoomSmoking(String roomNumber, boolean smoking) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setSmoking(smoking);
	}
	
	/**
	 * Toggles the smoking policies of a Room.
	 * Sets to the opposite of the original status.
	 * @param roomNumber The number of the Room to toggle.
	 */
	public void toggleRoomSmoking(String roomNumber) {
		Room r = findRoom(roomNumber);
		if (r != null) {
			r.setWifi(!r.isSmoking());
			System.out.println("Smoking set to " + r.isSmoking() + ".");
		}
	}
	
	/**
	 * Updates the maximum size of a Room with integer.
	 * @param roomNumber The number of the Room to update.
	 * @param maxSize The updated maximum size.
	 */
	public void updateRoomMaxSize(String roomNumber, int maxSize) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setMaxSize(maxSize);
	}
	
	/**
	 * Updates the maximum size of a Room with String.
	 * Prints error message if the new maximum size is invalid.
	 * @param roomNumber The number of the Room to update.
	 * @param maxSize The updated maximum size.
	 */
	public void updateRoomMaxSize(String roomNumber, String maxSize) {
		Room r = findRoom(roomNumber);
		if (r == null)
			return;
		try {
			int newMaxSize = Integer.parseInt(maxSize);
			r.setMaxSize(newMaxSize);
			System.out.println("Max size set to " + newMaxSize);
		} catch (NumberFormatException e) {
			System.out.println("Invalid size.");
		}
	}
}