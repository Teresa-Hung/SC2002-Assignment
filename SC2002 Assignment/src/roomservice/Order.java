package roomservice;

import java.time.LocalTime;

public class Order {
	private String orderID;
	private String roomNumber;
	private MenuItems item;
	private LocalTime orderTime;
	private String remarks;
	private OrderStatus status;
	
	public Order(String orderID, String number, MenuItems item, LocalTime time, String note, OrderStatus temp) {
		this.orderID = orderID;
		this.roomNumber = number;
		this.item = item;
		this.orderTime = time;
		this.remarks = note;
		this.status = temp;
	}
	public String getID() {
		return this.orderID;
	}
	public String getRoom() {
		return this.roomNumber;
	}
	public LocalTime getTime() {
		return this.orderTime;
	}
	public void updateOrderStatus(OrderStatus newStatus) {
		this.status = newStatus;
	}
	public String getRemarks() {
		return this.remarks;
	}
	public OrderStatus getStatus() {
		return this.status;
	}
	public MenuItems getItem() {
		return this.item;
	}
	public void printOrder() {
		System.out.println("------------------------------\n"
						 + "Order ID: " + orderID + "\n"
						 + "Room Number: " + roomNumber + "\n"
						 + "Item Ordered: " + item.getItemName() + "\n"
						 + "Order Time: " + orderTime + "\n"
						 + "Additional Remarks: " + remarks + "\n"
						 + "Order Status: " + status + "\n"
						 + "------------------------------");
	}
	
	public enum OrderStatus {RECEIVED, PREPARING, READY}
}