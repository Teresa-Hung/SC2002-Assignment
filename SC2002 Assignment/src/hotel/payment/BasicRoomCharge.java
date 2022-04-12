package payment;
import room.Room.RoomType

public class BasicRoomCharge {
	private String type;
	private double roomCharge;
	private RoomType type;
	
	public double getBaseCharge() {
		type = getRoomType();
		
		switch(type) {
		case SINGLE:
			roomCharge = 100.0;
		case DOUBLE:
			roomCharge = 150.0;
		case SUITE:
			roomCharge = 250.0;
		case VIP_SUITE:
			roomCharge = 350.0;
		}
	
		return roomCharge;
	}

}
