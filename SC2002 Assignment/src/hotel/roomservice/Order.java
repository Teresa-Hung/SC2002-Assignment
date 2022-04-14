import java.time.LocalTime;
import java.util.Scanner;
import java.io.IOException;
enum OrderStatus{RECEIVED, PREPARING, READY}
public class Order {
	public Order(int id, int itemID, int number, MenuItems item, LocalTime time, String note, OrderStatus temp) {
		this.orderID = id;
		this.itemID = itemID;
		this.roomNumber = number;
		this.item = item;
		this.orderTime = time;
		this.remarks = note;
		this.status = temp;
	}
	public int getID() {
		return this.orderID;
	}
	public int getRoom() {
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
	private int orderID;
	private int itemID;
	private int roomNumber;
	private MenuItems item;
	private LocalTime orderTime;
	private String remarks;
	private OrderStatus status;
}
// create order
// make a list of order
// add price to invoice
// 
