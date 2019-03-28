package com.bge.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bge.forms.TaxForm;
import com.bge.model.beans.TaxCalc;
import com.bge.model.dao.TaxDAO;
import com.bge.model.exceptions.TaxException;

/**
 * Servlet implementation class Index
 */
@WebServlet("")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CSV_URL = "eclipse-workspace/JEE_Impots/WebContent/impots.csv";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getServletContext().getRequestDispatcher("/WEB-INF/impots.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TaxForm formCheck = new TaxForm(request);
		
		request.setAttribute("tax", formCheck.getTax());
		
		Map<String, String> errors = formCheck.getErrors();
		
		if (errors.size() == 0)
		{
			try {
				TaxDAO taxDAO = new TaxDAO(CSV_URL);
				taxDAO.save(formCheck.getTax());
			}
			catch (TaxException e) {
				errors.put("file", e.getMessage());
			}
		}

		request.setAttribute("errors", errors);
		
		doGet(request, response);
	}

}
