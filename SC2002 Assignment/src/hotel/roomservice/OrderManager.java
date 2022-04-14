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
	public static void saveOrder(String filename, int id, int number) throws IOException{
		Scanner sc = new Scanner(System.in);
		Menu.printMenu("Menu.txt");
		System.out.println("Enter item choice:");
		int choice = sc.nextInt();
		MenuItems item = Menu.readMenu("Menu.txt").get(choice);
		LocalTime time = LocalTime.now();
		System.out.println("Remarks for order:");
		sc.nextLine();
		String note = sc.nextLine();
		OrderStatus temp = OrderStatus.RECEIVED;
		sc.close();
		Order obj = new Order(id, choice, number, item, time, note, temp);
		StringBuilder st = new StringBuilder();
		st.append(Integer.toString(id));
		st.append(SEP);
		st.append(Integer.toString(choice));
		st.append(SEP);
		st.append(Integer.toString(number));
		st.append(SEP);
		st.append(item.itemName());
		st.append(SEP);
		st.append(time);
		st.append(SEP);
		st.append(note);
		st.append(SEP);
		st.append(temp.name());
		//System.out.println("???");
		FileWriter fw = new FileWriter("order.txt",true);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(st);
		fw.close();
		pw.close();
	}
	public static ArrayList<MenuItems> getOrder(String filename, int id) throws IOException{
		ArrayList<String> in = (ArrayList)TextDB.read(filename);
		ArrayList<Order> out = new ArrayList<Order>();
		ArrayList<MenuItems> res = new ArrayList<MenuItems>();
		for(int i = 0; i < in.size();i++)
		{
			String st = (String)in.get(i);
			StringTokenizer star = new StringTokenizer(st,SEP);
			int pos1 = Integer.parseInt(star.nextToken().trim());
			int pos2 = Integer.parseInt(star.nextToken().trim());
			MenuItems item = Menu.readMenu("menu.txt").get(pos2);
			int pos3 = Integer.parseInt(star.nextToken().trim());
			star.nextToken().trim();
			LocalTime time = LocalTime.parse(star.nextToken().trim());
			String note = star.nextToken().trim();
			OrderStatus temp = OrderStatus.valueOf(star.nextToken().trim());
			Order add = new Order(pos1,pos2,pos3,item,time,note,temp);
			out.add(add);
		}
		for(int i = 0; i < out.size(); i++) {
			if(out.get(i).getRoom()==id) {
				res.add(out.get(i).getItem());
			}
		}
		// print out the size
		//System.out.println(" Details Size: " + pDetails.size());
		//System.out.println();
		return res;
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
	public static void clearOrder(String filename) throws IOException{
		PrintWriter writer = new PrintWriter(filename);
		writer.print("");
		writer.close();
	}
	public static void changeStatus(String filename, int id, OrderStatus status, ArrayList<Order> list ) throws IOException{
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getID()==id) {
				list.get(i).updateOrder(status);
			}
		}
	}
}
