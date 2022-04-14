import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.time.*;
public class Menu {
	public static final String SEP = "|";
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
				System.out.println(i + sc.nextLine());
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
	//public static MenuItems getMenuItem(int i) { //when in function minus by one, cuz count from 0
		//return(this.items.get(i));
	//}
	//private ArrayList<MenuItems> items;
	
}
// update menu
