package com.automationpractice.model;

public class Products implements ProductsInterface{

    /* (non-Javadoc)
	 * @see com.automationpractice.model.Prods#hashCode()
	 */
    @Override
	public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + id;
	result = prime * result + ((productName == null) ? 0 : productName.hashCode());
	result = prime * result + quantity;
	return result;
    }

 
    /* (non-Javadoc)
	 * @see com.automationpractice.model.Prods#equals(java.lang.Object)
	 */
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
	if (productName == null) {
	    if (other.productName != null)
		return false;
	} else if (!productName.equals(other.productName))
	    return false;
	if (quantity != other.quantity)
	    return false;
	return true;
    }

    private String productName;
    private int id;
    private int quantity;
    

	/* (non-Javadoc)
	 * @see com.automationpractice.model.Prods#getId()
	 */
	@Override
	public int getId() {
        return id;
    }


	/* (non-Javadoc)
	 * @see com.automationpractice.model.Prods#withId(int)
	 */
	@Override
	public Products withId(int id) {
        this.id = id;
        return this;
    }


	/* (non-Javadoc)
	 * @see com.automationpractice.model.Prods#getQuantity()
	 */
	@Override
	public int getQuantity() {
        return quantity;
    }


	/* (non-Javadoc)
	 * @see com.automationpractice.model.Prods#withQuantity(int)
	 */
	@Override
	public Products withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }



	/* (non-Javadoc)
	 * @see com.automationpractice.model.Prods#getName()
	 */
	@Override
	public String getProductName() {
	return productName;
    }


	/* (non-Javadoc)
	 * @see com.automationpractice.model.Prods#withName(java.lang.String)
	 */
	@Override
	public Products withProductName(String productName) {
	this.productName = productName;
	return this;
    }

}
