package roomservice;

import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import main.FileIO.ReadWrite;
import roomservice.Order.OrderStatus;
import java.time.LocalTime;
/**
 * Manages functionalities regarding the orders and menu items
 * @author User
 *
 */
public class OrderManager implements ReadWrite {
	/**
	 * Separator used when reading from and to file
	 */
	public static final String SEP = "|";
	/**
	 * An ArrayList containing the menu
	 */
	private ArrayList<MenuItems> menuList = new ArrayList<>();
	/**
	 * An ArrayList containing the orders
	 */
	private ArrayList<Order> orderList = new ArrayList<>();
	/**
	 * Constructor. Creates menuList from "menu.txt" and orderList from "order.txt"
	 */
	public OrderManager() {
		try {
			menuList = readMenu("menu.txt");
			orderList = readOrder("order.txt");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("read from file failed");
		}
	}
	/**
	 * Gets an item from the menu with the given name
	 * @param itemName The name of the item to search
	 * @return a MenuItems object if the search is successful, null if there is no such item
	 */
	public MenuItems findMenuItem(String itemName) {
		for (MenuItems menuItem: menuList) {
			if (menuItem.getItemName().equals(itemName))
				return menuItem;
		}
		return null;
	}
	/**
	 * Gets an item from the menu with the given index
	 * @param i The index of the item
	 * @return a MenuItems object if the search is successful, null if the index is out of range
	 */
	public MenuItems findMenuItem(int i) {
		if (i <= 0 || i > menuList.size())
			return null;
		else
			return menuList.get(i-1);
	}
	/**
	 * Adds a new menu item to the menuList
	 * @param item a new MenuItems object
	 */
	public void addMenuItem(MenuItems item) {
		menuList.add(item);
	}
	/**
	 * Deletes an item on the menu based on its index
	 * @param i the index of the item to remove
	 */
	public void removeMenuItem(int i) {
		if (i <= 0 || i > menuList.size()) {
			System.out.println("Invalid number.");
			return;
		}
		System.out.println("Menu item " + menuList.get(i-1).getItemName() + " removed.");
		menuList.remove(i-1);
	}
	/**
	 * Creates a random orderID
	 * @return a new randomized orderID
	 */
	public String createOrderID() {
		Random rand = new Random();
		char c = (char) (rand.nextInt(26) + 'A');
		Integer i = rand.nextInt(1000);
		return c + Integer.toString(i);
	}
	/**
	 * Adds a new Order to the orderList
	 * @param order the new Order to add
	 */
	public void addOrder(Order order) {
		orderList.add(order);
	}
	/**
	 * Deletes an order based on its id
	 * @param id the orderID of the Order object to delete
	 */
	public void removeOrder(String id) {
		for (Order order: orderList) {
			if (order.getID().equals(id)) {
				orderList.remove(order);
				return;
			}
		}
		System.out.println("Order ID does not exist.");
	}
	/**
	 * Print out all current orders
	 */
	public void printOrderList() {
		if (orderList.size() == 0) {
			System.out.println("There are currently no orders.");
			return;
		}
		System.out.println("Print Order List:");
		for (Order order: orderList)
			order.printOrder();
	}
	/**
	 * Print out all current orders in a room
	 * @param roomNumber the roomNumber of the room to print out the orders
	 */
	public void printRoomCurrentOrders(String roomNumber) {
		System.out.println("Current orders for room " + roomNumber + ":");
		for (Order order: orderList) {
			if (order.getRoom().equals(roomNumber))
				order.printOrder();
		}
	}
	/**
	 * Gets all current orders in a room
	 * @param roomNumber the roomNumber of the room to access
	 * @return an ArrayList of all the orders in that room
	 */
	public ArrayList<Order> getRoomCurrentOrders(String roomNumber) {
		ArrayList<Order> roomOrders = new ArrayList<>();
		for (Order order: orderList) {
			if (order.getRoom().equals(roomNumber))
				roomOrders.add(order);
		}
		return roomOrders;
	}
	/**
	 * Deletes all orders in a given room
	 * @param roomNumber the roomNumber to deletes all the orders in
	 */
	public void removeRoomCurrentOrders(String roomNumber) {
		Iterator<Order> itr = orderList.iterator();
		while (itr.hasNext()) {
			Order order = itr.next();
			if (order.getRoom().equals(roomNumber))
				itr.remove();
		}
	}
	/**
	 * Prints out the menu
	 */
	public void printMenu() {
		int i = 0;
		System.out.println("------------------------------");
		for (MenuItems item: menuList) {
			System.out.println("Menu Item " + ++i);
			item.printItem();
		}
		System.out.println("------------------------------");
	}
	/**
	 * Updates the status of an order given its orderID and new status
	 * @param id The orderID of the Order object to modify
	 * @param status The new status of the Order, passed in as an enum OrderStatus
	 */
	public void updateStatus(String id, OrderStatus status) {
		for (Order order: orderList) {
			if (order.getID().equals(id)) {
				order.updateOrderStatus(status);
				return;
			}
		}
	}
	/**
	 * Reads the .txt file that the menu is stored in
	 * @param filename The name of the file to read from
	 * @return An ArrayList containing the menu
	 * @throws IOException Wrong filename or file does not exist
	 */
	private ArrayList<MenuItems> readMenu(String filename) throws IOException {
		ArrayList<String> in = read(filename);
		ArrayList<MenuItems> store = new ArrayList<>();
		for(String st: in) {
			StringTokenizer star = new StringTokenizer(st, SEP);
			double price = Double.parseDouble(star.nextToken().trim());
			String itemName = star.nextToken().trim();
			String prepMethod = star.nextToken().trim();
			MenuItems item = new MenuItems(price, itemName, prepMethod);
			store.add(item);
		}
		return store;
	}
	/**
	 * Reads the .txt file that the list of orders is stored in
	 * @param filename The name of the file to read from
	 * @return An ArrayList containing all the orders
	 * @throws IOException Wrong filename or file does not exist
	 */
	private ArrayList<Order> readOrder(String filename) throws IOException {
		ArrayList<String> in = read(filename);
		ArrayList<Order> store = new ArrayList<>();
		for(String st: in) {
			StringTokenizer star = new StringTokenizer(st, SEP);
			String orderID = star.nextToken().trim();							// orderID
			String roomNumber = star.nextToken().trim();						// roomNumber
			String itemName = star.nextToken().trim();
			MenuItems item = findMenuItem(itemName);							// item
			String time = star.nextToken().trim();
			LocalTime orderTime = LocalTime.parse(time);						// orderTime
			String remarks = star.nextToken().trim();							// remarks
			OrderStatus status = OrderStatus.valueOf(star.nextToken().trim());	// status
			
			Order order = new Order(orderID, roomNumber, item, orderTime, remarks, status);
			store.add(order);
		}
		return store;
	}
	/**
	 * Save the menuList to the "menu.txt" file
	 */
	public void saveMenuList() {
		ArrayList<String> data = new ArrayList<>();
        for (MenuItems item: menuList) {
			StringBuilder st =  new StringBuilder();
			st.append(item.getPrice() + SEP);
			st.append(item.getItemName() + SEP);
			st.append(item.getPrep() + SEP);
			data.add(st.toString());
		}
        write("menu.txt", data);
	}
	/**
	 * Save the orderList to the "order.txt" file
	 */
	public void saveOrderList() {
		ArrayList<String> data = new ArrayList<>();
        for (Order order: orderList) {
			StringBuilder st =  new StringBuilder();
			st.append(order.getID() + SEP);
			st.append(order.getRoom() + SEP);
			st.append(order.getItem().getItemName() + SEP);
			st.append(order.getTime() + SEP);
			st.append(order.getRemarks() + SEP);
			st.append(order.getStatus() + SEP);
			data.add(st.toString());
		}
        write("order.txt", data);
	}
	/**
	 * FileIO helper with reading files
	 */
	public ArrayList<String> read(String filename) {
		ArrayList<String> data = new ArrayList<>();
		try {
			Scanner sc = new Scanner(new FileInputStream(filename));
			while(sc.hasNextLine())
				data.add(sc.nextLine());
			sc.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		return data;
	}
	/**
	 * File IO helper with writing files
	 */
	public void write(String filename, List<String> data) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(filename));
			for (String s: data)
				out.println(s);
			out.close();
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
}
