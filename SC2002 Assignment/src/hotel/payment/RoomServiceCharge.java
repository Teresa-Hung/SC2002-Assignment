package miniproj;
import java.util.ArrayList;
import roomservice.MenuItems;
import roomservice.OrderManager

public class RoomServiceCharge {
	private int numItems, i;
	private double totalCharge;
	
	
	public double roomServiceCharge(String roomId) {
		ArrayList<MenuItems> roomServiceItems;
		roomServiceItems = om.getOrder("order.txt", roomId, false);
		numItems = roomServiceItems.size();
		for(i=0;i<numItems;i++) {
			totalCharge = totalCharge + roomServiceItems(i).getPrice();
		}
		
		return totalCharge;
	}
	
	
	public void printAllItems(String roomId) {
		ArrayList<MenuItems> roomServiceItems;
		roomServiceItems = om.getOrder("order.txt", roomId, false);
		for(i=0;i<numItems;i++) {
			System.out.println(roomServiceItems(i).itemName());
		}
	}
}
