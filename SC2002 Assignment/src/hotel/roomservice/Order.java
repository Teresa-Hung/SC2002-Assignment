import java.time.LocalTime;
import java.util.Scanner;
import java.io.IOException;
enum OrderStatus{RECEIVED, PREPARING, READY}
public class Order {
	public Order(String number, String orderID, String itemID, MenuItems item, LocalTime time, String note, OrderStatus temp) {
		this.orderID = orderID;
		this.itemID = itemID;
		this.roomNumber = number;
		this.item = item;
		this.orderTime = time;
		this.remarks = note;
		this.status = temp;
	}
	public String getID() {
		return this.orderID;
	}
	public String getItemID() {
		return this.itemID;
	}
	public String getRoom() {
		return this.roomNumber;
	}
	public LocalTime getTime() {
		return this.orderTime;
	}
	public void updateOrder(OrderStatus newStatus) {
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
	private String orderID;
	private String itemID;
	private String roomNumber;
	private MenuItems item;
	private LocalTime orderTime;
	private String remarks;
	private OrderStatus status;
}
// create order
// make a list of order
// add price to invoice
// 
