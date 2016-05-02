package com.project.sean.androidpos;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.project.sean.androidpos.Database.AndroidPOSDBHelper;
import com.project.sean.androidpos.Database.StockInfo;

import java.math.BigDecimal;

/**
 *
 * Created by Sean on 01/04/2016.
 */
public class AddStockFragment extends Fragment implements View.OnClickListener {
    //Instance of the database
    private AndroidPOSDBHelper dbHelper;
    //Button to scan stock items
    private Button scanBtn;
    //Button to add stock item to the DB
    private Button addBtn;
    //Button to get stock information from the DB
    private Button button_get_stock;
    //Button to update stock item in the DB
    private Button button_stock_update;

    //EditText for user entry
    private EditText editStockID;
    private EditText editStockName;
    private EditText editSalePrice;
    private EditText editStockCost;
    private EditText editStockQuantity;
    private EditText editCategory;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AddStockFragment newInstance(int sectionNumber) {
        AddStockFragment fragment = new AddStockFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public AddStockFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_stock, container, false);

        //Get instance of the DB
        dbHelper = AndroidPOSDBHelper.getInstance(getActivity());

        //Initialise Button
        scanBtn = (Button)rootView.findViewById(R.id.scan_button);
        addBtn = (Button)rootView.findViewById(R.id.button_add);
        button_stock_update = (Button)rootView.findViewById(R.id.button_stock_update);
        button_get_stock = (Button)rootView.findViewById(R.id.button_get_stock);

        //Initialise EditText
        editStockID = (EditText)rootView.findViewById(R.id.editStockID);
        editStockName = (EditText)rootView.findViewById(R.id.editStockName);
        editSalePrice = (EditText)rootView.findViewById(R.id.editSalePrice);
        editStockCost = (EditText)rootView.findViewById(R.id.editStockCost);
        editStockQuantity = (EditText)rootView.findViewById(R.id.editStockQuantity);
        editCategory = (EditText)rootView.findViewById(R.id.editCategory);

        //Set button action listener
        scanBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        button_stock_update.setOnClickListener(this);
        button_get_stock.setOnClickListener(this);


        return rootView;
    }

    public void onClick(View v){
        if(v.getId()==R.id.scan_button) {
            IntentIntegrator.forSupportFragment(this).initiateScan();
        }
        if(v.getId()==R.id.button_add) {
            addData();
        }
        if(v.getId()==R.id.button_get_stock) {
            getStockInfo();
        }
        if(v.getId()==R.id.button_stock_update) {
            updateStockInfo();
        }
    }


    /**
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            if(scanningResult.getContents() == null) {
                Toast toast = Toast.makeText(getActivity(),
                        "Cancelled from fragment!", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                String scanContent = scanningResult.getContents();
                editStockID.setText(scanContent);
                Toast toast = Toast.makeText(getActivity(),
                        "Scan data received!", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(getActivity(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void addData(){
        if(!isEmpty(editStockID) &&
                !isEmpty(editStockName) &&
                !isEmpty(editSalePrice) &&
                !isEmpty(editStockCost) &&
                !isEmpty(editStockQuantity) &&
                !isEmpty(editCategory)) {

            if(!dbHelper.exsists(editStockID.getText().toString())) {

                StockInfo stockInfo = new StockInfo();

                stockInfo.setStockId(editStockID.getText().toString());
                stockInfo.setStockName(editStockName.getText().toString());
                stockInfo.setSalePrice(currencyIn(editSalePrice.getText().toString()));
                stockInfo.setCostPrice(currencyIn(editStockCost.getText().toString()));
                stockInfo.setStockQty(Integer.parseInt(editStockQuantity.getText().toString()));
                stockInfo.setCategory(editCategory.getText().toString());

                boolean isInserted = dbHelper.insertStockData(stockInfo);

                if(isInserted)  {
                    Toast.makeText(getActivity(), "Data inserted successfully!", Toast.LENGTH_LONG).show();
                    editStockID.getText().clear();
                    editStockName.getText().clear();
                    editSalePrice.getText().clear();
                    editStockCost.getText().clear();
                    editStockQuantity.getText().clear();
                    editCategory.getText().clear();
                } else {
                    Toast.makeText(getActivity(), "Error, data not inserted.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(), "Entry already exists.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity(), "All fields must be filled.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Get a selected stock items details and put them into the EditText fields.
     */
    public void getStockInfo() {
        if(!isEmpty(editStockID)) {
            if(dbHelper.exsists(editStockID.getText().toString())) {
                Cursor result = dbHelper.getStockDetails(editStockID.getText().toString());

                editStockID.setText(result.getString(0));
                editStockName.setText(result.getString(1));
                editSalePrice.setText(currencyOut(result.getInt(2)).toString());
                editStockCost.setText(currencyOut(result.getInt(3)).toString());
                editStockQuantity.setText(Integer.toString(result.getInt(4)));
                editCategory.setText(result.getString(5));

                Toast.makeText(getActivity(), "Stock Information details displayed.",
                        Toast.LENGTH_SHORT).show();
                result.close();
            } else {
                Toast.makeText(getActivity(), "Stock ID does not exist.",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Stock ID required.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Update a select stock items information.
     */
    public void updateStockInfo() {
        if(!isEmpty(editStockID) &&
                !isEmpty(editStockName) &&
                !isEmpty(editSalePrice) &&
                !isEmpty(editStockCost) &&
                !isEmpty(editStockQuantity) &&
                !isEmpty(editCategory)) {

            if(dbHelper.exsists(editStockID.getText().toString())) {

                StockInfo stockInfo = new StockInfo();

                stockInfo.setStockId(editStockID.getText().toString());
                stockInfo.setStockName(editStockName.getText().toString());
                stockInfo.setSalePrice(currencyIn(editSalePrice.getText().toString()));
                stockInfo.setCostPrice(currencyIn(editStockCost.getText().toString()));
                stockInfo.setStockQty(Integer.parseInt(editStockQuantity.getText().toString()));
                stockInfo.setCategory(editCategory.getText().toString());

                boolean isUpdated = dbHelper.updateStockInfo(stockInfo);

                if(isUpdated)  {
                    Toast.makeText(getActivity(), "Stock ID: " + stockInfo.getStockId()
                            +" updated successfully!", Toast.LENGTH_LONG).show();
                    editStockID.getText().clear();
                    editStockName.getText().clear();
                    editSalePrice.getText().clear();
                    editStockCost.getText().clear();
                    editStockQuantity.getText().clear();
                    editCategory.getText().clear();
                } else {
                    Toast.makeText(getActivity(), "Error, data not updated.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(), "Stock ID does not exist.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity(), "All fields must be filled.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Checks if an EditText field is empty.
     * @param etText
     * @return true if empty, false if not
     */
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
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

    /**
     * Used to create alert dialogue.
     */
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true); //Cancel after is use
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show(); //Will show the AlertDialog
    }
}
