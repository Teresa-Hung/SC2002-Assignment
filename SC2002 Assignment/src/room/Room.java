package room;

public class Room {
	private String roomNumber = "0";
	private RoomType roomType;
	private BedType bedType;
	private RoomStatus roomStatus = RoomStatus.VACANT;
	private boolean wifiEnabled = true, smoking = false, balcony = false;
	private int maxSize;
	
	public Room(RoomType roomType, BedType bedType, int maxSize) {
		this.roomType = roomType;
		this.bedType = bedType;
		this.maxSize = maxSize;
	}
	// get methods
	public String getRoomNumber() {return roomNumber;}
	public RoomType getRoomType() {return roomType;}
	public BedType getBedType() {return bedType;}
	public RoomStatus getRoomStatus() {return roomStatus;}
	public boolean isWifiEnabled() {return wifiEnabled;}
	public boolean isSmoking() {return smoking;}
	public boolean hasBalcony() {return balcony;}
	public int getMaxSize() {return maxSize;}
	// set methods
	public void setRoomNumber(String roomNumber) {this.roomNumber = roomNumber;}
	public void setRoomType(RoomType roomType) {this.roomType = roomType;}
	public void setBedType(BedType bedType) {this.bedType = bedType;}
	public void setRoomStatus(RoomStatus roomStatus) {this.roomStatus = roomStatus;}
	public void setWifi(boolean wifiEnabled) {this.wifiEnabled = wifiEnabled;}
	public void setSmoking(boolean smoking) {this.smoking = smoking;}
	public void setBalcony(boolean balcony) {this.balcony = balcony;}
	public void setMaxSize(int maxSize) {this.maxSize = maxSize;}
	
	public enum RoomType {SINGLE, DOUBLE, SUITE, VIP_SUITE}
	public enum BedType {TWIN, QUEEN, KING}
	public enum RoomStatus {VACANT, OCCUPIED, RESERVED, UNDER_MAINTENANCE}
}