package com.bge.model.dao;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bge.model.beans.TaxCalc;
import com.bge.model.exceptions.TaxException;

public class TaxDAO {
	private String filename;

	public TaxDAO(String filename) {
		this.filename = filename;
	}

	public void save(TaxCalc tax) throws TaxException {
		try (FileWriter fw = new FileWriter(filename, true)) {
			if (isEmpty()) {
				fw.write("Date de la simulation;Nom;Statut marital;Nombre d'enfants;Revenus;Charges déductibles;Revenu imposable;Parts;Montant de l'impôt\n");
			}

			SimpleDateFormat date = new SimpleDateFormat("YYYY/MM/dd hh:mm:ss");

			fw.write(date.format(new Date()) + ";");
			fw.write(tax.getName() + ";");
			fw.write((tax.isMarried() ? "M" : "C") + ";");
			fw.write(tax.getChildren() + ";");
			fw.write(tax.getIncome() + ";");
			fw.write(tax.getCharges() + ";");
			fw.write(tax.getRi() + ";");
			fw.write(tax.getParts() + ";");
			fw.write(tax.getTax() + "\n");

			fw.flush();
		} catch (IOException e) {
			throw new TaxException("Erreur pendant l'écriture dans le csv.");
		}
	}

	public boolean isEmpty() throws TaxException {
		try (FileReader fr = new FileReader(filename)) {
			return fr.read() == -1;
		} catch (IOException e) {
			throw new TaxException("Erreur pendant la lecture du csv.");
		}
	}
}
