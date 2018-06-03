package com.automationpractice.model;

public class Products {
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + id;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + quantity;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Products other = (Products) obj;
	if (id != other.id)
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (quantity != other.quantity)
	    return false;
	return true;
    }

    private String name;
    private int id;
    private int quantity;
    
    public int getId() {
        return id;
    }

    public Products withId(int id) {
        this.id = id;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public Products withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }


    public String getName() {
	return name;
    }

    public Products withName(String name) {
	this.name = name;
	return this;
    }

}
