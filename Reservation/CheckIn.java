package Reservation;

import java.sql.Timestamp;
import java.time.*;

import Reservation.Reservation.Status;

public class CheckIn {

	private Timestamp now;
	private Timestamp scheduledCheckIn;
	private Timestamp deadlineCheckIn;
	
	public CheckIn(Reservation reserv)
	{
		now = Timestamp.from(Instant.now());
		//System.out.println(reserv.dateCheckIn+" 14:00:00");
		//System.out.println(reserv.dateCheckIn.plusDays(1)+" 02:00:00");
		scheduledCheckIn = Timestamp.valueOf(reserv.dateCheckIn+" 14:00:00"); //2pm on check-in date
		deadlineCheckIn = Timestamp.valueOf(reserv.dateCheckIn.plusDays(1)+" 02:00:00"); //2am on the next day of check-in date
	}
	
	public boolean checkIn(Reservation reserv)
	{
		if(now.before(scheduledCheckIn) == true)
		{
			System.out.println("Please check-in at your scheduled check-in time.");
			return false;
		}
			
		reserv.reservStatus = Status.CHECKED_IN;
		return true;
	}
	
	public void expire(Reservation reserv)
	{
		if(now.after(deadlineCheckIn) == true)
			reserv.reservStatus = Status.EXPIRED;
		
		//empty the room
		
	}
}
