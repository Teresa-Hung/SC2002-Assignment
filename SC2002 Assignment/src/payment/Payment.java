package payment;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.text.DecimalFormat;

import room.Room;
import room.Room.RoomType;
import roomservice.Order;
import roomservice.OrderManager;
import reservation.Reservation;
import reservation.ReservationManager;

public class Payment {
	
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	private int i;
	private long numOfItems;
	private long numOfDays;
	private double baseCharge;
	private double dayTypeCharge;
	private LocalDate dateIn, dateOut;
	private DayOfWeek day;
	private RoomType rtype;
	private double roomCharge;
	private double roomServiceCharge;
	private double taxCharge;
	private double discountPromo;
	private double totalBill;
	private final double tax = 0.07;
	
	public Payment(Reservation rv) {
		numOfDays = numOfItems = i = 0;
		baseCharge = dayTypeCharge = roomCharge = roomServiceCharge = discountPromo = totalBill = 0.0;
		dateIn = rv.getCheckInDate();
		dateOut = rv.getCheckOutDate();
		day = rv.getCheckInDay();
		rtype = rv.getRType();
	}
	
	private void setBaseCharge(Room room) {
		rtype = room.getRoomType();
		
		
		switch(rtype) {
		case SINGLE:
			baseCharge = 100.0;
			break;
		case DOUBLE:
			baseCharge = 150.0;
			break;
		case SUITE:
			baseCharge = 250.0;
			break;
		case VIP_SUITE:
			baseCharge = 350.0;
			break;
		}
	}
	
	private void setNumOfDays( Reservation rv) {
		dateIn = rv.getCheckInDate();
		dateOut = rv.getCheckOutDate();
		numOfDays = ChronoUnit.DAYS.between(dateIn, dateOut);
	}
	
	private double getTypeCharge(DayOfWeek day) {
		DayOfWeek dayType = day;
		
		switch(dayType) {
		case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY:
			dayTypeCharge = 1.0;
			break;
		case SATURDAY, SUNDAY:
			dayTypeCharge = 1.5;
			break;
		}
		return dayTypeCharge;
	}
	
	private void getRoomCharge(String reservCode) {
		ReservationManager rvm = new ReservationManager();
		Reservation rv = rvm.searchReserv(reservCode);
		Room rm = rv.getRoom();
		
		setBaseCharge(rm);
		setNumOfDays(rv);
		
		day = rv.getCheckInDate().getDayOfWeek();
		
		for(i=0;i<numOfDays;i++) {
			roomCharge = roomCharge + baseCharge*getTypeCharge(day);
			day = day.plus(1);
		}
	}
	
	private void getRoomServiceCharge(String roomId) {
		OrderManager om = new OrderManager();
		ArrayList<Order> roomServiceItems;
		roomServiceItems = om.getRoomCurrentOrders(roomId);
		numOfItems = roomServiceItems.size();
		for(i=0;i<numOfItems;i++) {
			roomServiceCharge = roomServiceCharge + roomServiceItems.get(i).getItem().getPrice();
			System.out.println(roomServiceItems.get(i).getItem().getItemName());
		}
	}
	
	private void printAllItems(String roomId) {
		OrderManager om = new OrderManager();
		ArrayList<Order> roomServiceItems;
		roomServiceItems = om.getRoomCurrentOrders(roomId);
		for(i=0;i<numOfItems;i++) {
			System.out.println(roomServiceItems.get(i).getItem().getItemName());
		}
	}
	
	public void setPromo(int code) {
		switch(code) {
		case 0:
			discountPromo = 0.0;
			break;
		case 1:
			discountPromo = 0.1;
			break;
		case 2:
			discountPromo = 0.15;
			break;
		case 3:
			discountPromo = 0.2;
			break;
		default: 
			System.out.println("Invalid option.");
		}	
		
	}
	
	public void calculateTotal(Reservation rv) {
		roomCharge = 0.0;
		roomServiceCharge = 0.0;
		getRoomCharge(rv.getReservCode());
		getRoomServiceCharge(rv.getRoom().getRoomNumber());
		taxCharge = (roomCharge + roomServiceCharge)*tax;
		totalBill = (roomCharge + roomServiceCharge + taxCharge)*(1.0-discountPromo);
	}
	
	public void printBill() {
		System.out.println("Room charges:\t\t$" + df.format(roomCharge));	
		System.out.println("Room service charges:\t$" + df.format(roomServiceCharge)); 
		System.out.println("Tax (" + df.format(tax*100) + "%):\t\t$" + df.format(taxCharge));
		System.out.println("Total amount due:\t$" + df.format(totalBill));
		if(discountPromo == 0.0) {
			System.out.println("Promo code discount:\tNIL");
		}
		else {
			System.out.println("Promo code discount:\t" + df.format(discountPromo*100) + "% Off");
		}
	}
	
	public void printInvoice(String roomNum) {
		System.out.println("------------Invoice------------\n");
		System.out.println("Number of days stayed:\t" + numOfDays);
		System.out.println("Room charges:\t\t$" + df.format(roomCharge));	
		System.out.println("Room service charges:\t$" + df.format(roomServiceCharge));
		if(roomServiceCharge==0.0) {
			System.out.println("No room service items.");
		}
		else {
			System.out.println("Room Service Items: \t");
			printAllItems(roomNum);
		}
		System.out.println("Tax (" + df.format(tax*100) + "%):\t\t$" + df.format(taxCharge));
		System.out.println("Total amount due:\t$" + df.format(totalBill));
	}
	
}
