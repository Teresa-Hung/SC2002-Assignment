package payment;
import java.time.DayOfWeek;

public class DayTypeCharge {
	private double dayTypeCharge;
	private DayOfWeek day;
	
	public double getTypeCharge(DayOfWeek dayType) {
		day = dayType;
		
		switch(day) {
		case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY:
			dayTypeCharge = 1.0;
		case SATURDAY, SUNDAY:
			dayTypeCharge = 1.5;
		}
		
		return dayTypeCharge;
	}

}
