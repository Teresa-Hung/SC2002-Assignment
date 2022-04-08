import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
public class Menu {
	public static final String SEP = "|";
	public static ArrayList readMenu(String filename) throws IOException {
		//
		ArrayList in = (ArrayList)read(filename);
		ArrayList store = new ArrayList();
		for(int i = 0; i < in.size(); i++) {
			String st = (String)in.get(i);
			StringTokenizer star = new StringTokenizer(st, SEP);
			double price = Double.parseDouble(star.nextToken().trim());
			String itemName = star.nextToken().trim();
			String prepMethod = star.nextToken().trim();
			MenuItems item = new MenuItems(price, itemName, prepMethod);
			store.add(item);
		}
		return store;
	}
	public void printMenu(String filename) throws IOException {
		ArrayList in = (ArrayList)read(filename);
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
	
}
