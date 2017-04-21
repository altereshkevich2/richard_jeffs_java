package model;

public class Offer {
	private Long productId;
	private int itemsToBuy;
	private int itemsToGet;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public int getItemsToBuy() {
		return itemsToBuy;
	}

	public void setItemsToBuy(int itemsToBuy) {
		this.itemsToBuy = itemsToBuy;
	}

	public int getItemsToGet() {
		return itemsToGet;
	}

	public void setItemsToGet(int itemsToGet) {
		this.itemsToGet = itemsToGet;
	}

}
