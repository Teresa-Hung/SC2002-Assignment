import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.time.LocalTime;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
public class OrderManager{
	public static final String SEP = "|";
	public static void saveOrder(String filename, String number) throws IOException{
		ArrayList<String> in = (ArrayList)read(filename);
		ArrayList<Order> out = new ArrayList<Order>();
		ArrayList alr = new ArrayList();
		int count = 0;
		for(int i = 0; i < in.size();i++)
		{
			String temp = (String)in.get(i);
			//System.out.println(temp);
			StringTokenizer star = new StringTokenizer(temp,SEP);
			String pos1 = star.nextToken().trim();
			String pos2 = star.nextToken().trim();
			if(pos1.equals(number)){
				//System.out.println("WORK ");
				count++;
			}
			String pos3 = star.nextToken().trim();
			MenuItems item = readMenu("menu.txt").get(Integer.parseInt(pos3)-1);
			star.nextToken().trim();
			LocalTime time = LocalTime.parse(star.nextToken().trim());
			String note = star.nextToken().trim();
			OrderStatus status = OrderStatus.valueOf(star.nextToken().trim());
			Order add = new Order(pos1,pos2,pos3,item,time,note,status);
			out.add(add);
		}
		Scanner sc = new Scanner(System.in);
		printMenu("Menu.txt");
		int choice;
		MenuItems item = null;
		while(true) {
			try {
				System.out.println("Enter item choice:");
				choice = sc.nextInt();
				item = readMenu("Menu.txt").get(choice-1);
				break;
			} catch(Exception e) {
				System.out.println("That item does not exist");
			}
		}
		sc.nextLine();
		LocalTime time = LocalTime.now();
		System.out.println("Remarks for order:");
		//sc.nextLine();
		String note = sc.nextLine();
		OrderStatus temp = OrderStatus.RECEIVED;
		//sc.close();
		Order obj = new Order(number, Integer.toString(count+1), Integer.toString(choice), item, time, note, temp);
		//StringBuilder st = new StringBuilder();
		out.add(obj);
		for(int i = 0; i < out.size();i++) {
			StringBuilder st = new StringBuilder();
			Order toAdd = out.get(i);
			st.append(toAdd.getRoom());
			st.append(SEP);
			st.append(toAdd.getID());
			st.append(SEP);
			st.append(toAdd.getItemID());
			st.append(SEP);
			st.append(toAdd.getItem());
			st.append(SEP);
			st.append(toAdd.getTime());
			st.append(SEP);
			st.append(toAdd.getRemarks());
			st.append(SEP);
			st.append(toAdd.getStatus());
			alr.add(st.toString());
		}
		//System.out.println("???");
		write(filename, alr);
	}
	public static ArrayList<MenuItems> getOrder(String filename, String id, boolean print) throws IOException{
		ArrayList<String> in = (ArrayList)TextDB.read(filename);
		ArrayList<Order> out = new ArrayList<Order>();
		ArrayList<MenuItems> res = new ArrayList<MenuItems>();
		boolean found = false;
		for(int i = 0; i < in.size();i++)
		{
			String st = (String)in.get(i);
			StringTokenizer star = new StringTokenizer(st,SEP);
			String pos1 = star.nextToken().trim();
			String pos2 = star.nextToken().trim();
			//MenuItems item = readMenu("menu.txt").get(Integer.parseInt(pos2));
			String pos3 = star.nextToken().trim();
			MenuItems item = readMenu("menu.txt").get(Integer.parseInt(pos3)-1);
			star.nextToken().trim();
			LocalTime time = LocalTime.parse(star.nextToken().trim());
			String note = star.nextToken().trim();
			OrderStatus temp = OrderStatus.valueOf(star.nextToken().trim());
			Order add = new Order(pos1,pos2,pos3,item,time,note,temp);
			out.add(add);
		}
		for(int i = 0; i < out.size(); i++) {
			if(out.get(i).getRoom().equals(id)) {
				found = true;
				res.add(out.get(i).getItem());
			}
		}
		// print out the size
		//System.out.println(" Details Size: " + pDetails.size());
		//System.out.println();
		if(print == true) {
			for(int i = 0; i < res.size(); i++) {
				System.out.println(res.get(i).itemName());
			}
		}
		if(found==false) {
			System.out.println("No order in this room");
			return null;
		}
		return res;
	}
	public static void deleteOrder(String filename, String id, String number) throws IOException {
		ArrayList<String> in = (ArrayList)read(filename);
		ArrayList<Order> out = new ArrayList<Order>();
		ArrayList alr = new ArrayList();
		boolean found = false;
		//System.out.println(id + " " + number);
		for(int i = 0; i < in.size();i++)
		{
			String temp = (String)in.get(i);
			StringTokenizer star = new StringTokenizer(temp,SEP);
			String pos1 = star.nextToken().trim();
			//System.out.println(pos1);
			String pos2 = star.nextToken().trim();
			//System.out.println(pos2);
			if(pos1.equals(number) && pos2.equals(id)){
				//System.out.println("WORK ");
				found = true;
				continue;
			}
			String pos3 = star.nextToken().trim();
			MenuItems item = readMenu("menu.txt").get(Integer.parseInt(pos3)-1);
			star.nextToken().trim();
			LocalTime time = LocalTime.parse(star.nextToken().trim());
			String note = star.nextToken().trim();
			String stringStatus = star.nextToken().trim();
			OrderStatus status = OrderStatus.valueOf(stringStatus);
			Order add = new Order(pos1,pos2,pos3,item,time,note,status);
			out.add(add);
		}
		if(found == false) {
			System.out.println("The order does not exist");
			return;
		}
		for(int i = 0; i < out.size();i++) {
			StringBuilder st = new StringBuilder();
			Order toAdd = out.get(i);
			st.append(toAdd.getRoom());
			st.append(SEP);
			st.append(toAdd.getID());
			st.append(SEP);
			st.append(toAdd.getItemID());
			st.append(SEP);
			st.append(toAdd.getItem());
			st.append(SEP);
			st.append(toAdd.getTime());
			st.append(SEP);
			st.append(toAdd.getRemarks());
			st.append(SEP);
			st.append(toAdd.getStatus().name());
			alr.add(st.toString());
		}
		write(filename,alr);
	}
	public static void printOrder(String filename) throws IOException {
		Scanner sc = new Scanner(new FileInputStream(filename));
		try {
			while (sc.hasNextLine()) {
				System.out.println(sc.nextLine());
			}
		}
		finally {
			sc.close();
		}
	}
	public static void changeStatus(String filename, String id, String number,OrderStatus updatedStatus) throws IOException{
		ArrayList<String> in = (ArrayList)read(filename);
		ArrayList<Order> out = new ArrayList<Order>();
		ArrayList alr = new ArrayList();
		for(int i = 0; i < in.size();i++)
		{
			String temp = (String)in.get(i);
			StringTokenizer star = new StringTokenizer(temp,SEP);
			String pos1 = star.nextToken().trim();
			String pos2 = star.nextToken().trim();
			//MenuItems item = readMenu("menu.txt").get(Integer.parseInt(pos2));
			String pos3 = star.nextToken().trim();
			MenuItems item = readMenu("menu.txt").get(Integer.parseInt(pos2));
			star.nextToken().trim();
			LocalTime time = LocalTime.parse(star.nextToken().trim());
			String note = star.nextToken().trim();
			OrderStatus status= OrderStatus.valueOf(star.nextToken().trim());
			Order add = new Order(pos1,pos2,pos3,item,time,note,status);
			out.add(add);
		}
		for(int i = 0; i < out.size(); i++)
		{
			Order toChange = out.get(i);
			//System.out.println(toChange.getRoom());
			if(toChange.getRoom().equals(number))
			{
				//System.out.println("OK");
				if(toChange.getID().equals(id)) {
					toChange.updateOrder(updatedStatus);
					break;
				}
			}
		}
		for(int i = 0; i < out.size();i++) {
			StringBuilder st = new StringBuilder();
			Order toAdd = out.get(i);
			st.append(toAdd.getRoom());
			st.append(SEP);
			st.append(toAdd.getID());
			st.append(SEP);
			st.append(toAdd.getItemID());
			st.append(SEP);
			st.append(toAdd.getItem());
			st.append(SEP);
			st.append(toAdd.getTime());
			st.append(SEP);
			st.append(toAdd.getRemarks());
			st.append(SEP);
			st.append(toAdd.getStatus().name());
			alr.add(st.toString());
		}
		//System.out.println("???");
		write(filename, alr);
	}
	public static ArrayList<MenuItems> readMenu(String filename) throws IOException {
		//
		ArrayList in = (ArrayList)read(filename);
		ArrayList<MenuItems> store = new ArrayList<MenuItems>();
		for(int i = 0; i < in.size(); i++) {
			int number = i+1;
			String st = (String)in.get(i);
			StringTokenizer star = new StringTokenizer(st, SEP);
			double price = Double.parseDouble(star.nextToken().trim());
			String itemName = star.nextToken().trim();
			String prepMethod = star.nextToken().trim();
			MenuItems item = new MenuItems(number, price, itemName, prepMethod);
			store.add(item);
		}
		return store;
	}
	public static void printMenu(String filename) throws IOException {
		Scanner sc = new Scanner(new FileInputStream(filename));
		try {
			int i = 1;
			while (sc.hasNextLine()) {
				System.out.println(i +") " + sc.nextLine());
				i++;
			}
		}
		finally {
			sc.close();
		}
	}
	public static void write(String fileName, List data) throws IOException  {
	    PrintWriter out = new PrintWriter(new FileWriter(fileName));

	    try {
			for (int i =0; i < data.size() ; i++) {
	      		out.println((String)data.get(i));
			}
	    }
	    finally {
	      out.close();
	    }
	  }

	  /** Read the contents of the given file. */
	  public static List read(String fileName) throws IOException {
		List data = new ArrayList() ;
	    Scanner scanner = new Scanner(new FileInputStream(fileName));
	    try {
	      while (scanner.hasNextLine()){
	        data.add(scanner.nextLine());
	      }
	    }
	    finally{
	      scanner.close();
	    }
	    return data;
	 }
}
