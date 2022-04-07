package hotel;
import java.util.ArrayList;

public class RoomServiceCharge {
	private String roomServiceItem;
	private int numItems, i;
	private double totalCharge;
	
	ArrayList<ItemCharge> itemlist = new ArrayList<ItemCharge>();
	
	public void setNumItems(int items) {
		numItems = items;
	}
	
	public double roomServiceCharge() {
		totalCharge = 0.0;
		for(i=0;i<numItems;i++) {
			totalCharge  = totalCharge + ItemCharge[i].itemCost();
		}
		
		return totalCharge;
	}
}
