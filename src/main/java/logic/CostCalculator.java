package logic;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.DbUtil;
import model.Product;

public class CostCalculator {
	private List<Long> products;
	
	public CostCalculator(List<Long> products) {
		this.products = products;
	}
	
	public BigDecimal calculateCost() {
		BigDecimal cost = new BigDecimal(0d);
		if (products != null && !products.isEmpty()) {
			DbUtil db = new DbUtil();
			List<Product> productsList = db.loadProducts();
			Map<Long, BigDecimal> existingProducts = getExistingProductsMap(productsList, products);
			for (Long productId : products) {
				BigDecimal d = existingProducts.getOrDefault(productId, new BigDecimal(0d));
				cost = cost.add(d);
			}
		}
		return cost;
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
}
