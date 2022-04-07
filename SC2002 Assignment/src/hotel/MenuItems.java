public class MenuItems {
	public MenuItems(double a, String b, String c) {
		this.price = a;
		this.itemName = b;
		this.prepMethod = c;
	}
	public void printItem() {
		System.out.println("$"+this.price+" "+this.itemName+":"+this.prepMethod);
	}
	public double getPrice() {
		return price;
	}
	public String itemName() {
		return itemName;
	}
	public String getPrep() {
		return prepMethod;
	}
	private double price;
	private String itemName;
	private String prepMethod;
}
// have print menu
// make a list of menu items