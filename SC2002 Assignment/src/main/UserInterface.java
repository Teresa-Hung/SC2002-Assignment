package main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import guest.*;
import payment.Payment;
import reservation.*;
import reservation.Reservation.ReservStatus;
import room.*;
import room.Room.*;
import roomservice.*;
import roomservice.Order.OrderStatus;

public class UserInterface {
	public static void guestUI(Scanner sc) {
		GuestManager gm = GuestManager.getInstance();
		String choice;
		do {
			System.out.print("========================\n"
						   + "      Guest Menu:\n"
						   + "========================\n"
						   + "(1) Update Guest Details\n"
						   + "(2) Search Guest\n"
						   + "(3) Remove Guest\n"
						   + "(4) Return to Main Menu\n"
						   + "========================\n"
						   + "Enter option: ");
			choice = sc.nextLine();
			switch(choice) {
				case "1":
					gm.updateGuest(sc);
					break;
				case "2":
					if(gm.getGuestList().size() == 0) {
						System.out.println("There are no guests registered in the hotel.");
						return;
					}
					System.out.println("(1) Search by ID.");
					System.out.println("(2) Search by name.");
					Guest g = null;
					switch(sc.nextLine()) {
						case "1":
							System.out.print("Enter guest ID: ");
							String tempId = sc.nextLine();
							g = gm.findById(tempId);
							break;
						case "2":
							System.out.println("Enter first name and last name: ");
							String fname = sc.next();
							String lname = sc.next();
							if (sc.hasNextLine()) sc.nextLine();
							g = gm.findByName(fname, lname);
							break;
						default:
							System.out.println("Invalid option.");
					}
					if(g != null) {
						System.out.println("Guest found.");
						gm.displayGuestDetails(g);
					}
					else
						System.out.println("Guest does not exist.");
					break;
				case "3":
					System.out.print("Enter reservation code to remove: ");
					gm.removeGuest(sc.nextLine());
					break;
				case "4":
//					gm.saveGuest();
					break;
				default:
					System.out.println("Invalid option.");
			}
		} while (!choice.equals("4"));
	}
	
	public static void reservationUI(Scanner sc) {
		ReservationManager resm = ReservationManager.getInstance();
		GuestManager gm = GuestManager.getInstance();
		RoomManager rm = RoomManager.getInstance();
		String choice;
		do {
			System.out.print("============================\n"
					 	   + "     Reservation Menu:\n"
					 	   + "============================\n"
					 	   + "(1) Create Reservation\n"
					 	   + "(2) Update Reservation\n"
					 	   + "(3) Remove Reservation\n"
					 	   + "(4) Print Selected Reservation Status\n"
					 	   + "(5) Print All Reservation\n"
					 	   + "(6) Check In\n"
					 	   + "(7) Return to Main Menu\n"
					 	   + "============================\n"
					 	   + "Enter option: ");
			choice = sc.nextLine();
			switch(choice) {
			case "1":
				System.out.println("Creating reservation...");
				Reservation reserv = new Reservation();
				
				// generate reservation code
				String reservCode = resm.createReservCode();
				reserv.setReservCode(reservCode);
				
				// create guest
				Guest g = gm.createGuest(sc);
				if (g == null) return;
				reserv.setGuest(g);
				g.setReservCode(reservCode);
				
				// assign room for guest
				while (true) {
					try {
						System.out.print("Options: SINGLE, DOUBLE, SUITE, VIP_SUITE\n"
								   	   + "Enter the room type: ");
						RoomType roomType = RoomType.valueOf(sc.nextLine().toUpperCase());
						System.out.print("Options: TWIN, QUEEN, KING\n"
								   	   + "Enter the bed type: ");
						BedType bedType = BedType.valueOf(sc.nextLine().toUpperCase());
						Room r = rm.getAvailableRoom(roomType, bedType);
						if (r != null) {
							reserv.setRoom(r);
							reserv.setRType(roomType);
							reserv.setBType(bedType);
							r.setRoomStatus(RoomStatus.RESERVED);
							g.setRoomNum(r.getRoomNumber());
							break;
						}
					} catch (IllegalArgumentException e) {
						System.out.println("Invalid type entered.");
						continue;
					}
				}
				while (true) {
					try {
						// get check-in and check-out date
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						System.out.println("Enter Check-in Date (dd/mm/yyyy): ");
						LocalDate checkInDate = LocalDate.parse(sc.nextLine(), formatter);
						System.out.println("Enter Check-out Date (dd/mm/yyyy): ");
						LocalDate checkOutDate = LocalDate.parse(sc.nextLine(), formatter);
						if (resm.checkDates(checkInDate, checkOutDate)) {
							reserv.setCheckInDate(checkInDate);
							reserv.setCheckOutDate(checkOutDate);
							break;
						}
					} catch (DateTimeParseException e) {
						System.out.println("The date is invalid.");
						continue;
					}
				}
				while (true) {
					try {
						// get number of adults/children
						System.out.println("Enter the Number of Adults: ");
						reserv.setNumAdult(Integer.parseInt(sc.nextLine()));
						System.out.println("Enter the Number of Children: ");
						reserv.setNumChild(Integer.parseInt(sc.nextLine()));
						break;
					} catch (NumberFormatException e) {
						System.out.println("Invalid number entered.");
						continue;
					}
				}
				
				// success
				reserv.setReservStatus(ReservStatus.CONFIRMED);
				resm.addReserv(reserv);
				System.out.println("Reservation successfully created with code " + reservCode);
				break;
			case "2":
				System.out.println("Updating reservation...");
				updateReservationUI(sc);
				break;
			case "3":
				System.out.println("Enter reservation code to remove:");
				resm.removeReservRecord(sc.nextLine());
				break;
			case "4":
				System.out.println("Enter reservation code to search:");
				String reservCode1 = sc.nextLine();
				Reservation reserv1 = resm.searchReserv(reservCode1);
				if (reserv1 != null)
					reserv1.printReceipt();
				break;
			case "5":
				resm.printAllReserv();
				break;
			case "6":
				System.out.println("Checking in...");
				System.out.print("==============================\n"
						 	   + "       Check-in Menu:\n"
						 	   + "==============================\n"
						 	   + "(1) Check In With Reservation\n"
						 	   + "(2) Walk In\n"
						 	   + "(3) Return to Reservation Menu\n"
						 	   + "==============================\n"
						 	   + "Enter option: ");
				switch(sc.nextLine()) {
				case "1":
					System.out.println("Enter reservation code to check in:");
					Reservation reserv2 = resm.searchReserv(sc.nextLine());
					if(reserv2 == null) break;
					resm.checkIn(reserv2);
					break;
				case "2":
					Reservation reserv3 = new Reservation();
					
					// generate reservation code
					String reservCode3 = resm.createReservCode();
					reserv3.setReservCode(reservCode3);
					
					// create guest
					Guest g2 = gm.createGuest(sc);
					if (g2 == null) return;
					reserv3.setGuest(g2);
					g2.setReservCode(reservCode3);
					
					// assign room for guest
					while (true) {
						try {
							System.out.print("Options: SINGLE, DOUBLE, SUITE, VIP_SUITE\n"
									   	   + "Enter the room type: ");
							RoomType roomType = RoomType.valueOf(sc.nextLine().toUpperCase());
							System.out.print("Options: TWIN, QUEEN, KING\n"
									   	   + "Enter the bed type: ");
							BedType bedType = BedType.valueOf(sc.nextLine().toUpperCase());
							Room r = rm.getAvailableRoom(roomType, bedType);
							if (r != null) {
								reserv3.setRoom(r);
								reserv3.setRType(roomType);
								reserv3.setBType(bedType);
								r.setRoomStatus(RoomStatus.RESERVED);
								g2.setRoomNum(r.getRoomNumber());
								break;
							}
						} catch (IllegalArgumentException e) {
							System.out.println("Invalid type entered.");
							continue;
						}
					}
					while (true) {
						try {
							// get check-in and check-out date
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
							LocalDate checkInDate = LocalDate.now();
							System.out.println("Enter Check-out Date (dd/mm/yyyy): ");
							LocalDate checkOutDate = LocalDate.parse(sc.nextLine(), formatter);
							if (resm.checkDates(checkInDate, checkOutDate)) {
								reserv3.setCheckInDate(checkInDate);
								reserv3.setCheckOutDate(checkOutDate);
								break;
							}
						} catch (DateTimeParseException e) {
							System.out.println("The date is invalid.");
							continue;
						}
					}
					while (true) {
						try {
							// get number of adults/children
							System.out.println("Enter the Number of Adults: ");
							reserv3.setNumAdult(Integer.parseInt(sc.nextLine()));
							System.out.println("Enter the Number of Children: ");
							reserv3.setNumChild(Integer.parseInt(sc.nextLine()));
							break;
						} catch (NumberFormatException e) {
							System.out.println("Invalid number entered.");
							continue;
						}
					}
					
					// success
					reserv3.setReservStatus(ReservStatus.CONFIRMED);
					resm.addReserv(reserv3);
					resm.checkIn(reserv3);
					break;
				case "3":
					break;
				default:
					System.out.println("Invalid option.");
				}
				break;
			case "7":
//				resm.writeReservation();
//				rm.saveRoomList();
//				gm.saveGuest();
				break;
			default:
				System.out.println("Invalid option.");
			}
		} while (!choice.equals("7"));
	}
	
	public static void updateReservationUI(Scanner sc) {
		ReservationManager resm = ReservationManager.getInstance();
		System.out.print("Enter the reservation code: ");
		String code = sc.nextLine();

		// find the target reservation
		Reservation target = resm.searchReserv(code);
		if (target == null) return; // target does not exist

		// update
		System.out.print("==============================\n"
					   + "     Update Details Menu:\n"
					   + "==============================\n"
					   + "(1) Check-in date\n"
					   + "(2) Check-out date\n"
					   + "(3) Room\n"
					   + "(4) Number of Adults\n"
					   + "(5) Number of Children\n"
					   + "(6) Return to Reservation Menu\n"
					   + "==============================\n"
					   + "Enter option: ");
		switch (sc.nextLine()) {
		case "1":
			System.out.println("Enter Check-in Date (dd/mm/yyyy): ");
			resm.updateCheckInDate(code, sc.nextLine());
			break;
		case "2":
			System.out.println("Enter Check-out Date (dd/mm/yyyy): ");
			resm.updateCheckOutDate(code, sc.nextLine());
			break;
		case "3":
			RoomType roomType;
			BedType bedType;
			while (true) {
				try {
					System.out.println("Enter the room type: ");
					roomType = RoomType.valueOf(sc.nextLine().toUpperCase());
					System.out.println("Enter the bed type: ");
					bedType = BedType.valueOf(sc.nextLine().toUpperCase());
				} catch (IllegalArgumentException e) {
					System.out.println("Invalid type entered.");
					return;
				}
				RoomManager rm = RoomManager.getInstance();
				Room r = rm.getAvailableRoom(roomType, bedType);
				if (r != null) {
					target.setRoom(r);
					break;
				}
			}
			System.out.println("The room has been successfully updated.");
			break;
		case "4":
			try {
				System.out.println("Enter the Number of Adults: ");
				target.setNumAdult(Integer.parseInt(sc.nextLine()));
			} catch (NumberFormatException e) {
				System.out.println("Invalid number entered.");
				return;
			}
			System.out.println("Number of Adults updated to " + target.getNumAdult());
			break;
		case "5":
			try {
				System.out.println("Enter the Number of Children: ");
				target.setNumChild(Integer.parseInt(sc.nextLine()));
			} catch (NumberFormatException e) {
				System.out.println("Invalid number entered.");
				return;
			}
			System.out.println("Number of Children updated to " + target.getNumChild());
			break;
		case "6":
			break;
		default:
			System.out.println("Invalid option.");
		}
	}
	
	public static void roomUI(Scanner sc) {
		RoomManager rm = RoomManager.getInstance();
		String choice;
		do {
			System.out.print("===============================\n"
						   + "           Room Menu:\n"
						   + "===============================\n"
			        	   + "(1) Print Room Occupancy Report\n"
			        	   + "(2) Print Room Status Report\n"
			        	   + "(3) Print Room Report\n"
			        	   + "(4) Print Room Details\n"
			        	   + "(5) Update Room Details\n"
			        	   + "(6) Return to Main Menu\n"
			        	   + "===============================\n"
			        	   + "Enter option: ");
			choice = sc.nextLine();
			switch (choice) {
			case "1":
				rm.printOccupancyReport();
				break;
			case "2":
				rm.printStatusReport();
				break;
			case "3":
				rm.printRoomReport();
				break;
			case "4":
				System.out.print("Enter room number: ");
				rm.printRoomDetails(sc.nextLine());
				break;
			case "5":
				updateRoomDetailsUI(sc);
				break;
			case "6":
//				rm.saveRoomList();
				break;
			default:
				System.out.println("Invalid option.");
			}
		} while (!choice.equals("6"));
	}

	private static void updateRoomDetailsUI(Scanner sc) {
		RoomManager rm = RoomManager.getInstance();
		System.out.print("Enter room number: ");
		String roomNumber = sc.nextLine();
		if (rm.findRoom(roomNumber) == null) return;
		System.out.print("=======================\n"
					   + "  Update Details Menu:\n"
					   + "=======================\n"
					   + "(1) Room Number\n"
					   + "(2) Room Type\n"
					   + "(3) Bed Type\n"
					   + "(4) Room Status\n"
					   + "(5) Toggle WiFi\n"
					   + "(6) Toggle Smoking\n"
					   + "(7) Toggle Balcony\n"
					   + "(8) Max Size\n"
					   + "(9) Return to Room Menu\n"
					   + "=======================\n"
					   + "Enter option: ");
		switch (sc.nextLine()) {
		case "1":
			System.out.print("Enter new room number: ");
			rm.updateRoomNumber(roomNumber, sc.nextLine());
			break;
		case "2":
			System.out.print("Options: SINGLE, DOUBLE, SUITE, VIP_SUITE\n"
						   + "Enter new room type: ");
			rm.updateRoomType(roomNumber, sc.nextLine());
			break;
		case "3":
			System.out.print("Options: TWIN, QUEEN, KING\n"
						   + "Enter new bed type: ");
			rm.updateBedType(roomNumber, sc.nextLine());
			break;
		case "4":
			System.out.print("Options: VACANT, OCCUPIED, RESERVED, UNDER_MAINTENANCE\n"
						   + "Enter new room status: ");
			rm.updateRoomStatus(roomNumber, sc.nextLine());
			break;
		case "5":
			rm.toggleRoomWifi(roomNumber);
			break;
		case "6":
			rm.toggleRoomSmoking(roomNumber);
			break;
		case "7":
			rm.toggleRoomBalcony(roomNumber);
			break;
		case "8":
			System.out.print("Enter new max size: ");
			rm.updateRoomMaxSize(roomNumber, sc.nextLine());
			break;
		case "9":
			break;
		default:
			System.out.println("Invalid option.");
		}
	}
	
	public static void roomServiceUI(Scanner sc) {
		OrderManager om = OrderManager.getInstance();
		RoomManager rm = RoomManager.getInstance();
		String choice, room;
		MenuItems item;
		double price;
		int number;
		do {
			System.out.print("========================\n"
						   + "   Room Service Menu:\n"
						   + "========================\n"
						   + "(1) Create Order\n"
						   + "(2) Delete Order\n"
						   + "(3) Print Order List\n"
						   + "(4) Update Order Status\n"
						   + "(5) Create New Menu Item\n"
						   + "(6) Remove Menu Item\n"
						   + "(7) Print Menu\n"
						   + "(8) Update Menu Item\n"
						   + "(9) Return to Main Menu\n"
						   + "========================\n"
						   + "Enter option: ");
			choice = sc.nextLine();
			switch(choice) {
				case "1":
					String orderID = om.createOrderID();
					System.out.print("Enter room number: ");
					room = sc.nextLine();
					if (rm.findRoom(room) == null) return;
					if(rm.getRoomStatus(room) == RoomStatus.VACANT) {
						System.out.println("This room is empty.");
						break;
					}
					om.printMenu();
					System.out.println("Enter item choice:");
					try {
						item = om.findMenuItem(Integer.parseInt(sc.nextLine()));
					} catch (NumberFormatException e) {
						System.out.println("Invalid option.");
						return;
					}
					LocalTime time = LocalTime.now();
					System.out.println("Remarks for order:");
					String note = sc.nextLine();
					OrderStatus status = OrderStatus.RECEIVED;
					Order order = new Order(orderID, room, item, time, note, status);
					System.out.println("Order created:");
					order.printOrder();
					om.addOrder(order);
					break;
				case "2":
					System.out.print("Enter room number: ");
					room = sc.nextLine();
					if (rm.findRoom(room) == null) return;
					if(rm.getRoomStatus(room) == RoomStatus.VACANT) {
						System.out.println("This room is empty");
						break;
					}
					om.printRoomCurrentOrders(room);
					System.out.print("Enter order ID to remove: ");
					om.removeOrder(sc.nextLine());
					break;
				case "3":
					om.printOrderList();
					break;
				case "4":
					System.out.println("Enter room number");
					room = sc.nextLine();
					if (rm.findRoom(room) == null) return;
					if(rm.getRoomStatus(room) == RoomStatus.VACANT) {
						System.out.println("This room is empty");
						break;
					}
					System.out.println("Enter order id:");
					String orderID1 = sc.nextLine(); 
					System.out.println("(1) Preparing");
					System.out.println("(2) Ready");
					OrderStatus status1 = OrderStatus.valueOf(sc.nextLine().toUpperCase());
					om.updateStatus(orderID1, status1);
					break;
				case "5":
					System.out.println("Creating new menu item...");
					System.out.println("Enter item price: ");
					try {
						price = Double.parseDouble(sc.nextLine());
					} catch (NumberFormatException e) {
						System.out.println("Invalid price.");
						return;
					}
					System.out.println("Enter item name:");
					String name = sc.nextLine();
					System.out.println("Enter preparation method:");
					String prep = sc.nextLine();
					item = new MenuItems(price, name, prep);
					om.addMenuItem(item);
					System.out.println("New menu item created.");
					break;
				case "6":
					om.printMenu();
					System.out.print("Enter menu item number to remove: ");
					try {
						number = Integer.parseInt(sc.nextLine());
					} catch (NumberFormatException e) {
						System.out.println("Invalid number.");
						return;
					}
					om.removeMenuItem(number);
					break;
				case "7":
					om.printMenu();
					break;
				case "8":
					om.printMenu();
					System.out.print("Enter menu item number to update: ");
					try {
						number = Integer.parseInt(sc.nextLine());
					} catch (NumberFormatException e) {
						System.out.println("Invalid number.");
						return;
					}
					item = om.findMenuItem(number);
					System.out.print("=======================\n"
								   + "Enter detail to update:\n"
								   + "=======================\n"
								   + "(1) Price\n"
								   + "(2) Name\n"
								   + "(3) Preparation Method\n"
								   + "=======================\n"
								   + "Enter option: ");
					switch (sc.nextLine()) {
					case "1":
						System.out.print("Enter new price: ");
						try {
							price = Double.parseDouble(sc.nextLine());
						} catch (NumberFormatException e) {
							System.out.println("Invalid number.");
							break;
						}
						item.setPrice(price);
						System.out.println("Price set to " + item.getPrice());
						break;
					case "2":
						System.out.print("Enter new name: ");
						item.setItemName(sc.nextLine());
						System.out.println("Name set to " + item.getItemName());
						break;
					case "3":
						System.out.print("Enter new preparation method: ");
						item.setPrep(sc.nextLine());
						System.out.print("Preparation method set to " + item.getPrep());
						break;
					default:
						System.out.println("Invalid option.");
					}
					break;
				case "9":
//					om.saveMenuList();
//					om.saveOrderList();
					break;
				default:
					System.out.println("Invalid option.");
			}
		} while (!choice.equals("9"));
	}
	
	public static void paymentUI(Scanner sc){
		ReservationManager resm = ReservationManager.getInstance();
		GuestManager gm = GuestManager.getInstance();
		RoomManager rm = RoomManager.getInstance();
		OrderManager om = OrderManager.getInstance();
		String fn, ln, guestRoom, reservCode, choice;
		Guest payingGuest;
		Reservation rv;
		System.out.println("Enter first name and last name: ");
		fn = sc.next();
		ln = sc.next();
		if (sc.hasNextLine()) sc.nextLine();
		payingGuest = gm.findByName(fn, ln);
		if (payingGuest == null) {
			System.out.println("Guest does not exist.");
			return;
		}
		guestRoom = payingGuest.getRoomNum();
		reservCode = payingGuest.getReservCode();

		rv = resm.searchReserv(reservCode);
		Payment pay = new Payment(rv);
		
		do {
			System.out.print("=======================\n"
						   + "     Payment Menu:\n"
						   + "=======================\n"
						   + "(1) Make Payment\n"
						   + "(2) Print Invoice\n"
						   + "(3) Checkout\n"
						   + "(4) Occupancy Report\n"
						   + "(5) Return to Main Menu\n"
						   + "=======================\n"
						   + "Enter option: ");
			choice = sc.nextLine();
			switch(choice) {
			case "1":
				if(payingGuest.getPaid() == 1){
					System.out.println("Payment already made!\n");
				}
				else {
					// bill before promo code
					pay.calculateTotal(rv);
					pay.printBill();
					
					// bill after promo code
					System.out.print("================\n"
								   + "Promo Available:\n"
								   + "================\n"
								   + "(0) NIL\n"
								   + "(1) 10%\n"
								   + "(2) 15%\n"
								   + "(3) 20%\n"
								   + "================\n"
								   + "Enter option: ");
					try {
						pay.setPromo(Integer.parseInt(sc.nextLine()));
					} catch (NumberFormatException e) {
						System.out.println("Invalid option.");
						return;
					}
					pay.calculateTotal(rv);
					System.out.println("---new bill after promo---");
					pay.printBill();
					
					// payment type
					System.out.print("===============\n"
								   + " Payment Type:\n"
								   + "===============\n"
								   + "(1) Cash\n"
								   + "(2) Credit Card\n"
								   + "===============\n"
								   + "Enter option: ");
					switch(sc.nextLine()) {
						case "1": 
							System.out.println("Cash payment successful!\n");
							payingGuest.setPaid(1);
							break;
						case "2":
							gm.displayBillingDetails(payingGuest);
							System.out.println("Card payment successful!\n");
							payingGuest.setPaid(1);
							break;
						default:
							System.out.println("Invalid option.");
					}
				}
				break;
			case "2":
				if(payingGuest.getPaid() == 0) {
					System.out.println("Payment has not been made!\n");
				}
				else {
					//print final invoice
					pay.printInvoice(guestRoom);
				}
				break;
			case "3": 
				if (payingGuest.getPaid() == 1) {
					rm.updateRoomStatus(guestRoom, RoomStatus.VACANT);
					resm.removeReservRecord(reservCode);
					gm.removeGuest(reservCode);
					om.removeRoomCurrentOrders(guestRoom);
				}
				else {
					System.out.println("Payment has not been made!\n");
				}
				break;
			case "4":
				rm.printOccupancyReport();
				break;
			case "5":
				break;
			default:
				System.out.println("Invalid option.");
			}
		} while (!choice.equals("5"));
	}
}