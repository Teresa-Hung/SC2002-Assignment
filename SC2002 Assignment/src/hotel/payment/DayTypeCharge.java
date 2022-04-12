package payment;
import reservation.Reservation

public class DayTypeCharge {
	private double dayTypeCharge;
	
	public double getTypeCharge(DayOfWeek dayType) {
		
		switch(dayType) {
		case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY:
			dayTypeCharge = 1.0;
		case SATURDAY, SUNDAY:
			dayTypeCharge = 1.5;
		}
		
		return dayTypeCharge;
	}

}
