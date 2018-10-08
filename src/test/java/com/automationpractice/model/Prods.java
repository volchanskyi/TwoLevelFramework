package com.automationpractice.model;

public interface Prods {

	int hashCode();

	boolean equals(Object obj);

	int getId();

	Prods withId(int id);

	int getQuantity();

	Prods withQuantity(int quantity);

	String getProductName();

	Prods withProductName(String productName);

}