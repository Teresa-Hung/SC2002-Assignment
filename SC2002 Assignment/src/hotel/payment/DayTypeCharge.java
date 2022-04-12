package payment;
import reservation.Reservation

public class DayTypeCharge {
	private String dayType;
	private double dayTypeCharge;
	private DayOfWeek inDay, outDay;
	
	
	public double getTypeCharge() {
		inDay = getCheckInDay();
		outDay = getCheckOutDay();
		
		switch(day) {
		case "Monday", "Tuesday", "Wednesday", "Thursday", "Friday":
			dayTypeCharge = 1.0;
		case "Saturday", "Sunday":
			dayTypeCharge = 1.5;
		}
		
		return dayTypeCharge;
	}

}
