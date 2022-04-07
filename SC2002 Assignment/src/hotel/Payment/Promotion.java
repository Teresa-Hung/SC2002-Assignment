package Payment;

public class Promotion {
	private String promoCode;
	private double promoDiscount;
	
	public void setPromoCode(String promotion) {
		promoCode = promotion;
	}
	public double getPromoDisc() {
		switch(promoCode) {
		case " ":
			promoDiscount = 0.1;
		
		case "  ":
			promoDiscount = 0.15;
			
		case "   ":
			promoDiscount = 0.2;
		
		case "NIL":
			promoDiscount = 1.0;
		}
		
		return promoDiscount;
	}
}
