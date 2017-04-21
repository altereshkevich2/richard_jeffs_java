package database;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Offer;
import model.Product;

/**
 * This class emulates database.
 * 
 * @author a.teresh
 *
 */
public class DbUtil {
	public static final String PRODUCTS_FILE = "products.json";
	public static final String OFFERS_FILE = "offers.json";
	
	private Gson gson = new Gson();

	/**
	 * Loading existing products from database based on JSON file
	 * @return
	 */
	public List<Product> loadProducts() {
		String jsonString = getFileContent(PRODUCTS_FILE);
		if (jsonString != null) {
			try {
				return gson.fromJson(jsonString, new TypeToken<List<Product>>(){}.getType());
			} catch (Exception e) {
				System.out.println("Can not load products list");
			}
		}
		return null;
	}

	/**
	 * Loading existing offers from database based on JSON file
	 * @return
	 */
	public List<Offer> loadOffers() {
		String jsonString = getFileContent(OFFERS_FILE);
		if (jsonString != null) {
			try {
				return gson.fromJson(jsonString, new TypeToken<List<Offer>>(){}.getType());
			} catch (Exception e) {
				System.out.println("Can not load offers list");
			}
		}
		return null;
	}
	
	private String getFileContent(String fileName) {
		try {
			StringBuilder result = new StringBuilder("");
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource(fileName).getFile());
			try (Scanner scanner = new Scanner(file)) {
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					result.append(line).append("\n");
				}
				scanner.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String jsonString = result.toString();
			return jsonString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
