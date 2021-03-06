package main;

import java.io.IOException;
import java.util.Scanner;

import guest.*;
import reservation.*;
import room.*;
import roomservice.*;

/**
 * The main application of the Hotel Reservation and Payment System.
 * Contains five main applications-Guest, Room, Reservation, Room Service, and Payment.
 * @author BRYAN WU JIAHE, DERRICK NG CHOON SENG, EVANGELINE NG XUAN HUI, HUNG KUO-CHEN, NGUYEN TUNG BACH
 * @version 1.0
 * @since 2022-04-17
 */
public class mainApp {
	public static void main(String[] args) throws IOException {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("========================\n"
						 + " Hotel Reservation and\n"
						 + "    Payment System!\n"
						 + "========================");
		String choice;
		do {
			System.out.print("========================\n"
						   + "       Main Menu:\n"
						   + "========================\n"
						   + "(1) Manage Guests\n"
						   + "(2) Manage Reservations\n"
						   + "(3) Manage Rooms\n"
						   + "(4) Manage Room Services\n"
						   + "(5) Make Payment\n"
						   + "(6) Save and Quit\n"
						   + "========================\n"
						   + "Enter option: ");
			choice = sc.nextLine();
			switch(choice) {
				case "1":
					UserInterface.guestUI(sc);
					break;
				case "2":
					UserInterface.reservationUI(sc);
					break;
				case "3":
					UserInterface.roomUI(sc);
					break;
				case "4":
					UserInterface.roomServiceUI(sc);
					break;
				case "5":
					UserInterface.paymentUI(sc);
					break;
				case "6":
					ReservationManager.getInstance().writeReservation();
					GuestManager.getInstance().saveGuest();
					RoomManager.getInstance().saveRoomList();
					OrderManager.getInstance().saveMenuList();
					OrderManager.getInstance().saveOrderList();
					System.out.print("Quitting...");
					break;
				default:
					System.out.println("Invalid option.");
			}
		} while(!choice.equals("6"));
	}
}