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

public class RoomManager implements ReadWrite {
	public static final String SEPARATOR = "|";
	private ArrayList<Room> roomList = new ArrayList<>();
	
	public RoomManager() {
		try {
			this.readRoomList("roomlist.txt");
		} catch (Exception e) {
			System.out.println("readRoomList() failed.");
		}
	}
	// reads room data from file
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
	// saves room data to file
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
	
	public Room getAvailableRoom(RoomType roomType, BedType bedType) {
		for (Room r: roomList) {
			if (r.getRoomType() == roomType && r.getBedType() == bedType && r.getRoomStatus() == RoomStatus.VACANT)
				return r;
		}
		System.out.println("No available rooms found.");
		return null;
	}
	
	public boolean roomNumberExists(String roomNumber) {
		if (findRoom(roomNumber) != null)
			return true;
		else
			return false;
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
	
	public RoomType getRoomType(String roomNumber) {
		return findRoom(roomNumber).getRoomType();
	}
	
	public BedType getBedType(String roomNumber) {
		return findRoom(roomNumber).getBedType();
	}
	
	public RoomStatus getRoomStatus(String roomNumber) {
		return findRoom(roomNumber).getRoomStatus();
	}
	
	public boolean isRoomWifiEnabled(String roomNumber) {
		return findRoom(roomNumber).isWifiEnabled();
	}
	
	public boolean isRoomSmoking(String roomNumber) {
		return findRoom(roomNumber).isSmoking();
	}
	
	public boolean roomHasBalcony(String roomNumber) {
		return findRoom(roomNumber).hasBalcony();
	}
	
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
	
	public void updateRoomType(String roomNumber, RoomType roomType) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setRoomType(roomType);
	}
	
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
	
	public void updateBedType(String roomNumber, BedType bedType) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setBedType(bedType);
	}
	
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
	
	public void updateRoomStatus(String roomNumber, RoomStatus roomStatus) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setRoomStatus(roomStatus);
	}
	
	public void updateRoomStatus(String roomNumber, String roomStatus) {
		Room r = findRoom(roomNumber);
		if (r == null)
			return;
		try {
			RoomStatus newRoomStatus = RoomStatus.valueOf(roomStatus.toUpperCase());
			updateRoomStatus(roomNumber, newRoomStatus);
			System.out.println("Bed type set to " + newRoomStatus);
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid room status.");
		}
	}
	
	public void updateRoomWifi(String roomNumber, boolean wifiEnabled) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setWifi(wifiEnabled);
	}
	
	public void toggleRoomWifi(String roomNumber) {
		Room r = findRoom(roomNumber);
		if (r != null) {
			r.setWifi(!r.isWifiEnabled());
			System.out.println("WiFi Enabled set to " + r.isWifiEnabled() + ".");
		}
	}
	
	public void updateRoomBalcony(String roomNumber, boolean balcony) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setBalcony(balcony);
	}
	
	public void toggleRoomBalcony(String roomNumber) {
		Room r = findRoom(roomNumber);
		if (r != null) {
			r.setWifi(!r.hasBalcony());
			System.out.println("Balcony set to " + r.hasBalcony() + ".");
		}
	}
	
	public void updateRoomSmoking(String roomNumber, boolean smoking) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setSmoking(smoking);
	}
	
	public void toggleRoomSmoking(String roomNumber) {
		Room r = findRoom(roomNumber);
		if (r != null) {
			r.setWifi(!r.isSmoking());
			System.out.println("Smoking set to " + r.isSmoking() + ".");
		}
	}
	
	public void updateRoomMaxSize(String roomNumber, int maxSize) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setMaxSize(maxSize);
	}
	
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