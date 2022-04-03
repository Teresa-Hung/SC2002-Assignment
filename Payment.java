package miniproj;


public class Payment {
	private double totalBill;
	private double roomCharge;
	private final double tax = 0.07;
	private double roomService;
	private boolean discountPromo;
	
	RoomCharge rc;
	RoomServiceCharge rsc;
	
	roomCharge = rc.roomRate();
	roomService = rsc.roomServiceCharge();
	
	public void calculateTotal(double roomCharge, double roomService, double discountPromo) {
		totalBill = (roomCharge + roomService)*tax*discountPromo;
	}
	
	public void printBill() {
		System.out.println("Room charges: " + rc.roomRate());
		System.out.println("Room service charges: " + rsc.roomServiceCharge());
		System.out.println("Tax: " + tax);
		System.out.println("Total amount due: " + totalBill);
	}
	
	public void printInvoice() {
		System.out.println("Number of days stayed: " + rc.getNumOfDays());
		System.out.println("Room service items: " /*+ print roomservice items*/);
		System.out.println("Room service charges: " + rsc.roomServiceCharge());
		System.out.println("Tax: " + tax);
		System.out.println("Total amount due: " + totalBill);
	}
	
}
