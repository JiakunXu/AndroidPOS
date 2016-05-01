package com.project.sean.androidpos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.project.sean.androidpos.cart.ShoppingCart;
import com.project.sean.androidpos.cart.ShoppingCartItem;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Sean on 01/05/2016.
 */
public class PurchasedItemsActivity extends AppCompatActivity implements View.OnClickListener {

    private ShoppingCart shoppingCart;
    private ArrayList<ShoppingCartItem> shoppingCartItems;

    private ListView lvCheckoutItems;

    private TextView tvPurchasedTotal;

    private CheckoutAdapter mAdapter;

    private Button btPurchasedReciept;
    private Button btPurchasedCheckout;

    private TextView tvPaymentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased_items);

        setTitle("Purchased Items: ");

        tvPaymentType = (TextView) findViewById(R.id.tvPaymentType);
        tvPaymentType.setText(getIntent().getStringExtra("Payment"));

        shoppingCart = (ShoppingCart) getIntent().getSerializableExtra("ArrayList");

        lvCheckoutItems = (ListView) findViewById(R.id.lvCheckoutItems);
        LayoutInflater layoutInflater = getLayoutInflater();

        lvCheckoutItems.addHeaderView(layoutInflater.inflate(R.layout.cart_header, lvCheckoutItems, false));


        //Create the array adapter for the shopping cart
        mAdapter = new CheckoutAdapter(this, R.layout.adapter_cart_item, shoppingCart.getCartItems());

        lvCheckoutItems.setAdapter(mAdapter);

        tvPurchasedTotal = (TextView) findViewById(R.id.tvPurchasedTotal);
        tvPurchasedTotal.setText(currencyOut(shoppingCart.getSubtotal()).toString());

        btPurchasedReciept = (Button) findViewById(R.id.btPurchasedReciept);
        btPurchasedCheckout = (Button) findViewById(R.id.btPurchasedCheckout);

        btPurchasedReciept.setOnClickListener(this);
        btPurchasedCheckout.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btPurchasedReciept: {
                //Add method to print a receipt
                break;
            }
            case R.id.btPurchasedCheckout: {
                Intent intent = new Intent(PurchasedItemsActivity.this, CheckoutActivity.class);
                startActivity(intent);
                finish();
                break;
            }
        }
    }

    /**
     * Custom Adapter class to handle the ListView for the shopping cart.
     */
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

                holder.tvCartItemName = (TextView) convertView.findViewById(R.id.tvCartItemName);
                holder.tvCartItemQuantity = (TextView) convertView.findViewById(R.id.tvCartItemQuantity);
                holder.tvCartItemPrice = (TextView) convertView.findViewById(R.id.tvCartItemPrice);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

//            ShoppingCartItem shoppingCartItem = stockList.get(position);
            ShoppingCartItem shoppingCartItem = shoppingCart.getCartItems().get(position);

            BigDecimal itemPrice = currencyOut(shoppingCartItem.getTotal());
//            itemPrice = itemPrice.multiply(new BigDecimal(shoppingCartItem.getQuantity()));

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
