package room;

/**
 * Represents a room in the hotel.
 * @author BRYAN WU JIAHE
 * @version 1.0
 * @since 2022-04-17
 */
public class Room {
	/**
	 * The number of this room.
	 * Sets to be "0" as default.
	 */
	private String roomNumber = "0";
	/**
	 * The room type of this room.
	 */
	private RoomType roomType;
	/**
	 * The bed type of this room.
	 */
	private BedType bedType;
	/**
	 * The status of this room.
	 * Sets to be vacant as default.
	 */
	private RoomStatus roomStatus = RoomStatus.VACANT;
	/**
	 * The boolean variable indicating whether this room is WIFI enabled.
	 * Sets to be true as default.
	 */
	private boolean wifiEnabled = true;
	/**
	 * The boolean variable indicating whether this room is a smoking or non-smoking room.
	 * Sets to be false as default.
	 */
	private boolean smoking = false;
	/**
	 * The boolean variable indicating whether this room is equipped with a balcony.
	 * Sets to be false as default.
	 */
	private boolean balcony = false;
	/**
	 * The maximum size of group this room is able to accommodate. 
	 */
	private int maxSize;
	
	/**
	 * Creates a new Room with the given room type, bed type and the maximum size.
	 * @param roomType This Room's room type.
	 * @param bedType This Room's bed type.
	 * @param maxSize This Room's maximum size of group.
	 */
	public Room(RoomType roomType, BedType bedType, int maxSize) {
		this.roomType = roomType;
		this.bedType = bedType;
		this.maxSize = maxSize;
	}
	// get methods
	/**
	 * Gets the number of this Room.
	 * @return this Room's number.
	 */
	public String getRoomNumber() {return roomNumber;}
	/**
	 * Gets the room type of this Room.
	 * @return this Room's room type.
	 */
	public RoomType getRoomType() {return roomType;}
	/**
	 * Gets the bed type of this Room.
	 * @return this Room's bed type.
	 */
	public BedType getBedType() {return bedType;}
	/**
	 * Gets the status of this Room.
	 * @return this Room's status.
	 */
	public RoomStatus getRoomStatus() {return roomStatus;}
	/**
	 * Gets the WIFI availability of this Room.
	 * @return true if this Room is WIFI enabled; Otherwise, false.
	 */
	public boolean isWifiEnabled() {return wifiEnabled;}
	/**
	 * Gets the smoking policies of this Room.
	 * @return true if this Room is smoking room; Otherwise, false.
	 */
	public boolean isSmoking() {return smoking;}
	/**
	 * Gets the balcony information of this Room.
	 * @return true if this Room has a balcony; Otherwise, false.
	 */
	public boolean hasBalcony() {return balcony;}
	/**
	 * Gets the maximum size of group this Room can accommodate.
	 * @return the maximum size of group.
	 */
	public int getMaxSize() {return maxSize;}
	// set methods
	/**
	 * Sets the number of this Room.
	 * @param roomNumber This Room's room number.
	 */
	public void setRoomNumber(String roomNumber) {this.roomNumber = roomNumber;}
	/**
	 * Sets the room type of this Room.
	 * @param roomType This Room's room type.
	 */
	public void setRoomType(RoomType roomType) {this.roomType = roomType;}
	/**
	 * Sets the bed type of this Room.
	 * @param bedType  This Room's bed type.
	 */
	public void setBedType(BedType bedType) {this.bedType = bedType;}
	/**
	 * Sets the status of this Room.
	 * @param roomStatus This Room's status.
	 */
	public void setRoomStatus(RoomStatus roomStatus) {this.roomStatus = roomStatus;}
	/**
	 * Sets the WIFI availability of this Room.
	 * Sets true if this Room is WIFI enabled; Otherwise, false.
	 * @param wifiEnabled This Room's WIFI availability. 
	 */
	public void setWifi(boolean wifiEnabled) {this.wifiEnabled = wifiEnabled;}
	/**
	 * Sets the smoking policies of this Room.
	 * Sets true if this Room is smoking room; Otherwise, false.
	 * @param smoking This Room's smoking policies.
	 */
	public void setSmoking(boolean smoking) {this.smoking = smoking;}
	/**
	 * Sets the balcony information of this Room.
	 * Sets true if this Room has a balcony; Otherwise, false.
	 * @param balcony This Room's balcony information.
	 */
	public void setBalcony(boolean balcony) {this.balcony = balcony;}
	/**
	 * Sets the maximum size of group this Room can accommodate.
	 * @param maxSize The maximum size of group of this Room.
	 */
	public void setMaxSize(int maxSize) {this.maxSize = maxSize;}
	
	/**
	 * Different types of room available in the hotel.
	 */
	public enum RoomType {SINGLE, DOUBLE, SUITE, VIP_SUITE}
	/**
	 * Different types of bed available in the hotel.
	 */
	public enum BedType {TWIN, QUEEN, KING}
	/**
	 * Different types of room status.
	 */
	public enum RoomStatus {VACANT, OCCUPIED, RESERVED, UNDER_MAINTENANCE}
}