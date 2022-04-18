package roomservice;

import java.time.LocalTime;
/**
 * Represents one order instance in a list of orders. As of now one order can only have one menu item.
 *
 * @author Bach Nguyen
 *
 */
public class Order {
	/**
	 * The ID of the order
	 */
	private String orderID;
	/**
	 * The room number the order is in
	 */
	private String roomNumber;
	/**
	 * The menu item in the order
	 */
	private MenuItems item;
	/**
	 * The time the order was placed
	 */
	private LocalTime orderTime;
	/**
	 * Remarks regarding the order 
	 */
	private String remarks;
	/**
	 * The status of the order, whether it is ready, in preparation or has been received
	 */
	private OrderStatus status;
	/**
	 * Create a new order all necessary parameters
	 * @param orderID The order's ID
	 * @param number The room number it is in
	 * @param item The item being ordered
	 * @param time The time the order was placed
	 * @param note Remarks regarding the order
	 * @param temp The status of the order, usually it is RECEIVED
	 */
	public Order(String orderID, String number, MenuItems item, LocalTime time, String note, OrderStatus temp) {
		this.orderID = orderID;
		this.roomNumber = number;
		this.item = item;
		this.orderTime = time;
		this.remarks = note;
		this.status = temp;
	}
	/**
	 * Gets the order's ID
	 * @return This Order's orderID
	 */
	public String getID() {
		return this.orderID;
	}
	/**
	 * Gets the room number where the order was placed
	 * @return This Order's roomNumber
	 */
	public String getRoom() {
		return this.roomNumber;
	}
	/**
	 * Gets the time when the order was placed
	 * @return This Order's orderTime
	 */
	public LocalTime getTime() {
		return this.orderTime;
	}
	/**
	 * Changes the order status of this Order.
	 * @param newStatus This Order's new order status. Should be elements in the OrderStatus enum.
	 */
	public void updateOrderStatus(OrderStatus newStatus) {
		this.status = newStatus;
	}
	/**
	 * Gets the remarks that came with this order.
	 * @return This Order's remarks
	 */
	public String getRemarks() {
		return this.remarks;
	}
	/**
	 * Gets the status of the order
	 * @return This Order's status
	 */
	public OrderStatus getStatus() {
		return this.status;
	}
	/**
	 * Get the item that was ordered
	 * @return This Order's item
	 */
	public MenuItems getItem() {
		return this.item;
	}
	/**
	 * Print out one instance of an order with all necessary elements
	 */
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
	/**
	 * Used for an Order's status.
	 * @author User
	 *
	 */
	public enum OrderStatus {RECEIVED, PREPARING, READY}
}