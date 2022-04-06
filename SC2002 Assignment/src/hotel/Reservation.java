package hotel;

import java.time.*;

public class Reservation {
	
	enum Status
	{
		CONFIRMED,
		IN_WAITLIST,
		CHECKED_IN,
		EXPIRED
	}
	
	protected Status reservStatus;
	protected String reservCode;
	protected LocalDate dateCheckIn;
	protected LocalDate dateCheckOut;
	protected int numAdult;
	protected int numChild;
	
	public Status getReservStatus()
	{
		return reservStatus;
	}
	
	public LocalDate getCheckInDate()
	{
		return dateCheckIn;
	}
	
	public DayOfWeek getCheckInDay()
	{
		return dateCheckIn.getDayOfWeek();
	}
	
	public LocalDate getCheckOutDate()
	{
		return dateCheckOut;
	}
	
	public DayOfWeek getCheckOutDay()
	{
		return dateCheckOut.getDayOfWeek();
	}
	
	public void printReceipt()
	{
		System.out.println("\n-----Below is your reservation acknowledgement receipt-----");
		System.out.printf("Reservation Code: %s\n", reservCode);
		//guest details
		System.out.printf("Check-in date: %s %s\n", dateCheckIn, getCheckInDay());
		System.out.printf("Check-out date: %s %s\n", dateCheckOut, getCheckOutDay());
		//room info
		//billing info
		System.out.printf("No. of Adults: %d\n", numAdult);
		System.out.printf("No. of Children: %d\n", numChild);
		//System.out.printf("Reservation Status: ", reservStatus,"\n");
		System.out.println("");
	}
	
}
