package hotel;

import java.util.Scanner;

import Reservation.Reservation.Status;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;

public class ReservationManager {
	
	Scanner sc = new Scanner(System.in);
	
	public boolean createReserv(Reservation reserv)//only confirm or waitlist at this stage
	{
		//get check-in and check-out date
		String date;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			System.out.println("Please enter your intended check-in date (dd/mm/yyyy): ");
			date = sc.next();
			reserv.dateCheckIn = LocalDate.parse(date,formatter);
			System.out.println("Please enter your intended check-out date (dd/mm/yyyy): ");
			date = sc.next();
			reserv.dateCheckOut = LocalDate.parse(date,formatter);
		}
		catch(DateTimeParseException e)
		{
			System.out.println("The date is invalid.");
			return false;
		}
		finally
		{
			if(reserv.dateCheckIn.compareTo(reserv.dateCheckOut)>0)
			{
				System.out.println("The date is invalid.");
				return false;
			}	
		}
		
		//System.out.println(dateCheckIn);
		//System.out.println(dateCheckOut);
		
		//get number of adults/children
		System.out.println("Please enter the number of adults: ");
		reserv.numAdult = sc.nextInt();
		System.out.println("Please enter the number of children: ");
		reserv.numChild = sc.nextInt();
		
		/*
		//print room status
		//getStatus(); from room;

		//choose room
		Room room = new Room();
		System.out.println("Please enter the number of the room you would like to reserve: ");
		room = sc.nextInt();
		
		//check the availability of the room
		//if has empty room, confirm reservation
		reservStatus = Status.CONFIRMED;
		
		//if no empty room, waitlist
		reservStatus = Status.IN_WAITLIST;*/
		
		//set guest details
		
		//generate reservation code
		reserv.reservStatus = Status.CONFIRMED;// for testing
		//reserv.reservNum = createReservNum();
		reserv.reservCode = createReservCode();
		return true;
	}
	
	public long createReservNum()
	{
		Timestamp temp = Timestamp.from(Instant.now());
		return temp.getTime();
	}
	
	public String createReservCode()
	{
		Random rand = new Random();
		char c = (char) (rand.nextInt(26)+'A');
		Integer i = rand.nextInt(1000);
		return c+Integer.toString(i);
	}
	
	public void updateReservation(Reservation[] reservlist)
	{
		//long num;
		String code;
		int len = reservlist.length;
		System.out.println("Update reservation...");
		System.out.println("Please enter the reservation code: ");
		//num = sc.nextLong();
		code = sc.next();
		
		//find the target reservation
		Reservation target = new Reservation();
		boolean find = false;
		for(int i=0;i<len;i++)
		{
			if(reservlist[i].reservCode.compareTo(code) == 0)
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
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
		
		switch(choice)
		{
		case 1:
			String date;
			System.out.println("Please enter your intended check-in date (dd/mm/yyyy): ");
			date = sc.next();
			target.dateCheckIn = LocalDate.parse(date,formatter);
			System.out.println("The detail has been successfully updated.");
			break;
			
		case 2:
			String date2;
			System.out.println("Please enter your intended check-out date (dd/mm/yyyy): ");
			date2 = sc.next();
			target.dateCheckOut = LocalDate.parse(date2,formatter);
			System.out.println("The detail has been successfully updated.");
			break;
			
		case 3:
			break;
		
		case 4:
			break;
		
		case 5:
			break;
		
		case 6:
			System.out.println("Please enter the number of adults: ");
			target.numAdult = sc.nextInt();
			System.out.println("The detail has been successfully updated.");
			break;
		
		case 7:
			System.out.println("Please enter the number of children: ");
			target.numChild = sc.nextInt();
			System.out.println("The detail has been successfully updated.");
			break;
			
		default:
			System.out.println("The choice is invalid.");
			
		}
		target.printReceipt();
	}
}
