package miniproj;

public class Payment {
	private long numOfDays;
	private double roomCharge;
	private double roomServiceCharge;
	private double discountPromo;
	private double totalBill;
	private final double tax = 0.07;
	
	public void setNumOfDays(long days) {
		numOfDays = days;
	}
	
	public void setRoomCharge(double rc) {
		roomCharge = rc;
	}
	
	public void setRoomServiceCharge(double rs) {
		roomServiceCharge = rs;
	}
	
	public void setDiscountPromo(double disc) {
		discountPromo = disc;
	}
	
	public void calculateTotal() {
		totalBill = (roomCharge + roomService)*tax*(1.0-discountPromo);
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
	
	public void printInvoice() {
		System.out.println("Number of days stayed: " + numOfDays);
		System.out.println("Room service charges: " + roomServiceCharge;
		System.out.println("Tax: " + tax);
		System.out.println("Total amount due: " + totalBill);
	}
	
}
