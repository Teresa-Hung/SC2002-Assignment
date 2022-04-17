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

/**
 * Represents the billing details of the hotel fees.
 * @author DERRICK NG CHOON SENG
 * @version 1.0
 * @since 2022-04-17
 */
public class Payment {
	
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	private int i;
	/**
	 * The number of items ordered from room service of this Payment.
	 */
	private long numOfItems;
	/**
	 * The length of stay in the hotel of this Payment.
	 */
	private long numOfDays;
	/**
	 * The base charge of the room type of this Payment.
	 */
	private double baseCharge;
	/**
	 * The charge of different types of day.
	 * Weekdays and weekends will be charged differently.
	 */
	private double dayTypeCharge;
	/**
	 * The date of check in and check out of this Payment.
	 */
	private LocalDate dateIn, dateOut;
	/**
	 * The day of check in of this Payment.
	 */
	private DayOfWeek day;
	/**
	 * The type of room of this Payment.
	 */
	private RoomType rtype;
	/**
	 * The total room charge of this payment. 
	 */
	private double roomCharge;
	/**
	 * The total room service charge of this payment. 
	 */
	private double roomServiceCharge;
	/**
	 * The total tax charge of this payment.
	 */
	private double taxCharge;
	/**
	 * The discount rate of the promotion applied for this payment.
	 */
	private double discountPromo;
	/**
	 * The total charge of this payment.
	 */
	private double totalBill;
	/**
	 * The fixed 7% tax rate.
	 */
	private final double tax = 0.07;
	
	/**
	 * Creates a new Payment object with default settings.
	 * @param rv The corresponding Reservation record of this Payment.
	 */
	public Payment(Reservation rv) {
		numOfDays = numOfItems = i = 0;
		baseCharge = dayTypeCharge = roomCharge = roomServiceCharge = discountPromo = totalBill = 0.0;
		dateIn = rv.getCheckInDate();
		dateOut = rv.getCheckOutDate();
		day = rv.getCheckInDay();
		rtype = rv.getRType();
	}
	
	/**
	 * Sets the base charge of this Payment based on the room type.
	 * @param room The associated Room of this Payment.
	 */
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
	
	/**
	 * Sets the length of stay of this Payment based on check-in and check-out date.
	 * Gets the check-in and check-out dates from the Reservation record.
	 * @param rv The corresponding Reservation record of this Payment.
	 */
	private void setNumOfDays(Reservation rv) {
		dateIn = rv.getCheckInDate();
		dateOut = rv.getCheckOutDate();
		numOfDays = ChronoUnit.DAYS.between(dateIn, dateOut);
	}
	
	/**
	 * Gets the charge of the given day type.
	 * @param day The day type.
	 * @return the charge of the given day type.
	 */
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
	
	/**
	 * Computes and gets the total room charge based on the Reservation record.
	 * @param reservCode The corresponding reservation code of this Payment.
	 */
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
	
	/**
	 * Computes and gets the total room service charge based on the Order record of this Payment.
	 * @param roomId The room number to access the Order record.
	 */
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
	
	/**
	 * Prints all the items ordered for this Payment.
	 * @param roomId The room number to access the Order record.
	 */
	private void printAllItems(String roomId) {
		OrderManager om = new OrderManager();
		ArrayList<Order> roomServiceItems;
		roomServiceItems = om.getRoomCurrentOrders(roomId);
		for(i=0;i<numOfItems;i++) {
			System.out.println(roomServiceItems.get(i).getItem().getItemName());
		}
	}
	
	/**
	 * Sets the promoted discount rate provided for this Payment. 
	 * @param code The option for the discount rate.
	 */
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
	
	/**
	 * Calculates the total charge of this Payment.
	 * @param rv The corresponding Reservation record of this Payment.
	 */
	public void calculateTotal(Reservation rv) {
		roomCharge = 0.0;
		roomServiceCharge = 0.0;
		getRoomCharge(rv.getReservCode());
		getRoomServiceCharge(rv.getRoom().getRoomNumber());
		taxCharge = (roomCharge + roomServiceCharge)*tax;
		totalBill = (roomCharge + roomServiceCharge + taxCharge)*(1.0-discountPromo);
	}
	
	/**
	 * Prints the bill of this payment.
	 */
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
	
	/**
	 * Prints the invoice of this payment.
	 * @param roomNum The associated room number of this payment.
	 */
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
