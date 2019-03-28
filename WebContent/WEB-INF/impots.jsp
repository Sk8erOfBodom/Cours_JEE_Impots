<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Calcul de l'impôt</title>
		
		<link rel="stylesheet" href="css/main.css">
	</head>
	<body>
		<h1>Calcul de l'impôt sur le revenu</h1>
		<c:choose>
			<c:when test="${ !empty errors }">
				<section class="error">
					<h2>Erreur de la simulation</h2>
					<ul>
						<c:forEach items="${ errors }" var="error">
							<li>${ error.value }</li>
						</c:forEach>
					</ul>
				</section>
			</c:when>
			<c:when test="${ !empty tax }">
				<section class="success">
					<h2>Résultat de la simulation</h2>
					<p>
						<h4>Informations données</h4>
						Nom : ${ tax.name }<br>
						${ tax.children } enfant(s)<br>
						<c:choose>
							<c:when test="${ tax.married }">
								Marié(e)
							</c:when>
							<c:otherwise>
								Célibataire
							</c:otherwise>
						</c:choose><br>
						Revenu annuel : ${ tax.income } €<br>
						Charges déductibles : ${ tax.charges } €
					</p>
					<p>
						<h4>Impôt calculé</h4>
						D'après les informations que vous avez fournies, voici l'impôt qui a été calculé.<br>
						Revenu imposable : ${ tax.ri }€<br>
						Nombre de parts : ${ tax.parts }<br>
						Montant de l'impôt : ${ tax.tax }€
					</p>
				</section>
			</c:when>
		</c:choose>
		<section>
			<form method="POST" action="">
				<h2>Calculer un nouvel impôt</h2>
				<fieldset>
					<legend>Situation familiale</legend>
					<div class="row">
						<label for="f_name">Nom : </label>
						<input type="text" id="f_name" name="name" value="${ tax.name }" placeholder="Entrez votre nom" required>
					</div>
					<div class="row">
						<label for="f_children">Nombre d'enfants à charge : </label>
						<input type="number" id="f_children" name="children" value="${ empty tax.children ? 0 : tax.children }" min="0" required>
					</div>
					<div>
						<input type="radio" id="f_married" name="married" value="married" <c:if test="${ tax.married }">checked</c:if>>
						<label for="f_married"> Marié(e)</label><br>
						<input type="radio" id="f_single" name="married" value="single" <c:if test="${ !tax.married }">checked</c:if>>
						<label for="f_single"> Célibataire</label>
					</div>
				</fieldset>
				<fieldset>
					<legend>Revenus</legend>
					<div class="row">
						<label for="f_income">Revenu annuel : </label>
						<span class="euro_input">
							<input type="number" id="f_income" name="income" min="0" value="${ empty tax.income ? 0 : tax.income }" required>
							<i>€</i>
						</span>
					</div>
					<div class="row">
						<label for="f_charges">Charges déductibles : </label>
						<span class="euro_input">
							<input type="number" id="f_charges" name="charges" min="0" value="${ empty tax.charges ? 0 : tax.charges }" placeholder="0" required>
							<i>€</i>
						</span>
					</div>
				</fieldset>
				<input type="submit" value="Valider">
			</form>
		</section>
		<section>
			<h2>Historique des simulations</h2>
			<p>
				<a href="impots.csv" target="_blank">Fichier CSV</a> contenant l'historique des simulations.
			</p>
		</section>
	</body>
</html>