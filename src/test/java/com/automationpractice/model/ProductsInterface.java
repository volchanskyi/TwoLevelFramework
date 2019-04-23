package com.automationpractice.model;

interface ProductsInterface {

	int hashCode();

	boolean equals(Object obj);

	int getId();

	ProductsInterface withId(int id);

	int getQuantity();

	ProductsInterface withQuantity(int quantity);

	String getProductName();

	ProductsInterface withProductName(String productName);

}