package logic;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.DbUtil;
import model.Offer;
import model.Product;

public class CostCalculator {
	private DbUtil db = new DbUtil();
	
	private List<Long> products;
	private Map<Long, BigDecimal> existingProducts;
	private Map<Long, Offer> offersMap;
	
	public CostCalculator(List<Long> products) {
		this.products = products;
	}
	
	public BigDecimal calculateCost() {
		BigDecimal cost = new BigDecimal(0d);
		if (products != null && !products.isEmpty()) {
			List<Product> productsList = db.loadProducts();
			existingProducts = getExistingProductsMap(productsList, products);
			for (Long productId : products) {
				BigDecimal d = existingProducts.getOrDefault(productId, new BigDecimal(0d));
				cost = cost.add(d);
			}
		}
		return cost;
	}
	
	public BigDecimal calculateDicount() {
		BigDecimal discount = new BigDecimal(0d);
		List<Offer> offersList = db.loadOffers();
	    if (offersList != null && !offersList.isEmpty() 
	    		&& products != null && !products.isEmpty()) {
	    	offersMap = getOffersByProductsMap(offersList, products);
	    	
	    	// Map is used to store amount of each products to buy
	        Map<Long, Integer> productsCount = new HashMap<>();
	        products.stream().forEach(productId -> {
	        	productsCount.put(productId, productsCount.getOrDefault(productId, 0) + 1);
	        });
	        
	        if (existingProducts == null) {
	        	calculateCost();
	        }
	        
	        for (Map.Entry<Long, Integer> entry : productsCount.entrySet()) {
	        	Offer offer = offersMap.getOrDefault(entry.getKey(), null);
	        	if (offer != null) {
	        		// calculating how many items we need to buy with offer
	        		double itemsToBuyWithOffer = Math.ceil(productsCount.get(entry.getKey()) 
	        				* (double) offer.getItemsToBuy() / (double) offer.getItemsToGet());
	        		// if we need to buy less that we added to shopping cart - calculating discount
	        		if (itemsToBuyWithOffer < productsCount.get(entry.getKey())) {
	        			BigDecimal itemCost = existingProducts.getOrDefault(entry.getKey(), new BigDecimal(0d));
	        			BigDecimal productDiscount = itemCost.multiply(new BigDecimal(productsCount.get(entry.getKey()) - itemsToBuyWithOffer));
	        			discount = discount.add(productDiscount);
	        		}
	        	}
	        }
	    }
	    return discount;
	}
	
	private Map<Long, BigDecimal> getExistingProductsMap(List<Product> productsList, List<Long> products) {
		Map<Long, BigDecimal> map = new HashMap<>();
		if (productsList != null && !productsList.isEmpty()) {
			productsList.stream().forEach(product -> {
				if (products.contains(product.getId())) {
					map.put(product.getId(), product.getCost());
				}
			});
		}
		return map;
	}

	private Map<Long, Offer> getOffersByProductsMap(List<Offer> offersList, List<Long> products) {
		Map<Long, Offer> map = new HashMap<>();
		if (offersList != null && !offersList.isEmpty()) {
			offersList.stream().forEach(offer -> {
				if (products.contains(offer.getProductId())) {
					map.put(offer.getProductId(), offer);
				}
			});
		}
		return map;
	}
}
