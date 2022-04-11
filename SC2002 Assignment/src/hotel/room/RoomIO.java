package room;

import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.StringTokenizer;

//import guest.Guest;
//import guest.GuestManager;
import room.Room.BedType;
import room.Room.RoomStatus;
import room.Room.RoomType;

public class RoomIO {
	public static final String SEPARATOR = "|";
	
	public static ArrayList<Room> readRooms(String filename) throws IOException {
		ArrayList<String> stringArray = read(filename); // read array of strings that stores room details
		ArrayList<Room> roomList = new ArrayList<>(); // room list to store rooms

        for (int i = 0 ; i < stringArray.size() ; i++) {
			String st = stringArray.get(i);
			StringTokenizer star = new StringTokenizer(st , SEPARATOR);
			
			// reading room details
			String roomNumber = star.nextToken().trim();
			RoomType roomType = RoomType.valueOf(star.nextToken().trim());
			BedType bedType = BedType.valueOf(star.nextToken().trim());
			RoomStatus roomStatus = RoomStatus.valueOf(star.nextToken().trim());
			boolean wifiEnabled = Boolean.parseBoolean(star.nextToken().trim());
			boolean smoking = Boolean.parseBoolean(star.nextToken().trim());
			boolean balcony = Boolean.parseBoolean(star.nextToken().trim());
			int maxSize = Integer.parseInt(star.nextToken().trim());
			
//			GuestManager gm = new GuestManager();
//			Guest[] guestList = new Guest[maxSize];
//			for (int j = 0; j < maxSize; j++) guestList[i] = gm.findById(star.nextToken().trim(), guestlist);
			
			// creating room object with details
			Room r = new Room(roomType, bedType, maxSize);
			r.setRoomNumber(roomNumber);
			r.setRoomStatus(roomStatus);
			r.setWifi(wifiEnabled);
			r.setSmoking(smoking);
			r.setBalcony(balcony);
			
			// adding room to list
			roomList.add(r);
		}
		return roomList;
	}

	public static void saveRooms(String filename, ArrayList<Room> roomList) throws IOException {
		ArrayList<String> stringArray = new ArrayList<>() ;

        for (int i = 0 ; i < roomList.size() ; i++) {
			Room r = roomList.get(i);
			StringBuilder st =  new StringBuilder();
			
			st.append(r.getRoomNumber().trim()); st.append(SEPARATOR);					// roomNumber
			st.append(r.getRoomType().name().trim()); st.append(SEPARATOR);				// roomType
			st.append(r.getBedType().name().trim()); st.append(SEPARATOR);				// bedType
			st.append(r.getRoomStatus().name().trim()); st.append(SEPARATOR);			// roomStatus
			st.append(Boolean.toString(r.isWifiEnabled()).trim()); st.append(SEPARATOR);// wifiEnabled
			st.append(Boolean.toString(r.isSmoking()).trim()); st.append(SEPARATOR);	// smoking
			st.append(Boolean.toString(r.hasBalcony()).trim()); st.append(SEPARATOR);	// balcony
			st.append(Integer.toString(r.getGuests().length)); st.append(SEPARATOR);	// maxSize
			
			stringArray.add(st.toString());
		}
		write(filename, stringArray);
	}

	public static void write(String fileName, ArrayList<String> stringArray) throws IOException  {
		PrintWriter out = new PrintWriter(new FileWriter(fileName));
		
		try {
			for (int i =0; i < stringArray.size() ; i++) {
				out.println((String)stringArray.get(i));
			}
		}
		finally {
			out.close();
		}
	}
	
	public static ArrayList<String> read(String fileName) throws IOException {
		ArrayList<String> stringArray = new ArrayList<>() ;
	    Scanner scanner = new Scanner(new FileInputStream(fileName));
	    try {
	    	while (scanner.hasNextLine()){
	    		stringArray.add(scanner.nextLine());
	    	}
	    }
	    finally {
	    	scanner.close();
	    }
	    return stringArray;
	}

	public static void main(String[] aArgs)  {
		int i;
    	String fileName = "48_Hotel_Rooms.txt";
    	ArrayList<Room> roomList = new ArrayList<>();
		try {
			
//			ArrayList<Room> roomList = readRooms(fileName);
//			for (i = 0 ; i < roomList.size() ; i++) {
//				Room r = roomList.get(i);
//				???
//			}
			
			// floor 2: 8 single rooms
			for (i = 1; i <= 8; i++) {
				Room r = new Room(RoomType.SINGLE, BedType.QUEEN, 1);
				r.setRoomNumber("020" + i);
				roomList.add(r);
			}
			// floor 3: 8 single rooms
			for (i = 1; i <= 8; i++) {
				Room r = new Room(RoomType.SINGLE, BedType.QUEEN, 1);
				r.setRoomNumber("030" + i);
				roomList.add(r);
			}
			// floor 4: 8 double rooms
			for (i = 1; i <= 8; i++) {
				Room r = new Room(RoomType.DOUBLE, BedType.KING, 2);
				r.setRoomNumber("040" + i);
				roomList.add(r);
			}
			// floor 5: 8 double rooms
			for (i = 1; i <= 8; i++) {
				Room r = new Room(RoomType.DOUBLE, BedType.TWIN, 2);
				r.setRoomNumber("050" + i);
				roomList.add(r);
			}
			// floor 6: 8 suite rooms
			for (i = 1; i <= 8; i++) {
				Room r = new Room(RoomType.SUITE, BedType.KING, 4);
				r.setRoomNumber("060" + i);
				r.setBalcony(true);
				roomList.add(r);
			}
			// floor 7: 8 vip suite rooms
			for (i = 1; i <= 8; i++) {
				Room r = new Room(RoomType.VIP_SUITE, BedType.KING, 6);
				r.setRoomNumber("070" + i);
				r.setSmoking(true);
				r.setBalcony(true);
				roomList.add(r);
			}
			saveRooms(fileName, roomList);
			System.out.println("default rooms loaded.");
		} catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}
	}
}
