package reservation;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import reservation.Reservation.ReservStatus;
import room.*;
import room.Room.RoomType;
import room.Room.Status;

public class Reservation {
	
	public enum ReservStatus
	{
		CONFIRMED,
		IN_WAITLIST,
		CHECKED_IN,
		EXPIRED
	}
	
	private ReservStatus reservStatus;
	private String reservCode;
	private Room room;
	private LocalDate dateCheckIn;
	private LocalDate dateCheckOut;
	private int numAdult;
	private int numChild;
	
	Scanner sc = new Scanner(System.in);
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public void setReservStatus(ReservStatus s)
	{
		reservStatus = s;
	}
	
	public void setReservCode(String s)
	{
		reservCode = s;
	}
	
	public void setRoom(Room[] roomlist)
	{
		System.out.println("Please enter the room type you would like to reserve: ");
		RoomType type = RoomType.valueOf(sc.next());
		// check roomlist, if vacant then assign room to customer
		boolean vacant = false;
		for(int i=0; i<roomlist.length; i++)
		{
			if(roomlist[i].getRoomType() == type)
			{
				if(roomlist[i].getStatus() == Status.VACANT)
				{
					vacant = true;
					room = roomlist[i];
					setReservStatus(ReservStatus.CONFIRMED);
					break;
				}
			}
		}
		if(vacant == false) setReservStatus(ReservStatus.IN_WAITLIST);
	}
	
	public boolean setDates(boolean checkin, boolean checkout)
	{
		try 
		{
			String date;
			LocalDate dCI = dateCheckIn, dCO = dateCheckOut;
			if(checkin == true)
			{
				System.out.println("Please enter your intended check-in date (dd/mm/yyyy): ");
				date = sc.next();
				dCI = LocalDate.parse(date,formatter);
			}
			if(checkout == true)
			{
				System.out.println("Please enter your intended check-out date (dd/mm/yyyy): ");
				date = sc.next();
				dCO = LocalDate.parse(date,formatter);
			}
			// if dates are valid, update and return true
			if(checkDates(dCI,dCO))
			{
				dateCheckIn = dCI;
				dateCheckOut = dCO;
				return true;
			}
			return false;
		}
		
		catch(DateTimeParseException e)
		{
			System.out.println("The date is invalid.");
			return false;
		}
	}
	
	// check check-in/out dates are valid
	public boolean checkDates(LocalDate dCI, LocalDate dCO)
	{
		if(dCI.compareTo(dCO)>0)
		{
			System.out.println("The scheduled check-in and check-out dates are invalid.");
			return false;
		}
		return true;
	}
	
	public void setNumAdult()
	{
		System.out.println("Please enter the number of adults: ");
		numAdult = sc.nextInt();
	}
	
	public void setNumChild()
	{
		System.out.println("Please enter the number of children: ");
		numChild = sc.nextInt();
	}
	
	
	public ReservStatus getReservStatus()
	{
		return reservStatus;
	}
	
	public String getReservCode()
	{
		return reservCode;
	}
	
	public Room getRoom()
	{
		return room;
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
