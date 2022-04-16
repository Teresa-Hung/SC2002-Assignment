package roomservice;

import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import main.FileIO.ReadWrite;
import roomservice.Order.OrderStatus;
import java.time.LocalTime;

public class OrderManager implements ReadWrite {
	public static final String SEP = "|";
	private ArrayList<MenuItems> menuList = new ArrayList<>();
	private ArrayList<Order> orderList = new ArrayList<>();
	
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
	
	public MenuItems findMenuItem(String itemName) {
		for (MenuItems menuItem: menuList) {
			if (menuItem.getItemName().equals(itemName));
			return menuItem;
		}
		return null;
	}
	
	public MenuItems findMenuItem(int i) {
		if (i > menuList.size())
			return null;
		else
			return menuList.get(i-1);
	}
	
	public void addMenuItem(MenuItems item) {
		menuList.add(item);
	}
	
	public void removeMenuItem(int i) {
		if (i > menuList.size()) {
			System.out.println("Invalid number.");
			return;
		}
		System.out.println("Menu item " + menuList.get(i-1).getItemName() + " removed.");
		menuList.remove(i-1);
	}
	
	public String createOrderID() {
		Random rand = new Random();
		char c = (char) (rand.nextInt(26) + 'A');
		Integer i = rand.nextInt(1000);
		return c + Integer.toString(i);
	}
	
	public void addOrder(Order order) {
		orderList.add(order);
	}
	
	public void removeOrder(String id) {
		for (Order order: orderList) {
			if (order.getID().equals(id)) {
				orderList.remove(order);
				return;
			}
		}
		System.out.println("Order ID does not exist.");
	}
	
	public void printOrderList() {
		if (orderList.size() == 0) {
			System.out.println("There are currently no orders.");
			return;
		}
		System.out.println("Print Order List:");
		for (Order order: orderList)
			order.printOrder();
	}
	
	public void printRoomCurrentOrders(String roomNumber) {
		System.out.println("Current orders for room " + roomNumber + ":");
		for (Order order: orderList) {
			if (order.getRoom().equals(roomNumber))
				order.printOrder();
		}
	}
	
	public void printMenu() {
		int i = 0;
		System.out.println("------------------------------");
		for (MenuItems item: menuList) {
			System.out.println("Menu Item " + ++i);
			item.printItem();
		}
		System.out.println("------------------------------");
	}
	
	public void updateStatus(String id, OrderStatus status) {
		for (Order order: orderList) {
			if (order.getID().equals(id)) {
				order.updateOrderStatus(status);
				return;
			}
		}
	}
	
	private ArrayList<MenuItems> readMenu(String fileName) throws IOException {
		ArrayList<String> in = read(fileName);
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
	
	private ArrayList<Order> readOrder(String fileName) throws IOException {
		ArrayList<String> in = read(fileName);
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
	
	public void saveOrderList() {
		ArrayList<String> data = new ArrayList<>();
        for (Order order: orderList) {
			StringBuilder st =  new StringBuilder();
			st.append(order.getID() + SEP);
			st.append(order.getRoom() + SEP);
			st.append(order.getItem().getItemName() + SEP);
			st.append(order.getTime() + SEP);
			st.append(order.getStatus() + SEP);
			data.add(st.toString());
		}
        write("order.txt", data);
	}

	public ArrayList<String> read(String fileName) {
		ArrayList<String> data = new ArrayList<>();
		try {
			Scanner sc = new Scanner(new FileInputStream(fileName));
			while(sc.hasNextLine())
				data.add(sc.nextLine());
			sc.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		return data;
	}

	public void write(String fileName, List<String> data) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter("roomlist.txt"));
			for (String s: data)
				out.println(s);
			out.close();
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
}