
import java.util.Scanner;

import guest.Guest;
import guest.GuestManager;
import room.*;
import roomservice.Order.OrderStatus;
import roomservice.OrderManager;

public class mainApp {
	
	public static void main(String[] args) {
		RoomManager rm = new RoomManager("48_Hotel_Rooms.txt");
		Scanner sc = new Scanner(System.in);
		System.out.println("Hotel reservation system!");
		String choice1;
		do {
			System.out.println("1) Guest Information.");
			System.out.println("2) Reservation Information.");
			System.out.println("3) Room Information.");
			System.out.println("4) Room Service Information.");
			System.out.println("5) Payment Information.");
			System.out.println("6) Quit");
			choice1 = sc.nextLine();
			switch(choice1) {
				case "1":
					GuestManager manager = new GuestManager();
					System.out.println("1) Update Guest Details.");
					System.out.println("2) Search Guest.");
					switch(sc.nextLine()) {
						case "1":
							Guest g = manager.updateDetails();
							if(g != null)
								manager.displayGuestDetails(g);
							break;
						case "2":
							Guest guest1 = manager.searchGuest();
							if(guest1 != null) {
								System.out.println("Guest found.");
								manager.displayGuestDetails(guest1);
							}
							break;
						default:
							System.out.println("Invalid choice.");
					}
				case "2":
					//reservation functions
					System.out.println("1) Create Reservation.");
					System.out.println("2) Update Reservation.");
					System.out.println("3) Remove Reservation.");
					System.out.println("4) Print Reservation Status.");
					int b = sc.nextInt();
					switch(b) {
					
					
					
					}
					break;
				case "3":
					roomUI(rm, sc);
					break;
				case "4":
					// room service functions
					System.out.println("1) Create order");
					System.out.println("2) Delete order");
					System.out.println("3) Get the menu");
					System.out.println("4) Get list of orders");
					System.out.println("5) Update order status");
					String room;
					switch(sc.nextLine()) {
						case "1":
							System.out.println("Enter room number");
							room = sc.nextLine();
							if(rm.getRoomStatus(room) == RoomStatus.VACANT) {
								System.out.println("This room is empty");
								break;
							}
							OrderManager.saveOrder("order.txt",room);
							break;
						case "2":
							System.out.println("Enter room number");
							room = sc.nextLine();
							if(rm.getRoomStatus(room) == RoomStatus.VACANT) {
								System.out.println("This room is empty");
								break;
							}
							String id = sc.nextLine();
							OrderManager.deleteOrder("order.txt",id,room);
							break;
						case "3":
							OrderManager.printMenu("order.txt");
							break;
						case "4":
							OrderManager.printOrder("order.txt");
							break;
						case "5":
							System.out.println("Enter room number");
							room = sc.nextLine();
							if(rm.getRoomStatus(room) == RoomStatus.VACANT) {
								System.out.println("This room is empty");
								break;
							}
							System.out.println("Enter order id");
							String orderID = sc.nextLine(); 
							System.out.println("1) Preparing");
							System.out.println("2) Ready");
							int status = sc.nextInt();
							OrderManager.changeStatus("order.txt", orderID, room, OrderStatus.values()[status]);
							break;
						default:
							System.out.println("Invalid choice.");
					}
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
					rm.writeRoomList("48_Hotel_Rooms.txt"); //save room list before program exit
					break;
				default:
					System.out.println("Invalid choice.");
			}
		} while(!choice1.equals("6"));
	}
	
	private static void roomUI(RoomManager rm, Scanner sc) {
		String choice;
		do {
			System.out.println("-------------------------------\n"
					 + "Room Menu:\n"
			        	 + "(1) Print Room Occupancy Report\n"
			        	 + "(2) Print Room Status Report\n"
			        	 + "(3) Print Room Report\n"
			        	 + "(4) Print Room Details\n"
			        	 + "(5) Update Room Details\n"
			        	 + "(6) Exit\n"
			        	 + "-------------------------------");
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
				updateRoomDetailsUI(rm, sc);
				break;
			case "6":
				rm.writeRoomList("48_Hotel_Rooms.txt");
				break;
			default:
				System.out.println("Invalid option.");
			}
		} while (!choice.equals("6"));
	}

	private static void updateRoomDetailsUI(RoomManager rm, Scanner sc) {
		System.out.print("Enter room number: ");
		String roomNumber = sc.nextLine();
		if (rm.findRoom(roomNumber) == null) return;
		System.out.print("--------------------\n"
				+ "Update Details Menu:\n"
				+ "(1) Room Number\n"
				+ "(2) Room Type\n"
				+ "(3) Bed Type\n"
				+ "(4) Room Status\n"
				+ "(5) Toggle WiFi\n"
				+ "(6) Toggle Smoking\n"
				+ "(7) Toggle Balcony\n"
				+ "(8) Max Size\n"
				+ "--------------------\n"
				+ "Enter option: ");
		switch (sc.nextLine()) {
		case "1":
			System.out.print("Enter new room number: ");
			String newRoomNumber = sc.nextLine();
			if (newRoomNumber.length() != 4) {
				System.out.println("Invalid room number format.");
				return;
			}
			try {
				// if new room number is not numeric, parseInt throws NumberFormatException
				Integer.parseInt(newRoomNumber);
				boolean valid = roomNumber.charAt(0) == newRoomNumber.charAt(0) && roomNumber.charAt(1) == newRoomNumber.charAt(1);
				if (!valid) {
					System.out.println("Invalid room number format.");
					return;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid room number format.");
				return;
			}
			if (rm.findRoom(newRoomNumber, false) != null) {
				System.out.println("New room number already exists.");
				return;
			}
			rm.updateRoomNumber(roomNumber, newRoomNumber);
			System.out.println("Room number set to " + newRoomNumber);
			break;
		case "2":
			System.out.print("Options: SINGLE, DOUBLE, SUITE, VIP_SUITE\n"
					+ "Enter new room type: ");
			try {
				RoomType newRoomType = RoomType.valueOf(sc.nextLine().toUpperCase());
				rm.updateRoomType(roomNumber, newRoomType);
				System.out.println("Room type set to " + newRoomType);
			} catch (Exception e) {
				System.out.println("Invalid room type.");
			}
			break;
		case "3":
			System.out.print("Options: TWIN, QUEEN, KING\n"
					+ "Enter new bed type: ");
			try {
				BedType newBedType = BedType.valueOf(sc.nextLine().toUpperCase());
				rm.updateBedType(roomNumber, newBedType);
				System.out.println("Bed type set to " + newBedType);
			} catch (Exception e) {
				System.out.println("Invalid bed type.");
			}
			break;
		case "4":
			System.out.print("Options: VACANT, OCCUPIED, RESERVED, UNDER_MAINTENANCE\n"
					+ "Enter new room status: ");
			try {
				RoomStatus newRoomStatus = RoomStatus.valueOf(sc.nextLine().toUpperCase());
				rm.updateRoomStatus(roomNumber, newRoomStatus);
				System.out.println("Room status set to " + newRoomStatus);
			} catch (Exception e) {
				System.out.println("Invalid room status.");
			}
			break;
		case "5":
			boolean wifiEnabled = rm.isRoomWifiEnabled(roomNumber);
			rm.updateRoomWifi(roomNumber, !wifiEnabled);
			System.out.println("WiFi Enabled set to " + !wifiEnabled + ".");
			break;
		case "6":
			boolean smoking = rm.isRoomSmoking(roomNumber);
			rm.updateRoomSmoking(roomNumber, !smoking);
			System.out.println("Smoking set to " + !smoking + ".");
			break;
		case "7":
			boolean balcony = rm.roomHasBalcony(roomNumber);
			rm.updateRoomBalcony(roomNumber, !balcony);
			System.out.println("Balcony set to " + !balcony + ".");
			break;
		case "8":
			System.out.print("Enter new max size: ");
			try {
				int i = Integer.parseInt(sc.nextLine());
				rm.updateRoomMaxSize(roomNumber, i);
				System.out.println("Max size set to " + i);
			} catch (NumberFormatException e) {
				System.out.println("Invalid size.");
			}
			break;
		default:
			System.out.println("Invalid option.");
		}
	}
}
