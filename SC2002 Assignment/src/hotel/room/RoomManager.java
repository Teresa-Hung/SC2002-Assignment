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
		if (r != null)
			r.setRoomNumber(newRoomNumber);
	}
	
	public void updateRoomType(String roomNumber, RoomType roomType) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setRoomType(roomType);
	}
	
	public void updateBedType(String roomNumber, BedType bedType) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setBedType(bedType);
	}
	
	public void updateRoomStatus(String roomNumber, RoomStatus roomStatus) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setRoomStatus(roomStatus);
	}
	
	public void updateRoomWifi(String roomNumber, boolean wifiEnabled) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setWifi(wifiEnabled);
	}
	
	public void updateRoomBalcony(String roomNumber, boolean balcony) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setBalcony(balcony);
	}
	
	public void updateRoomSmoking(String roomNumber, boolean smoking) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setSmoking(smoking);
	}
	
	public void updateRoomMaxSize(String roomNumber, int maxSize) {
		Room r = findRoom(roomNumber);
		if (r != null)
			r.setMaxSize(maxSize);
	}
}
	
class RoomComparator implements Comparator<Room> {
	@Override
	public int compare(Room room1, Room room2) {
		return room1.getRoomNumber().compareTo(room2.getRoomNumber());
	}
}