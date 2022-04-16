package payment;
import java.util.ArrayList;
import roomservice.MenuItems;
import roomservice.OrderManager

public class RoomServiceCharge {
	private int numItems, i;
	private double totalCharge;
	
	public void RoomServiceCharge() {
		numItems = i = 0;
		totalCharge = 0.0;
	}
	
	public double roomServiceCharge() {
		ArrayList<MenuItems> roomServiceItems;
		OrderManager om = new OrderManager();
		roomServiceItems = om.getOrder("order.txt", roomId, false);
		numItems = roomServiceItems.size();
		for(i=0;i<numItems;i++) {
			totalCharge = totalCharge + roomServiceItems(i).getPrice();
		}
		
		return totalCharge;
	}
	
	public void printAllItems(String roomId) {
		ArrayList<MenuItems> roomServiceItems;
		OrderManager om = new OrderManager();
		roomServiceItems = om.getOrder("order.txt", roomId, false);
		for(i=0;i<numItems;i++) {
			System.out.println(roomServiceItems(i).itemName());
		}
	}
}
