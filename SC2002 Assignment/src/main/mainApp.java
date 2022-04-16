package main;

import java.io.IOException;
import java.util.Scanner;

import guest.*;
import reservation.*;
import room.*;
import roomservice.*;
import ui.UserInterface;

public class mainApp {
	public static void main(String[] args) throws IOException {
		GuestManager gm = new GuestManager();
		ReservationManager resm = new ReservationManager();
		RoomManager rm = new RoomManager();
		OrderManager om = new OrderManager();
		UserInterface ui = new UserInterface();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Hotel reservation system!");
		String choice;
		do {
			System.out.println("(1) Guest Information.\n"
							 + "(2) Reservation Information.\n"
							 + "(3) Room Information.\n"
							 + "(4) Room Service Information.\n"
							 + "(5) Payment Information.\n"
							 + "(6) Quit");
			choice = sc.nextLine();
			switch(choice) {
				case "1":
					ui.guestUI(gm, sc);
					break;
				case "2":
					ui.reservationUI(resm, rm, gm, sc);
					break;
				case "3":
					ui.roomUI(rm, sc);
					break;
				case "4":
					ui.roomServiceUI(om, rm, sc);
					break;
				case "5":
					ui.paymentUI(gm, resm, rm, sc);
					break;
				case "6":
					resm.writeReservation();// save reservation list
					gm.saveGuest(); 		//save guest list
					System.out.print("Quitting...");
					break;
				default:
					System.out.println("Invalid option.");
			}
		} while(!choice.equals("6"));
	}
}
