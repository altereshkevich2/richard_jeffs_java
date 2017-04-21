package main;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import logic.CostCalculator;

public class Main {
	public static void main(String[] args) {
		stepOne();
		stepTwo();
	}
	
	private static void stepOne() {
		List<Long> products = Arrays.asList(1L, 2L, 1L, 1L);
		CostCalculator calculator = new CostCalculator(products);
		BigDecimal cost = calculator.calculateCost();
		System.out.println("STEP 1: COST = " + cost);
	}

	private static void stepTwo() {
		System.out.println();
		List<Long> products = Arrays.asList(1L, 2L, 1L, 1L, 2L, 2L, 2L);
		CostCalculator calculator = new CostCalculator(products);
		BigDecimal cost = calculator.calculateCost();
		BigDecimal discount = calculator.calculateDicount();
		System.out.println("STEP 2: COST = " + cost + " | DISCOUNT = " + discount + " | FINAL COST = " + (cost.subtract(discount)));
	}
}
