package com.project.sean.androidpos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.sean.androidpos.Database.AndroidPOSDBHelper;
import com.project.sean.androidpos.cart.ShoppingCart;

/**
 * Created by Sean on 05/05/2016.
 */
public class ReturnItemActivity extends AppCompatActivity {
    //Instance of the database
    private AndroidPOSDBHelper dbHelper;

    //EditText
    private EditText editReturnSaleID;

    //ListView
    private ListView lvReturnItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_item);

        //Set the title
        setTitle(getString(R.string.return_item_activity_title));

        //Get instance of the DB
        dbHelper = AndroidPOSDBHelper.getInstance(this);

        editReturnSaleID = (EditText)findViewById(R.id.editReturnSaleID);

        lvReturnItem = (ListView) findViewById(R.id.lvReturnItem);
        //Create an adapter class
        //Set the adapter
        //Add a context menu to the list view


    }
}
