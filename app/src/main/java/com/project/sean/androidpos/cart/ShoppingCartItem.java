package com.project.sean.androidpos.cart;

/**
 * Created by Sean on 30/04/2016.
 */
public class ShoppingCartItem
{

    private CartItem cartItem;
    private int quantity;


    //-----------------------------------------------------------------------

    /**
     * Create a new instance of ShoppingCartItem.
     * @param cartItem
     */
    public ShoppingCartItem(CartItem cartItem)
    {
        this.cartItem = cartItem;
        quantity = 1;
    }//End of constructor ShoppingCartItem


    //-----------------------------------------------------------------------

    /**
     * Returns the current coffee selected.
     * @return coffee - coffee product
     */
    public CartItem getCartItem()
    {
        return cartItem;
    }//End of method getCoffee


    //-----------------------------------------------------------------------


    /**
     * Returns the total quantity of a coffee product.
     * @return quantity - total quantity of coffee
     */
    public int getQuantity()
    {
        return quantity;
    }//End of method getQuantity


    //-----------------------------------------------------------------------

    /**
     * Sets the quantity of coffee.
     * @param quantity - quantity of coffee
     */
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }//End of method setQuantity


    //-----------------------------------------------------------------------

    /**
     * Increases the quantity of coffee by one.
     */
    public void increaseQuantity()
    {
        quantity++;
    }//End of method increaseQuantity


    //-----------------------------------------------------------------------

    /**
     * Decreases the quantity of coffee by one.
     */
    public void decreaseQuantity()
    {
        quantity--;
    }//End of method decreaseQuantity


    //-----------------------------------------------------------------------

    /**
     * Calculates the total cost of the current coffee product.
     * @return amount - total cost of the coffee product
     */
    public int getTotal()
    {
        int amount = 0;
        amount = (this.getQuantity() * cartItem.getSalePrice());
        return amount;
    }//End of method getTotal


}//End of class ShoppingCartItem