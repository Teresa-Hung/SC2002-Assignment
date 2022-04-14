package sc2002Proj;
import java.util.Scanner;

public class mainApp {
	RoomManager rm = new RoomManager("48_Hotel_Rooms.txt");
	public static void main(String[] args) {
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
			choice1 = sc.next();
			switch(choice1) {
				case "1":
					GuestManager manager = new GuestManager();
					System.out.println("1) Update Guest Details.");
					System.out.println("2) Search Guest.");
					String a = sc.next();
					switch(a) {
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
					rm.roomUI();
					break;
				case "4":
					//room service functions
					//room service functions
					System.out.println("1) Create order");
					System.out.println("2) Delete order");
					System.out.println("3) Get the menu");
					System.out.println("4) Get list of orders");
					System.out.println("5) Update order status");
					int choice = sc.nextInt();
					switch(choice) {
						case 1:{
							System.out.println("Enter room number");
							String room = sc.nextLine();
							if(RoomManager.findRoom(room).getRoomStatus().name()==VACANT) {
								System.out.println("This room is empty");
								break;
							}
							OrderManager.saveOrder("order.txt",room);
							break;
						}
						case 2:{
							System.out.println("Enter room number");
							String room = sc.nextLine();
							if(RoomManager.findRoom(room).getRoomStatus().name()==VACANT) {
								System.out.println("This room is empty");
								break;
							}
							String id = sc.nextLine();
							OrderManager.deleteOrder("order.txt",id,room);
							break;
						}
						case 3:{
							OrderManager.printMenu("order.txt");
							break;
						}
						case 4:{
							OrderManager.printOrder("order.txt");
							break;
						}
						case 5:{
							System.out.println("Enter room number");
							String room = sc.nextLine();
							if(RoomManager.findRoom(room).getRoomStatus().name()==VACANT) {
								System.out.println("This room is empty");
								break;
							}
							System.out.println("Enter order id");
							String orderID = sc.nextLine(); 
							System.out.println("1) Preparing");
							System.out.println("2) Ready");
							int status = sc.nextInt();
							sc.nextLine();
							OrderManager.changeStatus("order.txt", orderID, room, OrderStatus.values()[status]);
							break;
						}
						default: 
							System.out.println("Wrong choice");
							break;
					}
				case "5":
					// create objects
					Payment pay = new Payment();
					Promotion promo = new Promotion();
					RoomCharge rc = new RoomCharge();
					RoomServiceCharge rsc = new RoomServiceCharge();
					Guest payingGuest = manager.searchGuest();
					ReservationManager resman = new ReservationManager();
					
					String guestRoom = payingGuest.getRoomNum();
					String reservCode = payingGuest.getReserveCode();
					ArrayList<Reservation> reservList = resman.getReservList();
					
					System.out.println("1) Make Payment\n");
					System.out.println("2) Print Invoice\n");
					System.out.println("Enter choice: \n");
					int choice = sc.nextInt();
					
					switch(choice){
						case 1: 
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
								paytype = sc.nextInt();
								switch(paytype) {
									case 1: 
										System.out.println("Cash payment successful!\n");
										guest.setPaid(1);
									case 2:
										System.out.println("Card payment successful!\n");
										guest.setPaid(1);
								}
							}
						case 2: 
							if(payingGuest.getPaid() == 0) {
								System.out.println("Payment has not been made!\n");
							}
							else {
								//print final invoice
								pay.printInvoice();
								rsc.printAllItems(guestRoom);
							}
					}
					
						
					//delete guest
							
					//set room to vacant
					break;
				default: 
					System.out.println("Invalid choice.");
			}
		}while(!choice1.equals("6"));
			
	rm.writeRoomList("48_Hotel_Rooms.txt") // end of program save room list
	}

}
