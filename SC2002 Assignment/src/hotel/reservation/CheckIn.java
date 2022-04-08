package reservation;

import java.sql.Timestamp;
import java.time.*;

import Reservation.Reservation.ReservStatus;

public class CheckIn {

	private Timestamp now;
	private Timestamp scheduledCheckIn;
	private Timestamp deadlineCheckIn;
	
	public CheckIn(Reservation reserv)
	{
		now = Timestamp.from(Instant.now());
		scheduledCheckIn = Timestamp.valueOf(reserv.getCheckInDate()+" 14:00:00"); //2pm on check-in date
		deadlineCheckIn = Timestamp.valueOf(reserv.getCheckInDate().plusDays(1)+" 02:00:00"); //2am on the next day of check-in date
	}
	
	public boolean checkIn(Reservation reserv)
	{
		if(now.before(scheduledCheckIn) == true)
		{
			System.out.println("Please check-in at your scheduled check-in time.");
			return false;
		}
			
		reserv.setReservStatus(ReservStatus.CHECKED_IN);
		return true;
	}
	
	public void expire(Reservation reserv)
	{
		if(now.after(deadlineCheckIn) == true)
			reserv.setReservStatus(ReservStatus.EXPIRED);
		
		//empty the room
		
	}
}
