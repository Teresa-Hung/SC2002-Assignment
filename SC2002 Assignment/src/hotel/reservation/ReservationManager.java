package reservation;

import java.util.Scanner;
import java.util.Random;

import reservation.Reservation.ReservStatus;
import room.*;

public class ReservationManager {
	
	Scanner sc = new Scanner(System.in);
	Room[] roomlist; // read roomlist from file
	String[] waitlist = new String[100]; // store the reservation in waitlist
	int waitlistNum = 0;
	
	public boolean createReserv(Reservation reserv)
	{
		//get check-in and check-out date
		if(!reserv.setDates(true,true)) return false;
		
		//get number of adults/children
		reserv.setNumAdult();
		reserv.setNumChild();
		
		reserv.setRoom(roomlist); // get room
		if(reserv.getReservStatus() == ReservStatus.IN_WAITLIST) // put reservation in waitlist
			waitlist[waitlistNum++] = reserv.getReservCode();
		
		//set guest details
		
		
		//generate reservation code
		reserv.setReservCode(createReservCode());
		return true;
	}
	
	public String createReservCode()
	{
		Random rand = new Random();
		char c = (char) (rand.nextInt(26)+'A');
		Integer i = rand.nextInt(1000);
		return c+Integer.toString(i);
	}
	
	public void updateReserv(Reservation[] reservlist)
	{
		String code;
		int len = reservlist.length;
		System.out.println("Update reservation...");
		System.out.println("Please enter the reservation code: ");
		code = sc.next();
		
		//find the target reservation
		Reservation target = new Reservation();
		boolean find = false;
		for(int i=0;i<len;i++)
		{
			if(reservlist[i].getReservCode().compareTo(code) == 0)
			{
				target = reservlist[i];
				find = true;
				break;
			}
		}
		if(find == false)
		{
			System.out.println("The reservation code does not exist.");
			return;
		}
			
		//update
		int choice;
		
		System.out.println("Please choose the detail you would like to update.");
		System.out.println("1: Check-in date");
		System.out.println("2: Check-out date");
		System.out.println("3: Room");
		System.out.println("4: Guest details");
		System.out.println("5: Billing information");
		System.out.println("6: number of adults");
		System.out.println("7: number of children");
		System.out.println("");
		choice = sc.nextInt();
		
		boolean success = true;
		switch(choice)
		{
		case 1:
			success = target.setDates(true, false);
			break;
			
		case 2:
			success = target.setDates(false, true);
			break;
			
		case 3:
			target.setRoom(roomlist);
			break;
		
		case 4:
			break;
		
		case 5:
			break;
		
		case 6:
			target.setNumAdult();
			break;
		
		case 7:
			target.setNumChild();
			break;
			
		default:
			System.out.println("The choice is invalid.");
			
		}
		if(success) System.out.println("The detail has been successfully updated.");
		target.printReceipt();
	}
	
	// method to manage waitlist
}
