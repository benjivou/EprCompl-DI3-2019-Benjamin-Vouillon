package fr.superesi.superapp;

/**
 * Main data class representing an order at the bar (e.g. 2 sodas at 5€ each
 */
public class Order {
	
	private String product;
	private int quantity;
	private float unitPrice;
	
	/**
	 * @param product
	 * @param quantity
	 * @param unitPrice
	 */
	public Order(String product, int quantity, float unitPrice) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}
	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the unitPrice
	 */
	public float getUnitPrice() {
		return unitPrice;
	}
	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the total price of the order
	 */
	public float getTotalPrice(){
		return	this.unitPrice*this.quantity;
	}

	
}
