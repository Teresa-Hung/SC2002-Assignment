import java.time.LocalTime;
public class Order {
	public Order(int id, MenuItems item,LocalTime time, String remarks, String status) {
		this.orderID = id;
		this.item = item;
		this.orderTime = time;
		this.remarks = remarks;
		this.orderStatus = status;
	}
	public void updateOrder(Order e, String newStatus) {
		e.orderStatus = newStatus;
	}
	public String getStatus(Order e) {
		return e.orderStatus;
	}
	private MenuItems item;
	private int orderID;
	private LocalTime orderTime;
	private String remarks;
	private String orderStatus;
}
// create order
// make a list of order
// add price to invoice
