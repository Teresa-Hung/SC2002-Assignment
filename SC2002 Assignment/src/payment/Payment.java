package payment;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import room.Room;
import room.Room.RoomType;
import roomservice.Order;
import roomservice.OrderManager;
import reservation.Reservation;
import reservation.ReservationManager;

public class Payment {
	
	private int i, promoCode;
	private long numOfItems;
	private long numOfDays;
	private double baseCharge;
	private double dayTypeCharge;
	private LocalDate dateIn, dateOut;
	private DayOfWeek day;
	private RoomType rtype;
	private double roomCharge;
	private double roomServiceCharge;
	private double discountPromo;
	private double totalBill;
	private final double tax = 0.07;
	
	public Payment(Reservation rv) {
		numOfDays = numOfItems = i = promoCode = 0;
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
		case DOUBLE:
			baseCharge = 150.0;
		case SUITE:
			baseCharge = 250.0;
		case VIP_SUITE:
			baseCharge = 350.0;
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
		case SATURDAY, SUNDAY:
			dayTypeCharge = 1.5;
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
	
	private void getRoomServiceCharge(String roomId) throws IOException {
		OrderManager om = new OrderManager();
		ArrayList<Order> roomServiceItems;
		roomServiceItems = om.getRoomCurrentOrders(roomId);
		numOfItems = roomServiceItems.size();
		for(i=0;i<numOfItems;i++) {
			roomServiceCharge = roomServiceCharge + roomServiceItems.get(i).getItem().getPrice();
		}
		
	}
	
	private void printAllItems(String roomId) throws IOException{
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
		case 1:
			discountPromo = 0.1;
		case 2:
			discountPromo = 0.15;
		case 3:
			discountPromo = 0.2;
		default: 
			System.out.println("Invalid choice.");
		}	
		
	}
	
	public void calculateTotal(Reservation rv) throws IOException {
		getRoomCharge(rv.getReservCode());
		getRoomServiceCharge(rv.getRoom().getRoomNumber());
		totalBill = (roomCharge + roomServiceCharge)*tax*(1.0-discountPromo);
	}
	
	public void printBill() {
		System.out.println("Room charges: " + roomCharge);	
		System.out.println("Room service charges: " + roomServiceCharge); 
		System.out.println("Tax: " + tax);
		System.out.println("Total amount due: " + totalBill);
		if(discountPromo == 0.0) {
			System.out.println("Promo code discount: NIL");
		}
		else {
			System.out.println("Promo code discount: " + discountPromo*100 + "% Off");
		}
	}
	
	public void printInvoice(String roomNum) throws IOException {
		System.out.println("Number of days stayed: " + numOfDays);
		System.out.println("Room service charges: " + roomServiceCharge);
		System.out.println("Tax: " + tax);
		System.out.println("Total amount due: " + totalBill);
		printAllItems(roomNum);
	}
	
}
