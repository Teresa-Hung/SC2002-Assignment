package room;

import guest.Guest;

public class Room {
	private String roomNumber = "0";
	private RoomType roomType;
	private BedType bedType;
	private RoomStatus roomStatus = RoomStatus.VACANT;
	private boolean wifiEnabled = true, smoking = false, balcony = false;
	private Guest[] guestList;
	
	public Room(RoomType roomType, BedType bedType, int maxSize) {
		this.roomType = roomType;
		this.bedType = bedType;
		guestList = new Guest[maxSize];
	}
	public String getRoomNumber() {return roomNumber;}
	public void setRoomNumber(String roomNumber) {this.roomNumber = roomNumber;}
	
	public RoomType getRoomType() {return roomType;}
	public void setRoomType(RoomType roomType) {this.roomType = roomType;}
	
	public BedType getBedType() {return bedType;}
	public void setBedType(BedType bedType) {this.bedType = bedType;}
	
	public RoomStatus getRoomStatus() {return roomStatus;}
	public void setRoomStatus(RoomStatus roomStatus) {this.roomStatus = roomStatus;}
	
	public boolean isWifiEnabled() {return wifiEnabled;}
	public void setWifi(boolean wifiEnabled) {this.wifiEnabled = wifiEnabled;}
	
	public boolean isSmoking() {return smoking;}
	public void setSmoking(boolean smoking) {this.smoking = smoking;}
	
	public boolean hasBalcony() {return balcony;}
	public void setBalcony(boolean balcony) {this.balcony = balcony;}
	
	public Guest[] getGuests() {return guestList;}
	
	public void addGuest(Guest guest) throws Exception {
		for (int i = 0; i < guestList.length; i++)
			if (guestList[i] == null) {
				guestList[i] = guest;
				return;
			}
		throw new Exception("Room is already full.");
	}
	
	public void removeGuest(Guest guest) throws Exception {
		for (int i = 0; i < guestList.length; i++)
			if (guest.getId().equals(guestList[i].getId())) {
				guestList[i] = null;
				return;
			}
		throw new Exception("Guest does not exist.");
	}
	
	public void removeAllGuests() {
		for (int i = 0; i < guestList.length; i++)
			guestList[i] = null;
	}
	
	public enum RoomType {SINGLE, DOUBLE, SUITE, VIP_SUITE}
	public enum BedType {TWIN, QUEEN, KING}
	public enum RoomStatus {VACANT, OCCUPIED, RESERVED, UNDER_MAINTENANCE}
}