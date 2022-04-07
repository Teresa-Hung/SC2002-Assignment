package hotel;

public class BasicRoomCharge {
	private String roomType;
	private double roomCharge;
	
	private void getRoomType(String room) {
		roomType = room;
	}
	
	public double baseCharge() {
		
		switch(roomType) {
		case "single":
			roomCharge = 1.0;
		case "Double":
			roomCharge = 2.0;
		case "Deluxe":
			roomCharge = 3.0;
		case "VIP Suite":
			roomCharge = 4.0;
		}
		
		return roomCharge;
	}

}