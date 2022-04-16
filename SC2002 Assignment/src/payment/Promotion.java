package payment;

public class Promotion {
	private int promoCode;
	private double promoDiscount;
	
	public Promotion() {
		promoCode = 0;
		promoDiscount = 0.0;
	}
	
	public void setPromoCode(int promotion) {
		promoCode = promotion;
	}
	public double getPromoDisc() {
		switch(promoCode) {
		case 0:
			promoDiscount = 0.0;
		case 1:
			promoDiscount = 0.1;
		case 2:
			promoDiscount = 0.15;
		case 3:
			promoDiscount = 0.2;
		default: 
			System.out.println("Invalid choice.");
		}	
		return promoDiscount;
	}
}
