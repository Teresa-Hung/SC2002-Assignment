package hotel;

enum RoomType {SINGLE, DOUBLE, SUITE, VIP_SUITE}
enum BedType {TWIN, QUEEN, KING}
enum Status {VACANT, OCCUPIED, RESERVED, UNDER_MAINTENANCE}

public class Room {
	private int roomNumber = 0;
	private RoomType roomType;
	private BedType bedType;
	private Status status = Status.VACANT;
	private boolean wifiEnabled = false, smoking = false, balcony = false;
	private Guest[] guest;
	
	public Room(RoomType rType, BedType bType, int maxSize) {
		roomType = rType;
		bedType = bType;
		guest = new Guest[maxSize];
	}
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int rn) {
		roomNumber = rn;
	}
	public RoomType getRoomType() {
		return roomType;
	}
	public BedType getBedType() {
		return bedType;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status st) {
		status = st;
	}
	public boolean isWifiEnabled() {
		return wifiEnabled;
	}
	public boolean isSmoking() {
		return smoking;
	}
	public void setSmoking(boolean sm) {
		smoking = sm;
	}
	public boolean hasBalcony() {
		return balcony;
	}
	public void setBalcony(boolean balc) {
		balcony = balc;
	}
	public Guest[] getGuests() {
		return guest;
	}
	public void addGuest(Guest gst) {
		for (int i = 0; i < guest.length; i++)
			if (guest[i] == null) {
				guest[i] = gst;
				break;
			}
	}
	public void removeGuest(Guest gst) {
		
	}
}