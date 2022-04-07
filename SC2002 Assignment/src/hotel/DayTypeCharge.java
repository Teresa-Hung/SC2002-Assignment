package hotel;

public class DayTypeCharge {
	private String dayType;
	private double dayTypeCharge;
	
	private void getDayType(String day) {
		dayType = day;
	}
	
	public double typeCharge() {
		
		switch(dayType) {
		case "Monday", "Tuesday", "Wednesday", "Thursday", "Friday":
			dayTypeCharge = 1.0;
		case "Saturday", "Sunday":
			dayTypeCharge = 1.5;
		}
		
		return dayTypeCharge;
	}

}
