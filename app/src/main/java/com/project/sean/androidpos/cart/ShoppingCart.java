package com.project.sean.androidpos.cart;

/**
 * Created by Sean on 30/04/2016.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the list of items added to the users shopping cart, as well as the
 * total number of items and cost.
 * @author Sean Sexton
 * @studentID 13023975
 */
public class ShoppingCart implements Serializable
{

    private ArrayList<ShoppingCartItem> cartItemList;
    private int numberOfItems;
    private int total;


    //-----------------------------------------------------------------------


    /**
     * Creates a new instance of ShoppingCart
     */
    public ShoppingCart()
    {
        cartItemList = new ArrayList<ShoppingCartItem>();
        numberOfItems = 0;
        total = 0;
    }//End of constructor


    //-----------------------------------------------------------------------


    /**
     * Adds a ShoppingCartItem to the ShoppingCart list. A ShoppingCartItem is
     * the item the customer wishes to purchase. If the item being
     * added is already in the list then it will instead increase the quantity
     * by one.
     * @param cartItem - the item being purchased
     */
    public void addItem(CartItem cartItem)
    {
        boolean newItem = true;

        for (ShoppingCartItem cartItems : cartItemList)
        {
            if (cartItems.getCartItem().getStockId().equals(cartItem.getStockId()))
            {
                newItem = false;
                cartItems.increaseQuantity();
            }
        }

        if (newItem)
        {
            ShoppingCartItem cartItems = new ShoppingCartItem(cartItem);
            cartItemList.add(cartItems);
        }
    }//End of method addItem


    //-----------------------------------------------------------------------


    /**
     * Remove the selected ShoppingCartItem from the ShoppingCart.
     * @param cartItem
     */
    public void removeItem(ShoppingCartItem cartItem)
    {
        cartItemList.remove(cartItem);
    }//End of method removeItem


    //-----------------------------------------------------------------------


    /**
     * Increase quantity of item in the shopping cart.
     */
    public void increaseCartQuantity(ShoppingCartItem cartItem)
    {
        cartItem.increaseQuantity();
    }//End of method increaseCartQuantity


    //-----------------------------------------------------------------------


    /**
     * Decrease quantity of item in the shopping cart. If quantity of an item
     * is zero or less than it will remove that item completely from the shopping
     * cart.
     */
    public void decreaseCartQuantity(ShoppingCartItem cartItem)
    {
        if (cartItem.getQuantity() != 1)
        {
            cartItem.decreaseQuantity();
        }
        else
        {
            cartItemList.remove(cartItem);
        }
    }//End of method decreaseCartQuantity


    //-----------------------------------------------------------------------


    /**
     * Returns the list of ShoppingCartItems.
     * @return cartItems
     */
    public ArrayList<ShoppingCartItem> getCartItems()
    {
        return cartItemList;
    }//End of method getCartItems


//-----------------------------------------------------------------------


    /**
     * Returns the total number of item quantities for each item in the shopping
     * cart.
     * @return numberOfItems - number of items in the cart
     */
    public int getNumberOfItems()
    {
        numberOfItems = 0;

        for (ShoppingCartItem cartItem : cartItemList)
        {
            numberOfItems += cartItem.getQuantity();
        }

        return numberOfItems;
    }//End of method getNumberOfItems


    //-----------------------------------------------------------------------


    /**
     * Calculates the sub-total of a shopping cart item.
     * @return subtotalAmount - cost of items times there quantity
     */
    public int getSubtotal()
    {
        int subtotalAmount = 0;

        for (ShoppingCartItem cartItem : cartItemList)
        {
            CartItem cartItems = (CartItem) cartItem.getCartItem();
            subtotalAmount += (cartItem.getQuantity() * cartItems.getSalePrice());
        }

        return subtotalAmount;
    }//End of method getSubtotal


    //-----------------------------------------------------------------------


    /**
     * Returns the total cost of the current shopping cart.
     * @return total
     */
    public int getTotal()
    {
        return total;
    }//End of method getTotal


    //-----------------------------------------------------------------------


    /**
     * Clears the current shopping cart of all items.
     */
    public void clearCart()
    {
        cartItemList.clear();
        numberOfItems = 0;
        total = 0;
    }//End of method clearCart


}//End of class ShoppingCart
