package com.bge.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bge.model.beans.TaxCalc;

public class TaxForm {
	private TaxCalc tax;
	private Map<String, String> errors;

	public TaxForm() {
		tax = new TaxCalc();
		errors = new HashMap<String, String>();
	}
	
	public TaxForm(HttpServletRequest request) {
		this();
		
		tax.setName(request.getParameter("name"));
		
		try {
			tax.setChildren( Integer.parseInt(request.getParameter("children")) );
		}
		catch(NumberFormatException e) {
			errors.put("children", "Le nombre d'enfants doit être un nombre.");
		}
		
		tax.setMarried( request.getParameter("married").equals("married") );
		
		try {
			tax.setIncome( Integer.parseInt(request.getParameter("income")) );
		}
		catch(NumberFormatException e) {
			errors.put("income", "Le revenu doit être un nombre entier.");
		}
		
		try {
			tax.setCharges( Integer.parseInt(request.getParameter("charges")) );
		}
		catch(NumberFormatException e) {
			errors.put("children", "Les charges doivent être un nombre entier.");
		}
		
		tax.update();
		
		check();
	}
	
	private void check() {
		if (tax.getName().length() < 3) {
			errors.put("name", "Le nom doit faire 3 caractères minimum.");
		}
		
		if (tax.getChildren() < 0) {
			errors.put("children", "Le nombre d'enfants ne peut pas être négatif");
		}
		
		if (tax.getIncome() <= 0) {
			errors.put("income", "Le revenu ne peut pas être négatif ou nul.");
		}
		
		if (tax.getCharges() < 0) {
			errors.put("charges", "Les charges ne peuvent pas être négatives");
		}
		
		if (tax.getTax() < 0) {
			errors.put("tax", "Erreur lors du calcul de l'impôt.");
		}
	}
	
	public TaxCalc getTax() {
		return tax;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setTax(TaxCalc tax) {
		this.tax = tax;
	}
}
