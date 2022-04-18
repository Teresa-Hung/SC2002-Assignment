package roomservice;
/**
 * Represents one item on the menu.
 * @author Bach Nguyen
 *
 */
public class MenuItems {
	/**
	 * The price of this item
	 */
	private double price;
	/**
	 * The name of this item
	 */
	private String itemName;
	/**
	 * The preparation method of this item
	 */
	private String prepMethod;
	/**
	 * Creates a new MenuItems with the given price, name and preparation method
	 * @param price This MenuItems' price
	 * @param itemName This MenuItems' name
	 * @param prepMethod This MenuItems' preparation method
	 */
	public MenuItems(double price, String itemName, String prepMethod) {
		this.price = price;
		this.itemName = itemName;
		this.prepMethod = prepMethod;
	}
	/**
	 * Gets the price of this MenuItems
	 * @return This MenuItems' price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * Sets the price of this MenuItems
	 * @param price This MenuItems' new price
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * Sets the name of this MenuItems
	 * @param itemName This MenuItems' new name
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 * Sets the preparation method of this MenuItems
	 * @param prepMethod This MenuItem's new preparation method
	 */
	public void setPrep(String prepMethod) {
		this.prepMethod = prepMethod;
	}
	/**
	 * Gets the name of this item
	 * @return This MenuItems' name
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * Gets the preparation method of this item
	 * @return This MenuItems' preparation method
	 */
	public String getPrep() {
		return prepMethod;
	}
	/**
	 * Print out all elements of this MenuItems
	 */
	public void printItem() {
		System.out.println("$" + price + "|" + itemName + "| Prepared: " + prepMethod);
	}
}