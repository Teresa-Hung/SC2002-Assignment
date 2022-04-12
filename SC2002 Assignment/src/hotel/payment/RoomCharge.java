package payment;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.ChronoUnit;

public class RoomCharge { 	
	private long numOfDays, i;
	private double roomCharge;
	private LocalDate dateIn, dateOut;
	private DayOfWeek day;
	
	BasicRoomCharge brc;
	DayTypeCharge dtc;
	
	public double roomRate() {
		dateIn = getCheckInDate();
		dateOut = getCheckOutDate(); 
		
		numOfDays = ChronoUnit.DAYS.between(dateIn, dateOut);
		day = dateIn.getDayOfWeek();
		
		roomCharge = 0.0;
		for(i=0;i<numOfDays;i++) {
			roomCharge = roomCharge + brc.getBaseCharge()*dtc.typeCharge(day);
			day = day.plus(1);
		}
		
		return roomCharge;
	}
}
