package com.project.sean.androidpos;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.project.sean.androidpos.Database.AndroidPOSDBHelper;
import com.project.sean.androidpos.cart.CartItem;
import com.project.sean.androidpos.cart.ShoppingCart;
import com.project.sean.androidpos.cart.ShoppingCartItem;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * This activity deals with the buying of goods from the shop.
 * Created by Sean on 29/04/2016.
 */
public class CheckoutActivity extends AppCompatActivity {
    //Instance of the database
    private AndroidPOSDBHelper dbHelper;

    private CheckoutAdapter mAdapter;

    private ShoppingCart shoppingCart = new ShoppingCart();

    private ArrayList<ShoppingCartItem> cartItemList;

    private ListView lvCheckoutItems;
    //EditText to enter stockID ID
    private EditText editCheckoutStockId;
    //Button to scan in an item of stock
    private Button btCheckoutScan;
    //Button for cash payment
    private Button bCash;
    //Button for card payment
    private Button bCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //Set the title
        setTitle(getString(R.string.checkout_activity_title));

        //Get instance of the DB
        dbHelper = AndroidPOSDBHelper.getInstance(this);

        lvCheckoutItems = (ListView) findViewById(R.id.lvCheckoutItems);
        LayoutInflater layoutInflater = getLayoutInflater();

        lvCheckoutItems.addHeaderView(layoutInflater.inflate(R.layout.cart_header, lvCheckoutItems, false));

//        //Inflates the header for the ListView
//        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.cart_header,
//                lvCheckoutItems, false);
//
//        //Adds the header to the ListView
//        lvCheckoutItems.addHeaderView(headerView);

        CartItem item = new CartItem("Bread", 100);
        shoppingCart.addItem(item);

        //Gets a list of the items in the cart
        cartItemList = new ArrayList<>(shoppingCart.getCartItems());

        //Create the array adapter for the shopping cart
        mAdapter = new CheckoutAdapter(this, R.layout.adapter_cart_item, cartItemList);

        //Add the adapter to the ListView
        lvCheckoutItems.setAdapter(mAdapter);

        btCheckoutScan = (Button) findViewById(R.id.btCheckoutScan);
        bCash = (Button) findViewById(R.id.bCash);
        bCard = (Button) findViewById(R.id.bCard);

    }

    public class CheckoutAdapter extends ArrayAdapter<ShoppingCartItem> {

        private ArrayList<ShoppingCartItem> stockList;

        public CheckoutAdapter(Context context, int resource, ArrayList<ShoppingCartItem> stockList) {
            super(context, resource, stockList);
            this.stockList = new ArrayList<ShoppingCartItem>();
            this.stockList.addAll(stockList);
        }

        private class ViewHolder {
            TextView tvCartItemName;
            TextView tvCartItemQuantity;
            TextView tvCartItemPrice;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.adapter_cart_item, null);

                holder = new ViewHolder();
                ;
                holder.tvCartItemName = (TextView) convertView.findViewById(R.id.tvCartItemName);
                holder.tvCartItemQuantity = (TextView) convertView.findViewById(R.id.tvCartItemQuantity);
                holder.tvCartItemPrice = (TextView) convertView.findViewById(R.id.tvCartItemPrice);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ShoppingCartItem shoppingCartItem = stockList.get(position);

            BigDecimal itemPrice = currencyOut(shoppingCartItem.getTotal());

            holder.tvCartItemName.setText(shoppingCartItem.getCartItem().getStockName());
            holder.tvCartItemQuantity.setText(Integer.toString(shoppingCartItem.getQuantity()));
            holder.tvCartItemPrice.setText(itemPrice.toString());

            return convertView;
        }

    }

    /**
     * Converts the currency from pounds into pence.
     * @param currency - pound
     * @return currencyInt - pence
     */
    public int currencyIn(String currency) {
        BigDecimal currencyBD = new BigDecimal(currency);
        currencyBD = currencyBD.multiply(new BigDecimal("100"));
        int currencyInt = currencyBD.intValueExact();
        return currencyInt;
    }

    /**
     * Converts the currency from pence into pounds.
     * @param currency
     * @return
     */
    public BigDecimal currencyOut(int currency) {
        BigDecimal currencyBD = new BigDecimal(currency);
        currencyBD = currencyBD.divide(new BigDecimal("100"));
        currencyBD = currencyBD.setScale(2);
        return currencyBD;
    }
}
