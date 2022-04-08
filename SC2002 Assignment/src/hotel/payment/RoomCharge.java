package payment;

public class RoomCharge { 	
	private int numOfDays, i;
	private double roomCharge;
	
	BasicRoomCharge brc;
	DayTypeCharge dtc;
	
	public void setNumOfDays(int days) {
		numOfDays = days;
	}
	
	public int getNumOfDays() {
		return numOfDays;
	}
	
	public double roomRate() {
		roomCharge = 0.0;
		for(i=0;i<numOfDays;i++) {
			roomCharge = roomCharge + brc.baseCharge()*dtc.typeCharge();
		}
		
		return roomCharge;
	}
}
