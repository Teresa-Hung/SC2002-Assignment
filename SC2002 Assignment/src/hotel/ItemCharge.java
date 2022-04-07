package hotel;

public class ItemCharge {
	private String roomServiceItem;
	private double itemCharge;
	
	public void setItem(String item) {
		roomServiceItem = item;
	}
	
	public double itemCost() {
		switch(roomServiceItem) {
		case " ":
			itemCharge = 1.0;
		case "  ":
			itemCharge = 2.0;
		case "   ":
			itemCharge = 3.0;
		}
		
		return itemCharge;
	}
}
