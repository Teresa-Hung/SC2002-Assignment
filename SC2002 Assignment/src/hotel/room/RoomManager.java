package room;

import room.Room.BedType;
import room.Room.RoomStatus;
import room.Room.RoomType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class RoomManager {
	
	private ArrayList<Room> roomList = null;
	
	public RoomManager(String fileName) {
		try {
			roomList = RoomIO.readRooms(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeRoomList(String fileName) {
		try {
			RoomIO.saveRooms(fileName, roomList);
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
		for (int i = 0; i < roomList.size(); i++) {
			Room r = roomList.get(i);
			if (roomNumber.equals(r.getRoomNumber()))
				return r;
		}
		if (print) System.out.println("Room number does not exist.");
		return null;
	}
	
	public void printOccupancyReport() {
		ArrayList<Room> single = new ArrayList<>();
		ArrayList<Room> doublee = new ArrayList<>();
		ArrayList<Room> suite = new ArrayList<>();
		ArrayList<Room> vip_suite = new ArrayList<>();

		for (int i = 0 ; i < roomList.size() ; i++) {
			Room r = roomList.get(i);
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
		int i, j;
		ArrayList<Room> listv = new ArrayList<>();
		for (i = 0, j = 0; i < list.size(); i++)
			if (list.get(i).getRoomStatus() == RoomStatus.VACANT) {
				listv.add(list.get(i));
				j++;
			}
		System.out.println(j + " out of " + list.size() + " rooms are vacant.");
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

		for (int i = 0 ; i < roomList.size() ; i++) {
			Room r = roomList.get(i);
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
		System.out.println("-------------------------------------------------------\n");
	}

	public void printRoomReport() {
		int i;
		ArrayList<Room> wifiEnabled = new ArrayList<>();
		ArrayList<Room> wifiDisabled = new ArrayList<>();
		ArrayList<Room> smokingT = new ArrayList<>();
		ArrayList<Room> smokingF = new ArrayList<>();
		ArrayList<Room> balconyT = new ArrayList<>();
		ArrayList<Room> balconyF = new ArrayList<>();
		
		for (i = 0 ; i < roomList.size() ; i++) {
			Room r = roomList.get(i);
			if (r.isWifiEnabled()) wifiEnabled.add(r);
			else wifiDisabled.add(r);
			if (r.isSmoking()) smokingT.add(r);
			else smokingF.add(r);
			if (r.hasBalcony()) balconyT.add(r);
			else balconyF.add(r);
		}
		System.out.println("-------------------------------------------------------\n"
						 + "Print Room Report:\n");
		System.out.println("WiFi Enabled:");
		if (wifiEnabled.isEmpty()) System.out.println("\tNo rooms are WiFi Enabled.");
		else printRoomUtil(wifiEnabled);
		System.out.println("WiFi Disabled:");
		if (wifiDisabled.isEmpty()) System.out.println("\tNo rooms are WiFi Disabled.");
		else printRoomUtil(wifiDisabled);
		System.out.println("Smoking:");
		if (smokingT.isEmpty()) System.out.println("\tNo rooms are smoking.");
		else printRoomUtil(smokingT);
		System.out.println("Non-Smoking:");
		if (smokingF.isEmpty()) System.out.println("\tNo rooms are non-smoking.");
		else printRoomUtil(smokingF);
		System.out.println("Has Balcony:");
		if (balconyT.isEmpty()) System.out.println("\tNo rooms have balcony.");
		else printRoomUtil(balconyT);
		System.out.println("No Balcony:");
		if (balconyF.isEmpty()) System.out.println("\tNo rooms have no balcony.");
		else printRoomUtil(balconyF);
		System.out.println("-------------------------------------------------------\n");
	}
	
	private void printRoomUtil(ArrayList<Room> list) {
		int i;
		list.sort(new RoomComparator());
		for (i = 0; i < list.size(); i++) {
			if (i == 0) System.out.print("\tRooms: ");
			if (i == list.size()-1) System.out.println(list.get(i).getRoomNumber() + ".");
			else System.out.print(list.get(i).getRoomNumber() + ",");
			if (i%8 == 7 && i != list.size()-1) System.out.print("\n\t       ");
		}
	}
	
	public void printRoomDetails(String roomNumber) {
		Room r = findRoom(roomNumber);
		if (r != null) {
			System.out.println("------------------------\n"
							 + "Print Room Details\n"
							 + "Room Number  : " + r.getRoomNumber() + "\n"
							 + "Room Type    : " + r.getRoomType() + "\n"
							 + "Bed Type     : " + r.getBedType() + "\n"
							 + "Room Status  : " + r.getRoomStatus() + "\n"
							 + "WiFi Enabled : " + r.isWifiEnabled() + "\n"
							 + "Smoking      : " + r.isSmoking() + "\n"
							 + "Has Balcony  : " + r.hasBalcony() + "\n"
							 + "Max Capacity : " + r.getMaxSize() + " guests\n"
							 + "------------------------\n");
		}
	}
	
	public void addRoom(Room room) {
		if (roomList.size() >= 48) {
			System.out.println("There are already 48 rooms in the hotel, "
					+ "remove a room before adding another one.");
			return;
		}
		Room r = findRoom(room.getRoomNumber(), false);
		if (r != null) {
			System.out.println("Room number already exists.");
			return;
		}
		roomList.add(room);
		System.out.println("Room added successfully.");
	}
	
	public void removeRoom(String roomNumber) {
		Room r = findRoom(roomNumber);
		if (r != null) {
			roomList.remove(r);
			System.out.println("Room removed successfully.");
		}
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
	
	private void updateDetailsMenu(Scanner sc) {
		
		System.out.println("--------------------\n"
						 + "Update Details Menu:\n"
						 + "(1) Room Number\n"
						 + "(2) Room Type\n"
						 + "(3) Bed Type\n"
						 + "(4) Room Status\n"
						 + "(5) Toggle WiFi\n"
						 + "(6) Toggle Smoking\n"
						 + "(7) Toggle Balcony\n"
						 + "--------------------");
		System.out.print("Enter room number: ");
		String s = sc.next();
		Room r = findRoom(s);
		if (r == null) return;
		System.out.print("Enter choice: ");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			System.out.print("Enter new room number: ");
			String newRoomNumber = sc.next();
			updateRoomNumber(s, newRoomNumber);
			break;
		case 2:
			System.out.print("Options: SINGLE, DOUBLE, SUITE, VIP_SUITE\n"
						   + "Enter new room type: ");
			RoomType newRoomType = RoomType.valueOf(sc.next().toUpperCase());
			try {
				updateRoomType(s, newRoomType);
			} catch (Exception e) {
				System.out.println("Invalid room type.");
			}
			break;
		case 3:
			System.out.print("Options: TWIN, QUEEN, KING\n"
						   + "Enter new bed type: ");
			BedType newBedType = BedType.valueOf(sc.next().toUpperCase());
			try {
				updateBedType(s, newBedType);
			} catch (Exception e) {
				System.out.println("Invalid bed type.");
			}
			break;
		case 4:
			System.out.print("Options: VACANT, OCCUPIED, RESERVED, UNDER_MAINTENANCE\n"
						   + "Enter new room status: ");
			try {
				RoomStatus newRoomStatus = RoomStatus.valueOf(sc.next().toUpperCase());
				updateRoomStatus(s, newRoomStatus);
			} catch (Exception e) {
				System.out.println("Invalid room status.");
			}
			break;
		case 5:
			r.setWifi(!r.isWifiEnabled());
			System.out.println("WiFi Enabled set to " + r.isWifiEnabled());
			break;
		case 6:
			r.setSmoking(!r.isSmoking());
			System.out.println("Smoking set to " + r.isSmoking());
			break;
		case 7:
			r.setBalcony(!r.hasBalcony());
			System.out.println("Balcony set to " + r.hasBalcony());
			break;
		default:
			System.out.println("Invalid option.");
			break;
		}
	}
	
	public void roomMenu() {
		Scanner sc = new Scanner(System.in);
		int choice = 0; String s = null;
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
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				printOccupancyReport();
				break;
			case 2:
				printStatusReport();
				break;
			case 3:
				printRoomReport();
				break;
			case 4:
				System.out.print("Enter room number: ");
				s = sc.next();
				printRoomDetails(s);
				break;
			case 5:
				updateDetailsMenu(sc);
				break;
			case 6:
				writeRoomList("48_Hotel_Rooms.txt");
				break;
			default:
				System.out.println("Invalid choice.");
			}
		} while (choice != 5);
		sc.close();
	}
}

class RoomComparator implements Comparator<Room> {
	@Override
	public int compare(Room room1, Room room2) {
		return room1.getRoomNumber().compareTo(room2.getRoomNumber());
	}
}