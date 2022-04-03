package Reservation;

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
	protected long reservNum;
	protected String reservCode;
	protected LocalDate dateCheckIn;
	protected LocalDate dateCheckOut;
	protected int numAdult;
	protected int numChild;
	
	public Status getReservStatus()
	{
		return reservStatus;
	}
	
	public void printReceipt()
	{
		System.out.println("\n-----Below is your reservation acknowledgement receipt-----");
		System.out.printf("Reservation Code: %s\n", reservCode);
		//guest details
		System.out.printf("Check-in date: "+dateCheckIn+"\n");
		System.out.printf("Check-out date: "+dateCheckOut+"\n");
		//room info
		//billing info
		System.out.printf("No. of Adults: %d\n", numAdult);
		System.out.printf("No. of Children: %d\n", numChild);
		//System.out.printf("Reservation Status: ", reservStatus,"\n");
		System.out.println("");
	}
	
}
