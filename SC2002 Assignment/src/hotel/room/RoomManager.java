package room;

import room.Room.BedType;
import room.Room.RoomStatus;
import room.Room.RoomType;
import guest.Guest;

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
		for (int i = 0; i < roomList.size(); i++) {
			Room r = roomList.get(i);
			if (roomNumber.equals(r.getRoomNumber()))
				return r;
		}
		System.out.println("Room number does not exist.");
		return null;
	}
	
	public void printOccupancyReport() {
		ArrayList<Room> single = new ArrayList<>();
		ArrayList<Room> doublee = new ArrayList<>();
		ArrayList<Room> suite = new ArrayList<>();
		ArrayList<Room> vip_suite = new ArrayList<>();
		int i;
		
		if (roomList == null || roomList.isEmpty()) {
			System.out.println("Print room occupancy report unsuccessful.");
			return;
		}
		
		for (i = 0 ; i < roomList.size() ; i++) {
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
		if (!listv.isEmpty())
			listv.sort(new RoomComparator());
			for (i = 0; i < listv.size(); i++) {
				if (i == 0) System.out.print("\tRooms: ");
				if (i == listv.size() - 1) System.out.println(listv.get(i).getRoomNumber() + ".");
				else System.out.print(listv.get(i).getRoomNumber() + ",");
				if (i%8 == 7 && i != list.size()-1) System.out.print("\n\t       ");
			}
	}
	
	public void printStatusReport() {
		ArrayList<Room> vacant = new ArrayList<>();
		ArrayList<Room> occupied = new ArrayList<>();
		ArrayList<Room> reserved = new ArrayList<>();
		ArrayList<Room> under_maintenance = new ArrayList<>();
		int i;
		
		if (roomList == null || roomList.isEmpty()) {
			System.out.println("Print room status report unsuccessful.");
			return;
		}
		
		for (i = 0 ; i < roomList.size() ; i++) {
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
		else printStatusUtil(vacant);
		// print occupied rooms
		System.out.println("Occupied:");
		if (occupied.isEmpty()) System.out.println("\tNo rooms are occupied.");
		else printStatusUtil(occupied);
		// print reserved rooms
		System.out.println("Reserved:");
		if (reserved.isEmpty()) System.out.println("\tNo rooms are reserved.");
		else printStatusUtil(reserved);
		// print under maintenance rooms
		System.out.println("Under Maintenance:");
		if (under_maintenance.isEmpty()) System.out.println("\tNo rooms are under maintenance.");
		else printStatusUtil(under_maintenance);
		System.out.println("-------------------------------------------------------\n");
		
	}

	private void printStatusUtil(ArrayList<Room> list) {
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
		if (roomList == null) {
			System.out.println("Print room details unsuccessful.");
			return;
		}
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
							 + "Max Capacity : " + r.getGuests().length + " guests\n"
							 + "------------------------\n");
		}
	}
	
	public void addRoom(Room room) {
		if (roomList == null) {
			System.out.println("Add room unsuccessful.");
			return;
		}
		if (roomList.size() >= 48) {
			System.out.println("There are already 48 rooms in the hotel, "
					+ "remove a room before adding another one.");
			return;
		}
		Room r = findRoom(room.getRoomNumber());
		if (r != null) {
			System.out.println("Room number already exists.");
			return;
		}
		roomList.add(room);
		System.out.println("Room added successfully.");
	}
	
	public void removeRoom(String roomNumber) {
		if (roomList == null) {
			System.out.println("Remove room unsuccessful.");
			return;
		}
		Room r = findRoom(roomNumber);
		if (r != null) {
			roomList.remove(r);
			System.out.println("Room removed successfully.");
		}
	}
	
	public void addGuest(String roomNumber, Guest gst) {
		if (roomList == null) {
			System.out.println("Add guest unsuccessful.");
			return;
		}
		Room r = findRoom(roomNumber);
		if (r != null) try {
			r.addGuest(gst);
			System.out.println("Guest added successfully.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void removeGuest(String roomNumber, Guest gst) {
		if (roomList == null) {
			System.out.println("Remove guest unsuccessful.");
			return;
		}
		Room r = findRoom(roomNumber);
		if (r != null) try {
			r.removeGuest(gst);
			System.out.println("Guest removed successfully.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void removeAllGuests(String roomNumber) {
		if (roomList == null) {
			System.out.println("Remove all guest unsuccessful.");
			return;
		}
		Room r = findRoom(roomNumber);
		if (r != null) r.removeAllGuests();
	}

	public void updateRoomNumber(String roomNumber, String newRoomNumber) {
		if (roomList == null) {
			System.out.println("Update room number unsuccessful.");
			return;
		}
		Room r = findRoom(newRoomNumber);
		if (r != null) {
			System.out.println("Room number already exists.");
			return;
		}
		findRoom(roomNumber).setRoomNumber(newRoomNumber);
		System.out.println("Room number updated successfully.");
	}
	
	public void updateRoomType(String roomNumber, RoomType roomType) {
		if (roomList == null) {
			System.out.println("Update room type unsuccessful.");
			return;
		}
		Room r = findRoom(roomNumber);
		if (r != null) {
			r.setRoomType(roomType);
			System.out.println("Room type updated successfully.");
		}
	}
	
	public void updateBedType(String roomNumber, BedType bedType) {
		if (roomList == null) {
			System.out.println("Update bed type unsuccessful.");
			return;
		}
		Room r = findRoom(roomNumber);
		if (r != null) {
			r.setBedType(bedType);
			System.out.println("Bed type updated successfully.");
		}
	}
	
	public void updateRoomStatus(String roomNumber, RoomStatus status) {
		if (roomList == null) {
			System.out.println("Update room status unsuccessful.");
			return;
		}
		Room r = findRoom(roomNumber);
		if (r != null) {
			r.setRoomStatus(status);
			System.out.println("Room status updated successfully.");
		}
	}
	
	public void updateRoomWifi(String roomNumber, boolean wifi) {
		if (roomList == null) {
			System.out.println("Update room wifi unsuccessful.");
			return;
		}
		Room r = findRoom(roomNumber);
		if (r != null) {
			r.setWifi(wifi);
			System.out.println("Room wifi updated successfully.");
		}
	}
	
	public void updateRoomBalcony(String roomNumber, boolean balc) {
		if (roomList == null) {
			System.out.println("Update room balcony unsuccessful.");
			return;
		}
		Room r = findRoom(roomNumber);
		if (r != null) {
			r.setBalcony(balc);
			System.out.println("Room balcony updated successfully.");
		}
	}
	
	public void updateRoomSmoking(String roomNumber, boolean sm) {
		if (roomList == null) {
			System.out.println("Update room smoking unsuccessful.");
			return;
		}
		Room r = findRoom(roomNumber);
		if (r != null) {
			r.setSmoking(sm);
			System.out.println("Room smoking updated successfully.");
		}
	}
	
	private void updateDetailsMenu(Scanner sc) {
		System.out.print("Enter room number: ");
		String s = sc.next();
		Room r = findRoom(s);
		if (r == null) return;
		System.out.println("Select Details:\n"
						 + "(1) Room Number\n"
						 + "(2) Room Type\n"
						 + "(3) Bed Type\n"
						 + "(4) Room Status\n"
						 + "(5) Toggle WiFi\n"
						 + "(6) Toggle Smoking\n"
						 + "(7) Toggle Balcony\n"
						 + "(8) Clear Room");
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
		case 8:
			r.setRoomStatus(RoomStatus.VACANT);
			removeAllGuests(s);
			System.out.println("Room cleared successfully.");
			break;
		default:
			break;
		}
	}
	
	public void roomMenu() {
		Scanner sc = new Scanner(System.in);
		int choice = 0; String s = null;
		do {
			System.out.println("-------------------------------\n"
							 + "Room Menu Options:\n"
			        		 + "(1) Print Room Occupancy Report\n"
			        		 + "(2) Print Room Status Report\n"
			        		 + "(3) Print Room Details\n"
			        		 + "(4) Update Room Details\n"
			        		 + "(5) Exit\n"
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
				System.out.print("Enter room number: ");
				s = sc.next();
				printRoomDetails(s);
				break;
			case 4:
				updateDetailsMenu(sc);
				break;
			case 5:
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