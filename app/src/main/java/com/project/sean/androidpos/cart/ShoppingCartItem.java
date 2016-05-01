package com.project.sean.androidpos.cart;

import java.io.Serializable;

/**
 * Created by Sean on 30/04/2016.
 */
public class ShoppingCartItem implements Serializable
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
     * Returns the current stock item selected.
     * @return cartItem - stock item
     */
    public CartItem getCartItem()
    {
        return cartItem;
    }//End of method getCartItem


    //-----------------------------------------------------------------------


    /**
     * Returns the total quantity of a stock item.
     * @return quantity - total quantity of stock item
     */
    public int getQuantity()
    {
        return quantity;
    }//End of method getQuantity


    //-----------------------------------------------------------------------

    /**
     * Sets the quantity of stock item.
     * @param quantity - quantity of stock item
     */
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }//End of method setQuantity


    //-----------------------------------------------------------------------

    /**
     * Increases the quantity of stock item by one.
     */
    public void increaseQuantity()
    {
        quantity++;
    }//End of method increaseQuantity


    //-----------------------------------------------------------------------

    /**
     * Decreases the quantity of stock item by one.
     */
    public void decreaseQuantity()
    {
        quantity--;
    }//End of method decreaseQuantity


    //-----------------------------------------------------------------------

    /**
     * Calculates the total cost of the current stock item.
     * @return amount - total cost of the stock item
     */
    public int getTotal()
    {
        int amount = 0;
        amount = (this.getQuantity() * cartItem.getSalePrice());
        return amount;
    }//End of method getTotal


}//End of class ShoppingCartItem