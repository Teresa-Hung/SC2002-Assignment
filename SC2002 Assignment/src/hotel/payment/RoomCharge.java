package miniproj;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import reservation.Reservation;

public class RoomCharge { 	
	private long numOfDays, i;
	private double baseCharge, roomCharge, dayTypeCharge;
	private LocalDate dateIn, dateOut;
	private DayOfWeek day;
	private RoomType rtype;
	
	public void RoomCharge() {
		numOfDays = i = 0;
		baseCharge = roomCharge = dayTypeCharge = 0.0;
		dateIn = dateOut = LocalDate.parse("00/00/0000");
		day = DayOfWeek.MONDAY;
		rtype = Room.setRoomType(SINGLE);
	}
	
	private void setBaseCharge(Room room) {
		rtype = room.getRoomType();
		
		switch(rtype) {
		case SINGLE:
			baseCharge = 100.0;
		case DOUBLE:
			baseCharge = 150.0;
		case SUITE:
			baseCharge = 250.0;
		case VIP_SUITE:
			baseCharge = 350.0;
		}
	}
	
	
	private void getTypeCharge(DayOfWeek day) {
		private DayOfWeek dayType = day;
		
		switch(dayType) {
		case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY:
			dayTypeCharge = 1.0;
		case SATURDAY, SUNDAY:
			dayTypeCharge = 1.5;
		}
	}
	
	
	public double getRoomCharge(ArrayList<Reservation> reservationList, string reservCode) {
		Reservation rv = searchReserv(reservationList, reservCode,);
		Room rm = rv.getRoom();
		
		setBaseCharge(rm);

		dateIn = rv.getCheckInDate();
		dateOut = rv.getCheckOutDate(); 
		
		numOfDays = ChronoUnit.DAYS.between(dateIn, dateOut);
		day = dateIn.getDayOfWeek();
		
		roomCharge = 0.0;
		for(i=0;i<numOfDays;i++) {
			roomCharge = roomCharge + baseCharge*getTypeCharge(day);
			day = day.plus(1);
		}
		
		return roomCharge;
	}
	
	
	public long getNumOfDays(){
		return numOfDays;
	}
}