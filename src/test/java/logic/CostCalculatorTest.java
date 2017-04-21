package logic;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class CostCalculatorTest {
	private List<Long> products;
	private CostCalculator calculator;
	
	@Test
	public void testCalculateCost() {
		products = Arrays.asList(1L, 2L, 1L, 1L);
		calculator = new CostCalculator(products);
		BigDecimal cost = calculator.calculateCost();
		assertEquals(cost.doubleValue(), 1.35, 0);
	}

	@Test
	public void testCalculateDicount() {
		products = Arrays.asList(1L, 2L, 1L, 1L, 2L, 2L, 2L);
		calculator = new CostCalculator(products);
		BigDecimal cost = calculator.calculateDicount();
		assertEquals(cost.doubleValue(), .85, 0);
	}
}
