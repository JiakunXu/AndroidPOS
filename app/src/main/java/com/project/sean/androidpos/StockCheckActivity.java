package com.project.sean.androidpos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.project.sean.androidpos.Database.AndroidPOSDBHelper;
import com.project.sean.androidpos.Database.StockInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sean on 29/04/2016.
 */
public class StockCheckActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    //Instance of the database
    private AndroidPOSDBHelper dbHelper;

    private StockCheckAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<StockInfo> stockCheckList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_check);

        //Set the title
        setTitle(getString(R.string.stock_check_activity_title));

        //Get instance of the DB
        dbHelper = AndroidPOSDBHelper.getInstance(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvStockCheck);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        stockCheckList = new ArrayList<StockInfo>();
        stockCheckList.addAll(dbHelper.getAllStockInfo());

        mAdapter = new StockCheckAdapter(this, stockCheckList);
        mRecyclerView.setAdapter(mAdapter);

    }

    /**
     * Create the unique menu bar for ViewStockFragment.
     * @param menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_stock, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    /**
     * On query submit do nothing.
     * @param query
     * @return
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<StockInfo> filteredStockList = filter(stockCheckList, query);
        mAdapter.animateTo(filteredStockList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }

    /**
     * Produce a filtered list using the list of stock information and the query
     * entered into the search view.
     * @param stockCheck
     * @param query
     * @return
     */
    public List<StockInfo> filter(List<StockInfo> stockCheck, String query) {
        query = query.toLowerCase();

        final List<StockInfo> filteredModelList = new ArrayList<>();
        for (StockInfo stockInfo : stockCheck) {
            final String text = stockInfo.getStockName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(stockInfo);
            }
        }
        return filteredModelList;
    }

    /**
     * Creates the view used to populate each row of the RecyclerView.
     */
    public class StockCheckHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        //Add the variables here
        private final TextView tvSCStockID;
        private final TextView tvSCStockName;
        private final TextView tvSCCategory;
        private final TextView tvSCSalePrice;
        private final TextView tvSCStockQty;

        public StockCheckHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            //Add the UI elements here
            tvSCStockID = (TextView) itemView.findViewById(R.id.tvSCStockID);
            tvSCStockName = (TextView) itemView.findViewById(R.id.tvSCStockName);
            tvSCCategory = (TextView) itemView.findViewById(R.id.tvSCCategory);
            tvSCSalePrice = (TextView) itemView.findViewById(R.id.tvSCSalePrice);
            tvSCStockQty = (TextView) itemView.findViewById(R.id.tvSCStockQty);
        }

        public void bind(StockInfo model) {
            //Sale Price
            int salePrice = model.getSalePrice();
            BigDecimal salePriceBD = currencyOut(salePrice);

            //Bind to the UI elements here
            tvSCStockID.setText(model.getStockId());
            tvSCStockName.setText(model.getStockName());
            tvSCCategory.setText(model.getCategory());
            tvSCSalePrice.setText(salePriceBD.toString());
            tvSCStockQty.setText(Integer.toString(model.getStockQty()));
        }

        @Override
        public void onClick(View v) {
            Log.d("Toast...", tvSCStockID.getText().toString() + tvSCStockName.getText().toString());
        }
    }

    /**
     * StockCheckAdapter inner class to create the RecyclerView
     */
    public class StockCheckAdapter extends RecyclerView.Adapter<StockCheckHolder> {

        private final LayoutInflater mInflater;
        private final List<StockInfo> stockCheckList;

        public StockCheckAdapter(Context context, List<StockInfo> models) {
            mInflater = LayoutInflater.from(context);
            stockCheckList = new ArrayList<>(models);
        }

        @Override
        public StockCheckHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View itemView = mInflater.inflate(R.layout.listview_stock_check, parent, false);
            return new StockCheckHolder(itemView);
        }

        @Override
        public void onBindViewHolder(StockCheckHolder holder, int position) {
            final StockInfo stockInfo = stockCheckList.get(position);
            holder.bind(stockInfo);
        }

        /**
         * Get the current size of the stockCheckList.
         * @return
         */
        @Override
        public int getItemCount() {
            return stockCheckList.size();
        }

        public void animateTo(List<StockInfo> models) {
            applyAndAnimateRemovals(models);
            applyAndAnimateAdditions(models);
            applyAndAnimateMovedItems(models);
        }

        /**
         * Adjust the list when an item is removed.
         * @param newModels
         */
        private void applyAndAnimateRemovals(List<StockInfo> newModels) {
            for (int i = stockCheckList.size() - 1; i >= 0; i--) {
                final StockInfo stockInfo = stockCheckList.get(i);
                if (!newModels.contains(stockInfo)) {
                    removeItem(i);
                }
            }
        }

        private void applyAndAnimateAdditions(List<StockInfo> newModels) {
            for (int i = 0, count = newModels.size(); i < count; i++) {
                final StockInfo stockInfo = newModels.get(i);
                if (!stockCheckList.contains(stockInfo)) {
                    addItem(i, stockInfo);
                }
            }
        }

        private void applyAndAnimateMovedItems(List<StockInfo> newModels) {
            for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
                final StockInfo stockInfo = newModels.get(toPosition);
                final int fromPosition = stockCheckList.indexOf(stockInfo);
                if (fromPosition >= 0 && fromPosition != toPosition) {
                    moveItem(fromPosition, toPosition);
                }
            }
        }

        /**
         * Remove an item from the stock info list and notify the adapter of the
         * change.
         * @param position
         * @return
         */
        public StockInfo removeItem(int position) {
            final StockInfo stockInfo = stockCheckList.remove(position);
            notifyItemRemoved(position);
            return stockInfo;
        }

        /**
         * Add an item to the stock info list and notify the adapter of the
         * change.
         * @param position
         * @param stockInfo
         */
        public void addItem(int position, StockInfo stockInfo) {
            stockCheckList.add(position, stockInfo);
            notifyItemInserted(position);
        }

        /**
         * Move an item in the stock info list and notify the adapter of the
         * change.
         * @param fromPosition
         * @param toPosition
         */
        public void moveItem(int fromPosition, int toPosition) {
            final StockInfo stockInfo = stockCheckList.remove(fromPosition);
            stockCheckList.add(toPosition, stockInfo);
            notifyItemMoved(fromPosition, toPosition);
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
