package roomservice;

public class MenuItems {
	private double price;
	private String itemName;
	private String prepMethod;
	
	public MenuItems(double price, String itemName, String prepMethod) {
		this.price = price;
		this.itemName = itemName;
		this.prepMethod = prepMethod;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public void setPrep(String prepMethod) {
		this.prepMethod = prepMethod;
	}
	public String getItemName() {
		return itemName;
	}
	public String getPrep() {
		return prepMethod;
	}
	public void printItem() {
		System.out.println("$" + price + "|" + itemName + "| Prepared: " + prepMethod);
	}
}