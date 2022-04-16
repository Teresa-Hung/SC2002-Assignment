package main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import guest.*;
import reservation.*;
import reservation.Reservation.ReservStatus;
import room.*;
import room.Room.*;
import roomservice.*;
import roomservice.Order.OrderStatus;

public class mainApp {
	public static void main(String[] args) {
		GuestManager gm = new GuestManager();
		ReservationManager resm = new ReservationManager();
		RoomManager rm = new RoomManager();
		OrderManager om = new OrderManager();
		
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
					gm.guestUI(gm, sc);
					break;
				case "2":
					resm.reservationUI(resm, rm, gm, sc);
					break;
				case "3":
					rm.roomUI(rm, sc);
					break;
				case "4":
					om.roomServiceUI(om, rm, sc);
					break;
				case "5":
					Payment pay = new Payment();
					Promotion promo = new Promotion();
					RoomCharge rc = new RoomCharge();
					RoomServiceCharge rsc = new RoomServiceCharge();
					Guest payingGuest = manager.searchGuest();
					ReservationManager resman = new ReservationManager();
					
					String guestRoom = payingGuest.getRoomNum();
					String reservCode = payingGuest.getReserveCode();
					ArrayList<Reservation> reservList = resman.getReservList();
					
					System.out.println("1) Make Payment");
					System.out.println("2) Print Invoice");
					System.out.println("3) Checkout");
					System.out.println("4) Occupancy Report");
					System.out.println("Enter choice: ");
					
					switch(sc.nextLine()){
						case "1": 
							if(payingGuest.getPaid() == 1){
								System.out.println("Payment already made!\n");
							}
							else{
								// bill before promo code
								pay.setRoomCharge(rc.getRoomCharge(reservList, reservCode));
								pay.setRoomServiceCharge(rsc.roomServiceCharge(guestRoom));
								pay.setDiscountPromo(0.0);
								pay.calculateTotal();
								pay.printBill
								
								// bill after promo code
								System.out.println("Enter promo code: (0)NIL, (1)10% (2)15% (3)20%\n");
								int promocode = sc.nextInt();
								promo.setPromoCode(promocode);
								
								pay.setDiscountPromo(promo.getPromoDisc());
								pay.calculateTotal();
								pay.printBill();
								
								// payment type
								System.out.println("Enter payment type: (1)Cash (2)Credit card\n");
								switch(sc.nextLine()) {
									case "1": 
										System.out.println("Cash payment successful!\n");
										payingGuest.setPaid(1);
										break;
									case "2":
										System.out.println("Billing Details: \n");
										System.out.println("Name" + payingGuest.getHolderFName() + payingGuest.getHolderLName()\n);
										System.out.println("Credit card number" + payingGuest.getCcNum()\n);
										System.out.println("Billing address" + payingGuest.getBillAddr()\n);
										System.out.println("Card payment successful!\n");
										payingGuest.setPaid(1);
										break;
									default:
										System.out.println("Invalid choice.");
								}
							}
							break;
						case "2": 
							if(payingGuest.getPaid() == 0) {
								System.out.println("Payment has not been made!\n");
							}
							else {
								//print final invoice
								pay.printInvoice();
								rsc.printAllItems(guestRoom);
							}
							break;
						case "3":
							if (payingGuest.getPaid() == 1)
								rm.updateRoomStatus(guestRoom, RoomStatus.VACANT);
							else
								System.out.println("Payment has not been made!\n");
							break;
						case "4":
							rm.printOccupancyReport();
							break;
						default:
							System.out.println("Invalid choice.");
					}
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