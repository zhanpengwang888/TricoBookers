package com.zhanpengwang.tricobookers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class BuyListActivity extends AppCompatActivity {

    private DBhelper mDBHelper;
    private ListView listView;
    private SellingListAdapter adapter;
    private SellingList sellingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_list);
        mDBHelper = new DBhelper(getBaseContext());
        Intent intent = getIntent();
        // get the ISBN
        String requestedISBN = intent.getStringExtra("ISBN");
        sellingList = new SellingList();
        ArrayList<Sellers> listOfSellers = mDBHelper.getAListOfSellers(requestedISBN);
        sellingList.addAll(listOfSellers);
        listView = findViewById(R.id.selling_list);
        adapter = new SellingListAdapter(getBaseContext(), sellingList, mDBHelper);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDBHelper.close();
    }
}
