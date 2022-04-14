package miniproj;

import java.util.*;
import guest.GuestManager;
import java.sql.Timestamp;
import reservation.Reservation;

public class PaymentApp {
	public void paymentApplication(ArrayList<Reservation> reservList){
		int choice, paytype;
		String promocode, guestID;
		
		Payment pay;
		Promotion promo;
		RoomCharge rc;
		RoomServiceCharge rsc;
		Scanner sc = new Scanner(System.in);
		
		Guest guest = manager.searchGuest();
		
		String guestRoom = guest.getRoomNum();
		String reservCode = guest.getReserveCode();
		
		

		System.out.println("1) Make Payment\n");
		System.out.println("2) Print Invoice\n");
		System.out.println("Enter choice: \n");
		choice = sc.nextInt();
		
		switch(choice) {
		case 1:
			if(guest.getPaid == 1) {
				System.out.println("Payment already made!\n");
			}
			else {				
				//set payment details
				pay.setRoomCharge(rc.getRoomCharge(reservList,reservCode));
				pay.setRoomServiceCharge(rsc.roomServiceCharge(guestRoom));
				pay.setDiscountPromo(0.0);
				pay.setNumOfDays(rc.getNumOfDays);
				
				//print bill without promo
				pay.calculateTotal();
				pay.printBill();
				
				//check for promo and print new bill
				System.out.println("Enter promo code: (0)NIL, (1)10% (2)15% (3)20%\n");
				promocode = sc.nextInt();
				promo.setPromoCode(promocode);
				
				pay.setDiscountPromo(promo.getPromoDisc());
				pay.calculateTotal();
				pay.printBill();
				
				//select payment type
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
			if(paysuccess == false) {
				System.out.println("Payment has not been made!\n");
			}
			else {
				//print final invoice
				pay.printInvoice();
				rsc.printAllItems(guestRoom);
			}
		}	
	}
