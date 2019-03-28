package com.bge.model.beans;

public class TaxCalc {
	private static final Double[][] LEVELS = {
			{ 0., 0., 0. },
			{ 9807., 0.14, 1_372.98 },
			{ 27_086., 0.3, 5_706.74 },
			{ 72_617., 0.41, 13_694.61 },
			{ 153_783., 0.45, 19_845.93 } };

	private String name;
	private int children;
	private boolean married;
	private int income;
	private int charges;

	private double parts;
	private double ri;
	private int tax;
	
	public TaxCalc() {
	}

	public TaxCalc(String name, int children, boolean married, int income, int charges) {
		this.name = capitalize(name);
		this.children = children;
		this.married = married;
		this.income = income;
		this.charges = charges;

		this.parts = calculateParts();
		this.ri = calculateRi();
		this.tax = calculateTax();
	}

	private String capitalize(String name) {
		String[] temp = name.split(" ");

		for (int i = 0; i < temp.length; i++) {
			temp[i] = temp[i].substring(0, 1).toUpperCase() + temp[i].substring(1).toLowerCase();
		}

		return String.join(" ", temp);
	}

	private double calculateParts() {
		return (married ? 1 : 0) + children * 0.5 + 1;
	}

	private double calculateRi() {
		return .8 * income / parts;
	}

	private int calculateTax() {
		if (ri < 0) {
			return -1;
		}
		
		double result;
		int i;
		
		for(i = 0; i < LEVELS.length; i++) {
			if (ri < LEVELS[i][0]) {
				i--;
				break;
			}
		}
		
		result = income * LEVELS[i][1] - LEVELS[i][2] * parts;
		
		if (result > 0) {
			result -= charges;
		}

		return (int) result;
	}
	
	public void update() {
		this.parts = calculateParts();
		this.ri = calculateRi();
		this.tax = calculateTax();
	}

	public String getName() {
		return name;
	}

	public int getChildren() {
		return children;
	}

	public boolean isMarried() {
		return married;
	}

	public int getIncome() {
		return income;
	}

	public int getCharges() {
		return charges;
	}

	public double getParts() {
		return parts;
	}

	public double getRi() {
		return Math.round(ri * 100) / 100.0;
	}

	public int getTax() {
		return tax;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setChildren(int children) {
		this.children = children;
	}

	public void setMarried(boolean married) {
		this.married = married;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	public void setCharges(int charges) {
		this.charges = charges;
	}
}
